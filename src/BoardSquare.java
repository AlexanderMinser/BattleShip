import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by Alex on 2016/11/11.
 */
public class BoardSquare extends JPanel implements MouseListener {

    private String coordinates; //should be two characters in format ex. "A1"
    private String owner;
    private boolean occupied;
    private boolean hit;
    private boolean cheat;
    private Game game;
    private GameBoard gameBoard;
    private Ship shipOnSquare;
    private JLabel shipLabel;

    public BoardSquare(String coordinates, Game game, GameBoard gameBoard) {
        this.coordinates = coordinates;
        this.game = game;
        this.gameBoard = gameBoard;
        occupied = false;
        hit = false;
        owner = "ABCDEFGHIJ".indexOf(coordinates.charAt(0)) < 5 ? "Computer" : "Player";
        cheat = false;
        addMouseListener(this);
        setOpaque(true);

        setLayout(new GridLayout(3,3,1,1));
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public void setOccupied(boolean state) {
        occupied = state;
    }


    public void mouseClicked(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {
        if (game.getStatus().equals("Place Your Ships")) {
            gameBoard.placeShip(this);
        } else if (game.getStatus().equals("Your Move")) {
            game.playerMove(this);
        }
    }

    public void mousePressed(MouseEvent e) {

    }


    public boolean isHit() {
        return hit;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public String getOwner() {
        return owner;
    }

    public void setHit(boolean status) {
        hit = status;
    }

    public void setShipOnSquare(Ship ship) {
        shipOnSquare = ship;
    }

    public void setLabel(String shipName) {
        if (shipLabel != null)
            remove(shipLabel);
        shipLabel = new JLabel(shipName);
        if (owner.equals("Player"))
            add(shipLabel);
    }

    public void setCheat(boolean on) {
        cheat = on;
        if (!cheat && owner.equals("Computer") && occupied)
            remove(shipLabel);
        else if (owner.equals("Computer") && occupied)
            add(shipLabel);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (hit && occupied) {
            setBackground(Color.red);
        } else if (hit) {
            setBackground(Color.white);
        } else if (occupied) {
            if (owner.equals("Player")) {
                setBackground(Color.gray);
            }
            else if (owner.equals("Computer") && cheat){
                setBackground(Color.orange);
            } else {
                setBackground(Color.blue);
            }
        } else {
            setBackground(Color.blue);
        }


    }

}
