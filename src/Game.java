import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Alex on 2016/11/27.
 */
public class Game extends JPanel {

    private GameBoard gameBoard;
    private GameMenuBar menuBar;
    private JFrame frame;
    private JLabel statusBar;
    private JLabel instructionsBar;
    private ShipChoices shipChoices;
    private OrientationChoices orientationChoices;
    private HashMap<String, Ship> playerShips;
    private HashMap<String, Ship> computerShips;
    private TimerC timer;
    private int turns;
    private boolean cheat;


    public Game() {

        initializeShips();
        menuBar = new GameMenuBar(this);
        add(buildWelcomeMessage());
        timer = new TimerC();
        turns = 0;

        buildFrame();
        frame.setVisible(true);
    }

    private void initializeShips() {
        playerShips = new HashMap<>();
        computerShips = new HashMap<>();
        for (String shipName : Arrays.asList("Aircraft Carrier", "Battleship", "Cruiser", "Submarine", "Destroyer")) {
            playerShips.put(shipName, new Ship(null, "Player", shipName));
            computerShips.put(shipName, new Ship(null, "Computer", shipName));
        }
    }


    private void buildFrame() {
        frame = new JFrame();
        frame.setTitle("BattleShip");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setJMenuBar(menuBar);
        frame.add(this);
        frame.setSize(new Dimension(450, 500));
        frame.setLocationRelativeTo(null);
    }

