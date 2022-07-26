/**
 * This is a program that plots city measurememnts  over the course of 12 months.
 *
 * the number of measurements per city must be consistant across all cities
 *
 * This code is cool because it dynamically scales the graph to fit the window size. (try using your cursor to resize the screen)
 *
 * Furthermore, you can add more cities to test. Ive added a commented seciton of code for LA. just uncomment the previous two lines and comment the next two lines that include LA.
 *
 * I've also changed the orginal code so that it nothing is hard coded. Most options are configurable. And the main funciton creates a bargraph that takes 2 arguements.
 * All the data which is a 2d array consisting of each city, and an array that contains the names of each city.
 *
 *
 *
 * @author Jeremy Hsieh
 * @version 7/26/2022
 */

import javax.swing.*;
import java.awt.*;

public class BarGraph extends JFrame{
//    config
////////////////////////////////////////////////////////////

 ///////////////////////////////////////////////////////////

    private JFrame frame;

    public BarGraph(double[][] all, String[] namesOfCities) {
        int[]widthHeight = {600,400};
        setTitle("Bar Graph");
        setSize(widthHeight[0], widthHeight[1]);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(getSize());

        add(new DrawBars(getSize(), all,namesOfCities));
        pack();
        setVisible(true);
    }


    public static void main(String... argv) {
//        USER CONFIGURABLE ///////////////////////////////////////////////////////////
        double[] dc = {2.79, 3.21, 2.31, 6.30, 2.49, 3.51, 6.51, 8.73, 5.53, 4.86, 6.14, 4.96};
        double[] seattle = {9.23, 3.63, 3.72, 2.71, 1.94, 1.57, .70, .88, 1.50, 3.48, 6.57, 5.35};
        double[] la = {3.79, 1.21, 2.31, 2.30, 2.49, 1.51, 2.51, 8.73, 9.53, 7.86, 8.14, 9.96};
        double[][] all = {dc,seattle};
        String[] namesOfCities = {"Washington", "Seattle"};

//        uncomment this section to try the program with 3 categories! remember to comment the same ones above.
//        double[][] all = {dc,seattle,la};
//        String[] namesOfCities = {"Washington", "Seattle", "Los Angeles"};


//        END USER CONFIGURABLE ////////////////////////////////////////////////////////


        for (int i = 0; i < all.length; i++) {
            if (all[i].length != 12) {
                System.out.println("Error: array length does not match 12");
                System.exit(418 );
            }
        }
        if (namesOfCities.length != all.length) {
            System.out.println("Error: number of city Names does not match number of cities");
            System.exit(418 );
        }
        
        new BarGraph(all,namesOfCities);
    }
    public static class DrawBars extends JPanel {

//        init all static variables
        public final int bottomPadding = 100;
        private double[][] all;
        private String[] namesOfCities;
        private double max;
        private final int scaleFactor = 15;
        private final String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        public int getIntMax(int[][] arr) {
            int max = arr[0][0];
            for (int outerIndex = 0; outerIndex < arr.length; outerIndex++) {
                for (int innerIndex = 0; innerIndex < arr[outerIndex].length; innerIndex++) {
                    if (arr[outerIndex][innerIndex] > max) {
                        max = arr[outerIndex][innerIndex];
                    }
                }
            }

            return max;
        }
        public double getDoubleMax(double[][] arr) {
            double max = arr[0][0];
            for (int outerIndex = 0; outerIndex < arr.length; outerIndex++) {
                for (int innerIndex = 0; innerIndex < arr[outerIndex].length; innerIndex++) {
                    if (arr[outerIndex][innerIndex] > max) {
                        max = arr[outerIndex][innerIndex];
                    }
                }
            }

            return max;
        }

//        draw tick is in it's own method for readibility. Iterates through position given and draws ticks stepping down uniformly until bottomPixel.
        public void drawTick(int x, int y, Graphics g, int BottomPixel, double max) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 12));
            final int numTicks = 4;
            double  tick =  max / numTicks;
            int pixelsPerTick = (y - BottomPixel) / numTicks;

            for (int tickIndex = 0; tickIndex < numTicks; tickIndex++) {
                g.fillRect(x-10,y-pixelsPerTick*tickIndex,20,4);
                g.drawString(Integer.toString((int) (max - (tick*tickIndex))), x-35, y-pixelsPerTick*tickIndex+5);

            }


        }

        public DrawBars(Dimension dimension, double[][] array, String[] namesOfCities) {
//          pass through user input variables to instance variables using this keyword
            this.namesOfCities = namesOfCities;
            this.all = array;
            setSize(dimension);
            setPreferredSize(dimension);


        }

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;  //g2 is the graphics object that we need to use
            //to draw things to the screen
            //create a background
            g2.setColor(Color.white);
            Dimension d = getSize();
            g2.fillRect(0, 0, d.width, d.height);
            //make some colors. This is expandable, if we want more than 3 cities just add more colors.
            Color navy = new Color(4, 30, 66);
            Color iceBlue = new Color(153, 217, 217);
            Color green = Color.PINK;

