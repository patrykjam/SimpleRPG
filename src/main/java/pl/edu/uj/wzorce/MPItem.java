package pl.edu.uj.wzorce;

public class MPItem implements Item {
    int value;
    @Override
    public void pickUp(Player player) {
        player.addMP(value);
    }
}
