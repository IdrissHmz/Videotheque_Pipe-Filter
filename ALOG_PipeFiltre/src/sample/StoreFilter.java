package sample;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
//import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
//import com.owlike.genson.Genson;
//import com.owlike.genson.GensonBuilder;
import javafx.event.ActionEvent;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class StoreFilter extends Filter {

    @FXML
    private Button RentButton ;
    @FXML
    private Button ItemButton ;
    @FXML
    private Button ClientButton ;
    @FXML
    private Button LogOutButton ;
    @FXML
    private Pane pane ;


    private Pipe _dataINPipeQuery;
    private Pipe _dataINPipeTransaction;
    private Pipe _dataOUTPipeQuery;
    private Pipe _dataOUTPipeTransaction;


    private Store store ;



    StoreFilter(Pipe _dataINPipeQuery, Pipe _dataINPipeTransaction, Pipe _dataOUTPipeQuery, Pipe _dataOUTPipeTransaction){
        super();
        this._dataINPipeQuery = _dataINPipeQuery;
        this._dataINPipeTransaction = _dataINPipeTransaction;
        this._dataOUTPipeQuery = _dataOUTPipeQuery;
        this._dataOUTPipeTransaction = _dataOUTPipeTransaction;
        this.store = new Store();


    }


    private Request getDatafromQuery(){
        return _dataINPipeQuery.dataOUT();
    }
    private Request getDatafromTransaction(){
        return _dataINPipeTransaction.dataOUT();
    }
    private void sendDataToQueryFilter( Request tempData){
        _dataOUTPipeQuery.dataIN(tempData);
    }
    private void sendDataToTransactionFilter( Request tempData){
        _dataOUTPipeTransaction.dataIN(tempData);
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        execute();
    }

    @Override
    synchronized void execute() {
        // TODO Auto-generated method stub
        //run();
    }


    @FXML
    private void Clients(ActionEvent event){

            pane.getChildren().clear();
            Label titre = new Label("Clients");
            titre.setId("titre");
            titre.setLayoutX(100);
            titre.setLayoutY(70);
            pane.getChildren().add(titre);
            VBox v=new VBox();
            v.setLayoutX(200);
            v.setLayoutY(150);
            for (Client C : store.getClients()) {
                Button btn = new Button();
                btn.setPrefWidth(300);
                btn.setPrefHeight(80);
                btn.setText(C.getNom());

                btn.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        System.out.println("show client");
                        //ShowClient();
                    }
                });
                v.getChildren().add(btn);
            }

            pane.getChildren().add(v);

            Label ajClient = new Label("+ Client");
            ajClient.setId("ajouter");
            ajClient.setOnMousePressed(e->{
                System.out.println("new client");
                NewClient();
            });
            ajClient.setLayoutX(700);
            ajClient.setLayoutY(150);
            pane.getChildren().add(ajClient);
            v.setAlignment(Pos.CENTER_LEFT);

        }

    @FXML
    private void Items(ActionEvent event){

    pane.getChildren().clear();
    Label titre = new Label("Items");
    titre.setId("titre");
    titre.setLayoutX(100);
    titre.setLayoutY(70);
    pane.getChildren().add(titre);
    Label info = new Label("Qliquez sur un article pour le louer");
    info.setId("info");
    info.setLayoutX(70);
    info.setLayoutY(120);
    pane.getChildren().add(info);

    int x=190;
    for (StockItem a: store.getItems()
    ) {
        Button but =new Button();
        but.setId(String.valueOf(a.itemID));
        but.setOnAction(e -> {
            Check(a.itemID);
        });
        but.setText(a.getName());
        but.setMinSize(100,20);
        but.setLayoutX(20);
        but.setLayoutY(x);

        pane.getChildren().add(but);
        x+=60;
    }
    Button ajouter =new Button();
        ajouter.setOnAction(e -> {
            NewItem();
        });
    Button overDue =new Button();
        overDue.setOnAction(e -> {
            OverDue();
        });
    Button filtreByTitle =new Button();
        filtreByTitle.setOnAction(e -> {
            TitleFilter();
        });
    Button filtreByActor =new Button();
        filtreByActor.setOnAction(e -> {
            ActorFilter();
        });
    ajouter.setText("+ Article");
        overDue.setText("Overdue Items");
        filtreByActor.setText("Filtre par acteur");
        filtreByTitle.setText("Filtre par titree");
        ajouter.setLayoutY(170);
        overDue.setLayoutY(220);
        filtreByActor.setLayoutY(270);
        filtreByTitle.setLayoutY(320);
        ajouter.setLayoutX(750);
        overDue.setLayoutX(750);
        filtreByActor.setLayoutX(750);
        filtreByTitle.setLayoutX(750);
    pane.getChildren().addAll(ajouter,overDue,filtreByActor,filtreByTitle);

    }

    @FXML
    private void Rents(ActionEvent event){

        pane.getChildren().clear();
        Label titre = new Label("Rented Items");
        titre.setId("titre");
        titre.setLayoutX(100);
        titre.setLayoutY(70);
        pane.getChildren().add(titre);

        Label info = new Label("Qliquez sur un article pour le louer");
        info.setId("info");
        info.setLayoutX(70);
        info.setLayoutY(120);
        pane.getChildren().add(info);

        int x=190;
        for (RentedItem a: store.getRented()
        ) {
            Button but =new Button();
            //but.setId(String.valueOf(a.itemID)+String.valueOf(a.getCustomerID()));
            but.setOnAction(e -> {
                List<Object> param = new LinkedList<Object>();
                param.add(String.valueOf(a.getCustomerID()));
                param.add(String.valueOf(a.itemID));
                sendDataToTransactionFilter(new Request(this.store,5,param));
                Request in = getDatafromTransaction();
                this.store = in.getStore();

                Rents(e);
            });
            but.setText(String.valueOf(a.getCustomerID()));
            but.setMinSize(100,20);
            but.setLayoutX(20);
            but.setLayoutY(x);

            pane.getChildren().add(but);
            x+=60;
        }
        Button ajouter =new Button();
        ajouter.setOnAction(e -> {
            System.out.println("new Item");
            NewItem();
        });
        ajouter.setText("+ Article");
        ajouter.setLayoutX(700);
        ajouter.setLayoutY(100);
        pane.getChildren().add(ajouter);

    }


    private void Check(int item){
        Stage stage= new Stage();
        Pane root = new Pane();
        //VBox V= new VBox();
        Label info = new Label("Choisissez le client oour qui louer l'article");
        info.setId("info");
        info.setLayoutX(10);
        info.setLayoutY(10);

        Button annuler = new Button();
        annuler.setLayoutY(150);
        annuler.setLayoutX(520);
        annuler.setText("Annuler");
        annuler.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Items(new ActionEvent());
                stage.close();
            }
        });


        int x=50;
        for (Client a: store.getClients()
        ) {



            Button but =new Button();
            but.setId(String.valueOf(a.customerID));
            but.setOnAction(e -> {

                List<Object> param = new LinkedList<Object>();
                param.add(String.valueOf(a.customerID));
                param.add(String.valueOf(item));
                sendDataToTransactionFilter(new Request(this.store,4,param));
                Request in = getDatafromTransaction();
                this.store = in.getStore();

                stage.close();
                Items(e);
            });
            but.setText(a.getNom());
            but.setMinSize(100,20);
            but.setLayoutX(200);
            but.setLayoutY(x);

            root.getChildren().addAll(but);

            x+=40;
        }



        root.getChildren().addAll(info,annuler);
        //V.setSpacing(15);
        //V.setAlignment(Pos.CENTER_RIGHT);
        //root.getChildren().add(V);
        //root.setAlignment(Pos.CENTER);
        stage.setScene(new Scene(root,600,200));
        stage.setResizable(false);
        stage.show();
    }

    private void OverDue(){




        List<Object> param = new LinkedList<Object>();
        sendDataToQueryFilter(new Request(this.store,6,param));
        Request in = getDatafromQuery();
        this.store = in.getStore();

        List<Object> overdueitems = in.getParametres();


        pane.getChildren().clear();
        Label titre = new Label("Items");
        titre.setId("titre");
        titre.setLayoutX(100);
        titre.setLayoutY(70);
        pane.getChildren().add(titre);
        int x=150;
        for (Object a: overdueitems
        ) {
            Button but =new Button();
            but.setId(String.valueOf(((StockItem)a).itemID));
            but.setOnAction(e -> {
                //sendDataToTransactionFilter("4@"+ jsonStore+"@"+"1"+"@"+but.getId());
                //Clients(e);
            });
            but.setText(((StockItem)a).getName());
            but.setMinSize(100,20);
            but.setLayoutX(20);
            but.setLayoutY(x);

            pane.getChildren().add(but);
            x+=60;
        }
        Button ajouter =new Button();
        ajouter.setOnAction(e -> {
            NewItem();
        });
        Button overDue =new Button();
        overDue.setOnAction(e -> {
            OverDue();
        });
        Button filtreByTitle =new Button();
        filtreByTitle.setOnAction(e -> {
            TitleFilter();
        });
        Button filtreByActor =new Button();
        filtreByActor.setOnAction(e -> {
            ActorFilter();
        });
        ajouter.setText("+ Article");
        overDue.setText("Overdue Items");
        filtreByActor.setText("Filtre par acteur");
        filtreByTitle.setText("Filtre par titree");
        ajouter.setLayoutY(170);
        overDue.setLayoutY(220);
        filtreByActor.setLayoutY(270);
        filtreByTitle.setLayoutY(320);
        ajouter.setLayoutX(750);
        overDue.setLayoutX(750);
        filtreByActor.setLayoutX(750);
        filtreByTitle.setLayoutX(750);
        pane.getChildren().addAll(ajouter,overDue,filtreByActor,filtreByTitle);

    }

    private void ActorFilter(){
        Stage stage= new Stage();
        GridPane root = new GridPane();
        TextField actor = new TextField();
        HBox H= new HBox();
        VBox V= new VBox();
        Button annuler = new Button();
        annuler.setText("Annuler");
        annuler.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                stage.close();
            }
        });
        Button fin = new Button();
        fin.setText("Filtrer");
        fin.setOnAction(e -> {
            if (actor.getText().equals("")) {
                System.out.println("nom de la acteur obligatoire");
            } else {



                List<Object> param = new LinkedList<Object>();
                param.add(String.valueOf(actor.getText()));
                sendDataToQueryFilter(new Request(this.store,3,param));
                Request in = getDatafromQuery();
                this.store = in.getStore();
                List<Object> params= in.getParametres();

                pane.getChildren().clear();
                Label titre = new Label("Items");
                titre.setId("titre");
                titre.setLayoutX(100);
                titre.setLayoutY(70);
                pane.getChildren().add(titre);
                int x=100;
                for (Object a: params
                ) {
                    Film f = (Film)a;
                    Button but =new Button();
                    but.setId(String.valueOf(f.itemID));
                    System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaa");
                    but.setText(f.getName() + " : " + f.getActeur() );
                    but.setMinSize(100,20);
                    but.setLayoutX(20);
                    but.setLayoutY(x);

                    pane.getChildren().add(but);
                    x+=60;
                }

                Button ajouter =new Button();
                ajouter.setOnAction(event -> {
                    NewItem();
                });
                Button overDue =new Button();
                overDue.setOnAction(event -> {
                    OverDue();
                });
                Button filtreByTitle =new Button();
                filtreByTitle.setOnAction(event -> {
                    TitleFilter();
                });
                Button filtreByActor =new Button();
                filtreByActor.setOnAction(event -> {
                    ActorFilter();
                });
                ajouter.setText("+ Article");
                overDue.setText("Overdue Items");
                filtreByActor.setText("Filtre par acteur");
                filtreByTitle.setText("Filtre par titree");
                ajouter.setLayoutY(170);
                overDue.setLayoutY(220);
                filtreByActor.setLayoutY(270);
                filtreByTitle.setLayoutY(320);
                ajouter.setLayoutX(750);
                overDue.setLayoutX(750);
                filtreByActor.setLayoutX(750);
                filtreByTitle.setLayoutX(750);
                pane.getChildren().addAll(ajouter,overDue,filtreByActor,filtreByTitle);

                stage.close();

            }


        });
        H.setAlignment(Pos.CENTER_RIGHT);
        H.setSpacing(30);
        H.getChildren().addAll(new Label("Nom du l'acteur:  "), actor);
        H.getChildren().addAll(fin,annuler);
        V.getChildren().addAll(H);
        V.setSpacing(15);
        V.setAlignment(Pos.CENTER_RIGHT);
        root.getChildren().add(V);
        root.setAlignment(Pos.CENTER);
        stage.setScene(new Scene(root,500,100));
        stage.setResizable(false);
        stage.show();
    }

    public List<Film> getFilms(List<StockItem> stock) {
        List<Film> result = new ArrayList<Film>();

        for (StockItem st : stock) {
            if (st instanceof Film) {
                result.add((Film)st);
            }
        }

        return result;
    }

    private void TitleFilter(){
        Stage stage= new Stage();
        GridPane root = new GridPane();
        TextField actor = new TextField();
        HBox H= new HBox();
        VBox V= new VBox();
        Button annuler = new Button();
        annuler.setText("Annuler");
        annuler.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                stage.close();
            }
        });
        Button fin = new Button();
        fin.setText("Filtrer");
        fin.setOnAction(e -> {
            if (actor.getText().equals("")) {
                System.out.println("titre obligatoire");
            } else {



                List<Object> param = new LinkedList<Object>();
                param.add(String.valueOf(actor.getText()));
                sendDataToQueryFilter(new Request(this.store,2,param));
                Request in = getDatafromQuery();
                this.store = in.getStore();
                List<Object> checked= in.getParametres();
                System.out.println(checked.size() != 0);


                pane.getChildren().clear();
                Label titre = new Label("Items");
                titre.setId("titre");
                titre.setLayoutX(100);
                titre.setLayoutY(70);
                pane.getChildren().add(titre);
                int x=100;
                for (Object a: checked
                ) {
                    StockItem item = (StockItem)a;
                    Button but =new Button();
                    but.setId(String.valueOf(item.itemID));
                    but.setText(item.getName());
                    but.setMinSize(100,20);
                    but.setLayoutX(20);
                    but.setLayoutY(x);

                    pane.getChildren().add(but);
                    x+=60;
                }

                Button ajouter =new Button();
                ajouter.setOnAction(event -> {
                    NewItem();
                });
                Button overDue =new Button();
                overDue.setOnAction(event -> {
                    OverDue();
                });
                Button filtreByTitle =new Button();
                filtreByTitle.setOnAction(event -> {
                    TitleFilter();
                });
                Button filtreByActor =new Button();
                filtreByActor.setOnAction(event -> {
                    ActorFilter();
                });
                ajouter.setText("+ Article");
                overDue.setText("Overdue Items");
                filtreByActor.setText("Filtre par acteur");
                filtreByTitle.setText("Filtre par titree");
                ajouter.setLayoutY(170);
                overDue.setLayoutY(220);
                filtreByActor.setLayoutY(270);
                filtreByTitle.setLayoutY(320);
                ajouter.setLayoutX(750);
                overDue.setLayoutX(750);
                filtreByActor.setLayoutX(750);
                filtreByTitle.setLayoutX(750);
                pane.getChildren().addAll(ajouter,overDue,filtreByActor,filtreByTitle);

               // Items(e);
                stage.close();


            }


        });
        H.setAlignment(Pos.CENTER_RIGHT);
        H.setSpacing(30);
        H.getChildren().addAll(new Label("titre du l'article:  "), actor);
        H.getChildren().addAll(fin,annuler);
        V.getChildren().addAll(H);
        V.setSpacing(15);
        V.setAlignment(Pos.CENTER_RIGHT);
        root.getChildren().add(V);
        root.setAlignment(Pos.CENTER);
        stage.setScene(new Scene(root,500,100));
        stage.setResizable(false);
        stage.show();
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


    private void NewClient() {
        Stage stage= new Stage();
        GridPane root = new GridPane();
        TextField nom = new TextField();
        TextField rent = new TextField();
        HBox H= new HBox();
        VBox V= new VBox();
        Button annuler = new Button();
        annuler.setText("Annuler");
        annuler.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                stage.close();
            }
        });
        Button fin = new Button();
        fin.setText("Ajouter");
        fin.setOnAction(e -> {
                if (nom.getText().equals("")||(rent.getText().equals(""))) {
                    System.out.println("parametres obligatoires");
                } else {

                        List<Object> param = new LinkedList<Object>();
                        param.add(String.valueOf(nom.getText()));
                        param.add(String.valueOf(rent.getText()));
                        sendDataToTransactionFilter(new Request(this.store,0,param));
                        Request in = getDatafromTransaction();
                        this.store = in.getStore();

                        Clients(e);
                        stage.close();

                }


        });
        H.setAlignment(Pos.CENTER_RIGHT);
        H.setSpacing(30);
        H.getChildren().addAll(new Label("Nom du client:  "), nom);
        HBox H2 = new HBox();
        H2.getChildren().addAll(fin,annuler);
        H2.setAlignment(Pos.CENTER_RIGHT);
        H2.setSpacing(15);
        HBox H3 = new HBox();
        H3.setAlignment(Pos.CENTER_RIGHT);
        H3.setSpacing(30);
        H3.getChildren().addAll(new Label("solde :  "), rent);
        V.getChildren().addAll(H,H3,H2);
        V.setSpacing(15);
        V.setAlignment(Pos.CENTER_RIGHT);
        root.getChildren().add(V);
        root.setAlignment(Pos.CENTER);
        stage.setScene(new Scene(root,500,200));
        stage.setResizable(false);
        stage.show();
    }


    private void NewItem() {

        Stage stage= new Stage();
        GridPane root = new GridPane();
        TextField nom = new TextField();
        TextField actplat = new TextField();
        TextField rent = new TextField();
        HBox H= new HBox();
        VBox V= new VBox();
        Button annuler = new Button();
        annuler.setText("Annuler");
        annuler.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                stage.close();
            }
        });

        Button AjFilm = new Button();
        AjFilm.setText("Ajouter un Film");
        AjFilm.setOnAction(e ->{
            if (nom.getText().equals("")||(rent.getText().equals(""))||(actplat.getText().equals(""))) {
                System.out.println("nom de la notion obligatoire");
            } else {


                List<Object> param = new LinkedList<Object>();
                param.add(String.valueOf(actplat.getText()));
                param.add(String.valueOf(nom.getText()));
                param.add(String.valueOf(rent.getText()));
                sendDataToTransactionFilter(new Request(this.store,2,param));
                Request in = getDatafromTransaction();
                this.store = in.getStore();

                Items(e);
                stage.close();
                //}
            }


        });
        Button AjJeu = new Button();
        AjJeu.setText("Ajouter un Jeu");
        AjJeu.setOnAction(e ->{
            if (nom.getText().equals("")||(rent.getText().equals(""))||(actplat.getText().equals(""))) {
                System.out.println("nom de la notion obligatoire");
            } else {

                List<Object> param = new LinkedList<Object>();
                param.add(String.valueOf(actplat.getText()));
                param.add(String.valueOf(nom.getText()));
                param.add(String.valueOf(rent.getText()));
                sendDataToTransactionFilter(new Request(this.store,3,param));
                Request in = getDatafromTransaction();
                this.store = in.getStore();

                Items(e);
                stage.close();
                //}
            }


        });
        H.setAlignment(Pos.CENTER_RIGHT);
        H.setSpacing(30);
        H.getChildren().addAll(new Label("titre :  "), nom);
        HBox H2 = new HBox();
        H2.getChildren().addAll(annuler,AjFilm,AjJeu);
        H2.setAlignment(Pos.CENTER_RIGHT);
        H2.setSpacing(15);
        HBox H3 = new HBox();
        H3.setAlignment(Pos.CENTER_RIGHT);
        H3.setSpacing(30);
        H3.getChildren().addAll(new Label("solde :  "), rent);
        HBox H4 = new HBox();
        H4.setAlignment(Pos.CENTER_RIGHT);
        H4.setSpacing(30);
        H4.getChildren().addAll(new Label("Platforme/Acteur :  "), actplat);
        V.getChildren().addAll(H,H3,H4,H2);
        V.setSpacing(15);
        V.setAlignment(Pos.CENTER_RIGHT);
        root.getChildren().add(V);
        root.setAlignment(Pos.CENTER);
        stage.setScene(new Scene(root,500,200));
        stage.setResizable(false);
        stage.show();
    }


        //run();
    }


