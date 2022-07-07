from random import randint 

# (personal prefrence I dislike magic numbers so I put them here )
maxAttempts = 3
minRandNumber = 1
maxRandNumber = 10

# loop until user enters a valid input
def getAndValidateInput():
      while True:
        userNumberInput = input(f"Enter a number to guess: ") 

        #guard clause to validate input because input() return type unknown
        if not userNumberInput.isdigit():
            print("\nFollow the correct input")
            continue
        return userNumberInput
        
    

def GuessANumberGame():

    numberToGuess = randint(minRandNumber, maxRandNumber)
    for i in range(maxAttempts):

        # helper function for readability
        userNumber = getAndValidateInput()

        # check if user's guess is correct
        if int(userNumber) == numberToGuess:
            print("Yes! You got it!")
            break
        
        print("Nope, that's not it")


# check if module is not being imported then run main game loop
if __name__ == "__main__":
    while True:
        
        GuessANumberGame()

        playAgainInput = input("Do you want to play again? (y/anything else to quit): ")

        if not playAgainInput.lower() == "y":
            print("Thanks for playing!")
            break