package game;

public class Card {

    private int _id;
    private int _pairId;
    private int _imageId;
    private boolean _discovered;

    public Card(int id, int pairId){
        _id=id;
        _pairId=pairId;
        _discovered = false;
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
}
