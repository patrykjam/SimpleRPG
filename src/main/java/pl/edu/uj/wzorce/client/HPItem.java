package pl.edu.uj.wzorce.client;

public class HPItem implements Item {
    private int value;

    public HPItem(int val) {
        value = val;
    }

    @Override
    public String getName() {
        return "hp";
    }
}
