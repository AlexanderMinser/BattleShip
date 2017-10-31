/**
 * Created by Alex on 2016/11/28.
 */
public class Ship {

    private BoardSquare[] squares;
    private String owner;
    private String name;

    public Ship(BoardSquare[] squares, String owner, String type) {
        this.squares = squares;
        this.owner = owner;
        this.name = type;
    }

    public void setSquares(BoardSquare[] squaresIn) {
        this.squares = squaresIn;
        for (int i = 0; i < squares.length; i++) {
            squares[i].setShipOnSquare(this);
            squares[i].setOccupied(true);
            squares[i].setLabel(Character.toString(name.charAt(0)));
        }
    }

    public BoardSquare[] getSquares() {
        return squares;
    }

    public boolean isSunk() {
        for (int i = 0; i < squares.length; i++) {
            if (!squares[i].isHit())
                return false;
        }
        return true;
    }

    public int getSize() {
        switch(name) {
            case "Aircraft Carrier":
                return 5;
            case "Battleship":
                return 4;
            case "Submarine":
                return 3;
            case "Cruiser":
                return 3;
            case "Destroyer":
                return 2;
            default:
                System.out.println("Unknown ship in Ship.java, getSize()");
                return -1;
        }
    }
}
