package tankrotationexample;

import tankrotationexample.Projectile;
import tankrotationexample.TRE;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class NonBreakableWall extends Walls {

    /**
     *
     * @author anthony-pc
     */


        private int x;
        private int y;
        private BufferedImage img;
        static Rectangle wall;

        NonBreakableWall(int x, int y, BufferedImage img) {
            this.x = x;
            this.y = y;
            this.img = img;

            this.wall = new Rectangle(this.x,this.y,this.img.getWidth(),this.img.getHeight());


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

    void drawImage(Graphics g) {
            AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);

            Graphics2D g2d = (Graphics2D) g;

            g2d.drawImage(this.img, rotation, null);


        }



    }


