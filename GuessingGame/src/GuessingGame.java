/**************************************************************************
 *  Author: Jeremy
 *  Date: 6 January, 2022
 *  Course: GOA CS2: Java
 *
 *  Creates a guessing game class that contains the game method as well as a main game loop.
 *  This program was transnlated from my first version in python https://github.com/TomAndJerry342/GOA/blob/master/Day%201/index.py
 *  I tested the following invalid guess inputs: <number> + space, <number> + character, <character>, <space>, null
 *  after I post I want to learn how to use Junit for unit testing.
 *
 *  References:
 *  For config https://www.differencebetween.com/difference-between-static-and-vs-final-in-java/#:~:text=The%20key%20difference%20between%20static,class%20that%20cannot%20be%20inherited
 *  For validating user input is only numbers https://stackoverflow.com/questions/10575624/java-string-see-if-a-string-contains-only-numbers-and-not-letters
 *  For converting string to int https://www.javatpoint.com/java-string-to-int
 *  For generating random numbers https://www.baeldung.com/java-generating-random-numbers-in-range
 *************************************************************************/
import java.util.Random;
import java.util.Scanner;




public class GuessingGame {
    //Start config
    // So much boilerplate in java. I also don't know if im using them correctly
    private static final Boolean instructorMode = true;
    private static final int maxAttempts = 3;
    private static final int minRandNumber = 1;
    private static final int maxRandNumber = 10;
    //end config

    private static int getAndValidateInput(){
        Scanner input = new Scanner(System.in);
        String guess = input.nextLine();

         try {
            return Integer.parseInt(guess);
        }
        catch(Exception e) {
            System.out.println("Please enter a number.");
            // figured out a better way to do this - recurse instead of looping
            return getAndValidateInput();
        }
    }

    public static void guessANumberGame(){
        Random rand = new Random();

        int numberToGuess = rand.nextInt(maxRandNumber  - minRandNumber ) + minRandNumber;

        if (instructorMode) System.out.println("The number to guess is: " + numberToGuess);
        System.out.println("Welcome to the Number Guessing Game!");
        System.out.println("Please guess a number between 1 and 10");

        for (int i = 0; i < maxAttempts; i++){
            int guess = getAndValidateInput();
            if (guess == numberToGuess){
                System.out.println("You guessed the number! Have a cookie \uD83C\uDF6A");
                return;
            }
            else{
                System.out.println("Wrong answer! "+ (maxAttempts - (i+1)) + " attempts remaining");
            }

        }
        System.out.println("You failed to guess the number "+ numberToGuess + "! Participation cookie \uD83C\uDF6A");


    }

    public static void main(String[] args){
        while(true){
            guessANumberGame();
            System.out.println("Would you like to play again? (y/anything else to exit)");
            Scanner input = new Scanner(System.in);
            String playAgain = input.nextLine();
            //Why in why why in java does '==' check for reference and not value.
            if (!playAgain.equals("y")) break;

        }

    }


    }
