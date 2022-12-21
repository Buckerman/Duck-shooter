import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.ArrayList;

class GameEngine extends JPanel implements Runnable {
    int fps = 60;

    ArrayList<Cloud> cloudsList;
    Image bg;
    JButton startButton = new JButton("Start Game");
    JButton highScoreButton = new JButton("High Score");
    JButton exitButton = new JButton("Exit");
    Thread gameThread;


    public GameEngine() {

        setLayout(null);
        cloudsList = new ArrayList<>();
        bg = new ImageIcon("images/dodatki/tlo.png").getImage();

        startCloudThread();

        {
            startButton.setBounds(90, 200, 400, 50);
            startButton.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
            startButton.setFont(new FontUIResource("Rockwell Extra Bold", Font.BOLD, 50));
            startButton.setContentAreaFilled(false);
            startButton.setFocusable(false);
            add(startButton);
        }


        {
            highScoreButton.setBounds(90, 300, 400, 70);
            highScoreButton.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
            highScoreButton.setFont(new FontUIResource("Rockwell Extra Bold", Font.BOLD, 50));
            highScoreButton.setContentAreaFilled(false);
            highScoreButton.setFocusable(false);
            add(highScoreButton);
        }


        {
            exitButton.setBounds(90, 410, 400, 50);
            exitButton.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
            exitButton.setFont(new FontUIResource("Rockwell Extra Bold", Font.BOLD, 50));
            exitButton.setContentAreaFilled(false);
            exitButton.setFocusable(false);
            exitButton.addActionListener(e -> System.exit(0));
            add(exitButton);
        }
    }

    public void cloudsRender() {
        Cloud cloud = new Cloud();
        cloudsList.add(cloud);

    }

    public void startCloudThread() {
        gameThread = new Thread(this);
        for (int i = 0; i < 25; i++) {
            cloudsRender();
        }
        gameThread.start();
    }

    @Override
    public void run() {
        //https://www.youtube.com/watch?v=VpH33Uw-_0E
        double drawInterval = 1000000000 / fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {

                update();
                repaint();
                delta--;

            }

        }

    }

    public void update() {
        for (int i = 0; i < cloudsList.size(); i++) {
            cloudsList.get(i).move();
            if (cloudsList.get(i).getX() > 600) {
                cloudsList.remove(i);
            }
        }
    }

    public void paintComponent(Graphics g) {
        g.drawImage(bg, 0, 0, this.getWidth(), this.getHeight(), null);
        if (!cloudsList.isEmpty()) {
            for (Cloud cloud : cloudsList) {
                g.drawImage(cloud.getImage(), cloud.getX(), cloud.getY(), null);
            }
        }
    }
}




