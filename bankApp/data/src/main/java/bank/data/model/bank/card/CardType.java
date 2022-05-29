package bank.data.model.bank.card;

public enum CardType {

    DEBUT(1, 5L),
    CREDIT(0, 3L);

    public final int typeIdentify;
    public final long dateExpiredSum;

    CardType(int typeIdentify, long dateExpiredSum) {
        this.typeIdentify = typeIdentify;
        this.dateExpiredSum = dateExpiredSum;
    }
}
