import javax.swing.*;


public class GameFrame extends JFrame {

    GameEngine gameEngine = new GameEngine();

    GameFrame() {

        this.add(gameEngine);
        gameEngine.startButton.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            this.dispose();
            new StartGame();
        }));
        gameEngine.highScoreButton.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            this.dispose();
            new HighScore();
        }));

        ImageIcon icon = new ImageIcon("images/kaczki/pixel/duck-icon.png");
        setIconImage(icon.getImage());

        {
            setTitle("Duck Shooter");
            setSize(600, 700);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(false);
            setVisible(true);
        }

    }

}




