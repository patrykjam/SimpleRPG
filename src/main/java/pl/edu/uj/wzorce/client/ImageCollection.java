package pl.edu.uj.wzorce.client;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ImageCollection {
    private static ImageCollection instance;
    private static Map<String, Image> imageMap;

    private ImageCollection() {
    }

    static ImageCollection getInstance() {
        if (instance == null) {
            instance = new ImageCollection();
            imageMap = new HashMap<>();
        }
        return instance;
    }

    Image getImage(String key, int size) {
        Image scaledInstance = null;
        if (imageMap.containsKey(key)) {
            return imageMap.get(key);
        } else {
            BufferedImage bufferedImage;
            URL resource = getClass().getClassLoader().getResource(key);
            if (resource != null) {
                try {
                    bufferedImage = ImageIO.read(resource);
                    scaledInstance = bufferedImage.getScaledInstance(size, size, Image.SCALE_SMOOTH);
                    imageMap.put(key, scaledInstance);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return scaledInstance;
        }
    }
}
