import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Allows the user to enjoy the classic game of Hangman. User gets 6 mistakes with 6 corresponding
 * body parts on the character before they lose the game. All methods used are contained within
 * the Hangman class besides the variable "person" which uses an instance of the HangmanImages class.
 *
 * Based on the MITx 6.00.1x online lesson from edX (Introduction to Computer Science
 * and Programming Using Python). Recreated in Java.
 */

public class Hangman{
    /**
     * Central run point of the program. Initializes the run and welcomes the user to the game.
     * Loads all words from "allWords.txt" and generates a random word by calling "chooseWord()".
     * Lists secret word length. Takes user's guess letter and evaluates its validity, eventually
     * deciding whether it is a part of the secret word before displaying its result. Counts the user's
     * mistakes and once user reaches 6, the game terminates. If the user successfully guesses the word
     * before running out of mistakes, presents user with winning screen.
     * @param args
     */
    public static void main(String[] args){
        ArrayList<String> wordList = loadWords();
        String secretWord = chooseWord(wordList);
        HangmanImages person = new HangmanImages();
        System.out.println("Welcome to the game Hangman!");
        System.out.println("I am thinking of a word that is "+ secretWord.length()+" letters long.");
        int mistakesMade = 0;
        ArrayList<String> lettersGuessed = new ArrayList<>();
        while (6 - mistakesMade > 0) {
            if (isWordGuessed(secretWord, lettersGuessed)){
                System.out.flush();
                System.out.println("\n\n\n------------");
                System.out.println(person.fullPerson(6 - mistakesMade));
                System.out.println("Congratulations, you won!");
                System.out.println("The word was: "+ secretWord +".");
                break;
            }
            else{
                Scanner input = new Scanner(System.in);
                System.out.println("------------");
                System.out.println("You have "+ (6 - mistakesMade)+ " errors left.");
                System.out.println(person.fullPerson(6 - mistakesMade));
                System.out.println("Available letters: "+ getAvailableLetters(lettersGuessed));
                System.out.println("Guessed letters: " + getGuessedLetters(lettersGuessed));
                System.out.println("Please guess a letter: ");
                String entry = input.next().toUpperCase();
                if (secretWord.contains(entry) && !(lettersGuessed.contains(entry))){
                    lettersGuessed.add(entry);
                    System.out.println("\n\n\n\nGood guess: "+ getGuessedWord(secretWord, lettersGuessed));
                }
                else if (lettersGuessed.contains(entry)){
                    System.out.println("\n\n\n\n Oops! You've already guessed that letter: "+ getGuessedWord(secretWord, lettersGuessed));
                }
                else if (!secretWord.contains(entry)){
                    if (entry.length() != 1){
                        System.out.println("\n\n\n\nOops! That entry is not a single letter. Try again.");
                        continue;
                    }
                    try{
                        Integer.parseInt(entry);
                        System.out.println("\n\n\nOops! That entry is not a single letter. Try again.");
                    }catch (NumberFormatException e){
                        System.out.println("\n\n\nOops! That letter is not in my word: "+getGuessedWord(secretWord, lettersGuessed));
                        lettersGuessed.add(entry);
                        mistakesMade++;

                    }
                }
            }
        }
        if (6 - mistakesMade == 0){
            System.out.println("\n\n\n\n------------");
            System.out.println(person.fullPerson(6 - mistakesMade));
            System.out.println("Sorry, you ran out of guesses. The word was "+ secretWord);
            System.out.println(("Game over."));
        }
    }


