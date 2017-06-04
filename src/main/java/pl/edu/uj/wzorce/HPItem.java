package pl.edu.uj.wzorce;

public class HPItem implements Item {
    private int value;

    HPItem(int val){
        value = val;
    }
    @Override
    public void pickUp(Player player) {
        player.addHP(value);
    }
}
