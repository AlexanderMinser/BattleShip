import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Alex on 2016/11/11.
 */
public class GameMenuBar extends JMenuBar implements ActionListener {

    private Game game;
    private JMenu file;
    private JMenu view;
    private JMenuItem newGame;
    private JMenuItem exit;
    private JMenuItem legend;
    private JMenuItem cheat;

    public GameMenuBar(Game parent) {
        game = parent;
        buildMenu();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exit) {
            System.exit(0);
        } else if (e.getSource() == newGame) {
            game.newGame();
        } else if (e.getSource() == legend) {
            game.showLegend();
        } else if (e.getSource() == cheat) {
            game.setCheat(!game.getCheat());
            game.refresh();
        }
    }

    private void buildMenu() {
        file = new JMenu("File");
        newGame = new JMenuItem("New Game");
        newGame.addActionListener(this);
        exit = new JMenuItem("Exit");
        exit.addActionListener(this);
        file.add(newGame);
        file.add(exit);
        add(file);

        view = new JMenu("View");
        legend = new JMenuItem("Legend");
        legend.addActionListener(this);
        cheat = new JMenuItem("Cheat");
        cheat.addActionListener(this);
        view.add(legend);
        view.add(cheat);
        add(view);
    }


}
