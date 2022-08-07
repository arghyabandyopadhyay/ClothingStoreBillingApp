package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    /**
     Company details
     */
    public static String companyName="Company Name";
    public static String companyTagLine="Deals in Fancy & Branded Suiting, Shirting, Shits & Sherwani";
    public static String gstin="Company's GSTIN";
    public static String contactNo1="ContactNo1";
    public static String contactNo2="ContactNo2";
    public static String address="CompanyAddress";
    public static String authorName="Author Name";
    public static String creatorDomain="creatorDomain.com";
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        sample.Controller controller = loader.getController();
        Pane rootNode = loader.load();
        Scene scene = new Scene(rootNode);
        primaryStage.setTitle("Billing System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
