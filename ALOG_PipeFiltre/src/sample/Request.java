package sample;

import java.util.List;

public class Request {

    private Store store;
    private int operation;
    private List<Object> parametres;

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public List<Object> getParametres() {
        return parametres;
    }

    public void setParametres(List<Object> parametres) {
        this.parametres = parametres;
    }

    public Request(Store store, int operation, List<Object> parametres) {
        this.store = store;
        this.operation = operation;
        this.parametres = parametres;
    }
}
