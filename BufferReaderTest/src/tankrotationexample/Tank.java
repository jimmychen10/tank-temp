package tankrotationexample;



import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;



/**
 *
 * @author anthony-pc
 */
public class Tank{


    private int x;
    private int y;
    private int vx;
    private int vy;
    private int angle;


    private final int R = 2;
    private final int ROTATIONSPEED = 4;



    private BufferedImage img;
    private BufferedImage bullet;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;
    Rectangle tank_box;
    Rectangle tank_left_side;
    Rectangle tank_right_side;
    Rectangle tank_front_side;
    Rectangle tank_back_side;










    Tank(int x, int y, int vx, int vy, int angle, BufferedImage img, BufferedImage bullet) {
        this.x = x;
        this.y = y;

        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.bullet = bullet;
        this.angle = angle;

        this.tank_box = new Rectangle(this.x,this.y,this.img.getWidth(),this.img.getHeight());

        this.tank_front_side = new Rectangle(this.x+1,this.y,this.img.getWidth()-2, 1);
        this.tank_back_side = new Rectangle(this.x+1,this.y + this.img.getWidth()-2,this.img.getWidth()-2, 1);
        this.tank_left_side = new Rectangle(this.x+1,this.y,this.img.getWidth()-2, 1);
        this.tank_right_side = new Rectangle(this.x+this.img.getWidth()-2,this.y+1,1, this.img.getHeight()-2);
    }




    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    void shootProjectile() {
        this.ShootPressed = true;
        TRE.projectiles.add(new Projectile(x,y,vx,vy,angle,bullet));

    }
    void unProjectile() {
        this.ShootPressed = false;

    }



    public void update() {
        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        if(this.ShootPressed){
            this.shootProjectile();
        }

    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
        if(tank_left_side.intersects(NonBreakableWall.wall)){
            this.angle += this.ROTATIONSPEED;
        }
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void moveBackwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        checkBorder();
    }

    private void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
    }



    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= TRE.SCREEN_WIDTH - 88) {
            x = TRE.SCREEN_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= TRE.SCREEN_HEIGHT - 80) {
            y = TRE.SCREEN_HEIGHT - 80;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getVx() {
        return vx;
    }

    public int getVy() {
        return vy;
    }

    public int getAngle() {
        return angle;
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

    }



}
