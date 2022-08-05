
/*
 *
 * This program finds the largest number in an array of integers.
 *
 * @author Jeremy
 * @version 8/5/2022
 */
import java.util.ArrayList;
import java.util.Arrays;

public class FindLargest {
//    Helper function to find the largest number in an array and return the index
    public static int findLargestIndex(ArrayList<Integer> temps) {
//        check if the user is trying to break me.
        if (temps.size() == 0) {
            System.out.println("Temps array is empty");
            System.exit(1);
        }
// initialize max to first value, and index to 0 then we loop and compare and store larger value in max.
        int max = temps.get(0);
        int index = 0;
        for (int i = 0; i < temps.size(); i++) {
            if (temps.get(i) > max) {
                max = temps.get(i);
                index = i;
            }
        }
        return index;
    }
    public static void main(String[] args){
        String[] ms = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sep", "Oct", "Nov", "Dec"};
        Integer[] ts = {34, 32, 40, 50, 55, 70, 73, 75, 70, 65, 55, 40,123};
        ArrayList<Integer> temps = new ArrayList<Integer>(Arrays.asList(ts));
        ArrayList<String> months = new ArrayList<String>(Arrays.asList(ms));

        int index = findLargestIndex(temps);
// we can have temps array be samller than months because we can index to any value in months if temps is smaller.
// but if temps is bigger than we have to make sure if the user inputs a temp in an index thats larger tan max months index
// it doesnt give us an out-of-bounds exception.

        try {
            System.out.println("The largest temperature is " + temps.get(index) + " degrees F in " + months.get(index));
        }
        catch (IndexOutOfBoundsException e) {
            System.out.println("there was no month found with index " + index + " \nthe last month given was " + months.get(months.size() - 1) + " with a index of " + (months.size() - 1));
            System.exit(1);
        }

    }
}
