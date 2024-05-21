package ViewModel;

import Model.HotelModel;
import Model.Room;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The CleaningViewModel class is responsible for managing the cleaning state of rooms
 * It implements PropertyChangeListener to respond to property change events from the HotelModel
 */

public class CleaningViewModel implements PropertyChangeListener
{
  private final HotelModel model;
  private StringProperty roomInfo;
  private StringProperty roomNumber;
  private StringProperty roomState;

  /**
   * Constructs a CleaningViewModel with the specified HotelModel.
   * Initializes the room information properties and adds this ViewModel as a listener to the HotelModel.
   *
   * @param model the HotelModel to be associated with this ViewModel.
   */

  public CleaningViewModel(HotelModel model)
  {
    this.model = model;
    model.addPropertyChangeListener(this);
    roomInfo = new SimpleStringProperty();
    roomNumber = new SimpleStringProperty("");
    roomState = new SimpleStringProperty("");
  }

  /**
   * Binds the specified StringProperty to the roomInfo property of this ViewModel.
   * @param property  the StringProperty to bind to roomInfo
   */

  public void bindRoomInfo(StringProperty property)
  {
    property.bind(roomInfo);
  }

  /**
   * Binds the specified StringProperty to the roomNUmber property of this ViewModel.
   *
   * @param property the StringProperty to bind to roomNumber.
   */
  public void bindRoomNumber(StringProperty property)
  {
    property.bind(roomNumber);
  }

  /**
   * Binds the specified StringProperty to the roomState property of this ViewModel.
   *
   * @param property the StringProperty to bind to roomState.
   */

  public void bindRoomState(StringProperty property)
  {
    property.bind(roomState);
  }

  /**
   * Sets the state of the room to "CLEANED" and updates the HotelModel
   * It also resets the fields of the ViewModel
   */

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

  /**
   * Sets the state of the room to "UNDER_CLEANING" and updates the HotelModel
   * It also resets the fields of the ViewModel
   */

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

  /**
   * Resets the roomInfo, roomNumber and roomState properties to empty strings.
   */

  public void resetFields()
  {
    roomInfo.set("");
    roomNumber.set("");
    roomState.set("");
  }

  /**
   * Responds to property change events from the HotelModel
   * Updates the room information properties when a "Room Selected For Cleaning" event is received
   *
   * @param evt A PropertyChangeEvent object describing the event source
   *          and the property that has changed.
   */
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
