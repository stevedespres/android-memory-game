package game;

import com.example.usrlocal.memory.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Memory implements Serializable {

    private static Memory instance;
    // Globales variables
    static private int N_PAIRS_MIN = 1;
    static private int N_PAIRS_MAX = 6;

    // Variables to manage pairs
    private int _nPairs;
    private int _nPairsDiscovered;

    // Variables to manage cards
    private List<Card> cardsList;

    // Variables to manage picking
    private boolean _firstCardPicked;
    private Card card1;
    private Card card2;
    private boolean lastPairIsGood = true;
    /**
     * Constructor
     * @param nPairs
     * @throws MemoryException
     */
    private Memory(final int nPairs) throws MemoryException {
        init(nPairs);
    }

    /**
     * Singleton : get instance of Memory
     * @param nPairs
     * @return
     * @throws MemoryException
     */
    public static Memory getInstance(final int nPairs) throws MemoryException {
        synchronized (Memory.class) {
            if (instance == null)
                instance = new Memory(nPairs);
            return instance;
        }
    }

    /**
     * Init game creating pairs
     * @param nPairs Number of pairs
     * @throws MemoryException Exception when init game
     */
    public void init(int nPairs) throws MemoryException {

        // Init variables
        _nPairs = nPairs;
        _nPairsDiscovered = 0;
        _firstCardPicked=false;
        card1 = null;
        card2 = null;
        cardsList = new ArrayList<>();

        // Create cards
        if((_nPairs>=N_PAIRS_MIN) && (_nPairs<=N_PAIRS_MAX)){
            // For each pairs
            for(int i=0; i<_nPairs;  i++){
                // Create 2 cards
                Card card1 = new Card(i ,i, generateImageViewId(i));
                Card card2 = new Card((2*N_PAIRS_MAX)+i, i, generateImageViewId(i));
                cardsList.add(card1);
                cardsList.add(card2);
            }
        }else{
            throw new MemoryException("Erreur lors de l'initialisation du jeu");
        }
    }

    /**
     * Test if game is win
     * @return is success
     */
    public boolean isSuccess(){
        if(_nPairsDiscovered == _nPairs){
            return true;
        }else{
            return false;
        }
    }

    public int pickACard(Card card){

        if(!card.isDiscovered()) {
            // Invert the flag
            _firstCardPicked = !_firstCardPicked;
            card.setDiscovered(true);

            // If is the first card picked
            if (_firstCardPicked) {
                setLastPair();
                // Save card id of the first card picked
                card1 = card;
                return 0;
                // If is the second card picked
            } else {
                // Save card id of the second card picked
                card2 = card;
                // If the pair is discovered
                if (card1.getPairId() == card2.getPairId()) {
                    _nPairsDiscovered++;
                    lastPairIsGood = true;
                    if(isSuccess())
                    {
                        return 2;
                    }else
                        return 1;
                }
                lastPairIsGood = false;
                return 3;
            }
        }
        return -1;
    }

    /**
     * Get list of cards
     * @return cards list
     */
    public List<Card> getCardsList(){
        return cardsList;
    }

    /**
     * Get number of pairs on the game
     * @return nb pairs
     */
    public int getNPairs(){
        return _nPairs;
    }

    /**
     * Get number of pairs discovered
     * @return nb pairs discovered
     */
    public int getNPairsDiscovered(){
        return _nPairsDiscovered;
    }

    /**
     * Get card by Id
     * @param id
     * @return
     */
    public Card getCardById(int id){
        for (Card c : cardsList){
            if(id == c.getId()){
                return c;
            }
        }
        return null;
    }

    /**
     * Generate an image view id from the card id (association image <=> card)
     * @param id card id
     * @return image view id
     */
    public int generateImageViewId(int id){
        switch (id){
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
     * Méthode qui permet de configurer la pair précédement retournée
     */
    public void setLastPair() {
        if (!lastPairIsGood) {
            card1.setDiscovered(false);
            card2.setDiscovered(false);
            card1.getFrament().wrongCard();
            card2.getFrament().wrongCard();
            lastPairIsGood = true;
        }
    }
}
