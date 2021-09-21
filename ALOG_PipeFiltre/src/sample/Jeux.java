package sample;
public class Jeux extends StockItem {
    private String platform ;

    public Jeux (String plt,String nom, int rentprc){
        super(nom,rentprc);
        platform = plt;
    }
    public String getPlatform() {
        return platform;
    }
    public void setPlatform(String platform) {
        this.platform = platform;
    }

    /*@Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof Jeux)) {
            return false;
        }

        Jeux c = (Jeux) o;

        return Double.compare(itemID, c.itemID) == 0 ;
    }*/
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
