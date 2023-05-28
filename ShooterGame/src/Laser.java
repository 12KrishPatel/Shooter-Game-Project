public class Laser {
    // private instance variables, such as x and y coords and defining the speed of the laser
    private double xPos;
    private double yPos;
    private double velocity;
    private static boolean P1Laser = false;
    private static boolean P2Laser = false;
    private boolean P1Landed;
    private boolean P2Landed;
    private static boolean doesExist;
    public Laser(double x, double y) {
        xPos = x;
        yPos = y;
    }

    public static boolean hasP1Shot() {
        return P1Laser;
    }

    public static void setP1Shot(boolean isShot) {
        P1Laser = isShot;
    }

    public static boolean hasP2Shot() {
        return P2Laser;
    }

    public static void setP2Shot(boolean isShot) {
        P2Laser = isShot;
    }

    public double getXPos() {
        return xPos;
    }

    public double getYPos() {
        return yPos;
    }

    public void laserMoving(int user, double timeElapsed) {
        if (user == 1) {
            xPos += 25;

        }else if (user == 2) {
            xPos -= 25;
        }
    }

    public void drawLaser(double xPos, double yPos){
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.picture(xPos, yPos, "laser-fotor-bg-remover-2023041023654.png", 50, 25);
        //StdDraw.filledRectangle(xPos, yPos, 8, 2);
    }

    public void setP1Landed(boolean isLanded) {
        P1Landed = isLanded;
    }

    public boolean isP1Landed() {
        return P1Landed;
    }

    public void setP2Landed(boolean isLanded){
        P2Landed = isLanded;
    }

    public boolean isP2Landed(){
        return P2Landed;
    }

    public void setExists(boolean doesExist) {
        this.doesExist = doesExist;
    }

    public static boolean isDoesExist() {
        return doesExist;
    }
}