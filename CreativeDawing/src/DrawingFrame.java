/**************************************************************************
 *  Author: Jeremy
 *  Date: 7 uly, 2022
 *  Course: GOA CS2: Java
 *
 *  Procedurally generates a surbaban house with a city skyline.
 *
 *  These 14 lines of text are comments. They are not part of the code;
 *  they are here as a note to the reader of the program.
 *      The first 3 lines tell the reader who, when, and why
 *      The next line describes the big picture purpose of the program.
 *
 *  We will always include such lines in our programs
 *************************************************************************/

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class DrawingFrame extends JFrame {
    final Color skyBlue = new Color(77, 170, 221);
    final Color baseGrass = new Color(117, 201, 32);
    final Color brownHouseColor = new Color(20, 105, 100);
    final Color darkerHouseColor = new Color(57, 43, 39);
    final Color[] grays = {new Color(100, 100, 100), new Color(136, 136, 131), new Color(82, 82, 90), new Color(93, 93, 90), new Color(67, 63, 64), new Color(88, 98, 97), new Color(82, 82, 82)};
    final Color[] grassGreens = {new Color(92, 139, 28),new Color(58, 87, 17),new Color(61, 108, 20), new Color(61, 120, 20), new Color(61, 90, 20), new Color(61, 108, 20), new Color(61, 108, 20)};

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
//            Here we initalize our constants for colors and configuration

            super.paint(g);
//            Fill the screen w/ a white rect and setup background
            g.setColor(Color.white);
            g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
            g.setColor(skyBlue);
            g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT/2);
            g.setColor(baseGrass);
            g.fillRect(0, BOARD_HEIGHT/2, BOARD_WIDTH, BOARD_HEIGHT/2);
//            Dynamically generate the background buildings.
            int numberOfBuildings = 25;
            for (int i = 0; i < numberOfBuildings; i ++){
//                Define where the buildings should go
                int lowerBound = BOARD_HEIGHT/2;
                int upperBound = BOARD_HEIGHT;
//                generate random width and position for buildings
                int randomWidth = new Random().nextInt(BOARD_WIDTH/8);
                int randomX = new Random().nextInt(BOARD_WIDTH);
                int randomHeight = -new Random().nextInt(upperBound - lowerBound) + lowerBound;
                Color randomGray = grays[new Random().nextInt(grays.length)];
                g.setColor(randomGray);

                g.fillRect(randomX, lowerBound-randomHeight, randomWidth, randomHeight);



            }

//            texture baseGrass
            for (int i = 0; i < 8000; i++){
                paintSkylineBuilding(g);
            }
//            Draw the sun
            g.setColor(Color.yellow);
            g.fillOval(-25, -25, 125, 125);
            int topLeftOfHouseWidth = new Random().nextInt(BOARD_WIDTH);
            int topLeftOfHouseHeight = new Random().nextInt(BOARD_HEIGHT-200)+200;
            paintHouse(g, topLeftOfHouseWidth, topLeftOfHouseHeight);


            //write text
            //and to add text to the drawing...
            g.setColor(Color.BLUE);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            g.drawString("Procedural Suburban Sunshine", 200,  20);

            g.dispose();
        }
        private void paintHouse(Graphics g, int topLeftOfHouseWidth, int topLeftOfHouseHeight){
            //            Draw the house. Shadow has to come first so house goes on top
            g.setColor(new Color(42, 73, 11));
//            shadow of house
            g.fillOval(topLeftOfHouseWidth-25, topLeftOfHouseHeight+150, 200, 50);
//            draw house main body
            g.setColor(brownHouseColor);
            g.fillRect(topLeftOfHouseWidth, topLeftOfHouseHeight, 150, 175);
//           make the roof of the house
            g.setColor(darkerHouseColor);
            g.fillPolygon(new int[]{topLeftOfHouseWidth-50, topLeftOfHouseWidth+75, topLeftOfHouseWidth+200}, new int[]{topLeftOfHouseHeight, topLeftOfHouseHeight-100, topLeftOfHouseHeight}, 3);
//            coardnates relative to top left of house


//          create windows
            g.setColor(darkerHouseColor);
            g.fillRoundRect(topLeftOfHouseWidth + 20, topLeftOfHouseHeight+20, 25, 25,5,5);
            g.fillRoundRect(topLeftOfHouseWidth + 90, topLeftOfHouseHeight+20, 25, 25,5,5);
            g.fillRoundRect(topLeftOfHouseWidth + 20, topLeftOfHouseHeight+ 60, 25, 25,5,5);
            g.fillRoundRect(topLeftOfHouseWidth + 90, topLeftOfHouseHeight+60, 25, 25,5,5);

//            door
            g.setColor(darkerHouseColor);
            g.fillRect(topLeftOfHouseWidth + 50, topLeftOfHouseHeight+125, 25, 50);
            g.fillOval(topLeftOfHouseWidth + 50, topLeftOfHouseHeight+110, 25, 50);
        }
        private void paintSkylineBuilding(Graphics g) {
            Color randomGreen = grassGreens[new Random().nextInt(grassGreens.length)];
            g.setColor(randomGreen);
            int randomX = new Random().nextInt(BOARD_WIDTH);
            int randomY = new Random().nextInt(BOARD_HEIGHT/2)+BOARD_HEIGHT/2;
//              texture a little bit by turning ovals into crescents
            g.fillOval(randomX, randomY, 5, 5);
            g.setColor(baseGrass);
            g.fillOval(randomX+2, randomY, 5, 5);
        }
    }
}
