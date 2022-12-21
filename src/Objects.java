import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Objects {
}

class Cloud {
    Random random = new Random();

    private int speed = random.nextInt(5) + 1;
    private final int width, height;
    private int x;
    private int y;
    Image cloud;

    public Cloud() {
        cloud = new ImageIcon("images/dodatki/chmura" + (((int) (Math.random() * 7)) + 1) + ".png").getImage();
        this.x = ((int) (Math.random() * -100) - 50);
        this.y = ((int) (Math.random() * 600));
        width = cloud.getWidth(null);
        height = cloud.getHeight(null);

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move() {
        x += speed;
        if (x > 600) {
            x = ((int) (Math.random() * -100) - 50);
            y = ((int) (Math.random() * 600));
            speed = random.nextInt(2) + 1;
        }
    }

    public Image getImage() {
        return cloud;
    }

}
class Cloud2{
    Random random = new Random();

    private int speed = random.nextInt(2) + 1;
    private final int width, height;
    private int x;
    private int y;
    BufferedImage cloud;

    Cloud2() {
        try {
            cloud = ImageIO.read(new File("images/dodatki/chmura" + (((int) (Math.random() * 7)) + 1) + ".png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.x = ((int) (Math.random() * 300)+10);
        this.y = ((int) (Math.random() * 400));
        width = cloud.getWidth(null);
        height = cloud.getHeight(null);
    }
    public void move() {
        x += speed;
        if(x >= 530 || x <= -10) {
            speed = speed * -1;
            this.y = ((int) (Math.random() * 400));
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean hit(MouseEvent e) {
        Point hitpoint = e.getPoint();

        hitpoint.x -= getX();
        hitpoint.y -= getY();

        return contains(cloud, hitpoint.x, hitpoint.y);
    }
    public boolean contains(BufferedImage image, int x, int y) {
        if (x < 0 || y < 0 || x >= image.getWidth() || y >= image.getHeight()) {
            return false;
        }
        return true;
    }

    public BufferedImage getImage() {
        return cloud;
    }
}

class Duck {
    Random random = new Random();

    private int speed = random.nextInt(1) + 1;
    private int duckHealth;
    private int x;
    private int y;
    private int type = (int) ((Math.random() * 4) + 1);

    BufferedImage duck;

    public Duck() {
        try {
            duck = ImageIO.read(new File("images/kaczki/duck" + type + ".png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.x = ((int) (Math.random() * -100) - 50);
        this.y = ((int) (Math.random() * 400));
        switch (type) {
            case 1 -> duckHealth = 1;
            case 2 -> duckHealth = 2;
            case 3 -> duckHealth = 3;
            case 4 -> duckHealth = 4;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move() {
        x += speed;
    }

    public BufferedImage getImage() {
        return duck;
    }

    public boolean hit(MouseEvent e) {
        Point hitpoint = e.getPoint();

        hitpoint.x -= getX();
        hitpoint.y -= getY();

        return contains(duck, hitpoint.x, hitpoint.y);
    }

    public int getDuckHealth() {
        return duckHealth;
    }

    public void setDuckHealth(int duckHealth) {
        this.duckHealth = duckHealth;
    }

    public int getType() {
        return type;
    }

    public boolean contains(BufferedImage image, int x, int y) {
        if (x < 0 || y < 0 || x >= image.getWidth() || y >= image.getHeight()) {
            return false;
        }
        return true;
    }


}