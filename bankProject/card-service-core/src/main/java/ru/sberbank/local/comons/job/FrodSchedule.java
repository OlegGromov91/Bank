package ru.sberbank.local.comons.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.sberbank.local.model.bank.card.Card;
import ru.sberbank.local.service.FrodService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FrodSchedule {

    private final FrodService frodService;

    public FrodSchedule(FrodService frodService) {
        this.frodService = frodService;
    }

    /**
     * Джоба следит за операциями по картам. Каждые пять минут вызова мы вычитаем две минуты, чтобы оставить
     * в памяти более свежие операции и получем timeForDeleteCandidate.
     * Затем удаляются все операции старее timeForDeleteCandidate.
     */
    @Scheduled(cron = "0 */5 * ? * *")
    public void dropOperationCounterEveryFiveMinute() {
        LocalDateTime timeForDeleteCandidate = LocalDateTime.now().minusMinutes(2);
        for (Card card : frodService.getOperationPerTimeCounter().keySet()) {
            if (!card.isBlock()) {
                List<LocalDateTime> times = frodService.getOperationPerTimeCounter().get(card).stream()
                        .filter(time -> time.isAfter(timeForDeleteCandidate))
                        .collect(Collectors.toList());
                frodService.getOperationPerTimeCounter().replace(card, frodService.getOperationPerTimeCounter().get(card), times);
            }
        }
    }
}
