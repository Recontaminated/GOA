
/**
 * Image Filterer to have functionality to
 * invert an image,
 * mirror an image left to right,
 * mirror an image diagonally,
 * create an image from edges detected in the original.
 *
 * @author edited by Jeremy Hsieh
 * @author original by Matt Memmo
 * @version August 3rd 2022
 */

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageFilterer {

    private Pixel[][] imgPixels;
    private Pixel[][] newPixels;
    private int width = 0;
    private int height = 0;

    public static String newImgName = "z";

    /**
     * PRE: origFile is a String filename of a picture file (gif, jpg, or png)
     * POST: imgPixels stores the corresponding components for each pixel in the image.
     *      imgPixels   -> 2D array of each pixel in the image  
     */
    public void splitRGBs(String origFile) {
        BufferedImage img = null;

        File myFile = new File(origFile);
        try {
            img = ImageIO.read(myFile);

            if (img != null) {
                width = img.getWidth();
                height = img.getHeight();
            }
            System.out.println("Width " + width + " " + height);

            imgPixels = new Pixel[height][width];
            newPixels = new Pixel[height][width];

            //Read the pixels of the image into a 2D array of Pixel objects.
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    Pixel p = new Pixel(img.getRGB(col, row));
                    imgPixels[row][col] = p;
                }
            }
        }
        catch (Exception e) {
            System.out.println("Exception caught: " + e);
        }
    }

    /**
     * Invert the image's pixels!
     */
    public void invertImage() {

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                newPixels[row][col] = new Pixel();
//              Store all inverted colors in variables for reability and to avoid magic numbers
                int invertedRed = 255 - imgPixels[row][col].getRed();
                int invertedGreen = 255 - imgPixels[row][col].getGreen();
                int invertedBlue = 255 - imgPixels[row][col].getBlue();
                newPixels[row][col].setRGB(invertedRed, invertedGreen, invertedBlue);

            }
        }

    }

    /**
     * Mirrors the left side of the image onto the right.
     */
    public void leftRightMirror() {

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
//                subtract by 1 to avoid overcounting and gettting null index
//                Region of interest is only left half of the image. The right half is copied from the left
                if (col > width / 2) {
                    continue;
                }
//          create pixels mirrirong the vertical centerline
                newPixels[row][col] = new Pixel();
                newPixels[row][width - col - 1] = new Pixel();
//          copy first pixel as usual, second one to mirrored location
                newPixels[row][col].copy(imgPixels[row][col]);
                newPixels[row][width - col - 1].copy(imgPixels[row][col]);
            }
        }

    }

    /**
     * Mirrors a square part of the picture from bottom left to top right around a mirror placed on the 
     * diagonal line. This will copy the triangular area to the left and below the diagonal line. 
     * This is like folding a square piece of paper from the bottom left to the top right, 
     * painting just the bottom left triangle and then (while the paint is still wet) folding the paper up 
     * to the top right again. The paint would be copied from the bottom left to the top right
     *
     */
    public void diagMirror() {
        int SquareSideLength;
//        Find the smaller of the two values
        if (width > height) {
            SquareSideLength = height;
        }
        else {
            SquareSideLength = width;
        }
        System.out.println("SquareSideLength: " + SquareSideLength);
        for (int row = 0; row < height; row++) {

            for (int col = 0; col < width; col++) {
//                Region of interest is square. Ignore everythign to the right of the square
                if (col >= SquareSideLength || row >= SquareSideLength) {
                    newPixels[row][col] = new Pixel();
                    newPixels[row][col].copy(imgPixels[row][col]);
                    continue;
                }
//                row < col defines the bottom left triangle. if pixel is in bottom left, then copy the pixel from the inverse of the diagonal line.
                if (row < col) {
                    newPixels[row][col] = new Pixel();
                    newPixels[row][col].copy(imgPixels[col][row]);
                    continue;

                }
//                Othersiwe copy the pixel as normal
                newPixels[row][col] = new Pixel();
                newPixels[row][col].copy(imgPixels[row][col]);

            }
        }

    }

    /**
     * Compare the color at the current pixel with the pixel in the next column to the right. 
     *
     * If the colors differ by more than some specified amount, this indicates that an edge has been 
     * detected and the current pixel color should be set to black. Otherwise, the current pixel is not 
     * part of an edge and its color should be set to white. 
     *
     * You can use the Pixel class' colorDistance method to calculate the difference between two colors. 
     *
     * Compare the current pixel with the one below and sets the current pixel color to black when the 
     * color distance is greater than the specified edge distance.
     */
    public void edges() {
        int edgeDist = 9;  //You can change this value to determine the best value.

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
//                    quick check to make sure we are not working on the right most collum of pixels because there is no pixel to the right we can check
                if (col == width - 1) {
                    newPixels[row][col] = new Pixel();
                    continue;
                }
//                    get color distance of pixel and next pixel to the right and check if its greater than edge dist.
                if (imgPixels[row][col].colorDistance(imgPixels[row][col + 1]) > edgeDist) {
                    newPixels[row][col] = new Pixel();
                    newPixels[row][col].setRGB(0, 0, 0);
                }
                else {
                    newPixels[row][col] = new Pixel();
                    newPixels[row][col].setRGB(255, 255, 255);

                }

            }

        }

    }

    /**
     * From the newPixels array, create a new image.
     */
    public void createEditedImage(String fileName) {

        //create new image using new values
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Pixel p = newPixels[row][col];
                img.setRGB(col, row, p.backToInt());
            }
        }
        File f = new File(fileName + ".jpg");
        try {
            ImageIO.write(img, "JPEG", f);
            newImgName = fileName + ".jpg";
        }
        catch (Exception e) {
            System.out.println("Exception caught: " + e);
            System.out.println("Image could not be saved.");

        }
    }
}