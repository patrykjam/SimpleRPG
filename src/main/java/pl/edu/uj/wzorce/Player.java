package pl.edu.uj.wzorce;

public interface Player {

    String getPLAYER_CLASS();

    int getMAX_HP();

    void setMAX_HP(int MAX_HP);

    int getCURRENT_HP();

    void setCURRENT_HP(int CURRENT_HP);

    int getMAX_MP();

    void setMAX_MP(int MAX_MP);

    int getCURRENT_MP();

    void setCURRENT_MP(int CURRENT_MP);

    int getId();

    void addHP(int val);

    void addMP(int val);

    void setId(int id);
}
