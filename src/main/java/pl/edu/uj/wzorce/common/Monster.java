package pl.edu.uj.wzorce.common;

public interface Monster extends Cloneable {
    int getMaxHp();

    int getCurrHp();

    int getAtkVal();

    String getName();

    void addHp(int hp);

    Object copy();
}
