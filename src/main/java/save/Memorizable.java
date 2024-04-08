package save;

/**
 * An object that can save and recover itself from local data
 */
public interface Memorizable {
    /**
     * Store class state data locally
     */
    void memorize();

    /**
     * Extract stored data and recover the class state with it
     *
     * @throws WindowInitException - exception occurred during window initialization
     */
    void dememorize() throws WindowInitException;
}
