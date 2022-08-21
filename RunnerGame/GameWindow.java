import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
// Runnable class for running inside a thread
//extend Jfrmae for parent properties
public class GameWindow extends JFrame implements Runnable {
//    initialize game constants

    double gravity = 0.75;
    Graphics2D g2;
    int width = 1000; //changing these values WILL break the textures Please dont change theese
    int height = 1000;
    final int FPS = 60; // The game speed is coupled to fps. avoid changing.
//    initialize game variables
    Player player;
    ArrayList<Wall> walls = new ArrayList<>();
    KeyHandler keyHandler = new KeyHandler();
    int cameraX;
    int offsetWalls;
    int score = 0;
    int offsetBGTiles;
    ArrayList<ArrayList<Wall>> chunks = ChunkLoader.chunks();

//Paralax background. Bglayer 1 is root
    BackgroundTile bgLayer1;
    BackgroundTile bgLayer2;



    public GameWindow() {
//   initialize game window by setting defaults and adding keylistener
        this.setResizable(false);
        this.setTitle("Window");
        this.setSize(width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//      https://stackoverflow.com/questions/10876491/how-to-use-keylistener
        this.addKeyListener(keyHandler);
        this.setVisible(true);
        this.g2 = (Graphics2D) this.getGraphics();
        this.g2.setFont(new Font("Arial", Font.BOLD, 70));
        player = new Player(width / 2, height / 2, this);
        int tileWidth = 3000;
        int tileHeight = 1000;
        try {
            bgLayer1 = new BackgroundTile(0, 0, 3000, 0.4, toCompatibleImage(ImageIO.read(getClass().getResource("assets/background1texture.png"))));
            bgLayer2 = new BackgroundTile(0, 0, 3000, 0.6, toCompatibleImage(ImageIO.read(getClass().getResource("assets/background2texture.png"))));
        }
        catch (IOException e) {
         System.exit(1);
        }


        //        create itinal walls
        resetPlayer();
    }

    private void makeWalls(int xScroll) {
//        size of walls
        int s = 50;
        Random rand = new Random();
        int randomIndex = rand.nextInt(14);
        System.out.println("making more walls");


        System.out.println("Spawning chunk: " + randomIndex + "at X scroll " + xScroll);
        ArrayList<Wall> chunk = chunks.get(randomIndex);
        System.out.println("Chunk size: " + chunk.size());
        for (int i = 0; i < chunk.size(); i++) {
            Wall wall = chunk.get(i);
            walls.add(new Wall(((wall.x*50) + xScroll), ((wall.y*50)-300), wall.width, wall.height));
        }
    }

    public void tick() {
        Image dbImage = createImage(getWidth(), getHeight());

        Graphics dbg = dbImage.getGraphics();
        scrollBuffer();
        scrollWalls();
        this.tickGame(dbg);

        g2.drawImage(dbImage, 0, 0, this);

    }

    //we use double buffering to avoid flickering and to make everything smooth like butter
//https://www.iitk.ac.in/esc101/05Aug/tutorial/2d/images/doublebuffering.html#:~:text=When%20a%20graphic%20is%20complex,is%20often%20used%20for%20animations.
    private void tickGame(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        bgLayer1.setX(cameraX);
        bgLayer1.draw(this, g2, cameraX);
        bgLayer2.setX(cameraX);
        bgLayer2.draw(this, g2, cameraX);



        boolean isTouchingGround = player.isTouchingGround(this);
        if (isTouchingGround) {
            player.doubleJumpAvailable = true;
        }
        if (player.x -cameraX > score) score = -cameraX;
        //        write text and make nice backround for text
        g2.setColor(new Color(37,44,69));
        g2.fillRoundRect(20, 65, width-40, 60, 20, 20);
        g2.setColor(Color.white);
        g2.drawString("A - left, Space - Jump, D - Right", width/2, 100);
        if (player.doubleJumpAvailable){
            g2.setColor(Color.WHITE);
            g2.drawString("Double Jump", getWidth() - 200, 100);
        }


        else {
            g2.setColor(Color.GRAY);
            g2.drawString("Double Jump", getWidth() - 200, 100);
        }


        g2.setColor(Color.WHITE);
        g2.drawString("Distance: " + score + "m", 100, 100);
        if (walls.get(walls.size() - 1).x < width + 100) {
            offsetWalls += width;
            System.out.println("running out of space making more walls");
            makeWalls(offsetWalls);
        }


        player.changeVelocity(0, gravity);


            if (player.velocityX > 0) {
                player.changeVelocity(-0.3, 0);
            }
            if (player.velocityX < 0) {
                player.changeVelocity(0.3, 0);
            }
            else {
                player.changeVelocity(0, 0);
            }

        if (keyHandler.isKeyPressed(KeyEvent.VK_D)) {
            player.changeVelocity(0.75, 0);
        }
        if (keyHandler.isKeyPressed(KeyEvent.VK_A)) {
            player.changeVelocity(-0.75, 0);
        }

        if (keyHandler.isKeyPressed(KeyEvent.VK_SPACE)) {
//          this will maek the player tocuh the ground instead of 1 px abouve because we are using rect.interrcets method
            player.hitBox.y ++;
//            player.doubleJumpAvailable = true;
            for (Wall wall: walls){
//                https://docs.oracle.com/javase/7/docs/api/java/awt/Rectangle.html thank god we can use rectangle intersection method to check for hitbox collision
                boolean isTouchingFloor = wall.hitBox.intersects(player.hitBox);
                if (isTouchingFloor || player.doubleJumpAvailable){
                    if (isTouchingFloor){

                        player.setVelocity(player.velocityX, -10);
                        break;
                    }
//                    player.velocityY > 5 is a crude way to implement debounce
                    else if(player.doubleJumpAvailable && player.velocityY > 2){
                        player.setVelocity(player.velocityX, -10);

                        player.doubleJumpAvailable = false;
                    }


                }
            }

            player.hitBox.y --;
//            player.setVelocity(player.velocityX, -10);

        }

        if (player.velocityX > 7) {
            player.setVelocity(7, player.velocityY);
        }
        else if (player.velocityX < -7) {
            player.setVelocity(-7, player.velocityY);
        }
//        Horizontal colision

        player.hitBox.x += player.velocityX;
        for (Wall wall : walls) {

            if (player.hitBox.intersects(wall.hitBox)) {
//             move as close to the wall as possible without going through it
                player.hitBox.x -= player.velocityX;
                while (!wall.hitBox.intersects(player.hitBox)) {
//                    how to step 1 depending on if positive negative or zero using speed https://www.geeksforgeeks.org/java-signum-method-examples/
                    player.hitBox.x += Math.signum(player.velocityX);
                }
                player.hitBox.x -= Math.signum(player.velocityX);
                player.setVelocity(0, player.velocityY);
                player.x = player.hitBox.x;
            }
        }
//        Vertical colision

        player.hitBox.y += player.velocityY;
        for (Wall wall : walls) {
            if (player.hitBox.intersects(wall.hitBox)) {
//             move as close to the wall as possible without going through it
                player.hitBox.y -= player.velocityY;
                while (!wall.hitBox.intersects(player.hitBox)) {
//                    how to step 1 depending on if positive negative or zero using speed https://www.geeksforgeeks.org/java-signum-method-examples/
                    player.hitBox.y += Math.signum(player.velocityY);
                }
                player.hitBox.y -= Math.signum(player.velocityY);
                player.setVelocity(player.velocityX, 0);
                player.y = player.hitBox.y;
            }
        }

        player.updatePlayerPos();
        if (player.y > height + 300) {
            resetPlayer();
        }
        for (int i = 0; i < walls.size(); i++) {
            Wall wall = walls.get(i);
            if (wall.x < -width - 100) {
                walls.remove(i);
            }
        }
        //        draw stuff
        for (Wall wall : walls) {
            wall.draw(g2);
        }
        // update game logic

        player.drawPlayer(g2);
    }

    public void run() {
        int framesPast = 0;
        long previousTime = System.currentTimeMillis();

        while (true) {
            long currentTime = System.currentTimeMillis();
            if (currentTime >= previousTime + 1000) {
                System.out.println(framesPast + "fps");
                framesPast = 0;
                previousTime = System.currentTimeMillis();
            }
            try {
                long startTime = System.currentTimeMillis();
                tick();
                long elapsedTime = System.currentTimeMillis() - startTime;

                long timeToSleep = (1000 / FPS) - elapsedTime;

                if (timeToSleep > 0) {
                    Thread.sleep(timeToSleep);
                }
                else {
                    System.out.println("Tick took too long");
                }

                framesPast++;
            }
            catch (InterruptedException e) {
            }

        }
    }

    private void scrollBuffer() {
        if (player.x > getWidth() - 200) {
            cameraX = getWidth() - player.x - 200 + cameraX;
            player.x = getWidth() - 200;
            player.hitBox.x = player.x;

        }
        if (player.x < 200) {
            cameraX = -player.x + 200 + cameraX;
            player.x = 200;
            player.hitBox.x = player.x;

        }
    }

    private void scrollWalls() {
        for (int i = 0; i < walls.size(); i++) {
            walls.get(i).updateXScroll(cameraX);
        }
    }

    public void resetPlayer() {
        player.x = 500;
        player.y = 150;
        player.velocityX = 0;
        player.velocityY = 0;
        cameraX = 0;
        offsetWalls = 0;
        score = 0;
        walls.clear();
        for (int i = 0; i < 20; i++) {
            walls.add(new Wall(offsetWalls + i * 50, 600, 50, 50));
        }
        for( int i = 550; i >= 350; i -= 50){
            walls.add(new Wall(0, i, 50, 50));
        }
        for( int i = 550; i >= 350; i -= 50){
            walls.add(new Wall(50, i, 50, 50));
        }
        for( int i = 550; i >= 350; i -= 50){
            walls.add(new Wall(100, i, 50, 50));
        }
        for( int i = 550; i >= 350; i -= 50){
            walls.add(new Wall(150, i, 50, 50));
        }



    }
    public BufferedImage toCompatibleImage(BufferedImage image) {
        // obtain the current system graphical settings
        GraphicsConfiguration gfx_config = GraphicsEnvironment
                .getLocalGraphicsEnvironment().getDefaultScreenDevice()
                .getDefaultConfiguration();



        //if image is already compatible and optimized for current system
        //settings, simply return it

        if (image.getColorModel().equals(gfx_config.getColorModel())) {

            return image;
        }

        // image is not optimized, so create a new image that is
        BufferedImage new_image = gfx_config.createCompatibleImage(
                image.getWidth(), image.getHeight(), Transparency.TRANSLUCENT);

        // get the graphics context of the new image to draw the old image on
        Graphics2D g2d = (Graphics2D) new_image.getGraphics();

        // actually draw the image and dispose of context no longer needed
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        // return the new optimized image
        // System.out.println(image.getColorModel());
        return new_image;
    }

}
