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

/**
 * The StartApplication class serves as the entry point for the Hotel Management application
 * It sets up the client, model, view model factory and view handler, and it manages the primary stage of the JavaFX application.
 *
 */
public class StartApplication extends Application
{
  /**
   * The start method is the main entry point for all JavaFX applications.
   * It initializes the HotelClient, HotelModel, ViewModelFactory and ViewHandler
   * and sets up the primary stage and its close request handler.
   *
   * @param primaryStage the primary stage for this application, onto which
   * the application scene can be set.
   * Applications may create other stages, if needed, but they will not be
   * primary stages.
   * @throws Exception if any error occurs during initialization.
   */

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

  /**
   * The main method serves as the entry point for the Java application.
   * It launches the JavaFX application.
   *
   * @param args the command line arguments.
   */

  public static void main(String[] args)
  {
    launch();
  }
}
