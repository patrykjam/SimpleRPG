package pl.edu.uj.wzorce;

public class Ladybug implements Monster {

    private int MAX_HP;
    private int CURRENT_HP;
    private int ATK = 1;


    public Ladybug(int hp){
        MAX_HP = CURRENT_HP = hp;
    }

    @Override
    public int getMaxHp() {
        return MAX_HP;
    }

    @Override
    public int getCurrHp() {
        return CURRENT_HP;
    }

    @Override
    public boolean receiveDmg(int hp) {
        CURRENT_HP -= hp;
        return CURRENT_HP <= 0;
    }

    @Override
    public int getAtkVal() {
        return ATK;
    }

    @Override
    public String getName() {
        return "ladybug";
    }

    @Override
    public void addHp(int hp) {
        CURRENT_HP+=hp;
    }

    @Override
    public Object copy(){
        try {
            return this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
