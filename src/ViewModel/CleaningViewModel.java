package ViewModel;

import Model.HotelModel;
import Model.Room;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CleaningViewModel implements PropertyChangeListener
{
  private final HotelModel model;
  private StringProperty roomInfo;
  private StringProperty roomNumber;
  private StringProperty roomState;

  public CleaningViewModel(HotelModel model)
  {
    this.model = model;
    model.addPropertyChangeListener(this);
    roomInfo = new SimpleStringProperty();
    roomNumber = new SimpleStringProperty("");
    roomState = new SimpleStringProperty("");
  }

  public void bindRoomInfo(StringProperty property)
  {
    property.bind(roomInfo);
  }
  public void bindRoomNumber(StringProperty property)
  {
    property.bind(roomNumber);
  }

  public void bindRoomState(StringProperty property)
  {
    property.bind(roomState);
  }

  public void setCleaned()
  {
    try
    {
      int roomNumber = Integer.parseInt(this.roomNumber.get());
      Room toClean = new Room(roomNumber);
      toClean.setState(Room.CLEANED);
      model.checkOut(toClean);
      model.loadAllRooms();
      resetFields();
    }
    catch (NumberFormatException e)
    {
      System.out.println("Something went wrong while converting integer");
    }
  }

  public void setUndergoingCleaning()
  {
    try
    {
      int roomNumber = Integer.parseInt(this.roomNumber.get());
      Room toClean = new Room(roomNumber);
      toClean.setState(Room.UNDER_CLEANING);
      model.checkOut(toClean);
      model.loadAllRooms();
      resetFields();
    }
    catch (NumberFormatException e)
    {
      System.out.println("Something went wrong while converting integer");
    }
  }

  public void resetFields()
  {
    roomInfo.set("");
    roomNumber.set("");
    roomState.set("");
  }
  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    if(evt.getPropertyName().equals("Room Selected For Cleaning"))
    {
      Room selected = (Room) evt.getOldValue();
      roomInfo.set((selected.toString()));
      String roomNum = "";
      roomNum += selected.getRoomNumber();
      roomNumber.set(roomNum);
      roomState.set(selected.getState());
    }
  }
}
