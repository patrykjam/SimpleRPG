package pl.edu.uj.wzorce;

public class Griffin implements Monster {

    private int MAX_HP;
    private int CURRENT_HP;
    private int ATK = 2;

    public Griffin(int hp){
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
        if(CURRENT_HP < (MAX_HP / 4)){
            return ATK * 2;
        }
        return ATK;
    }

    @Override
    public String getName() {
        return "griffin";
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
