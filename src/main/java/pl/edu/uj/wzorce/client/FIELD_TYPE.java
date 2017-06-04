package pl.edu.uj.wzorce.client;

public enum FIELD_TYPE {
    SAND, GRASS, WATER, WALL;

    public static String getPath(FIELD_TYPE fieldType) {
        switch (fieldType) {
            case SAND:
                return "images/sand.jpg";
            case GRASS:
                return "images/trees.png";
            case WATER:
                return "images/water.png";
            case WALL:
                return "images/wall.png";
            default:
                return "images/trees.png";
        }
    }
}
