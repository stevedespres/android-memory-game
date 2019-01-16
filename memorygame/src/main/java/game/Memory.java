package game;

import com.example.usrlocal.memory.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Memory implements Serializable {

    //Instance of the game
    private static Memory instance;

    // Globales variables
    static private int N_PAIRS_MIN = 1;
    static private int N_PAIRS_MAX = 6;

    //Variables to manage the game and score
    private int nbPlayedGames = 0;
    private int nbWin = 0;
    private String difficulty = "Facile";

    // Variables to manage pairs
    private int nbPairs;
    private int nbDiscoveredPairs;

    // Variables to manage cards
    private List<Card> cards;

    // Variables to manage picking
    private boolean firstCardPicked;
    private Card card1;
    private Card card2;
    private boolean lastPairIsGood = true;

    /**
     * Constructor
     */
    private Memory() {
    }

    /**
     * Singleton : Getter instance
     *
     * @ return instance
     */
    public static Memory getInstance() {
        synchronized (Memory.class) {
            if (instance == null)
                instance = new Memory();
            return instance;
        }
    }

    /**
     * Init game
     * Create pairs
     *
     * @param nPairs Number of pairs
     * @throws MemoryException Exception when init game
     */
    public void init(int nPairs) throws MemoryException {

        // Init variables
        nbPairs = nPairs;
        nbDiscoveredPairs = 0;
        firstCardPicked = false;
        card1 = null;
        card2 = null;
        cards = new ArrayList<>();

        // Create cards
        if ((nbPairs >= N_PAIRS_MIN) && (nbPairs <= N_PAIRS_MAX)) {
            // For each pairs
            for (int i = 0; i < nbPairs; i++) {
                // Create 2 cards
                Card card1 = new Card(i, i, generateImageViewId(i));
                Card card2 = new Card((2 * N_PAIRS_MAX) + i, i, generateImageViewId(i));
                cards.add(card1);
                cards.add(card2);
            }
        } else {
            throw new MemoryException("Erreur lors de l'initialisation du jeu");
        }
    }

    /**
     * Test if game is win
     *
     * @return true if is success
     */
    public boolean isSuccess() {
        if (nbDiscoveredPairs == nbPairs) {
            return true;
        } else {
            return false;
        }
    }

    public boolean pickACard(Card card) {

        if (!card.isDiscovered()) {
            if (firstCardPicked && card == card1)
                return false;
            // Invert the flag
            firstCardPicked = !firstCardPicked;
            // If is the first card picked
            if (firstCardPicked) {
                setLastPair();
                // Save card id of the first card picked
                card1 = card;
                card1.setVisible(true);
            }
            // If is the second card picked
            else {
                // Save card id of the second card picked
                card2 = card;
                card2.setVisible(true);
                // If the pair is discovered
                if (card1.getPairId() == card2.getPairId()) {
                    nbDiscoveredPairs++;
                    card1.setDiscovered(true);
                    card2.setDiscovered(true);
                    lastPairIsGood = true;
                } else {
                    lastPairIsGood = false;
                }
            }
        }
        return isSuccess();
    }

    /**
     * Generate an image view id from the card id (association image <=> card)
     *
     * @param id card id
     * @return image view id
     */
    public int generateImageViewId(int id) {
        switch (id) {
            case 0:
                return R.drawable.pair_1;
            case 1:
                return R.drawable.pair_2;
            case 2:
                return R.drawable.pair_3;
            case 3:
                return R.drawable.pair_4;
            case 4:
                return R.drawable.pair_5;
            case 5:
                return R.drawable.pair_6;
            default:
                return R.drawable.pair_1;
        }
    }

    /**
     * Function to configure the last pair
     * Actions only if the last pair was wrong
     */
    public void setLastPair() {
        if (!lastPairIsGood) {
            card1.setDiscovered(false);
            card2.setDiscovered(false);
            card1.setVisible(false);
            card2.setVisible(false);
            lastPairIsGood = true;
        }
    }

    /**
     * Getters and Setters
     */
    public List<Card> getCards() {
        return cards;
    }

    public int getNPairs() {
        return nbPairs;
    }

    public int getNbWin() {
        return nbWin;
    }

    public void setNbWin(int nbWin) {
        this.nbWin = nbWin;
    }

    public int getNbPlayedGames() {
        return nbPlayedGames;
    }

    public void setNbPlayedGames(int nbPlayedGames) {
        this.nbPlayedGames = nbPlayedGames;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDifficulty() {
        return this.difficulty;
    }
}
