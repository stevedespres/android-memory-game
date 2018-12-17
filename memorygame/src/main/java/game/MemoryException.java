package game;

import java.io.Serializable;

public class MemoryException extends Exception implements Serializable {

    public MemoryException(String message) {
        super(message);
    }
}
