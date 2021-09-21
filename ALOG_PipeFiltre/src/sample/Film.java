package sample;


public class Film extends StockItem {
    private String acteur ;
    public Film(String act,String nom , int rentprc){
        super(nom,rentprc);
        acteur = act ;
    }
    public String getActeur() {
        return acteur;
    }
    public void setActeur(String acteur) {
        this.acteur = acteur;
    }

    /*@Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof Film)) {
            return false;
        }

        Film c = (Film) o;

        return Double.compare(itemID, c.itemID) == 0 ;
    }*/
    @Override
    public boolean equals(Object o) {

        if (o == this) {return true;}
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

