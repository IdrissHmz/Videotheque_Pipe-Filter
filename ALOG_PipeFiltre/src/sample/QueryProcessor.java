/*package sample;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

public class QueryProcessor {

    private AtomicReference<Store> storeRef;
    //Map <Integer, Client> c = new HashMap<Integer, Client>();
    //Map <Integer, StockItem> i = new HashMap<Integer, StockItem>();
    //List<RentedItem> renteditm = new LinkedList<RentedItem>();

    public QueryProcessor(AtomicReference<Store> ref){
        this.storeRef = ref;
    }

    public boolean FindByTitle(String titre){
        Store store = storeRef.get();
        boolean exist = false;
        Map <Integer, StockItem> i = store.ItemMap;

        Iterator it = i.keySet().iterator();
        while (it.hasNext())
        {
            Object key = it.next();
            StockItem val = i.get(key);
            if (val.getName() == titre){
                exist = true;
            }
        }
        return exist;
    }
    public List<StockItem> FindbyActor(String act){
        Store store = storeRef.get();
        List<StockItem> film = store.getItems();
        Map <Integer, StockItem> i = store.ItemMap;

        Iterator it = i.keySet().iterator();
        while (it.hasNext())
        {
            Object key = it.next();
            Object val = i.get(key);

            if(val instanceof Film){
                if(((Film) val).getActeur() == act){
                    film.add((StockItem)val);
                }
            }

        }
        return film;
    }
    public boolean IsCheckedOut(StockItem item){
        Store store = storeRef.get();
        List<RentedItem> renteditm = store.getRented();
        boolean isrented = false;
        for (int i = 0; i < renteditm.size(); i++) {
            if (item.getItemID() == renteditm.get(i).getItemID()){
                isrented = true;
            }
        }
        return isrented;
    }
    public float Solde (int clt){
        Store store = storeRef.get();
        List<RentedItem> renteditm = store.getRented();
        Map <Integer, StockItem> i = store.ItemMap;

        float solde = 0;
        for (int j = 0; j < renteditm.size(); j++){
            if(renteditm.get(j).getCustomerID() == clt){
                Iterator it = i.keySet().iterator();
                while (it.hasNext())
                {
                    Object key = it.next();
                    StockItem val = i.get(key);
                    if (val.getItemID() == renteditm.get(j).getItemID()){
                        solde= solde + val.getRentalPrice();
                    }
                }
            }
        }
        return solde;
    }
    public List<StockItem> OverDueItems(){
        Store store = storeRef.get();
        List<RentedItem> renteditm = store.getRented();
        Map <Integer, StockItem> i = store.ItemMap;

        List<StockItem> overdueitems = new LinkedList<StockItem>();
        for (int j = 0; j < renteditm.size(); j++){
            if(renteditm.get(j).getDueDate().compareTo(getCurrentdate())<0){
                Iterator it = i.keySet().iterator();
                while (it.hasNext())
                {
                    Object key = it.next();
                    StockItem val = i.get(key);
                    if (val.getItemID() == renteditm.get(j).getItemID()){
                        overdueitems.add(val);
                    }
                }

            }
        }
        return overdueitems;
    }

    public Date getCurrentdate (){
        Calendar today = Calendar.getInstance();
        today.clear(Calendar.HOUR); today.clear(Calendar.MINUTE); today.clear(Calendar.SECOND);
        return today.getTime();
    }


}
*/