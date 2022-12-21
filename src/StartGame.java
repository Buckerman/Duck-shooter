import javax.swing.*;
import java.awt.event.*;


public class StartGame extends JFrame implements KeyListener {

    StartEngine startEngine = new StartEngine();

    StartGame() {

        startEngine.setLayout(null);
        add(startEngine);

        ImageIcon icon = new ImageIcon("images/kaczki/pixel/duck-icon.png");
        setIconImage(icon.getImage());

        setTitle("Duck Shooter");
        setSize(600, 700);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        addKeyListener(this);

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_Q) {
            startEngine.stop();
            startEngine.time.stopTime();
            SwingUtilities.invokeLater(() -> {
                this.dispose();
                new GameFrame();
            });
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

}





