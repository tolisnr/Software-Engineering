import javax.swing.*;
import java.awt.*;

public class BackgroundImageExample extends JPanel {
    private Image backgroundImage;

    public BackgroundImageExample(String fileName) {
        backgroundImage = new ImageIcon(fileName).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
