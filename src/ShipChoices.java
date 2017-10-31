import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Alex on 2016/11/27.
 */
public class ShipChoices extends JPanel implements MouseListener{

    private ArrayList<JLabel> shipLabels;
    private JLabel selected;

    public ShipChoices() {
        buildShipLabels();
    }

    private void buildShipLabels() {
        shipLabels = new ArrayList<>();
        List<String> shipNames = Arrays.asList("Aircraft Carrier", "Battleship", "Cruiser", "Submarine", "Destroyer");
        for (String name : shipNames) {
            JLabel shipLabel = new JLabel(name);
            shipLabel.setFont(shipLabel.getFont().deriveFont(13.0f));
            shipLabel.setBorder(BorderFactory.createLineBorder(Color.black));
            shipLabel.addMouseListener(this);
            shipLabel.setOpaque(true);
            shipLabels.add(shipLabel);
            add(shipLabel);
        }
        shipLabels.get(0).setBackground(Color.cyan);
        selected = shipLabels.get(0);
    }

    public JLabel getSelected() {
        return selected;
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {
        shipLabels.forEach(x -> x.setBackground(Color.white));
        JLabel clickedLabel = (JLabel) e.getSource();
        selected = clickedLabel;
        clickedLabel.setBackground(Color.cyan);
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }


}
