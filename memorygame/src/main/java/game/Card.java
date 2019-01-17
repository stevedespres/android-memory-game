package game;

import java.io.Serializable;

public class Card implements Serializable {

    /**
     * Variables of a card object
     */
    private int id;
    private int pairId;
    private int imageViewId;
    private boolean discovered;
    private boolean visible;

    /**
     * Constructor of a card
     *
     * @param id
     * @param pairId
     * @param imageViewId
     */
    public Card(int id, int pairId, int imageViewId) {
        this.id = id;
        this.pairId = pairId;
        discovered = false;
        this.imageViewId = imageViewId;
    }

    /**
     * Getters and setters
     */
    public int getId() {
        return id;
    }

    public int getPairId() {
        return pairId;
    }

    public void setDiscovered(boolean value) {
        discovered = value;
    }

    public boolean isDiscovered() {
        return discovered;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean _visible) {
        this.visible = _visible;
    }

    public int getImageViewId() {
        return imageViewId;
    }
}
