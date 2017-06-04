package pl.edu.uj.wzorce;


public class Archer implements Player {
    private int MAX_HP;
    private int CURRENT_HP;
    private int MAX_MP;
    private int CURRENT_MP;
    private int ATK;
    private final String PLAYER_CLASS = "archer";
    private int player_id = -1;

    public Archer(int maxhp, int currhp, int maxmp, int currmp, int atk) {
        MAX_HP = maxhp;
        CURRENT_HP = currhp;
        MAX_MP = maxmp;
        CURRENT_MP = currmp;
        ATK = atk;
    }


    @Override
    public String getPLAYER_CLASS() {
        return PLAYER_CLASS;
    }

    @Override
    public int getMAX_HP() {
        return MAX_HP;
    }

    @Override
    public void setMAX_HP(int MAX_HP) {
        this.MAX_HP = MAX_HP;
    }

    @Override
    public int getCURRENT_HP() {
        return CURRENT_HP;
    }

    @Override
    public void setCURRENT_HP(int CURRENT_HP) {
        this.CURRENT_HP = CURRENT_HP;
    }

    @Override
    public int getMAX_MP() {
        return MAX_MP;
    }

    @Override
    public void setMAX_MP(int MAX_MP) {
        this.MAX_MP = MAX_MP;
    }

    @Override
    public int getCURRENT_MP() {
        return CURRENT_MP;
    }

    @Override
    public void setCURRENT_MP(int CURRENT_MP) {
        this.CURRENT_MP = CURRENT_MP;
    }

    @Override
    public int getATK() {
        return ATK;
    }

    @Override
    public void setATK(int ATK) {
        this.ATK = ATK;
    }

    @Override
    public int getId() {
        return player_id;
    }

    @Override
    public void addHP(int val) {
        if(CURRENT_HP + val > MAX_HP){
            CURRENT_HP = MAX_HP;
        }
        else
            CURRENT_HP += val;
    }

    @Override
    public void addMP(int val) {
        if(CURRENT_MP + val > MAX_MP){
            CURRENT_MP = MAX_MP;
        }
        else
            CURRENT_MP += val;
    }

    @Override
    public void setId(int id) {
        player_id = id;
    }
}
