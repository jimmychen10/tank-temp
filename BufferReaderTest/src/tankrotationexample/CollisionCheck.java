package tankrotationexample;

import java.awt.Rectangle;

public class CollisionCheck {

    Rectangle Boundries;
    Rectangle tank;
    Rectangle tank_front_side;
    Rectangle tank_back_side;
    Rectangle tank_left_side;
    Rectangle tank_right_side;


    Rectangle bullet;
    Rectangle breakableWall;
    Rectangle nonBreakableWall;

    public CollisionCheck(){

    }
    public boolean collision_bullet_with_nonwall(Projectile b, NonBreakableWall w){
        this.nonBreakableWall= new Rectangle(w.getX(),w.getY(),w.getImg().getWidth(),w.getImg().getHeight());

        this.bullet = new Rectangle(b.getX(),b.getY(),b.getImg().getWidth(),b.getImg().getHeight());

        if (this.bullet.intersects(this.nonBreakableWall)){
            return true;
        }
        else{
            return false;
        }

    }
    public boolean collision_bullet_with_wall(Projectile b, BreakableWall w){
        this.breakableWall= new Rectangle(w.getX(),w.getY(),w.getImg().getWidth(),w.getImg().getHeight());

        this.bullet = new Rectangle(b.getX(),b.getY(),b.getImg().getWidth(),b.getImg().getHeight());

        if (this.bullet.intersects(this.breakableWall)){
            return true;
        }
        else{
            return false;
        }

    }


}
