package src;

/**
 * Exception for when a player's hand is empty
 * @author rleboeu
 */
public class EmptyHandException extends Exception {
    
    public EmptyHandException() {
        super("Your hand is empty.");
    }

}
