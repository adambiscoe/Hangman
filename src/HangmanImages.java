/**
 * Creates and returns the images of the iconic hangman character. After each wrong guess, the character
 * loses a body part. Once the user has used all guesses, the character is fully gone.
 */

public class HangmanImages {
    /**
     * Instantiated in Hangman.java. Once called, method takes amount of mistakes that the user
     * has left, using a switch statement to determine the appropriate character to return.
     * @param mistakesLeft Number of mistakes that the user has left. Starts the game with 6,
     *                     decreasing by 1 with each error.
     * @return A multi-line string representing the hangman character.
     */
    public String fullPerson(int mistakesLeft){
        return switch (mistakesLeft) {
            case 6 -> """
                    |--|
                    |  0
                    | /|\\
                    | / \\
                    |""";
            case 5 -> """
                    |--|
                    |  0
                    | /|\\
                    | / 
                    |""";
            case 4 -> """
                    |--|
                    |  0
                    | /|\\
                    | 
                    |""";
            case 3 -> """
                    |--|
                    |  0
                    | /|
                    | 
                    |""";
            case 2 -> """
                    |--|
                    |  0
                    | /
                    | 
                    |""";
            case 1 -> """
                    |--|
                    |  0
                    | 
                    | 
                    |""";
            case 0 -> """
                    |--|
                    |  
                    | 
                    | 
                    |""";
            default -> null;
        };
    }

}

