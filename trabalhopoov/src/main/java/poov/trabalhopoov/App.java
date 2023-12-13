package poov.trabalhopoov;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Scene cena;
        
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/telaprincipal.fxml"));
        cena = new Scene(root);
        stage.setScene(cena);
        stage.setTitle("Vacina");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
