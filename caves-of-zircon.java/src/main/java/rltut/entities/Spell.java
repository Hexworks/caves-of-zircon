package rltut.entities;

public class Spell {

    private String name;

    public String name() {
        return name;
    }

    private int manaCost;

    public int manaCost() {
        return manaCost;
    }

    private Effect effect;

    public Effect effect() {
        return new Effect(effect);
    }

    public boolean requiresTarget() {
        return true;
    }

    Spell(String name, int manaCost, Effect effect) {
        this.name = name;
        this.manaCost = manaCost;
        this.effect = effect;
    }
}
