
/**
 * Implementation for a Panel that allows the user to choose a file to use in the program
 *
 * @author Edited by Leah Wolf
 * @author Original by Matt Memmo
 * @version February 2020
 */

import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;

public class FileChooser extends JPanel implements ActionListener {
    private JButton go;
    static String sourceFolder = "z";
    private String theFile = "";
    private JFileChooser chooser;
    private String choosertitle;
    
    public FileChooser() {
        go = new JButton("Choose Folder");
        go.addActionListener(this);
        add(go);
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Button Clicked");
        chooser = new JFileChooser(); 
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(choosertitle);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG, PNG & GIF Images", "jpg", "gif", "png");
        chooser.setFileFilter(filter);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        // FILES_AND_DIRECTORIES. The default is FILES_ONLY or DIRECTORIES_ONLY
        // disable the "All files" option.
        //
        //chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            String dirr = "" + chooser.getCurrentDirectory();
            File file = chooser.getSelectedFile();
           
            if(dirr.substring(dirr.length()-1,dirr.length()).equals(".")){
                dirr = dirr.substring(0,dirr.length()-1);
                sourceFolder=""+dirr + "" + file.getName();
            }
            else{   
                sourceFolder=""+dirr + "/" + file.getName();
            }
            
            System.out.println("Folder path: " + dirr + " | File Name: " + file.getName());
            System.out.println(sourceFolder);
            // ExamineImage ei = new ExamineImage();
            // ei.lum(sourceFolder);
        }
        else {
            System.out.println("No Selection ");
        }
    }
    
    public Dimension getPreferredSize(){
        return new Dimension(200, 200);
    }
    
    public static void start() {
        JFrame frame = new JFrame("");
        FileChooser panel = new FileChooser();
        frame.addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.getContentPane().add(panel,"Center");
        frame.setSize(panel.getPreferredSize());
        frame.setVisible(true);
    }
}
