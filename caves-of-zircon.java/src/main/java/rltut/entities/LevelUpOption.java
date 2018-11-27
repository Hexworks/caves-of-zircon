package rltut.entities;

import rltut.ai.Creature;

public abstract class LevelUpOption {
    private String name;

    String name() {
        return name;
    }

    LevelUpOption(String name) {
        this.name = name;
    }

    public abstract void invoke(Creature creature);
}
