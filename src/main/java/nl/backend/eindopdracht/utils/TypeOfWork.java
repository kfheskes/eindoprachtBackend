package nl.backend.eindopdracht.utils;


public enum TypeOfWork {
    KANTOOR_REINIGING(1),
    HUIS(2),
    RAAM_REINIGING(3),
    KLINIEK(4),
    MEDISCH(5);

    private final int value;

    TypeOfWork(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }


}
