package game;

import com.example.usrlocal.memory.CardFragment;

import java.io.Serializable;

public class Card implements Serializable {

    private int _id;
    private int _pairId;
    private int _imageViewId;
    private boolean _discovered;
    private boolean _visible;

    public Card(int id, int pairId, int imageViewId) {
        _id = id;
        _pairId = pairId;
        _discovered = false;
        _imageViewId = imageViewId;
    }

    public int getId() {
        return _id;
    }

    public int getPairId() {
        return _pairId;
    }

    public void setDiscovered(boolean value) {
        _discovered = value;
    }

    public boolean isDiscovered() {
        return _discovered;
    }

    public boolean isVisible() {
        return _visible;
    }

    public void setVisible(boolean _visible) {
        this._visible = _visible;
    }

    public int getImageViewId() {
        return _imageViewId;
    }
}
