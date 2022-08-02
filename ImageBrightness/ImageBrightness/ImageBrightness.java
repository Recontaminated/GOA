/**
 * A class used to calculate the average luminance of an image input to the program
 * by iterating through each pixel in the image, calculating its luminance, and using
 * the results to calculate the average luminance in the image.
 *
 * @author edited by Jeremy Hsieh
 * @author original by Matt Memmo
 * @version 8/12/2022
 */
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;
     
public class ImageBrightness{

    public static double getLuminance(Pixel pixel){
//        https://stackoverflow.com/questions/19689952/how-to-determine-luminance for luminance calculation
        return (double) (0.2126 * pixel.getRed() + 0.7152 * pixel.getGreen() + 0.0722 * pixel.getBlue());
    }
    public static void lum(File imageFile) {
        BufferedImage img = null;
        
        try{
            img = ImageIO.read(imageFile);
//            quick guard to avoid null pointer exception incase image is improperly loaded
            if (img == null){
                throw new Exception("Image has no image data.");

            }

            int width;
            int height;

            width = img.getWidth();
            height = img.getHeight();


//            itinalize arrays to store luminance values to be averaged
            double[] cols = new double[height];
            for (int heightIndex = 0; heightIndex < height; heightIndex++) {
                double[] rows = new double[width];
                for (int widthIndex = 0; widthIndex < width; widthIndex++) {
//                    create a new pixel object at current location and get luminence.
                    Pixel pixel = new Pixel(img.getRGB(widthIndex, heightIndex));
                    double luminance = getLuminance(pixel);
//                  add luminence to array of luminence values for current row. then once we are done with a row we can average it.
                    rows[widthIndex] = luminance;
                }
//                Java's built in Array API provides a convient way to average without for loops. https://www.baeldung.com/java-array-sum-average
                cols[heightIndex] = Arrays.stream(rows).average().getAsDouble();
            }
            double averageLuminance = Arrays.stream(cols).average().getAsDouble();
            System.out.println("The average luminance of the image is: " + averageLuminance);



            
        }
        catch (IOException e){
            System.out.println(e + "\nChoose a file in your directory! " +
                "Don't try to create a new one...");
        }
        catch (Exception e) {
            System.out.println(e + "\nPlease choose an Image file.");
        }
    }
}