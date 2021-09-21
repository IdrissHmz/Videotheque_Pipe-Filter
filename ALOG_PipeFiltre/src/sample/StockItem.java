package sample;

public class StockItem {
    public int itemID;
    private String name;
    private int RentalPrice;
    private static int nbitems;


    public StockItem(String name, int rent) {
        this.itemID = nbitems +1;
        this.name = name;
        this.RentalPrice = rent;
        nbitems += 1;
    }

    public int getRentalPrice() {
        return RentalPrice;
    }

    public void setRentalPrice(int rentalPrice) {
        RentalPrice = rentalPrice;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }
        if(o==null){return false;}
        if (o.getClass() == StockItem.class) {
            StockItem c = (StockItem) o;
            return Double.compare(itemID, c.itemID) == 0 ;
        }
        if(o.getClass() == Film.class){
            Film c = (Film) o;
            return Double.compare(itemID, c.itemID) == 0 ;
        }
        if (o.getClass() == Jeux.class){
            Jeux c = (Jeux) o;
            return Double.compare(itemID, c.itemID) == 0 ;
        }
            return false;
        }


}
