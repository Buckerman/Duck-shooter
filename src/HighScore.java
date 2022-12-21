import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;

public class HighScore extends JFrame {

    JButton backButton = new JButton("Back");

    MyModel model = new MyModel();
    JList jList = new JList(model);
    JScrollPane js = new JScrollPane(jList);
    JPanel panel = new JPanel() {
        Image bg = new ImageIcon("images/dodatki/tlo.png").getImage();

        public void paintComponent(Graphics g) {
            g.drawImage(bg, 0, 0, this.getWidth(), this.getHeight(), null);
        }
    };

    JLabel textLabel = new JLabel("High Score", SwingConstants.CENTER);
    JPanel buttonPanel = new JPanel();

    HighScore() {

        textLabel.setFont(new FontUIResource("Rockwell Extra Bold", Font.BOLD, 50));
        add(panel);

        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.add(backButton, BorderLayout.WEST);
        buttonPanel.setOpaque(false);


        panel.setLayout(new BorderLayout());
        panel.add(js, BorderLayout.CENTER);

        panel.add(textLabel, BorderLayout.PAGE_START);
        panel.add(buttonPanel, BorderLayout.PAGE_END);
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        jList.setBackground(Color.decode("#A5D8FF"));
        jList.setFont(new FontUIResource("Rockwell Extra Bold", Font.BOLD, 20));
        jList.setForeground(Color.WHITE);

        backButton.setBounds(0, 550, 200, 50);
        backButton.setBorder(BorderFactory.createEmptyBorder(50, 4, 4, 4));
        backButton.setFont(new FontUIResource("Rockwell Extra Bold", Font.BOLD, 50));
        backButton.setContentAreaFilled(false);
        backButton.setFocusable(false);
        backButton.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            this.dispose();
            new GameFrame();
        }));

        ImageIcon icon = new ImageIcon("images/kaczki/pixel/duck-icon.png");
        setIconImage(icon.getImage());

        setTitle("Duck Shooter");
        setSize(600, 700);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);

    }
}