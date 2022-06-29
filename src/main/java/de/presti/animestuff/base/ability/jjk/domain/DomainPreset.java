package de.presti.animestuff.base.ability.jjk.domain;

public enum DomainPreset {

    MALEVOLENT_SHRINE("Malevolent Shrine", true),
    COFFIN_OF_THE_IRON_MOUNTAIN("Coffin of the Iron Mountain", false),
    UNLIMITED_VOID("Unlimited Void", true),
    HORIZON_OF_THE_CAPTIVATING_SKANDHA("Horizon of the Captivating Skandha", false),
    SELF_EMBODIMENT_OF_PERFECTION("Self-Embodiment of Perfection", true),
    CHIMERA_SHADOW_GARDEN("Chimera Shadow Garden", true),
    SMALLPOX_DEITY("Smallpox Deity", true);

    private String clearName;
    private boolean loopAround;
    DomainPreset(String clearName, boolean loopAround) {
        this.clearName = clearName;
        this.loopAround = loopAround;
    }

    public String getClearName() { return clearName; }
    public boolean loopsAround() { return loopAround; }

}
