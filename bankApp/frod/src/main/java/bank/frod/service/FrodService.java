package bank.frod.service;

import bank.data.model.bank.card.Card;
import bank.data.model.bank.history.BlockReason;
import bank.data.model.bank.history.History;
import bank.data.model.bank.history.OperationType;
import bank.data.repo.CardRepository;
import bank.data.repo.HistoryRepository;
import bank.frod.common.exceptions.FrodOperationException;
import bank.history.service.HistoryService;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

/**
 * Будем следить за последними операциями с помощью сервиса истории операций
 * Блокировки:
 * 1) Если время отличается на десять секунд, то блочим
 * 2) Если сумма больше ста тысяч блочим
 * 3) завести каунтер по лимитам - эта будет мапа, которая будет чиститься каждую минуту джобой
 */

@Service
public class FrodService {

    private final HistoryRepository historyRepository;
    private final HistoryService historyService;
    private final CardRepository cardRepository;
    private static final String BAD_SUSPICIOUS_MESSAGE = "you.have.suspicious.activity.card.is.block";
    private static final Integer MAX_OPERATION_PER_TIME_COUNTER = 7;
    private static final Integer TEN_SECONDS_FROD_VALUE = 10;
    @Getter
    private Map<Card, List<LocalDateTime>> operationPerTimeCounter = new HashMap<>();

    public FrodService(HistoryRepository historyRepository, HistoryService historyService, CardRepository cardRepository) {
        this.historyRepository = historyRepository;
        this.historyService = historyService;
        this.cardRepository = cardRepository;
    }

    /**
     * @param cardSender Карта с которой проводится операция перевода
     * @param money      сумма переворда
     * @return Если после последней операции прошло меньше десяти секунд и пользователь пытается снова сделать перевод,
     * то считаем, что операция подозрительная и блокируем его.
     */
    public boolean tenSecondsFrodBlock(Card cardSender, String cardRecipientNumber, String money, OperationType operationType) {
        History lastOperationHistory = historyRepository.findFirstByIsSuccessAndCard_CardIdOrderByHistoryIdDesc(true, cardSender.getCardId());
        if (Objects.isNull(lastOperationHistory)) {
            return false;
        }
        if (compareTime(lastOperationHistory.getOperationDate())) {
            cardSender.setBlock(true);
            cardRepository.save(cardSender);
            historyService.saveFrodHistoryOperation(cardSender, cardRecipientNumber, BAD_SUSPICIOUS_MESSAGE, money, BlockReason.TEN_SECOND_FROD_BLOCK, operationType);
            throw new FrodOperationException(BAD_SUSPICIOUS_MESSAGE + " " + BlockReason.TEN_SECOND_FROD_BLOCK);
        }
        return cardSender.isBlock();
    }

    /**
     * @param cardSender Карта с которой проводится операция перевода
     * @param money      сумма переворда
     * @return Если пользователь в течении пяти минут делает больше операций, чем MAX_OPERATION_PER_TIME_COUNTER,
     * то считаем, что операции подозрительные и блокируем его. Время задает джоба, которая каждые пять минуты обнуляет счетчик
     * операций по карте
     * @see .job.FrodSchedule dropOperationCounterEveryFiveMinute()
     */
    public boolean manyOperationPerTimeBlock(Card cardSender, String cardRecipientNumber, String money, OperationType operationType) {
        if (Objects.isNull(operationPerTimeCounter.get(cardSender))) {
            List<LocalDateTime> operationsPerTime = new ArrayList<>();
            operationPerTimeCounter.put(cardSender, operationsPerTime);
        }
        if (operationPerTimeCounter.get(cardSender).size() >= MAX_OPERATION_PER_TIME_COUNTER) {
            cardSender.setBlock(true);
            cardRepository.save(cardSender);
            historyService.saveFrodHistoryOperation(cardSender, cardRecipientNumber, BAD_SUSPICIOUS_MESSAGE, money, BlockReason.MANY_OPERATION_PER_TIME_FROD_BLOCK, operationType);
            throw new FrodOperationException(BAD_SUSPICIOUS_MESSAGE + " " + BlockReason.TEN_SECOND_FROD_BLOCK);
        } else {
            operationPerTimeCounter.get(cardSender).add(LocalDateTime.now());
        }
        return cardSender.isBlock();
    }


    public boolean checkForAllFrod(Card cardSender, String cardRecipientNumber, String money, OperationType operationType) {
        this.tenSecondsFrodBlock(cardSender, cardRecipientNumber, money, operationType);
        this.manyOperationPerTimeBlock(cardSender, cardRecipientNumber, money, operationType);
        return true;
    }

    private boolean compareTime(Timestamp historyTime) {
        Long nowSecondsTime = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        Long historySecondsTime = historyTime.toLocalDateTime().toEpochSecond(ZoneOffset.UTC);
        return nowSecondsTime - historySecondsTime < TEN_SECONDS_FROD_VALUE;
    }
}
