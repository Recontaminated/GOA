/**************************************************************************
 *  Author: Jeremy Hsieh
 *  Date: 7 July 2022
 *  Course: GOA CS2: Java
 *
 *  Finds the hottest month given two arrays of length 12
 *
 * I did this implementation by utilizing loops and two tracker variables. however:
 *  The best way would be to combine the two inputs into the java equavlaent of a python dictionary. Then sort by key (temp) and return the last value which would be the highest.
 *  Unfortunately, I do not know how to work with hashmaps yet and I will figure that out as soon as I finish this assignment.
 *************************************************************************/
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FindLargest {

    private static final  boolean betterMethod = true;
    static String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sep", "Oct", "Nov", "Dec"};
    static int[] temps = {34, 100, 40, 50, 55, 70, 73, 75, 70, 90, 55, 40};

    public  static void main(String[] args) {
//        First make sure array lenghts are both == 12
        if (months.length != 12 || temps.length != 12) {
            System.out.println("Arrays must be of length 12");
            return;
        }


        getHottestMonth();

    }
//(I am not proud of this implementation)
    private static void getHottestMonth() {
        int largestTemp = 0;
        int LargestIndex = 0;
        for (int i = 0; i < temps.length; i++) {
            if (temps[i] > largestTemp) {
                largestTemp = temps[i];
                LargestIndex = i;
            }
        }
        System.out.println("Largest temp is " + temps[LargestIndex] + " degrees in the month of " + months[LargestIndex]);
    }



}
