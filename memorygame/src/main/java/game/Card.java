package game;

public class Card {

    private int _id;
    private int _pairId;
    private int _imageViewId;
    private boolean _discovered;

    public Card(int id, int pairId, int imageViewId){
        _id=id;
        _pairId=pairId;
        _discovered = false;
        _imageViewId = imageViewId;
    }

    public int getId() {
        return _id;
    }

    public int getPairId() {
        return _pairId;
    }

    public void setDiscovered(){
        _discovered=true;
    }

    public boolean isDiscovered() {
        return _discovered;
    }

    public int getImageViewId(){ return _imageViewId; }
}
