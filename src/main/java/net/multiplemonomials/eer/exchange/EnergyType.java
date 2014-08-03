package net.multiplemonomials.eer.exchange;

public enum EnergyType
{
    OMNI, CORPOREAL, KINETIC, TEMPORAL, ESSENTIA, AMORPHOUS, VOID;

    public static final EnergyType[] TYPES = EnergyType.values();

    public static final EnergyType DEFAULT = EnergyType.CORPOREAL;
}
