import javax.swing.*;
import java.awt.*;

/**
 * Created by Alex on 2016/11/30.
 */
public class LegendPiece extends JPanel {

    private Color color;
    private JLabel description;

    public LegendPiece(Color color, String description) {
        this.color = color;
        this.description = new JLabel(description);
        this.description.setFont(this.description.getFont().deriveFont(13.0f));
        setOpaque(true);
        setBackground(color);
        add(this.description);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
    }
}
