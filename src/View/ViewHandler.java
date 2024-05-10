package View;

import ViewModel.ViewModelFactory;

public class ViewHandler
{
  private ViewFactory viewFactory;

  ViewHandler(ViewModelFactory viewModelFactory)
  {
    this.viewFactory = new ViewFactory(this, viewModelFactory);
  }
}