//            store colors in an array so we can iterate through them programatically.
            Color[] colors = {navy, iceBlue,green};

            if (all.length > colors.length) {
                System.out.println("I dont have enough colors to show this. please add more colors to me!");
                System.exit(418 );
            }
//            create new empty scaled array with dimentions identical to orginal array.
            int[][] scaled = new int[all.length][all[0].length];

//            scale the array to fit the screen.
            for(int outerIndex = 0; outerIndex < all.length; outerIndex++){
                 double[] arr = all[outerIndex];
                    for(int innerIndex = 0; innerIndex < arr.length; innerIndex++){

                        scaled[outerIndex][innerIndex] = (int) (arr[innerIndex] * scaleFactor);
                    }
             }
//            get the max in preperation for drawing tick marks
            int scaledMax = getIntMax(scaled);

            System.out.println("scaled max is: " + scaledMax);
            g2.setColor(Color.black);
//            draw itinal line vertical. "d.height-bottomPadding-scaledMax" is for dynamically positioning it so when you resize the window it stays with the graph.
            g2.fillRect(50, d.height-bottomPadding-scaledMax,5 , scaledMax );
            drawTick(50, d.height-bottomPadding-scaledMax, g2, d.height-bottomPadding, getDoubleMax(all));
//            we have 12 catagories so we split width and height by 12
            int margins = 80;

//            spacing is the distance needed between each set of bars. bar widith is each bar's width.
            int spacing = (d.width - margins) / 12;
            int barWidth =  spacing / 4;

//          Draw the bars
            int xPos = margins;

            for(int outerIndex = 0; outerIndex < scaled.length; outerIndex++){
                int[] arr = scaled[outerIndex];
                for(int innerIndex = 0; innerIndex < arr.length; innerIndex++){
                    int barHeight = scaled[outerIndex][innerIndex];
//                    quick check that we have 12 buckets because number of months is 12.
                    if (outerIndex == 0 && months.length == 12){

                        g2.setColor(navy);
                        g2.setFont (new Font("Arial", Font.BOLD, 11));
//                        the move text to same vertical position as bar, then shift 15 px down
                        g2.drawString(months[innerIndex], xPos,  d.height-bottomPadding + 15);
                    }

//                    dynamically set colors as needed
                    g2.setColor(colors[outerIndex]);
                    g2.fillRect(xPos, d.height- barHeight-bottomPadding, barWidth, barHeight);
//                    new bars should be "spacing" apart from each other
                    xPos = xPos + spacing;
                }
//                this is important. When done drawing first set bars, reset to the left of display, offset by barwidth, and continue
                xPos = margins + (barWidth * (outerIndex+1));

            }


            //display text
//          refactored orginal code to programatically display cities so its configurable and scalable beyond comparing just 2 cities.
            for (int i = 0; i < namesOfCities.length; i++) {
                g2.setColor(colors[i]);
                g2.drawString(namesOfCities[i], (30 + i*100), 30);
            }
        }
    }
}