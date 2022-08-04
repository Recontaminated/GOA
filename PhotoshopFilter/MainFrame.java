/**
 * Main Frame creates the main frame of the program
 * Run the program by calling main in this class.
 *
 * @author Edited by Leah Wolf
 * @author Original by Matt Memmo
 * @version February 2020
 */

import javax.swing.JFrame;

public class MainFrame extends JFrame {

    public MainFrame()
    {
        add(new ImageFilterPanel());
        setTitle("Image Inverter");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800,500);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}