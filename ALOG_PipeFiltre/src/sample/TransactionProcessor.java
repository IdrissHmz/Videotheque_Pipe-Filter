package sample;

import javafx.beans.binding.ListBinding;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class TransactionProcessor {
    private AtomicReference<Store> storeRef;

    //public Map<Integer,Client> CustomerMap = new HashMap<Integer,Client>() ;
    //public Map<Integer,StockItem> ItemMap= new HashMap<Integer,StockItem>() ;
    //public Map<Integer,RentedItem> RentedItemMap= new HashMap<Integer,RentedItem>() ;
    //public List<RentedItem> RentedItems = new LinkedList<RentedItem>() ;


    public TransactionProcessor(AtomicReference<Store> ref){
        this.storeRef = ref;
    }
    public void AddClient(String nom,int ab){
        Store store = storeRef.get();

        Client c = new Client(nom,ab);
        List<Client> current =  store.getClients();
        current.add(c);
        store.setClients(current);
        store.CustomerMap.put(c.customerID,c);

        storeRef.set(store);
    }
    public void AddStockItem(String nom,int rent){
        Store store = storeRef.get();
        StockItem s = new StockItem(nom,rent);
        List<StockItem> current =  store.getItems();
        current.add(s);
        store.setItems(current);
        store.ItemMap.put(s.itemID,s);

        storeRef.set(store);
    }
    public void AddFilm(String nom,int rent,String act){
        Store store = storeRef.get();

        Film s = new Film(act,nom,rent);
        List<StockItem> current  =  store.getItems();
        current.add(s);
        store.setItems(current);
        store.ItemMap.put(s.itemID,s);

        storeRef.set(store);
    }
    public void AddGame(String nom,int rent,String plt){
        Store store = storeRef.get();

        Jeux s = new Jeux(plt,nom,rent);
        List<StockItem> current  =  store.getItems();
        current.add(s);
        store.setItems(current);
        store.ItemMap.put(s.itemID,s);

        storeRef.set(store);
    }
    public void CheckOut(int customerID, int itemID, Date rentAt){
        Store store = storeRef.get();

        RentedItem r = new RentedItem(customerID,itemID,rentAt);
        List<StockItem> currentStock  = store.getItems();
        List<RentedItem> currentRented = store.getRented();
        Map<Integer,StockItem> ItemMap = new HashMap<Integer,StockItem>() ;
        //RentedItemMap.put(itemID,r);

        StockItem item = store.ItemMap.get(itemID);

        currentRented.add(r);
        currentStock.remove(item);
        store.setRented(currentRented);
        store.setItems(currentStock);

        storeRef.set(store);
    }
    public void CheckIn(int itemID){
        Store store = storeRef.get();

        //RentedItem r = RentedItemMap.get(stockItem.itemID);
        List<StockItem> currentStock  = store.getItems();
        List<RentedItem> currentRented = store.getRented();
        Map<Integer,StockItem> ItemMap= new HashMap<Integer,StockItem>() ;


        StockItem item = store.ItemMap.get(itemID);

        currentRented.remove(itemID);
        currentStock.add(item);
        store.setRented(currentRented);
        store.setItems(currentStock);

        storeRef.set(store);
    }
}
