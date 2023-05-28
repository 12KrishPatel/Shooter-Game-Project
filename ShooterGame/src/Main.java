/*

    - Get the laser drawn onto the canvas
    - Figure out how to calculate time in Java
    - Make the calculations for reducing health when coming in contact with a bullet/mob
    - Draw a health bar with the length equating to the player's hp. Use a filled Rect and hollow Rect
    - Make the game reach a resolution when one player's hp reaches 0
    - if there's time, add med-kits that randomly spawn in that heal about 20 hp
    - if there's time, add a wall here and there to provide cover for the player
    - if there's time, add power ups that increase range, speed, or damage temporarily
    - if there's time, add a timer that concludes the game and closes the program

 */

import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    // Define the canvas's dimensions
    static double canvasWidth = 768;
    static double canvasHeight = 768;
    static Player P1;
    static Player P2;

    public static void main(String[] args) {
        StdDraw.setCanvasSize((int)canvasWidth, (int)canvasHeight);
        StdDraw.setScale(0, canvasWidth);
        StdDraw.enableDoubleBuffering();

        // Create the players and spawn them in a random location. Range is limited to ensure they do not spawn partially off-screen.
        double randomP1X = (Math.random()*((canvasWidth/2) - 50)) + 25;
        double randomP2X = (Math.random()*((canvasWidth/2) - 50)) + 25;
        double randomP1Y = (Math.random()*(canvasHeight - 50)) + 25;
        double randomP2Y = (Math.random()*(canvasHeight - 50)) + 25;

        P1 = new Player(1, randomP1X, randomP1Y);
        P2 = new Player(2, randomP2X + (canvasWidth/2), randomP2Y);

        double timeElapsed = 0.017;
        boolean isSpacePressed = false;
        boolean isCtrlPressed = false;
        final boolean[] isTimer1Running = {false};
        final boolean[] isTimer2Running = {false};

        while(true) {

            // Player controls for basic movement
            if (StdDraw.isKeyPressed(KeyEvent.VK_W)) {
                P1.moveUp();
            } else if (StdDraw.isKeyPressed(KeyEvent.VK_S)) {
                P1.moveDown();
            }

            if (StdDraw.isKeyPressed(KeyEvent.VK_A)) {
                P1.moveLeft();
            } else if (StdDraw.isKeyPressed(KeyEvent.VK_D)) {
                P1.moveRight();
            }

            if (StdDraw.isKeyPressed(KeyEvent.VK_UP)) {
                P2.moveUp();
            } else if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN)) {
                P2.moveDown();
            }

            if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT)) {
                P2.moveLeft();
            } else if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)) {
                P2.moveRight();
            }

            // Player controls to shoot a laser from the player's square
            if(!StdDraw.isKeyPressed(KeyEvent.VK_SPACE) &&  isSpacePressed && !isTimer1Running[0]){
                Timer timer = new Timer();
                isTimer1Running[0] = true;
                //https://www.iitk.ac.in/esc101/05Aug/tutorial/essential/threads/timer.html
                timer.scheduleAtFixedRate(new TimerTask() {
                    //@Override
                    public void run() {
                        P1.shoot();

                        if (!Laser.isDoesExist()) {
                            timer.cancel();
                            isTimer1Running[0] = false;
                        }
                    }
                }, 0, 1000);
                isSpacePressed = false;
            }
            if(StdDraw.isKeyPressed(KeyEvent.VK_SPACE)){
                isSpacePressed = true;
            }


            if(!StdDraw.isKeyPressed(KeyEvent.VK_CONTROL) &&  isCtrlPressed && !isTimer2Running[0]){
                Timer timer = new Timer(); //https://www.iitk.ac.in/esc101/05Aug/tutorial/essential/threads/timer.html
                isTimer2Running[0] = true;
                timer.scheduleAtFixedRate(new TimerTask() {
                    //@Override
                    public void run() {
                        P2.shoot();

                        if (!Laser.isDoesExist()) {
                            timer.cancel();
                            isTimer2Running[0] = false;
                        }
                    }
                }, 0, 1000);

                isCtrlPressed = false;
            }
            if(StdDraw.isKeyPressed(KeyEvent.VK_CONTROL)){
                isCtrlPressed = true;
            }

            // Draw the map, players/mobs, lasers, and power-ups
            drawMap();
            P1.draw();
            P2.draw();

            StdDraw.show();
            StdDraw.pause((int) (timeElapsed * 1000));
            StdDraw.clear();

        }
    }

    public static void drawMap(){
        // Setting to background to light gray
        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        StdDraw.filledSquare(canvasWidth/2,canvasHeight/2,canvasWidth/2);

        // Line defining the playable area
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledRectangle(canvasWidth/2, canvasHeight/2, 1, canvasHeight/2);
    }
}
