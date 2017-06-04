package pl.edu.uj.wzorce.client;

public class MPItem implements Item {
    private int value;

    public MPItem(int val) {
        value = val;
    }

    @Override
    public String getName() {
        return "mp";
    }
}
