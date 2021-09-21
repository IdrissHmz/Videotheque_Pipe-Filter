package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.util.List;
import java.util.LinkedList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{



        Pipe SQ = new BlockingQueue();
        Pipe QS = new BlockingQueue();
        Pipe ST = new BlockingQueue();
        Pipe TS = new BlockingQueue();


        Filter st = new StoreFilter(QS,TS,SQ,ST);
        Filter tran = new TransactionFilter(ST,TS);
        Filter quer = new QueryFilter(SQ,QS);


        Thread th1 = new Thread( st );
        Thread th2 = new Thread( tran );
        Thread th3 = new Thread( quer );

        th1.start();
        th2.start();
        th3.start();


        FXMLLoader loader = new FXMLLoader();
        loader.setController(st);
        loader.setLocation(getClass().getResource("/sample/sample.fxml"));
        Parent root = loader.load();


        /*FXMLLoader loader = new FXMLLoader();
        primaryStage.setTitle("Hello World");
        loader.setController(st);
        Parent root = FXMLLoader.load(getClass().getResource("src/sample/sample.fxml"));*/
        primaryStage.setScene(new Scene(root, 1268,708));
        primaryStage.getScene().getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setResizable(false);
        primaryStage.setTitle("ESI_VIDEOTHEQUE");
        primaryStage.show();
    }


    public static void main(String[] args) {

        /*Pipe p1 = new BlockingQueue();
        Pipe p2 = new BlockingQueue();
        Pipe p3 = new BlockingQueue();


        Filter a1 = new FilterA(null,p1);
        Filter b1 = new FilterB(p1,p2);
        Filter c1 = new FilterC(p2,p3);
        Filter d1 = new FilterD(p3,null);

        Thread th1 = new Thread( a1 );
        Thread th2 = new Thread( b1 );
        Thread th3 = new Thread( c1 );
        Thread th4 = new Thread( d1 );

        th1.start();
        th2.start();
        th3.start();
        th4.start();*/

        /*Pipe pp1 = new BlockingQueue();
        Pipe pp2 = new BlockingQueue();


        Filter st = new StoreFilter(null,pp1,pp2);
        Filter tran = new TransactionFilter(pp2,null);
        Filter quer = new QueryFilter(pp1,null);


        Thread th1 = new Thread( st );
        Thread th2 = new Thread( tran );
        Thread th3 = new Thread( quer );

        th1.start();
        th2.start();
        th3.start();*/


        /*Store store = new Store();
        for(int i=0; i<5; i++){
            store.transacProc.AddClient("idriss" ,100);
        }
        for(int i=0; i<10; i++){
            store.transacProc.AddFilm("star wars", 100,"fatima");
        }



        List<StockItem> films = store.queryProc.FindbyActor("fatima");

        for(StockItem f : films){
            System.out.printf(f.getName()+ "\n");
        }

        for(Client f : store.getClients()){
            System.out.printf(f.getNom() + "\n");
        }
        //*/





        launch(args);
    }
}
