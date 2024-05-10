import Model.HotelModel;
import Model.HotelModelManager;
import View.ViewHandler;
import ViewModel.ViewModelFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class StartApplication extends Application
{

  @Override public void start(Stage primaryStage) throws Exception
  {
    HotelModel model = new HotelModelManager();
    ViewModelFactory viewModelFactory = new ViewModelFactory(model);
    ViewHandler viewHandler = new ViewHandler(viewModelFactory);
    viewHandler.start(primaryStage);
  }

  public static void main(String[] args)
  {
    launch();
  }
}
