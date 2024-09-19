package com.jvvas.restnewgenstats.Objects;

public class DataLiveActions {

    String action;

    @Override
    public String toString() {
        return action;
    }

    // Empty Constructor
    public DataLiveActions() { }

    public DataLiveActions(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