    /**
     * Loads the words from file "allWords.txt" using a scanner.
     * File copied from MITx 6.00.1x online lesson from edX.
     * Appends all words to a list.
     * @return The list of every word from "allWords.txt".
     */
    public static ArrayList<String> loadWords () {
        System.out.println("Loading word list from file...");
        Scanner x;
        try {
            x = new Scanner(new File("allWords.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ArrayList<String> list = new ArrayList<>();
        while (x.hasNext()){
            String a = x.next();
            list.add(a);
        }
        System.out.println(" "+ list.size()+ " words loaded");
        return list;
    }


    /**
     * Chooses a random word from list of every word generated by loadWords().
     * @param wordList A list of every word generated by loadWords();
     * @return The chosen random word that the user will guess.
     */
    public static String chooseWord(ArrayList<String> wordList) {
        Random random = new Random();
        int x = random.nextInt(wordList.size());
        return wordList.get(x);
    }


    /**
     * Determines if user has guessed the secret word. Loops through the secret word characters
     * and determines if each letter has been guessed yet.
     * @param secretWord Randomly generated word that the user is trying to guess.
     * @param lettersGuessed List of letters the user has already guessed.
     * @return A boolean - false if the secret word has a letter not contained in the letters guessed.
     *                     true if the letters guessed contains every word that the user guessed.
     */
    public static Boolean isWordGuessed (String secretWord, ArrayList<String> lettersGuessed){
        for (int i = 0; i < secretWord.length(); i++) {
            char a = secretWord.charAt(i);
            String z = String.valueOf(a);
            if (!lettersGuessed.contains(z)){
                return false;
            }
        }
        return true;
    }


    /**
     * Generates a string based on each letter of the secret word. Loops through the secret word's
     * characters. If the user has guessed the given letter, the letter is added to a list in
     * the order given by the corresponding secret word. If the user didn't guess the given letter,
     * a '_' has been added to the list at the same point to represent the missing character in the
     * secret word the user needs to guess. After looping, the list is then joined to make a string,
     * separating each letter or '_' with a space.
     * @param secretWord Randomly generated word the user is trying to guess.
     * @param lettersGuessed List of letters the user has already guessed.
     * @return A string composed of letters the user has successfully guessed, separated by spaces
     * and '_' in case the user hasn't guessed the given letter in the secret word.
     */
    public static String getGuessedWord (String secretWord, ArrayList<String> lettersGuessed){
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < secretWord.length(); i++){
            char x = secretWord.charAt(i);
            String y = String.valueOf(x);
            if (lettersGuessed.contains(y)) {
                result.add(y.toUpperCase());
            }
            else {
                result.add("_");
            }
        }
        return String.join(" ", result);
    }


    /**
     * Creates a string of letters that the user has yet to guess. When the user
     * first runs the game, the list is full of every letter in the alphabet, and decreases
     * after each unique guess. Every letter is looped through and if the given letter is not guessed,
     * it is added to a list, that eventually will be joined into a string.
     * @param lettersGuessed List of letters the user has already guessed.
     * @return A string of letters that the user hasn't guessed separated by spaces.
     */
    public static String getAvailableLetters(ArrayList<String> lettersGuessed){
        ArrayList<String> alph = new ArrayList<>();
        alph.add("A");
        alph.add("B");
        alph.add("C");
        alph.add("D");
        alph.add("E");
        alph.add("F");
        alph.add("G");
        alph.add("H");
        alph.add("I");
        alph.add("J");
        alph.add("K");
        alph.add("L");
        alph.add("M");
        alph.add("N");
        alph.add("O");
        alph.add("P");
        alph.add("Q");
        alph.add("R");
        alph.add("S");
        alph.add("T");
        alph.add("U");
        alph.add("V");
        alph.add("W");
        alph.add("X");
        alph.add("Y");
        alph.add("Z");
        ArrayList<String> remain = new ArrayList<>();
        for (String s : alph) {
            if (!lettersGuessed.contains(s)) {
                remain.add(s);
            }
        }
        return String.join(" ", remain);
    }


    /**
     * Creates a string of letters that the user has already guessed. When the user
     * first runs the game, the list is empty and adds by one at each unique guess.
     * Every letter is looped through and if the given letter is guessed,
     * it is added to a list, that eventually will be joined into a string.
     * @param lettersGuessed List of letters the user has already guessed.
     * @return A string of letters that the user hasn't guessed separated by spaces.
     */
    public static String getGuessedLetters(ArrayList<String> lettersGuessed){
        ArrayList<String> alph = new ArrayList<>();
        alph.add("A");
        alph.add("B");
        alph.add("C");
        alph.add("D");
        alph.add("E");
        alph.add("F");
        alph.add("G");
        alph.add("H");
        alph.add("I");
        alph.add("J");
        alph.add("K");
        alph.add("L");
        alph.add("M");
        alph.add("N");
        alph.add("O");
        alph.add("P");
        alph.add("Q");
        alph.add("R");
        alph.add("S");
        alph.add("T");
        alph.add("U");
        alph.add("V");
        alph.add("W");
        alph.add("X");
        alph.add("Y");
        alph.add("Z");
        ArrayList<String> remain = new ArrayList<>();
        for (String s : alph) {
            if (lettersGuessed.contains(s)) {
                remain.add(s);
            }
        }
        return String.join(" ", remain);
    }
}
