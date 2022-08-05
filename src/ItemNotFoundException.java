package src;

/**
 * Exception for when an Item/Material is not found
 * @author rleboeu
 */
public class ItemNotFoundException extends Exception {
    
    public ItemNotFoundException() {
        super("That item doesn't exist.");
    }

}
