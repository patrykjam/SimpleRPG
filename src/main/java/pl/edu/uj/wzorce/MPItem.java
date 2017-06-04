package pl.edu.uj.wzorce;

public class MPItem implements Item {
    private int value;

    public MPItem(int val) {
        value = val;
    }

    @Override
    public void pickUp(Player player) {
        player.addMP(value);
    }

    @Override
    public String getName() {
        return "mp";
    }
}
