package game;

import java.util.ArrayList;
import java.util.List;

public class Memory {

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

    /**
     * Constructor
     * @param nPairs
     * @throws MemoryException
     */
    public Memory(final int nPairs) throws MemoryException {
        init(nPairs);
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
                Card card1 = new Card(i ,i);
                Card card2 = new Card((2*N_PAIRS_MAX)+i, i);
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
        if(_nPairsDiscovered==_nPairsDiscovered){
            return true;
        }else{
            return false;
        }
    }

    public void pickACard(Card card){

        // Invert the flag
        _firstCardPicked = !_firstCardPicked;

        // If is the first card picked
        if(_firstCardPicked){
            // Save card id of the first card picked
            card1 = card;
        // If is the second card picked
        }else{
            // Save card id of the second card picked
            card2 = card;
            // If the pair is discovered
            if(card1.getPairId() == card2.getPairId()){
                _nPairsDiscovered++;
            }
        }
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

}
