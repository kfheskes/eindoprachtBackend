package nl.backend.eindoprdracht.utils;

public enum TermOfPayment {
    DAGEN_30(1),
    DAGEN_60(2),
    DAGEN_90(3);

    private final int value;

    TermOfPayment(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}