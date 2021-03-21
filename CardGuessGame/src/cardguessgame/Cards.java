package cardguessgame;

import java.io.Serializable;

public class Cards implements Serializable {
    private boolean visible = false;
    private String value;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }       
}