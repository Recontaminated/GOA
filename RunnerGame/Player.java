import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player {

    GameWindow gameWindow;
    int x;
    int y;
    double velocityX = 0;
    double velocityY = 0;

    int width;
    int height;

    Rectangle hitBox;
    boolean doubleJumpAvailable = true;
    BufferedImage playerImage;

    public Player(int x, int y, GameWindow gameWindow) {
//      very helpful to have gamewindow object for accessing wall hiboxes etc
        this.gameWindow = gameWindow;
        this.x = x;
        this.y = y;
        try {
//            load the player avatar from png and caluclate hitbox for it
            playerImage = ImageIO.read(getClass().getResource("assets/player.png"));
        }
        catch (IOException e) {
            System.out.println("Error: " + e);
        }
        width = playerImage.getWidth();
        height = playerImage.getHeight();
        hitBox = new Rectangle(x, y, width, height);


    }

    void drawPlayer(Graphics2D g2) {
        g2.drawImage(playerImage, x, y, width, height, null);
    }
//        g2.setColor(Color.RED);
//        g2.fill(new  Rectangle2D.Double(x,y , 50, 50));


    public boolean isTouchingGround(GameWindow gameWindow) {
            hitBox.y ++;
            for (int i = 0; i < gameWindow.walls.size(); i++) {
                Wall wall = gameWindow.walls.get(i);
//                https://docs.oracle.com/javase/7/docs/api/java/awt/Rectangle.html thank god we can use rectangle intersection method to check for hitbox collision

                if (wall.hitBox.intersects(hitBox)){
                    hitBox.y --;
                    return true;
                }
            }
            hitBox.y --;
            return false;

    }
//useful setter methods
    public void changeVelocity(double x, double y) {
        this.velocityX += x;
        this.velocityY += y;
    }
    public void setVelocity(double x, double y) {
        this.velocityX = x;
        this.velocityY = y;
    }
    public void updatePlayerPos() {
        x += velocityX;
        this.y += this.velocityY;
        hitBox.x = x;
        hitBox.y = y;

    }
}
