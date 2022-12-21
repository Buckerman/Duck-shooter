import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

class StartEngine extends JPanel implements Runnable {

    int fps = 60;
    JButton upgradeButton = new JButton("Upgrade");
    private int points = 0;

    private int currentpoints;
    public static TimeThread time;
    private String currenttime;

    private static int heartNumber = 10;
    private static int mode = 1;
    boolean blocked;

    private int currentheart;
    private BufferedImage[] heart;
    private BufferedImage pistol;
    private BufferedImage shotgun;
    private BufferedImage sniper;
    private BufferedImage banana;
    private int gun = 0;
    private int gunDMG = 1;
    private int currentGunDMG;
    private int upgradeTime = 1;
    private int upgradePoints = 500;
    private int currentgun;
    private int pos_x = 225;
    private int pos_y = 500;


    CopyOnWriteArrayList<Duck> ducksList;
    CopyOnWriteArrayList<Cloud2> cloudsList;
    Image bgImg;
    Thread startThread, startDucks;

    JCheckBox checkBox = new JCheckBox("Easy");
    JCheckBox checkBox1 = new JCheckBox("Medium");
    JCheckBox checkBox2 = new JCheckBox("Hard");
    Object[] checkBoxMode = {"Choose difficulty:", checkBox, checkBox1, checkBox2};

