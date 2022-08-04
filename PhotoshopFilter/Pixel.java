
/**
 * Class to represent a Pixel. 
 * Users may create pixels using the integer representation of the ARGB components
 * of the pixel and it will be separated into each value distinctly.
 *
 * @author Leah Wolf
 * @version February 2020
 * 
 */
public class Pixel {
    // instance variables - replace the example below with your own
    private int alpha;
    private int red;
    private int green;
    private int blue;

    /**
     * Constructor for objects of class Pixel
     */
    public Pixel(int rgb){
        // initialise instance variables
        alpha = (rgb >>> 24) & 0xff;
        red = (rgb >>> 16) & 0xff;
        green = (rgb >>> 8) & 0xff;
        blue = rgb & 0xff;
    }
    
    // initialise as a black pixel
    public Pixel(){
        alpha = 0;
        red = 0;
        green = 0;
        blue = 0;
    }
    
    //Copy the values from Pixel p to me
    public void copy (Pixel p){
        this.alpha = p.alpha;
        this.red = p.red;
        this.green = p.green;
        this.blue = p.blue;
    }
    
    // Shifting the bits so the red is to the left, 
    // green in the middle, blue on the right.
    public int backToInt(){
        int color = (alpha << 24) |(red << 16) | (green << 8) | blue;
        return color;
    }
    
    //Set each value, only if in correct range
    public void setRGB(int r, int g, int b){
        setRed(r);
        setGreen(g);
        setBlue(b);
    }
    
    public void setARGB(int a, int r, int g, int b){
        setAlpha(a);
        setRed(r);
        setGreen(g);
        setBlue(b);
    } 
    
    public void setAlpha(int n){
        if (n >= 0 && n <= 255){
            alpha = n;
        }
    }
    
    public void setRed(int n){
        if (n >= 0 && n <= 255){
            red = n;
        }
    }
    
    public void setGreen(int n){
        if (n >= 0 && n <= 255){
            green = n;
        }
    }
    
    public void setBlue(int n){
        if (n >= 0 && n <= 255){
            blue = n;
        }
    }
    /**
    * Method to get the distance between this pixel's color and the passed color
    * @param testColor the color to compare to
    * @return the distance between this pixel's color and the passed color
    */
    public double colorDistance(Pixel testColor){
        double redDistance = this.getRed() - testColor.getRed();
        double greenDistance = this.getGreen() - testColor.getGreen();
        double blueDistance = this.getBlue() - testColor.getBlue();
        double distance = Math.sqrt(redDistance * redDistance + 
                                   greenDistance * greenDistance +
                                   blueDistance * blueDistance);
        return distance;
    }
 
    /**
    * Method to compute the color distances between two color objects
    * @param color1 a color object
    * @param color2 a color object
    * @return the distance between the two colors
    */
    public static double colorDistance(Pixel color1, Pixel color2){
        double redDistance = color1.getRed() - color2.getRed();
        double greenDistance = color1.getGreen() - color2.getGreen();
        double blueDistance = color1.getBlue() - color2.getBlue();
        double distance = Math.sqrt(redDistance * redDistance + 
                                   greenDistance * greenDistance +
                                   blueDistance * blueDistance);
        return distance;

    }
    
    //Getters for the instance variables below - allow access without allowing outside changing
    public int getAlpha(){
        return alpha;
    }
    
    public int getRed(){
        return red;
    }
    
    public int getGreen(){
        return green;
    }
    
    public int getBlue(){
        return blue;
    }
}
