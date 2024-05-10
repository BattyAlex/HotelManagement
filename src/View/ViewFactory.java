package View;

import ViewModel.ViewModelFactory;

import java.security.PublicKey;

public class ViewFactory
{
  private final ViewHandler viewHandler;
  private final ViewModelFactory viewModelFactory;

  public ViewFactory(ViewHandler viewHandler, ViewModelFactory viewModelFactory)
  {
    this.viewHandler = viewHandler;
    this.viewModelFactory = viewModelFactory;
  }
}
