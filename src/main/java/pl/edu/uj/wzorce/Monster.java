package pl.edu.uj.wzorce;

public interface Monster extends Cloneable {
    int getMaxHp();

    int getCurrHp();

    boolean receiveDmg(int hp);

    int getAtkVal();

    String getName();

    void addHp(int hp);

    Object copy();
}
