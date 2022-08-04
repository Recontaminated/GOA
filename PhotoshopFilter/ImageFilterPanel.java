    
/**
 * The physical pieces in the frame that load the image, filtered image, and the interactive buttons
 *
 * @author Edited by Leah Wolf
 * @author Original by Matt Memmo
 * @version February 2020
 */

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import javax.imageio.stream.*; 

public class ImageFilterPanel extends JPanel implements Runnable, MouseListener {
    
    private Dimension d;
    static int BOARD_WIDTH = 800;
    static int BOARD_HEIGHT = 500;
    private BufferedImage img;
    
    private boolean start = true;
    private boolean imgReady = false;
    private boolean ingame = true;
    
    private Thread animator;
    private int count = 0;
    private String tempS = "z";
    private BufferedImage img2;
    private boolean invertedImgReady = false;
    private ImageFilterer filter = new ImageFilterer();

    int x = 100;
    int y = 200;
    
    public ImageFilterPanel(){
        addKeyListener(new TAdapter());
        addMouseListener(this);
        setFocusable(true);
        d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
        setBackground(Color.black);
           
        if (animator == null || !ingame) {
           animator = new Thread(this);
           animator.start();
        }

        setDoubleBuffered(true);
    }
    
    //Handle open button action. 
    public void selectFile() {
        FileChooser.start();
    }

    public void imageSelect(){
        if(!FileChooser.sourceFolder.equals("z")){ //don't do anything if it's z
            setImages();
        } 
    }  
        
    public void setImages(){
        try {
            img = ImageIO.read(new FileImageInputStream(new File(FileChooser.sourceFolder)));
            tempS = FileChooser.sourceFolder;
            filter.splitRGBs(FileChooser.sourceFolder);
            imgReady = true;
            
        } catch (IOException e) {
            System.out.println("Image could not be read");
        }
    }
    public void image2Ready(){ 
        if(!(ImageFilterer.newImgName.equals("z"))){
            System.out.println("New Image Created");
            try {
                img2 = ImageIO.read(new FileImageInputStream(new File(ImageFilterer.newImgName)));
                invertedImgReady = true;
            } catch (IOException e) {
                System.out.println("Image could not be read");
            }
        }   
    }
    
    public void buildButton(Graphics g, int x, int y, String label){
        //button for inverting image
        g.setColor(Color.gray);
        g.fillRect(x, y, 100, 25);
        g.setColor(Color.white);
        g.drawString(label, x + 5, y + 15);
    }
    
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.white);
        g.fillRect(0, 0, d.width, d.height);
        
        if (ingame) {
            //button for selecting file
            g.setColor(Color.gray);
            g.fillRect(50, d.height-75, 100, 25);
            Font small = new Font("Helvetica", Font.BOLD, 14);
            FontMetrics metr = this.getFontMetrics(small);
            g.setColor(Color.white);
            g.setFont(small);
            g.drawString("Select File", 55, d.height-60);
            //select file

            if (imgReady){
                g.drawImage(img,0,0,400,400 ,null);
                
                if (tempS != FileChooser.sourceFolder){
                    setImages();
                    invertedImgReady = false;
                }
                //button for inverting image
                buildButton(g, 250, d.height - 75, "Invert Image");
                buildButton(g, 350, d.height - 75, "Mirror L-R");  
                buildButton(g, 250, d.height - 50, "Edges");  
                buildButton(g, 350, d.height - 50, "Mirror Diag");  

                if (invertedImgReady){//invert image
                    g.drawImage(img2,420,0,400,400 ,null);
                }
                else{
                    image2Ready(); 
                }
            }
            else{
                imageSelect();
            }
        }
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            // player.keyReleased(e);
            int key = e.getKeyCode(); 
            if(key==39){ }        
            if(key==37){ }
        }

        public void keyPressed(KeyEvent e) {
            System.out.println( e.getKeyCode());
            // message = "Key Pressed: " + e.getKeyCode();
            int key = e.getKeyCode();
        
            if(key==39){} 
            if(key==37){ }
            if(key==38){ }
        }
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        
        //Clicked the setlect file button
        if(mx > 50 && mx < 150 && my > d.height-75 && my < d.height-75 + 25){
            selectFile();
        }
        //Clicked the invert button
        else if(mx > 250 && mx < 350 && my > d.height-75 && my < d.height-75 + 25){
            System.out.println("Invert");
            filter.invertImage();
            filter.createEditedImage("invert");
            image2Ready(); 
        }
        //Clicked the mirror left-right button
        else if (mx > 350 && mx < 450 && my >d.height-75 && my < d.height-75 + 25){
            System.out.println("Mirror Left-Right");
            filter.leftRightMirror();
            filter.createEditedImage("mirrorLR");
            image2Ready(); 
        }
        //Clicked the edges button
        else if(mx > 250 && mx < 350 && my > d.height-50 && my < d.height-50 + 25){
            System.out.println("Edges");
            filter.edges();
            filter.createEditedImage("edges");
            image2Ready(); 
        }
        //Clicked the mirror diagonally button
        else if(mx > 350 && mx < 550 && my > d.height-50 && my < d.height-50 + 25){
            System.out.println("Mirror Diagonally");
            filter.diagMirror();
            filter.createEditedImage("mirrorDiag");
            image2Ready(); 
        }
    }
    
    //Empty interface methods - must be here otherwise compile error!
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}

    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();
        int animationDelay = 10;
        long time = System.currentTimeMillis();
        while (true) {
            // spriteManager.update();
            repaint();
            try {
             time += animationDelay;
             Thread.sleep(Math.max(0,time - 
             System.currentTimeMillis()));
            }catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}