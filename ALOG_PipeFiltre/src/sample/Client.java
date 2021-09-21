package sample;

public class Client {
    private int accountBalance;
    private String nom;
    public int customerID;
    static int nbClient;

    public Client(String nom,int AcBalance) {
        this.accountBalance = AcBalance;
        this.nom = nom;
        this.customerID = nbClient + 1;
        nbClient += 1;
    }

    public int getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(int accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
}
