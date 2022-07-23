/**
 * JFrame (graphics window) built, then adds a BoardFace to it.
 *
 * TASKS: 
 *    1. Inspect and understand the code within the paint method 
 *       (beginning on line  51).
 *    2. Using your knowledge of shapes, colors, and the coordinate plane,
 *       draw your own picture. 
 *       Remember the links to the documentation on the assignment page.
 *
 * @author Leah Wolf - Adapted from Matt Memmo
 * @version Summer 2020
 */

import java.awt.*;
import java.util.*;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class DrawingFrame extends JFrame {

    //Create & set up the window (JFrame)
    public DrawingFrame(){
        add(new BoardFace());  //Add the drawing Panel onto the Frame
        setTitle("Drawing Window");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500,500);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    public static void main(String[] args) {  //Run the program
        new DrawingFrame();
    }

    private class BoardFace extends JPanel {

        private Dimension d;
        public final int BOARD_WIDTH = 500;
        public final int BOARD_HEIGHT = 500;
        private int x = 0;

        public BoardFace() {
            setFocusable(true);
            //Panel is same size as Frame
            d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
        }

        public void paint(Graphics g){
            final Color skyBlue = new Color(77, 170, 221);
            final Color grass = new Color(117, 201, 32);
            final Color brownHouseColor = new Color(110, 105, 100);
            final Color darkerHouseColor = new Color(57, 43, 39);
            super.paint(g);
//            Fill the screen w/ a white rect and setup background
            g.setColor(Color.white);
            g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
            g.setColor(skyBlue);
            g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT/2);
            g.setColor(grass);
            g.fillRect(0, BOARD_HEIGHT/2, BOARD_WIDTH, BOARD_HEIGHT/2);


//            Draw the sun
            g.setColor(Color.yellow);
            g.fillOval(-25, -25, 125, 125);
//            for loop to draw sun rays
            g.setColor(brownHouseColor);
            g.fillRect(BOARD_WIDTH-200, BOARD_HEIGHT-300, 150, 175);
//           triangle
            g.fillPolygon(new int[]{BOARD_WIDTH-200, BOARD_WIDTH-100, BOARD_WIDTH-200}, new int[]{BOARD_HEIGHT-300, BOARD_HEIGHT-300, BOARD_HEIGHT-200}, 3);


//          g.setColor(Color.black);
//            //face
//            g.setColor(Color.yellow); // yellow is a field of class Color
//            g.fillOval(80,100, 350, 350);
//
//            //eyes
//            g.setColor(Color.black);// ...so is black...
//            g.fillOval(160,175, 50, 50);
//            g.fillOval(290,175, 50, 50);
//
//            //mouth
//            g.setColor(Color.red);// ...and red.
//            g.fillOval(160,275, 175, 125);
//            g.setColor(Color.yellow);   //Hide the top of the oval to make the mouth
//            g.fillOval(160,260, 175, 125);
//
//            //nose
//            Color orange = new Color(255,128,0);//variable orange
//            g.setColor(orange);
//            g.fillOval(240,280,20, 20);
//
//            //hair
//            //brown becomes a new Color object...
//            Color brown = new Color(101,0,0);
//            //...& brown can now be used as parameter in method setColor
//            g.setColor(brown);
//
//            int x = 60;  //x,y, and change in y (dy) - for use in hair loop below
//            int y = 130;
//            int dy = 30;
//            for (int i = 0; i < 9; i++){
//                g.fillOval(x,y, 75, 75);
//                x += 40; //Move to the right for the next tuft of hair
//
//                //make the vertical arch of the hair tufts
//                if (i < 4){
//                    y -= dy;  //Y value goes ^ (remember (0, 0) is at top left)
//                    dy -= 8;
//                }
//                else{
//                    dy += 8;
//                    y += dy;
//                }
//            }
//
//            //hat
//            Color purple = new Color(102,0, 102); //variable purple
//            g.setColor(purple);
//            g.fillRect(75, 75, 375, 50);
//            g.fillRect(150, 25, 225, 100);

            //writing- keep smiling 
            //and to add text to the drawing...
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            g.drawString("Keep Smiling", 20,  420);

            g.dispose();
        }
    }
}