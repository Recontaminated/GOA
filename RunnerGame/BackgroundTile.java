import java.awt.*;
import java.awt.image.BufferedImage;

public class BackgroundTile {
    int x1;
    int y1;
    int x2;
    int width;
    double speed;
    int height = 1000;
    BufferedImage image;

    public BackgroundTile(int x1, int y1, int width, double speed, BufferedImage image) {
        this.speed = speed;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = width;
        this.width = width;
        this.image = image;
    }
    public void draw(GameWindow window, Graphics2D g2, int cameraX) {
        BufferedImage dbImage = new BufferedImage(window.getWidth(), window.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics dbg = dbImage.getGraphics();


//        get current time in ms
//        TODO: fix experimental tiling
        long currentTime = System.currentTimeMillis();

        dbg.drawImage(image, (int) (x1 *speed), y1,3000,1000, null);
        dbg.drawImage(image, (int) (x2 *speed), y1, 3000,1000,null);
//        Currently the tiling does not work properly
        if ((-x1*speed)% this.width> width- 100){
            System.out.println("GETTING CLOSE MOVING X1");
            System.out.println("TELEPOITING x1 TO "+ x2);
            x1 = x2;
        }
        else if ((-x2*speed )% this.width> width- 100){
            System.out.println("GETTING CLOSE MOVING X2");
            System.out.println("TELEPOITING x2 TO "+ x1);
            x2 = x1;
        }
        g2.drawImage(dbImage, 0, 0, window);

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - currentTime;




    }
    public void setX(int x1){
        int previousX1 = this.x1;
        this.x1 = x1;
        this.x2 = x2 + x1 - previousX1;

    }
}
