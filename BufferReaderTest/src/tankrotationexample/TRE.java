/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankrotationexample;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;


import java.util.Objects;

import static javax.imageio.ImageIO.read;

/**
 *
 * @author Jimmy Chen
 *
 * Class : CSC 413
 *
 *
 * This program is a incomplete version of the thank game.
 * The game has 2 players, both can shoot bullets and has walls and breable walls which bullets can collide with
 * The game HOWEVER, does not have collision check with the 2 players colliding with bullets and walls
 *
 * The game is incomplete due to myself being overconfident and not haveing to due with some
 * personal issues.
 *
 * This project has gave me insight on how to create my game for my presentatoin.
 *
 */
public class TRE extends JPanel  {


    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 960;
    private BufferedImage world;
    private Graphics2D buffer;
    private JFrame jf;
    private tankrotationexample.Tank t1;
    private tankrotationexample.Tank t2;
    private tankrotationexample.Projectile projectile;
    private Image background;
    static ArrayList<Projectile> projectiles;
    static ArrayList<Walls> walls; // delete
    static ArrayList<BreakableWall> bwall;
    static ArrayList<NonBreakableWall> nbwall;
    private CollisionCheck collision;

    public static void main(String[] args) {
        Thread x;
        TRE trex = new TRE(); // the makeing of the game
        trex.init();  // initiliazes the game
        try {

            while (true) {
                trex.t1.update();
                trex.t2.update();



                trex.repaint();   // check this out
                trex.jf.repaint();
//
//                System.out.println(trex.t1);
//                System.out.println(trex.t2);
//                System.out.println(projectiles.size());


                Thread.sleep(1000 / 144);

            }
        } catch (InterruptedException ignored) {

        }

    }


    private void init() {
        this.jf = new JFrame("Tank Rotation");
        this.world = new BufferedImage(TRE.SCREEN_WIDTH, TRE.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB); // creates the screen of the game
        this.collision = new CollisionCheck();
        BufferedImage t1img = null, t2img = null, bullet= null,breakable_wall=null, wall = null;
        try {
            System.out.println(System.getProperty("user.dir"));
            /*
             * note class loaders read files from the out folder (build folder in netbeans) and not the
             * current working directory.
             */
            t1img = read(new File("tank1.png"));
            t2img = read(new File("Tank2.png"));
            bullet = read(new File("projectile.png"));
            breakable_wall = read(new File("Wall2.gif"));
            wall = read(new File("Wall1.gif"));





            this.background = ImageIO.read(new File("Background.bmp"));


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        t1 = new Tank(200, 200, 0, 0, 0, t1img,bullet);
        t2 = new Tank(1000, 600, 0, 0, 180, t2img,bullet);
        projectiles = new ArrayList<Projectile>();
        nbwall = new ArrayList<NonBreakableWall>();
        bwall = new ArrayList<BreakableWall>();

        String[][] wal = { { "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W" },
                { "B", " ", " ", " ", "B", " ", " ", " ", "B", " ", " ", " ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "  },
                { "B", " ", " ", "B", "B", "B", " ", " ", "B", " ", " ", " ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "  },
                { " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "  },
                { "B", "B", " ", " ", " ", " ", " ", "B", "B", " ", " ", " ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "   },
                { "B", "B", " ", " ", " ", " ", " ", "B", "B", " ", " ", " ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "   },
                { "B", "B", " ", "B", "B", "B", " ", "B", "B", " ", " ", " ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "   },
                { "B", "B", " ", "B", " ", "B", " ", "B", "B", " ", " ", " ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "   },
                { "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W"," ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " " },
                { "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W"," ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " " },
                { "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W"," ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " " },
                { "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W"," ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " " },
                { "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W"," ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " " },
                { "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W"," ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " " },
                { "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W" },
                { "B", " ", " ", " ", "B", " ", " ", " ", "B", " ", " ", " ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "  },
                { "B", " ", " ", "B", "B", "B", " ", " ", "B", " ", " ", " ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "  },
                { " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "  },
                { "B", "B", " ", " ", " ", " ", " ", "B", "B", " ", " ", " ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "   },
                { "B", "B", " ", " ", " ", " ", " ", "B", "B", " ", " ", " ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "   },
                { "B", "B", " ", "B", "B", "B", " ", "B", "B", " ", " ", " ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "   },
                { "B", "B", " ", "B", " ", "B", " ", "B", "B", " ", " ", " ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "   },
                { "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W"," ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " " },
                { "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W"," ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " " },
                { "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W"," ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " " },
                { "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W"," ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " " },
                { "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W"," ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " " },
                { "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W"," ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " " },


                 { "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W", "W" }} ;
        for(int i = 0; i < wal.length;i++ ){
            for(int j = 0; j<wal[i].length ;j++){
                System.out.print(wal[i][j]);
                if (wal[i][j]=="W"){
                    nbwall.add(new NonBreakableWall(j*32,i*32,wall));

                }
                else if (wal[i][j]=="B") {
                    bwall.add(new BreakableWall( j*32 , i*32, breakable_wall));

                }

            }
            System.out.print("\n");
        }




        TankControl tc1 = new TankControl(t1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_1);  // controls for the tank
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_ENTER);  // controls for the tank
        this.jf.setLayout(new BorderLayout());
        this.jf.add(this);


        this.jf.addKeyListener(tc1);
        this.jf.addKeyListener(tc2);


        this.jf.setSize(TRE.SCREEN_WIDTH, TRE.SCREEN_HEIGHT + 30);
        this.jf.setResizable(false);
        jf.setLocationRelativeTo(null);

        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jf.setVisible(true);


    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;



        buffer = world.createGraphics();

        super.paintComponent(g2);



        g2.drawImage(world,0,0,this);

        buffer.drawImage(background,0,0,this);



        this.t1.drawImage(g2);

        this.t2.drawImage(g2);


        for (int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).drawImage(g2);
        }

        for (int i = 0; i < bwall.size(); i++) {
            bwall.get(i).drawImage(g2);
        }
        for (int i = 0; i < nbwall.size(); i++) {
            nbwall.get(i).drawImage(g2);
        }



        // Collision check with a non breakable wall
        for (int i = 0; i < projectiles.size(); i++) {
            for (int j = 0; j < nbwall.size(); j++){
                if (collision.collision_bullet_with_nonwall(projectiles.get(i),nbwall.get(j))) {
                    projectiles.remove(i);
                }

            }
        }

        // Collision check with a breakable wall
        for (int i = 0; i < projectiles.size(); i++) {
            for (int j = 0; j < bwall.size(); j++){
                if (collision.collision_bullet_with_wall(projectiles.get(i),bwall.get(j))) {
                    bwall.remove(j);
                    projectiles.remove(i);
                }

            }
        }



    }


}
