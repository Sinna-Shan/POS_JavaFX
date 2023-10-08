import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("com/example/view/mainView.fxml"))));
        stage.setTitle("Shopify");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}