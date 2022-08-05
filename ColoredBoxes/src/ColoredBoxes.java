/**
 * Colored box will take a STDIO argument and will produce a box with colored subboxes.
 *
 * Input is validated so that there are only 5 acceptable options
 *
 * Box size is configurable. BUT THE ARRAY OPERATIONS WILL ONYL WORK IF THE ROWS == COLS
 *(impossible to split a rectangle into 2 iso triangles)
 *
 *
 * @author Jeremy
 * @version 7.22.2022
 * Refrences:
 * https://www.w3schools.com/java/java_switch.asp for switch case expression
 */
import java.awt.*;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ColoredBoxes extends JFrame {github

//ONLY HAVE ON ONE AT ONCE
    private static boolean mirrorMode = false;
    private static boolean twoSidesDiffernetColor = false;
    private static boolean fourTriangle = false;
    private static boolean fourTriangleDiffernetColor = false;
    private static boolean DEBUG = false;

    public static void main(String[] args) {
// Let the user choose which one they want to see
        int input;
        Scanner scanner = new Scanner(System.in);
        System.out.println("(1,2,3,4,5) Enter 1 for basic, 2 for mirror mode, 3 to fill both sides with different color, or 4 to create  4 triangles 5 to fill those triangles with different colors");
        input = scanner.nextInt();
        scanner.close();


        switch (input) {
            case 1:
                break;
            case 2:
                mirrorMode = true;
                break;
            case 3:
                twoSidesDiffernetColor = true;
                break;
            case 4:
                fourTriangle = true;
                break;
            case 5:
                fourTriangleDiffernetColor = true;
                break;
            default:
                System.out.println("Invalid input");
                System.exit(418);
        }

        new ColoredBoxes();
    }

    public ColoredBoxes() {
        setTitle("Boxes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new BoxesPanel());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static class BoxesPanel extends JPanel {

        /**
         * TO DO:
         *   Change grid to shape of square by changing the size of the myColors array.
         */
        static final int ROWS = 21;
        static final int COLS = 21;
        static final int BOX_SIZE = 30;




        /**
         * 2D array is created here
         */
        Color[][] myColors = new Color[ROWS][COLS];

        public BoxesPanel() {
//            all the challenge options break because of how array checks are handled when ROWS != COLS
            if ((mirrorMode || twoSidesDiffernetColor || fourTriangle || fourTriangleDiffernetColor) && ROWS != COLS) {
                System.out.println("ERROR: Cannot have rectangular box wile using challenge options. Basic mode will work though.");
                System.exit(0);
            }
//  reassign Color.{color} to variable for readibility
            Color color1 = Color.BLUE;
            Color color2 = Color.MAGENTA;
            Color color3 = Color.GREEN;
            Color color4 = Color.YELLOW;

            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {
                    int c1 = (int) (Math.random() * 255);
                    int c2 = (int) (Math.random() * 255);
                    int c3 = (int) (Math.random() * 255);
//                  test if current index is on any edge
                    if (col == 0 || row == 0 || row == ROWS - 1 || col == COLS - 1) {
                        myColors[row][col] = new Color(255, 0, 0);
                        continue;
                    }
//                  only draw diag line if box is square.
                    if (ROWS == COLS && (row == col)) {
                        myColors[row][col] = new Color(255, 0, 0);
//                        continue statement ensures that other checks are not run.
                        continue;
                    }

//                    if mirror mode, check if square is occupied, then chosose a color and draw it but reflect its position across board.
                    if (mirrorMode) {
                        if (myColors[col][row] == null) {
                            Color color = new Color(c1, c2, c3);
                            myColors[row][col] = color;
                            myColors[col][row] = color;
                        }
                        else {
//                          it is safe to asume  myColors[row][col]; will exist otherwise the first if statemtnt will function as a guard clause
//
                            myColors[col][row] = myColors[row][col];
                        }
                        continue;
                    }

                    if (twoSidesDiffernetColor) {

//                        check if color is already assigned to avoid overwriting our reflected colors
                        if (myColors[row][col]== null){
//                          (this boolean took me 37 minutes of poking and trying out things... im stupid)
                            boolean onRightSide = col > row ;
                            if (onRightSide) {
                                myColors[row][col] = color2;
                                continue;
                            }

                            myColors[row][col] = color1;
                            continue;

                        }
                    }
//                     row + col gives the line -y=x
                    if (fourTriangle){
                        if (myColors[row][col] == null) {
                            if (row + col == ROWS -1){
                                myColors[row][col] = Color.red;
                            }
                        }
                    }


                    if (fourTriangleDiffernetColor){

                        if (myColors[row][col]== null){
                            System.out.println("row: " + row + " col: " + col);
//                         the other line will add up to number of rows on the board. but since we count from 0, we have to subtract 1
                            if (row + col == ROWS - 1){
                                myColors[row][col] = Color.red;
                                continue;
                            }
//                          if the box is on the right side of -y=x then fill it with a color, find the flipped box, and fill it with a different color. also acts as guard clause with continue statement.
                            if (row + col > ROWS - 1){
                                myColors[row][col] = color1;
                                myColors[col][row] = color2;
                                continue;
                            }
//                            theese are boxes on left of y=x
                            myColors[row][col] = color3;
                            myColors[col][row] = color4;
                            continue;

                        }
                    }
//                    prevent us from overriding set colors by first checking if they exist
                    if (myColors[row][col] == null) {
                        myColors[row][col] = new Color(c1, c2, c3);
                    }

                }


            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(COLS * BOX_SIZE, ROWS * BOX_SIZE);
        }

        @Override
        protected void paintComponent(Graphics g) {

            // DO NOT EDIT THIS METHOD

            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();

            int xOffset = (getWidth() - (COLS * BOX_SIZE)) / 2;
            int yOffset = (getHeight() - (ROWS * BOX_SIZE)) / 2;

            // DO NOT EDIT THIS METHOD

            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {
                    g2d.setColor(myColors[row][col]);
                    g2d.fillRect(xOffset + (col * BOX_SIZE), yOffset + (row * BOX_SIZE), BOX_SIZE, BOX_SIZE);
                    g2d.setColor(Color.BLACK);
//                    debug variable to write cooard to box for easier development
                    if(DEBUG){
                        g2d.drawString(row-1+ "," + col, xOffset + (col * BOX_SIZE), yOffset + (row * BOX_SIZE));
                    }

                }
            }
            // DO NOT EDIT THIS METHOD
            g2d.dispose();
        }
    }
}
