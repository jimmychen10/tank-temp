package tankrotationexample;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Rectangle;

public class Projectile {


    private int x;
    private int y;
    private int vx;
    private int vy;
    private int angle;

    private final int R = 2;



    private BufferedImage img;
    Rectangle bullet;



    Projectile(int x, int y, int vx, int vy, int angle,BufferedImage img) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;

        this.bullet = new Rectangle(this.x,this.y,this.img.getWidth(),this.img.getHeight());

    }



    private void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;

    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public BufferedImage getImg() {
        return this.img;
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }


    void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(this.img, rotation, null);
        moveForwards();

//        if (this.bullet.intersects(Tank.tank_box)){
//
//        }

    }
}
