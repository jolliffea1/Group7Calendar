package group7calendar;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javax.swing.event.ChangeEvent;
import java.time.YearMonth;
import java.util.Calendar;

public class EventPane extends GridPane {
    private ObservableList<EventView> eventViews = FXCollections.observableArrayList();
    private ListView<EventView> listView = new ListView<EventView>(eventViews);
    private int daysInMonth = 1;
    private String[] amOrPm = {"AM","PM"};
    private int year = Calendar.getInstance().get(Calendar.YEAR);

    public EventPane() {
        super();
        GridPane eventGrid = new GridPane();

        TextField dayBox = new TextField("day:");
        dayBox.setEditable(false);
        GridPane.setRowIndex(dayBox, 2);
        GridPane.setColumnIndex(dayBox, 6);

        Spinner<Integer> dayInput = new Spinner<>(1,daysInMonth,1, 1);
        GridPane.setRowIndex(dayInput, 2);
        GridPane.setColumnIndex(dayInput, 7);

        TextField yearBox = new TextField("year:");
        yearBox.setEditable(false);
        GridPane.setRowIndex(yearBox, 2);
        GridPane.setColumnIndex(yearBox, 2);

        Spinner<Integer> monthInput = new Spinner<>(1,12,1,1);

        Spinner<Integer> yearInput = new Spinner<>(
                year -100, year +100,
                year,1);
        GridPane.setRowIndex(yearInput, 2);
        GridPane.setColumnIndex(yearInput, 3);
        yearInput.valueProperty().addListener((e) -> {
            YearMonth yearMonthObject = YearMonth.of((int)yearInput.getValue(),(int)monthInput.getValue());
            daysInMonth = yearMonthObject.lengthOfMonth();
            dayInput.setValueFactory( new SpinnerValueFactory.IntegerSpinnerValueFactory(1, daysInMonth, 1,1));
        });

        TextField monthBox = new TextField("month:");
        monthBox.setEditable(false);
        GridPane.setRowIndex(monthBox, 2);
        GridPane.setColumnIndex(monthBox, 4);

        GridPane.setRowIndex(monthInput, 2);
        GridPane.setColumnIndex(monthInput, 5);
        monthInput.valueProperty().addListener((e) -> {
            YearMonth yearMonthObject = YearMonth.of((int)yearInput.getValue(),(int)monthInput.getValue());
            daysInMonth = yearMonthObject.lengthOfMonth();
            dayInput.setValueFactory( new SpinnerValueFactory.IntegerSpinnerValueFactory(1, daysInMonth, 1,1));
        });

        TextField startBox = new TextField("startTime:");
        startBox.setEditable(false);
        GridPane.setRowIndex(startBox, 4);
        GridPane.setColumnIndex(startBox, 2);

        TextField startTimeInput = new TextField();
        GridPane.setRowIndex(startTimeInput, 4);
        GridPane.setColumnIndex(startTimeInput, 3);

        TextField endBox = new TextField("endTime:");
        endBox.setEditable(false);
        GridPane.setRowIndex(endBox, 4);
        GridPane.setColumnIndex(endBox, 4);

        TextField endTimeInput = new TextField();
        GridPane.setRowIndex(endTimeInput, 4);
        GridPane.setColumnIndex(endTimeInput, 5);

        TextField descripBox = new TextField("descrip:");
        descripBox.setEditable(false);
        GridPane.setRowIndex(descripBox, 6);
        GridPane.setColumnIndex(descripBox, 2);

        TextField descripInput = new TextField();
        GridPane.setRowIndex(descripInput, 6);
        GridPane.setColumnIndex(descripInput, 3);
        GridPane.setColumnSpan(descripInput, 3);

        Button saveButton = new Button("Save Event");
        GridPane.setRowIndex(saveButton, 8);
        GridPane.setColumnIndex(saveButton, 4);

        eventGrid.getChildren().addAll(monthBox, dayBox, yearBox, startBox, endBox, descripBox, saveButton, monthInput, dayInput, yearInput, startTimeInput, endTimeInput,descripInput);

        saveButton.setOnAction(e -> {
            Events newEvent = new Events();
            newEvent.setEventDay(dayInput.getValue());
            newEvent.setEventMonth(monthInput.getValue());
            newEvent.setEventYear(yearInput.getValue());
            newEvent.setEventStartTime(startTimeInput.getText());
            newEvent.setEventEndTime(endTimeInput.getText());
            newEvent.setEventDescrip(descripInput.getText());
            addEvents(newEvent);

            startTimeInput.setText("");
            endTimeInput.setText("");
            descripInput.setText("");
        });

        //saveButton.disableProperty().bind(Bindings.isEmpty(monthInput.valueProperty()));

        setRowIndex(listView, 9);
        setColumnIndex(listView, 0);

        getChildren().addAll(listView,eventGrid);

    }
    //Creates a string array of times in 15 min increments
    private String[] createTimes(){
        String[] timeString = new String[48];
        int i = 0;
        for(int hours = 0; hours < 12 ;hours++){
            for(int minutes = 0; minutes<60; minutes+=15) {
                timeString[i] = String.format("%02d", (hours+1)%12) + ":" + String.format("%02d", minutes);
                i++;
            }
        }
        return timeString;
    }
    private void addEvents(Events x) {
        EventView view = new EventView(x);
       eventViews.add(view);
    }

}