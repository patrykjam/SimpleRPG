package pl.edu.uj.wzorce;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ImageCollection {
    public static ImageCollection instance;
    private static Map<String, BufferedImage> imageMap;

    private ImageCollection() {
    }

    public static ImageCollection getInstance() {
        if (instance == null) {
            instance = new ImageCollection();
            imageMap = new HashMap<>();
        }
        return instance;
    }

    public BufferedImage getBufferedImage(String key) {
        if (imageMap.containsKey(key)) {
            return imageMap.get(key);
        } else {
            BufferedImage bufferedImage = null;
            URL resource = getClass().getClassLoader().getResource(key);
            if (resource != null) {
                try {
                    bufferedImage = ImageIO.read(resource);
                    imageMap.put(key, bufferedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return bufferedImage;
        }
    }
}
