import java.awt.*;

public class Wall {
    int x;
    int y;
    int width;
    int height;
    int startX;
    Rectangle hitBox;

    public Wall(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.startX = x;
        this.width = width;
        this.height = height;
        hitBox = new Rectangle(x, y, width, height);
    }

    public void draw(Graphics2D g2) {

        g2.setColor(Color.BLACK);
        g2.drawRect(x, y, width, height);
        g2.setColor(Color.white);
        g2.fillRect(x+1, y+1, width-2, height-2);

    }
    public int updateXScroll(int cameraX){
//        store "orginal lcoation of wall tile"
        x = startX + cameraX;
        hitBox.x = x;
        return x;
    }
}
