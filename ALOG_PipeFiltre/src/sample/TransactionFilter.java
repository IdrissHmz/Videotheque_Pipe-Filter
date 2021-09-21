package sample;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import javafx.beans.binding.ListBinding;

import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;


public class TransactionFilter extends Filter {

    Pipe _dataINPipe;
    Pipe _dataOUTPipe;

    public TransactionFilter(Pipe _dataINPipe, Pipe _dataOUTPipe) {
        super();
        this._dataINPipe = _dataINPipe;
        this._dataOUTPipe = _dataOUTPipe;


    }

    public Request getData(){
        return _dataINPipe.dataOUT();
    }
    public void sendData( Request tempData){
        _dataOUTPipe.dataIN(tempData);
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        execute();
    }
    @Override
    synchronized void execute() {
        // TODO Auto-generated method stub
        Request in = getData();
        Store store = in.getStore();
        List<Object> intab = in.getParametres();
        String nom ;
        int rent;
        List<StockItem> lstItems;
        List<Film> lstFilms;
        List<Jeux> lstJeux;
        switch(in.getOperation()) {
            case 0:
                nom = (String)intab.get(0);
                rent = Integer.parseInt((String)intab.get(1));
                Client c = new Client(nom,rent);

                List<Client> clt = store.getClients();
                clt.add(c);
                store.setClients(clt);
                store.CustomerMap.put(c.customerID,c);

                break;
            case 2:
                String act =  (String)intab.get(0);
                nom = (String)intab.get(1);
                rent = Integer.parseInt((String)intab.get(2));

                lstItems = store.getItems();
                lstFilms = store.getStockFilms();
                Film f = new Film(act,nom,rent);
                lstItems.add(f);
                lstFilms.add(f);
                store.ItemMap.put(f.itemID,f);

                break;
            case 3:
                String plat =  (String)intab.get(0);
                nom = (String)intab.get(1);
                rent = Integer.parseInt((String)intab.get(2));

                lstItems = store.getItems();
                lstJeux = store.getStockGames();
                Jeux j = new Jeux(plat,nom,rent);
                lstItems.add(j);
                lstJeux.add(j);
                store.ItemMap.put(j.itemID,j);
                break;
            case 4:
                //int custId = Integer.parseInt(intab[3]);
                int custId = Integer.parseInt((String)intab.get(0));
                //int itemId = Integer.parseInt(intab[4]);
                int itemId = Integer.parseInt((String)intab.get(1));

                RentedItem r = new RentedItem(custId,itemId,new Date());
                List<RentedItem> currentRented = store.getRented();
                List<StockItem> currentStock = store.getItems();
                currentRented.add(r);
                for(int i =0;i<currentStock.size();i++){
                    if(currentStock.get(i).itemID == itemId){
                        currentStock.remove(i);
                        break;
                    }
                }

                break;
            case 5:
                //int custId = Integer.parseInt(intab[3]);
                int custId2 = Integer.parseInt((String)intab.get(0));
                //int itemId = Integer.parseInt(intab[4]);
                int itemId2 = Integer.parseInt((String)intab.get(1));

                lstItems  = store.getItems();
                currentRented = store.getRented();
                Map<Integer,StockItem> mapStocks = store.ItemMap;
                StockItem stocitem = mapStocks.get(itemId2);
                for(int i =0;i<currentRented.size();i++){
                    if(currentRented.get(i).getCustomerID()==custId2 && currentRented.get(i).getItemID()==itemId2){
                        currentRented.remove(i);
                        break;
                    }
                }

                lstItems.add(stocitem);
                break;
            default:
                // code block
        }

        sendData(new Request(store,-1,new LinkedList<Object>()));
        run();
    }



}
