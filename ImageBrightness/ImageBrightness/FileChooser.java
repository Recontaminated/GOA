/**
 * A class to create a GUI that allows the user of the program to choose a file for
 * the program to manipulate.
 *
 * @author edited by Leah Wolf
 * @author original by Matt Memmo
 * @version February 2020
 */

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;

public class FileChooser extends JPanel implements ActionListener {
    
    private static String newline = "\n";
    private JTextArea log;
    private JFileChooser fc;
     
    public FileChooser() {
        super(new BorderLayout());
     
        //Create the log first, because the action listener needs to refer to it.
        log = new JTextArea(5,20);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);
         
        JButton sendButton = new JButton("Attach...");
        sendButton.addActionListener(this);
         
        add(sendButton, BorderLayout.PAGE_START);
        add(logScrollPane, BorderLayout.CENTER);
    }
     
    public void actionPerformed(ActionEvent e) {
        //Set up the file chooser.
        if (fc == null) {
            //Open a new window to choose a file.
            //It defaults to opening in the current directory
            fc = new JFileChooser(new File(System.getProperty("user.dir")));
             
            //Add a custom file filter and disable the default
            //(Accept All) file filter.
            // fc.addChoosableFileFilter(new ImageFilter());
            fc.setAcceptAllFileFilterUsed(false);
         
        }
     
        //Show it.
        int returnVal = fc.showDialog(FileChooser.this,"Attach");
         
        //Process the results.
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            log.append("Attaching file: " + file.getName() + "." + newline);
            ImageBrightness.lum(file);
        } 
        else {
            log.append("Attachment cancelled by user." + newline);
        }
        log.setCaretPosition(log.getDocument().getLength());      
        
        //Reset the file chooser for the next time it's shown.
        fc.setSelectedFile(null);
    }
     
    /**
    * Create the GUI and show it. For thread safety, this method should be invoked from the
    * event dispatch thread.
    */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("FileChooser");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
        //Add content to the window.
        frame.add(new FileChooser());
         
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
     
    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //  creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                // UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
    }
}
