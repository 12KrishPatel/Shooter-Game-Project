import java.awt.*;

public class Player {
    // private instance variables, such as x and y coords and defining the shape of the character
    private double xPos;
    private double yPos;
    private final double velocity = 8.5;
    private int user;
    private int hp;
    private final double halfSize = 20;
    private final double canvasWidth = Main.canvasWidth;
    private final double canvasHeight = Main.canvasHeight;
    private Laser pew;


    public Player(int user, double x, double y){
        // num variable to differentiate between the two players
        this.user = user;

        // Health points
        hp = 100;

        // X and Y Coordinates of the player
        xPos = x;
        yPos = y;
    }

    public int getHealth() {
        return hp;
    }

    public void damage(int damage) {
        hp -= damage;
    }

    public void heal(int heal) {
        hp += heal;
    }

    public void moveRight() {
        // only move right if player 1 is NOT touching the middle line
        if (user == 1) {
            if (xPos <= (canvasWidth/2) - halfSize - 3) {
                xPos += velocity;
            }

            // only move right if player 2 is NOT touching the right wall
        }else if (user == 2) {
            if (xPos <= canvasWidth - halfSize) {
                xPos += velocity;
            }
        }
    }

    public void moveLeft() {
        // only move left if player 2 is NOT touching the middle line
        if (user == 2) {
            if (xPos >= (canvasWidth/2) + halfSize + 3) {
                xPos -= velocity;
            }

            // only move left if player 1 is NOT touching the left wall
        } else if (user == 1) {
            if (xPos >= halfSize) {
                xPos -= velocity;
            }
        }
    }

    public void moveUp() {
        // only move up if NOT touching the ceiling
        if (yPos <= canvasHeight - halfSize) {
            yPos += velocity;
        }
    }

    public void moveDown() {
        // only move down if NOT touching the floor
        if (yPos >= halfSize) {
            yPos -= velocity;
        }
    }

    public void shoot() {

        // If a laser has not already been shot, then
        if (!Laser.hasP1Shot()) {

            // Create a new Laser object and note that we have already shot
            if(pew == null){
                pew = new Laser(xPos, yPos);
            }
            pew.setExists(true);

            // Set the isShot flag to true now that a laser exists
            Laser.setP1Shot(true);

            // While the laser exists, then
            if (pew != null) {
                if (user == 1) {
                    if (pew.getXPos() + 2 >= (Main.P2.xPos - 19) /*&& pew.getXPos() + 2 <= (Main.P2.xPos + 19)*/) {
                        // If the yPos of the laser is in the y-range of P2, then
                        if (pew.getYPos() >= (Main.P2.yPos - 19) && pew.getYPos() <= (Main.P2.yPos + 19)) {
                            // If the xPos of the laser is in the x-range of P2, then

                            System.out.println("\n P1's LASER LANDED ONTO P2! \n");
                            Main.P2.hp -= 10;

                            //System.out.println("P2 HP: " + Main.P2.hp);

                            pew.setP1Landed(true);
                        }
                    } else if (pew.getXPos() >=  768) {
                        // Otherwise, if the laser exceeds its max range, then
                        // Nullify the laser, making it garbage
                        pew.setExists(false);
                        pew = null;
                        System.out.println(" P1's laser has traveled its max distance.");
                    }
                }
                // If the laser landed on P2 OR the laser lost range, then set the laser object to null
                if (pew.isP1Landed()) {
                    // Setting the laser's value to null, so it gets collected by the garbage collector
                    pew.setExists(false);
                    pew = null;
                }
            }
            // Note that we have not shot since the laser does not exist anymore
            Laser.setP1Shot(false);
        }

        if (!Laser.hasP2Shot()) {
            // Create a new Laser object and note that we have already shot
            if(pew == null){
                pew = new Laser(xPos, yPos);
            }
            pew.setExists(true);
            // Set the isShot flag to true now that a laser exists
            Laser.setP2Shot(true);
            // While the laser exists, then
            if (pew != null) {
                if (user == 2) {
                    if (pew.getXPos() + 2 >= (Main.P1.xPos - 19) /*&& pew.getXPos() + 2 <= (Main.P2.xPos + 19)*/) {
                        // If the yPos of the laser is in the y-range of P2, then
                        if (pew.getYPos() >= (Main.P1.yPos - 19) && pew.getYPos() <= (Main.P1.yPos + 19)) {
                            // If the xPos of the laser is in the x-range of P2, then

                            System.out.println("\n P2's LASER LANDED ONTO P1! \n");
                            Main.P1.hp -= 10;

                            //System.out.println("P1 HP: " + Main.P1.hp);

                            pew.setP1Landed(true);
                        }
                    } else if (pew.getXPos() <=  0) {
                        // Otherwise, if the laser exceeds its max range, then
                        // Nullify the laser, making it garbage
                        pew.setExists(false);
                        pew = null;
                        System.out.println(" P2's laser has traveled its max distance.");
                    }
                }
            }
            // Note that we have not shot since the laser does not exist anymore
            Laser.setP2Shot(false);
        }

        if (pew != null && ((pew.isP1Landed() || pew.getXPos() >= 768) || (pew.isP2Landed() || pew.getXPos() <= 0))) {
            pew.setExists(false);
            pew = null;
        }

        // Invoking the garbage collector to get rid of the Laser object(s)
        System.gc();
        System.out.println("Garbage collector has been called!");
    }

    //Manual Debugging
    /*
    timer.run -> p1.shoot
    does p1 have a shot?
    if no
    {
        pew = new pew();
        ....
        ....
        pew = null;
    }
    gc.collect
     */
    //End of Debugging

    public void draw() {
        Font font = new Font("Arial", Font.BOLD, 30);
        // Set the color based upon which player you are
        if(user == 1){
            StdDraw.setPenColor(StdDraw.BOOK_RED);
            StdDraw.setFont(font);
            StdDraw.picture(xPos, yPos, "steve.png", 50, 50);

            StdDraw.text(250, 50, "Player 1 HP: " + Main.P1.hp);

            if(Main.P1.hp <= 0){
                System.exit(0);
            }
        }else if(user == 2){
            StdDraw.setPenColor(StdDraw.BOOK_BLUE);
            StdDraw.setFont(font);
            StdDraw.picture(xPos, yPos, "thanos.png", 50, 50);
            StdDraw.text(520, 50, "Player 2 HP: " + Main.P2.hp);
            if(Main.P2.hp <= 0){
                System.exit(0);
            }
        }

        //StdDraw.filledSquare(xPos, yPos, halfSize);

        if(pew != null){
            double timeElapsed = 0.017;
            // Keep the laser moving as long as pew is not null
            pew.laserMoving(user, timeElapsed);
            pew.drawLaser(pew.getXPos(), pew.getYPos());
        }
    }


}
