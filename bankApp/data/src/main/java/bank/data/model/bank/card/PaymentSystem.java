package bank.data.model.bank.card;

public enum PaymentSystem {

    MIR(2),
    VISA(4),
    MASTERCARD(5);

    public final long systemIdentify;

    PaymentSystem(long systemIdentify) {
        this.systemIdentify = systemIdentify;
    }
}
