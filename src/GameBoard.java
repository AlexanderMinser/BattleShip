import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;


/**
 * Created by Alex on 2016/11/11.
 */
public class GameBoard extends JPanel {

    private BoardSquare[][] squares; //outer list = letters, inner = numbers
    private Game game;
    private boolean placeHorizontal;

    public GameBoard(Game game){
        this.game = game;
        setLayout(new GridLayout(10, 10));
        buildBoard();
        setPreferredSize(new Dimension(500, 450));
        placeHorizontal = true;

    }


    private void buildBoard() {
        squares = new BoardSquare[10][10];
        String letters = "ABCDEFGHIJ";
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                squares[i][j] = new BoardSquare(letters.charAt(i)+Integer.toString(j+1), game, this);
                this.add(squares[i][j]);
            }
        }

    }

    public BoardSquare[] getAdjacentSquares(String coordinates, int size, boolean isHorizontal) throws ArrayIndexOutOfBoundsException {
        BoardSquare[] adjacentSquares = new BoardSquare[size - 1];
        int columnIndex;
        if (coordinates.length() == 3)
            columnIndex = 9;
        else
            columnIndex = Character.getNumericValue(coordinates.charAt(1)) - 1;
        int rowIndex = "ABCDEFGHIJ".indexOf(coordinates.charAt(0));
        for (int i = 1; i < size; i++) {
            if (isHorizontal)
                adjacentSquares[i-1] = squares[rowIndex][columnIndex+i];
            else
                adjacentSquares[i-1] = squares[rowIndex+i][columnIndex];
        }
        return adjacentSquares;
    }

    public boolean allSquaresFree(BoardSquare[] selectedSquares) {
        for (int i = 0; i < selectedSquares.length; i++) {
            if (selectedSquares[i].isOccupied())
                return false;
        }
        return true;
    }

    public boolean allSquaresThisOwner(String owner, BoardSquare[] selectedSquares) {
        for (int i = 0; i < selectedSquares.length; i++) {
            if (!selectedSquares[i].getOwner().equals("Computer"))
                return false;
        }
        return true;
    }

    public void placeShip(BoardSquare firstSquare) {
        if (firstSquare.getOwner().equals("Computer")) {
            JOptionPane.showMessageDialog(null, "Square belongs to opponent!");
        }
        else {
            try {
                JLabel ship = game.getSelectedShip();
                boolean horizontal = game.getSelectedOrientation().getText().equals("Horizontal");
                if (ship == null)
                    return;
                String shipName = ship.getText();
                int size = game.getShip("Player", shipName).getSize();
                BoardSquare[] adjacentSquares = getAdjacentSquares(firstSquare.getCoordinates(), size, horizontal);
                if (game.getShip("Player", shipName).getSquares() != null) {
                    JOptionPane.showMessageDialog(null, "You've already placed this ship!");
                } else if (!allSquaresFree(adjacentSquares) || firstSquare.isOccupied()) {
                    JOptionPane.showMessageDialog(null, "One or more of those squares is already occupied.");
                } else {
                    BoardSquare[] shipSquares = new BoardSquare[size];
                    shipSquares[0] = firstSquare;
                    for (int i = 0; i < adjacentSquares.length; i++) {
                        shipSquares[i + 1] = adjacentSquares[i];
                    }
                    game.setShip("Player", shipSquares, shipName);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                JOptionPane.showMessageDialog(null, "Insufficient space for ship.");
            }
        }
        game.refresh();
    }

    public BoardSquare[][] getSquares() {
        return squares;
    }

    public void setCheat(boolean on) {
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[0].length; j++) {
                squares[i][j].setCheat(on);
            }
        }
    }



}
