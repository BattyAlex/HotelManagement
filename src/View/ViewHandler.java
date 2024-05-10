package View;

import ViewModel.ViewModelFactory;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Region;

public class ViewHandler
{
  private ViewFactory viewFactory;
  private Scene currentScene;
  private Stage primaryStage;

  ViewHandler(ViewModelFactory viewModelFactory)
  {
    this.viewFactory = new ViewFactory(this, viewModelFactory);
    this.currentScene = new Scene(new Region());
  }
  public void start(Stage primaryStage)
  {
    this.primaryStage = primaryStage;
    openView(ViewFactory.LOGIN);
  }
  public void openView(String id)
  {
    Region root = viewFactory.load(id);
    currentScene.setRoot(root);
    primaryStage.setTitle("Hotel Management System");
    primaryStage.setScene(currentScene);
    primaryStage.sizeToScene();
    primaryStage.show();
  }
  public void closeView()
  {
    primaryStage.close();
  }
}
