import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Alex on 2016/11/29.
 */
public class OrientationChoices extends JPanel implements MouseListener {

    private ArrayList<JLabel> labels;
    private JLabel selected;

    public OrientationChoices() {
        buildOrientationLabels();
    }

    private void buildOrientationLabels() {
        labels = new ArrayList<>();
        for (String orientation : Arrays.asList("Horizontal", "Vertical")) {
            JLabel label = new JLabel(orientation);
            label.setFont(label.getFont().deriveFont(13.0f));
            label.setBorder(BorderFactory.createLineBorder(Color.black));
            label.addMouseListener(this);
            label.setOpaque(true);
            labels.add(label);
            add(label);
        }
        labels.get(0).setBackground(Color.cyan);
        selected = labels.get(0);
    }

    public JLabel getSelected() {
        return selected;
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {
        labels.forEach(x -> x.setBackground(Color.white));
        JLabel clickedLabel = (JLabel) e.getSource();
        selected = clickedLabel;
        clickedLabel.setBackground(Color.cyan);
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

}
