package sample;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class Store {
    private List<Client> Clients = new LinkedList<Client>();
    private List<StockItem> StockItems = new LinkedList<StockItem>();
    private List<Film> StockFilms = new LinkedList<Film>();
    private List<Jeux> StockGames = new LinkedList<Jeux>();
    private List<RentedItem> RentedItems = new LinkedList<RentedItem>();
    //public TransactionProcessor transacProc = new TransactionProcessor( new AtomicReference<Store>(this));
    //public QueryProcessor queryProc = new QueryProcessor( new AtomicReference<Store>(this));
    public Map<Integer,Client> CustomerMap = new HashMap<Integer,Client>() ;
    public Map<Integer,StockItem> ItemMap= new HashMap<Integer,StockItem>() ;


    public Store(){

    }


    public List<Film> getStockFilms() {
        return StockFilms;
    }

    public void setStockFilms(List<Film> stockFilms) {
        StockFilms = stockFilms;
    }

    public List<Jeux> getStockGames() {
        return StockGames;
    }

    public void setStockGames(List<Jeux> stockGames) {
        StockGames = stockGames;
    }

    public List<Client> getClients(){ return this.Clients; }

    public void setClients(List<Client> c){  this.Clients = c; }
    public List<StockItem> getItems(){ return this.StockItems; }
    public void setItems(List<StockItem> c){  this.StockItems = c; }
    public List<RentedItem> getRented(){ return this.RentedItems; }
    public void setRented(List<RentedItem> c){  this.RentedItems = c; }


}
