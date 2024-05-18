import Client.HotelClient;
import Client.HotelClientImplementation;
import Model.Guest;
import Model.HotelModel;
import Model.HotelModelManager;
import Model.Room;
import View.ViewHandler;
import ViewModel.ViewModelFactory;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class StartApplication extends Application
{

  @Override public void start(Stage primaryStage) throws Exception
  {
    HotelClient client = new HotelClientImplementation("localhost", 8080);
    HotelModel model = new HotelModelManager(client);
    ViewModelFactory viewModelFactory = new ViewModelFactory(model);
    ViewHandler viewHandler = new ViewHandler(viewModelFactory);
    viewHandler.start(primaryStage);
    primaryStage.setOnCloseRequest(event -> {
      model.exitClient();
      viewHandler.closeView();
    });
  }

  public static void main(String[] args)
  {
    launch();
  }
}
