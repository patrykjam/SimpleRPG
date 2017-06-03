package pl.edu.uj.wzorce;


public class Mage implements Player {
    private int MAX_HP;
    private int CURRENT_HP;
    private int MAX_MP;
    private int CURRENT_MP;
    private int ATK;
    private final String PLAYER_CLASS = "mage";

    public Mage(int maxhp, int currhp, int maxmp, int currmp, int atk) {
        MAX_HP = maxhp;
        CURRENT_HP = currhp;
        MAX_MP = maxmp;
        CURRENT_MP = currmp;
        ATK = atk;
    }

    public int getMAX_HP() {
        return MAX_HP;
    }

    public void setMAX_HP(int MAX_HP) {
        this.MAX_HP = MAX_HP;
    }

    public int getCURRENT_HP() {
        return CURRENT_HP;
    }

    public void setCURRENT_HP(int CURRENT_HP) {
        this.CURRENT_HP = CURRENT_HP;
    }

    public int getMAX_MP() {
        return MAX_MP;
    }

    public void setMAX_MP(int MAX_MP) {
        this.MAX_MP = MAX_MP;
    }

    public int getCURRENT_MP() {
        return CURRENT_MP;
    }

    public void setCURRENT_MP(int CURRENT_MP) {
        this.CURRENT_MP = CURRENT_MP;
    }

    public int getATK() {
        return ATK;
    }

    public void setATK(int ATK) {
        this.ATK = ATK;
    }

    @Override
    public String getPLAYER_CLASS() {
        return PLAYER_CLASS;
    }
}
