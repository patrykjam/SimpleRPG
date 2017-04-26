import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

class Field extends JPanel {

    private int size;
    private BufferedImage bufferedImage = null;


    Field(int size, Color color){
        this.size = size;
        setBackground(color);
    }

    public void setImage(String background_image_name){
        if(background_image_name != null) {
            Path path = Paths.get("").toAbsolutePath().resolve("images").resolve(background_image_name);
            try {
                bufferedImage = ImageIO.read(path.toFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Image scaled = bufferedImage.getScaledInstance(size, size, Image.SCALE_SMOOTH);
            ImageIcon image = new ImageIcon(scaled);
            JLabel label = new JLabel(image);
            setLayout(new BorderLayout());
            add(label, BorderLayout.CENTER);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(size, size);
    }
}