    public StartEngine() {

        checkBox.setFocusable(false);
        checkBox1.setFocusable(false);
        checkBox2.setFocusable(false);

        JOptionPane.showMessageDialog(null, checkBoxMode, null, JOptionPane.INFORMATION_MESSAGE);
        if (checkBox1.isSelected()) {
            heartNumber = 5;
            mode = 2;
        } else if (checkBox2.isSelected()) {
            heartNumber = 1;
            mode = 3;
        } else {
        }

        upgradeButton.setContentAreaFilled(false);
        upgradeButton.setBounds(0, 495, 100, 50);
        upgradeButton.setFocusable(false);
        upgradeButton.setBorder(BorderFactory.createEtchedBorder());
        upgradeButton.setFont(new FontUIResource("New Times Roman", Font.BOLD, 15));
        upgradeButton.setVisible(false);
        add(upgradeButton);

        upgradeButton.addActionListener(e -> {
            gun += 1;
            gunDMG += 1;
            points -= 300 * upgradeTime;
            upgradeTime += 1;
            upgradePoints = 500 * upgradeTime;
            upgradeButton.setVisible(false);
        });

        ducksList = new CopyOnWriteArrayList<>();
        cloudsList = new CopyOnWriteArrayList<>();
        bgImg = new ImageIcon("images/dodatki/tlo-v1.png").getImage();
        heart = new BufferedImage[10];
        time = new TimeThread();
        time.setIsRunning(true);

        for (int i = 0; i < heartNumber; i++) {
            try {
                heart[i] = ImageIO.read(new File("images/heart.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            pistol = ImageIO.read(new File("images/bronie/pistol.png"));
            shotgun = ImageIO.read(new File("images/bronie/shotgun.png"));
            sniper = ImageIO.read(new File("images/bronie/sniper.png"));
            banana = ImageIO.read(new File("images/bronie/banana.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        time.start();
        startDucksThread();
        cloudsRenderer();
        Thread shoot = new Thread(() -> this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (Cloud2 c : cloudsList) {
                    if (c.hit(e)) {
                        blocked = true;
                    } else blocked = false;
                }
                for (Duck d : ducksList) {
                    if (d.hit(e) && !blocked) {
                        d.setDuckHealth(d.getDuckHealth() - currentGunDMG);
                    }
                    if (d.getDuckHealth() <= 0) {
                        ducksList.remove(d);
                        points += (10 + d.getType()) * mode;
                    }
                }
            }

            public void mousePressed(MouseEvent e) {
                pos_x = 245;
                pos_y = 520;
            }

            public void mouseReleased(MouseEvent e) {
                pos_x = 225;
                pos_y = 500;
            }
        }));
        shoot.start();
        repaint();
    }

    public void startDucksThread() {
        startThread = new Thread(this);
        startDucks = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep((long) ((Math.random() * (2000 - time.getHarder())) + 500));
                    ducksRenderer();
                } catch (Exception ignored) {
                }
            }
        });
        startDucks.start();
        startThread.start();
    }

    public void ducksRenderer() {
        Duck duck = new Duck();
        ducksList.add(duck);
    }

    public void cloudsRenderer() {
        for (int i = 0; i < 4; i++) {
            Cloud2 cloud2 = new Cloud2();
            cloudsList.add(cloud2);
        }
    }

    @Override
    //https://www.youtube.com/watch?v=VpH33Uw-_0E - pomysl na fps
    public void run() {
        double drawInterval = 1000000000 / fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (!startThread.isInterrupted()) {
            if (heartNumber == 0) {
                gameEnd();
            }
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

    public void stop() {
        time.stopTime();
        startThread.interrupt();
        startDucks.interrupt();
        heartNumber = 10;
        mode = 1;
    }

    public void update() {
        currentGunDMG = gunDMG;
        currentgun = gun;
        currentheart = heartNumber;
        currentpoints = points;
        if (currentpoints >= upgradePoints && upgradeTime < 4) {
            upgradeButton.setVisible(true);
        }

        currenttime = time.getTime();
        for (int i = 0; i < ducksList.size(); i++) {
            ducksList.get(i).move();
            if (ducksList.get(i).getX() > getWidth()) {
                ducksList.remove(i);
                decreaseHealth();
            }
        }
        for (int i = 0; i < cloudsList.size(); i++) {
            cloudsList.get(i).move();
        }
    }

    public void decreaseHealth() {
        heartNumber--;
    }

    public void gameEnd() {

        List<Score> scoreArrayList = new ArrayList<>();
        String n = JOptionPane.showInputDialog(null, "Type Scoreboard Name:", null, JOptionPane.INFORMATION_MESSAGE);

        FileOutputStream fileOut;
        ObjectOutputStream out;
        FileInputStream fileIn;
        ObjectInputStream objectIn;

        try {
            fileIn = new FileInputStream("data/highscore.ser");
            objectIn = new ObjectInputStream(fileIn);
            scoreArrayList = (ArrayList<Score>) objectIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (n != null) {
            while (isNumeric(n) || java.util.Objects.equals(n, "")) {
                JOptionPane.showMessageDialog(null, "Wrong name input!", "Warning!", JOptionPane.WARNING_MESSAGE);
                n = JOptionPane.showInputDialog(null, "Type Scoreboard Name:", "Scoreboard Name", JOptionPane.INFORMATION_MESSAGE);
            }
            if (n != null) {
                Score score = new Score(n, currentpoints, currenttime);
                scoreArrayList.add(score);
                JOptionPane.showMessageDialog(null, "Zapisano do tablicy wynikow.", null, JOptionPane.INFORMATION_MESSAGE);
            }
        }

        try {
            fileOut = new FileOutputStream("data/highscore.ser");
            out = new ObjectOutputStream(fileOut);
            out.writeObject(scoreArrayList);
            out.flush();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        stop();
        SwingUtilities.invokeLater(() -> {
            //https://community.oracle.com/tech/developers/discussion/2440302/how-to-get-jframe-instance-from-a-jpanel
            JFrame f = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this);
            f.dispose();
            new GameFrame();
        });
    }

    public static boolean isNumeric(String string) {
        if (string == null) {
            return false;
        }
        try {
            Double.parseDouble(string);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(bgImg, 0, 0, this.getWidth(), this.getHeight(), null);
        if (!ducksList.isEmpty()) {
            for (Duck duck : ducksList) {
                g.drawImage(duck.getImage(), duck.getX(), duck.getY(), null);
            }
        }
        if (!cloudsList.isEmpty()) {
            for (Cloud2 cloud2 : cloudsList) {
                g.drawImage(cloud2.getImage(), cloud2.getX(), cloud2.getY(), null);
            }
        }
        for (int i = 0; i < currentheart; i++) {
            int x = i * 30;
            int y = 610;
            if (x > 120) {
                x = (i - 5) * 30;
                y = 635;
            }
            g.drawImage(heart[i], x, y, this);
        }
        switch (currentgun) {
            case 0 -> g.drawImage(pistol, pos_x, pos_y, null);
            case 1 -> g.drawImage(shotgun, pos_x, pos_y, null);
            case 2 -> g.drawImage(sniper, pos_x, pos_y, null);
            case 3 -> g.drawImage(banana, pos_x - 10, pos_y, null);
        }
        g.setColor(Color.YELLOW);
        g.setFont(new FontUIResource("Rockwell Extra Bold", Font.PLAIN, 25));
        g.drawString("Points: " + currentpoints, 370, 630);
        g.setColor(Color.YELLOW);
        g.setFont(new FontUIResource("Rockwell Extra Bold", Font.PLAIN, 20));
        g.drawString("Time: " + currenttime, 370, 655);

    }
}