    private JPanel buildWelcomeMessage() {
        JPanel welcomePanel = new JPanel();
        JLabel label = new JLabel("Welcome to Battleship!");
        label.setFont(label.getFont ().deriveFont(20.0f));
        ImageIcon startPic = new ImageIcon("startPic.PNG");
        JLabel imageLabel = new JLabel(startPic);
        JButton button = new JButton("Start");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == button) {
                    frame.setVisible(false);
                    remove(welcomePanel);
                    setupGameBoard();
                }
            }
        });
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setAlignmentY(Component.CENTER_ALIGNMENT);
        imageLabel.setAlignmentX(CENTER_ALIGNMENT);
        imageLabel.setAlignmentY(CENTER_ALIGNMENT);
        button.setAlignmentY(Component.CENTER_ALIGNMENT);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomePanel.add(label);
        welcomePanel.add(imageLabel);
        welcomePanel.add(button);
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.PAGE_AXIS));
        return welcomePanel;
    }

    private JPanel buildButtonPanel() {
        JButton button = new JButton("Submit");
        JButton resetButton = new JButton("Reset");
        JPanel buttonPanel = new JPanel();
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (statusBar.getText().equals("Place Your Ships")) {
                    if (allShipsSet()) {
                        statusBar.setText("Your Move");
                        remove(shipChoices);
                        remove(orientationChoices);
                        remove(button);
                        instructionsBar.setText("Click on a square to fire!");
                        timer.start();
                        refresh();
                    } else {
                        JOptionPane.showMessageDialog(null, "All you ships have not been set yet!");
                    }
                }
            }
        });
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(buttonPanel);
                newGame();
            }
        });
        buttonPanel.add(button);
        buttonPanel.add(resetButton);
        return buttonPanel;
    }

    private JLabel buildInstructionsBar() {
        JLabel label = new JLabel("Click on a ship and orientation, then click where on the board you would like " +
                "to place it at.");
        label.setFont(label.getFont ().deriveFont(13.0f));
        label.setAlignmentY(CENTER_ALIGNMENT);
        label.setAlignmentX(CENTER_ALIGNMENT);
        label.setPreferredSize(new Dimension(600, 50));
        return label;

    }

    private void showEndMessage(String winner) {
        JFrame endFrame = new JFrame();
        JPanel endPanel = new JPanel();
        endFrame.setSize(300, 300);
        endFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        String endText = winner.equals("Player") ? "Congratulations! You Won" : "Sorry, you lost...";
        JLabel label = new JLabel(endText);
        JButton again = new JButton("Play Again");
        JButton stats = new JButton("View Statistics");
        again.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame();
            }
        });
        stats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showStats();
            }
        });
        label.setFont(label.getFont().deriveFont(13.0f));
        label.setAlignmentY(CENTER_ALIGNMENT);
        label.setAlignmentX(CENTER_ALIGNMENT);
        again.setAlignmentX(CENTER_ALIGNMENT);
        again.setAlignmentY(CENTER_ALIGNMENT);
        stats.setAlignmentY(CENTER_ALIGNMENT);
        stats.setAlignmentX(CENTER_ALIGNMENT);
        endPanel.add(label);
        endPanel.add(again);
        endPanel.add(stats);
        endPanel.setLayout(new BoxLayout(endPanel, BoxLayout.PAGE_AXIS));
        endFrame.add(endPanel);
        endFrame.setLocationRelativeTo(null);
        endFrame.setTitle("End");
        endFrame.setVisible(true);
    }

    private void showStats() {
        JFrame statsFrame = new JFrame();
        JPanel statsPanel = new JPanel();
        statsFrame.setTitle("Statistics");
        statsFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        JLabel time = new JLabel("Time: " + getTime()+ " seconds");
        JLabel turns = new JLabel("Turns Take: " + getTurns());
        JLabel accuracy = new JLabel("Accuracy: " + getAccuracy()+"%");
        time.setFont(time.getFont().deriveFont(13.0f));
        turns.setFont(turns.getFont().deriveFont(13.0f));
        accuracy.setFont(accuracy.getFont().deriveFont(13.0f));
        time.setAlignmentX(CENTER_ALIGNMENT);
        time.setAlignmentY(CENTER_ALIGNMENT);
        turns.setAlignmentX(CENTER_ALIGNMENT);
        turns.setAlignmentY(CENTER_ALIGNMENT);
        accuracy.setAlignmentX(CENTER_ALIGNMENT);
        accuracy.setAlignmentY(CENTER_ALIGNMENT);

        statsPanel.add(time);
        statsPanel.add(turns);
        statsPanel.add(accuracy);
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.PAGE_AXIS));
        statsFrame.add(statsPanel);
        statsFrame.setSize(300, 300);
        statsFrame.setLocationRelativeTo(null);
        statsFrame.setVisible(true);
    }

    public void showLegend() {
        JFrame legendFrame = new JFrame();
        JPanel legendPanel = new JPanel();
        LegendPiece curr;
        Color[] colors = {Color.white, Color.blue, Color.red, Color.orange, Color.gray};
        String[] descriptions = {"Miss", "Empty/Possible Enemy Ship", "Hit", "Enemy Ship(cheating on)", "Your Ship"};
        for (int i = 0; i < colors.length; i++) {
            curr = new LegendPiece(colors[i], descriptions[i]);
            curr.setAlignmentX(CENTER_ALIGNMENT);
            curr.setAlignmentY(CENTER_ALIGNMENT);
            legendPanel.add(curr);
        }
        legendPanel.setLayout(new BoxLayout(legendPanel, BoxLayout.PAGE_AXIS));
        legendFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        legendFrame.setSize(200, 400);
        legendFrame.setTitle("Legend");
        legendFrame.add(legendPanel);
        legendFrame.setLocationRelativeTo(null);
        legendFrame.setVisible(true);
    }

    private void setupGameBoard() {
        statusBar = new JLabel("Place Your Ships");
        statusBar.setFont(statusBar.getFont().deriveFont(15.0f));
        shipChoices = new ShipChoices();
        orientationChoices = new OrientationChoices();
        gameBoard = new GameBoard(this);
        instructionsBar = buildInstructionsBar();
        placeComputerShips();
        JPanel buttonPanel = buildButtonPanel();


        add(statusBar);
        add(gameBoard);
        add(instructionsBar);
        add(shipChoices);
        add(orientationChoices);

        add(buttonPanel);
        statusBar.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusBar.setAlignmentY(Component.CENTER_ALIGNMENT);
        buttonPanel.setAlignmentY(CENTER_ALIGNMENT);
        buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
        shipChoices.setAlignmentY(CENTER_ALIGNMENT);
        shipChoices.setAlignmentX(CENTER_ALIGNMENT);
        orientationChoices.setAlignmentY(CENTER_ALIGNMENT);
        orientationChoices.setAlignmentX(CENTER_ALIGNMENT);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        frame.setSize(new Dimension(650, 700));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    private boolean allShipsSet() {
        return true;
    }


    public String getStatus() {
        return statusBar.getText();
    }

    public JLabel getSelectedShip(){
        return shipChoices.getSelected();
    }

    public JLabel getSelectedOrientation() {
        return orientationChoices.getSelected();
    }

    public void refresh() {
        frame.setVisible(false);
        frame.setVisible(true);
    }

    private double getTime() {
        return timer.getElapsedTime();
    }

    private int getAccuracy() {
        double accuracy = 17 / ((double) turns);
        return (int) Math.round(accuracy * 100);
    }

    private int getTurns() {
        return turns;
    }

    public TimerC getTimer() {
        return timer;
    }

    public void setCheat(boolean on) {
        if (gameBoard != null) {
            gameBoard.setCheat(on);
            cheat = on;
        }
    }

    public boolean getCheat() {
        return cheat;
    }

    public void playerMove(BoardSquare square) {
        if (square.getOwner().equals("Player")) {
            JOptionPane.showMessageDialog(null, "You can't fire on yourself!");
        } else if (square.isHit()) {
            JOptionPane.showMessageDialog(null, "Square has already been fired upon. Pick another.");
        } else {
            turns++;
            square.setHit(true);
            playSound("explosion.wav");
            computerMove();
        }

        refresh();
        if (gameOver()) {
            showEndMessage(getWinner());
            String endSound = getWinner().equals("Player") ? "win.wav" : "lose.wav";
            playSound(endSound);
        }
    }

    private void computerMove() {
        boolean badSquare = true;
        while (badSquare) {
            int rowIndex = ThreadLocalRandom.current().nextInt(5, 10);
            int columnIndex = ThreadLocalRandom.current().nextInt(0, 10);
            BoardSquare square = gameBoard.getSquares()[rowIndex][columnIndex];
            if (!square.isHit()) {
                square.setHit(true);
                badSquare = false;
            }
        }
    }

    private boolean gameOver() {
        boolean playerLost = playerShips.values().stream().allMatch(x -> x.isSunk());
        boolean computerLost = computerShips.values().stream().allMatch(x -> x.isSunk());
        return playerLost || computerLost;
    }

    public void newGame() {
        if (statusBar.getText().equals("Place Your Ships")) {
            System.out.println("Ran");
            turns = 0;
            timer.reset();
            initializeShips();
            remove(statusBar);
            remove(gameBoard);
            remove(shipChoices);
            remove(orientationChoices);
            remove(instructionsBar);
            setupGameBoard();

        } else if (statusBar.getText().equals("Your Move")) {
            turns = 0;
            timer.reset();
            initializeShips();
            remove(statusBar);
            remove(instructionsBar);
            remove(gameBoard);
            setupGameBoard();
        }
    }

    private void playSound(String fileName) {
        try {
            File soundFile = new File(fileName);
            AudioInputStream stream = AudioSystem.getAudioInputStream(soundFile);
            AudioFormat format = stream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
        } catch(UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    private String getWinner() {
        boolean playerLost = playerShips.values().stream().allMatch(x -> x.isSunk());
        if (playerLost)
            return "Computer";
        else
            return "Player";
    }

    public Ship getShip(String owner, String shipName) {
        if (owner.equals("Player"))
            return playerShips.get(shipName);
        else
            return computerShips.get(shipName);
    }

    public void setShip(String owner, BoardSquare[] occupiedSquares, String shipName) {
        Ship ship = owner.equals("Player") ? playerShips.get(shipName) : computerShips.get(shipName);
        ship.setSquares(occupiedSquares);
        for (int i = 0; i < occupiedSquares.length; i++) {
            occupiedSquares[i].setShipOnSquare(ship);
        }
    }

    private void placeComputerShips() {
        boolean shipGood;
        for (String shipName : computerShips.keySet()) {
            shipGood = false;
            while (!shipGood) {
                int shipSize = computerShips.get(shipName).getSize();
                boolean horizontal = ThreadLocalRandom.current().nextInt(0, 2) == 0;
                int rowIndex = ThreadLocalRandom.current().nextInt(0, 5);
                int columnIndex = ThreadLocalRandom.current().nextInt(0, 10);
                BoardSquare firstSquare = gameBoard.getSquares()[rowIndex][columnIndex];
                String coordinates = firstSquare.getCoordinates();
                try {
                    BoardSquare[] adjacentSquares = gameBoard.getAdjacentSquares(coordinates, shipSize, horizontal);
                    BoardSquare[] allSquares = new BoardSquare[shipSize];
                    allSquares[0] = firstSquare;
                    for (int i = 1; i < shipSize; i++)
                        allSquares[i] = adjacentSquares[i-1];
                    if (gameBoard.allSquaresFree(allSquares) && gameBoard.allSquaresThisOwner("Computer", allSquares)) {
                        computerShips.get(shipName).setSquares(allSquares);
                        shipGood = true;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {

                }
            }//end while
        }//end for
    }


}
