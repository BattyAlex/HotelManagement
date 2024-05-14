package View;

import ViewModel.ViewModelFactory;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Region;

/**
 * The ViewHandler class is responsible for managing the different views in the application
 * It handles the loading, displaying, and closing of views
 */

public class ViewHandler
{
  private ViewFactory viewFactory;
  private Scene currentScene;
  private Stage primaryStage;

  /**
   * Constructs a ViewHandler with the specified ViewModelFactory.
   *
   * @param viewModelFactory The factory for creating view models.
   */

  public ViewHandler(ViewModelFactory viewModelFactory)
  {
    this.viewFactory = new ViewFactory(this, viewModelFactory);
    this.currentScene = new Scene(new Region());
  }

  /**
   * Starts the application by displaying the primary stage with the login view
   *
   * @param primaryStage The primary stage of the application.
   */
  public void start(Stage primaryStage)
  {
    this.primaryStage = primaryStage;
    openView(ViewFactory.LOGIN);
  }

  /**
   * Opens the specified view by loading it from the ViewFactory
   * @param id The ID of the view to load
   */
  public void openView(String id)
  {
    Region root = viewFactory.load(id);
    currentScene.setRoot(root);
    if (root.getUserData() == null) {
      primaryStage.setTitle("");
    } else {
      primaryStage.setTitle(root.getUserData().toString());
    }
    primaryStage.setScene(currentScene);
    primaryStage.sizeToScene();
    primaryStage.show();
  }

  /**
   * Closes the current view by closing the primary stage
   */
  public void closeView()
  {
    primaryStage.close();
  }
}
