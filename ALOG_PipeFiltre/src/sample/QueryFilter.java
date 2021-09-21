package sample;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;

import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class QueryFilter extends Filter {

    Pipe _dataINPipe;
    Pipe _dataOUTPipe;

    private AtomicReference<Store> storeRef;

    public QueryFilter(Pipe _dataINPipe, Pipe _dataOUTPipe) {
        super();
        this._dataINPipe = _dataINPipe;
        this._dataOUTPipe = _dataOUTPipe;

    }
    public Request getData(){
        return _dataINPipe.dataOUT();
    }

    public void sendData(Request tempData){
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
        List<Object> param = in.getParametres();
        List<Object> paramout = new LinkedList<Object>();

        Map<Integer,StockItem> mapStocks;
        Iterator it;
        switch(in.getOperation()) {

            case 2:
                boolean exist = false;
                String titre = (String) param.get(0);
                //Map<Integer, StockItem> i = ;
                mapStocks=store.ItemMap;
                it = mapStocks.keySet().iterator();
                while (it.hasNext())
                {
                    Object key = it.next();
                    StockItem val = mapStocks.get(key);
                    if (val.getName().equals(titre) ){
                        exist = true;
                        paramout.add(val);
                    }
                }
                break;
            case 3:

                String act = (String) param.get(0);
                List<Film> film = new LinkedList<Film>();
                mapStocks=store.ItemMap;
                it = mapStocks.keySet().iterator();
                while (it.hasNext())
                {
                    Object key = it.next();
                    Object val =  mapStocks.get(key);
                    System.out.println("Film : "+(((StockItem) val).getName()));
                    if(val instanceof Film){
                        Film f = (Film)val;
                        String actor = f.getActeur();
                        System.out.println(actor);
                        if( actor.equals(act) ){
                            System.out.println("Film : "+ f.getName());
                            film.add(f);

                            paramout.add(f);

                        }
                    }

                }

                break;
            case 4:
                int itemId = (Integer)param.get(1);
                paramout.add(IsCheckedOut(store,itemId));

                break;

            case 5:
                //int clt  = Integer) .parseInt(intab[3]);
                int clt = (Integer) param.get(0);
                List<RentedItem> renteditm = store.getRented();
                mapStocks = store.ItemMap;
                //Map <Integer, StockItem> i =;

                float solde = 0;
                for (int j = 0; j < renteditm.size(); j++){
                    if(renteditm.get(j).getCustomerID() == clt){
                        it =  mapStocks.keySet().iterator();
                        while (it.hasNext())
                        {
                            Object key = it.next();
                            StockItem val =  mapStocks.get(key);
                            if (val.getItemID() == renteditm.get(j).getItemID()){
                                solde= solde + val.getRentalPrice();
                            }
                        }
                    }
                }
                paramout.add(solde);
                break;
            case 6:
                renteditm = store.getRented();
                mapStocks = store.ItemMap;
                //Map <Integer, StockItem> i = store.ItemMap;

                List<StockItem> overdueitems = new LinkedList<>();
                for (int j = 0; j < renteditm.size(); j++){
                    if(renteditm.get(j).getDueDate().compareTo(getCurrentdate())<0){
                        it = mapStocks.keySet().iterator();
                        while (it.hasNext())
                        {
                            Object key = it.next();
                            StockItem val = mapStocks.get(key);
                            if (val.getItemID() == renteditm.get(j).getItemID()){
                                overdueitems.add(val);
                                paramout.add(val);
                            }
                        }

                    }
                }
                for(StockItem f :overdueitems){
                    System.out.println(f.getName());
                }
                break;
            case 7:
                Date today = getCurrentdate();
                paramout.add(today);
                System.out.println(today);
                break;

            default:
                // code block
        }

        sendData(new Request(store,10,paramout));
        run();
    }

    public <T extends StockItem> List<T> getStockItemOfType(Class<T> type,List<StockItem> stock) {
        List<T> result = new ArrayList<T>();

        for (StockItem st : stock) {
            if (type.isAssignableFrom(st.getClass())) {
                result.add(type.cast(st)); // Compiler warning here
                // Note, (T)vehicle generates an "Unchecked cast" warning (IDE can see this one)
            }
        }

        return result;
    }

    public Date getCurrentdate (){
        Calendar today = Calendar.getInstance();
        today.clear(Calendar.HOUR); today.clear(Calendar.MINUTE); today.clear(Calendar.SECOND);
        return today.getTime();
    }

    public boolean IsCheckedOut(Store store,int item){
        List<RentedItem> renteditm = store.getRented();
        boolean isrented = false;
        for (int i = 0; i < renteditm.size(); i++) {
            if (item == renteditm.get(i).getItemID()){
                isrented = true;
            }
        }
        return isrented;
    }


}
