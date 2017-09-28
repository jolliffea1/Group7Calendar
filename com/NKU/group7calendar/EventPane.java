package group7calendar;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class EventPane extends GridPane {
    private ObservableList<EventView> eventViews = FXCollections.observableArrayList();
    private ListView<EventView> listView = new ListView<EventView>(eventViews);

    public EventPane() {
        super();
        GridPane eventGrid = new GridPane();

        TextField monthBox = new TextField("month:");
        monthBox.setEditable(false);
        GridPane.setRowIndex(monthBox, 2);
        GridPane.setColumnIndex(monthBox, 2);

        TextField monthInput = new TextField();
        GridPane.setRowIndex(monthInput, 2);
        GridPane.setColumnIndex(monthInput, 3);

        TextField dayBox = new TextField("day:");
        dayBox.setEditable(false);
        GridPane.setRowIndex(dayBox, 2);
        GridPane.setColumnIndex(dayBox, 4);

        TextField dayInput = new TextField();
        GridPane.setRowIndex(dayInput, 2);
        GridPane.setColumnIndex(dayInput, 5);

        TextField yearBox = new TextField("year:");
        yearBox.setEditable(false);
        GridPane.setRowIndex(yearBox, 2);
        GridPane.setColumnIndex(yearBox, 6);

        TextField yearInput = new TextField();
        GridPane.setRowIndex(yearInput, 2);
        GridPane.setColumnIndex(yearInput, 7);

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
            newEvent.setEventDay(Integer.parseInt( dayInput.getText()));
            newEvent.setEventMonth(Integer.parseInt(monthInput.getText()));
            newEvent.setEventYear(Integer.parseInt(yearInput.getText()));
            newEvent.setEventStartTime(startTimeInput.getText());
            newEvent.setEventEndTime(endTimeInput.getText());
            newEvent.setEventDescrip(descripInput.getText());
            addEvents(newEvent);
            monthInput.setText("");
            dayInput.setText("");
            yearInput.setText("");
            startTimeInput.setText("");
            endTimeInput.setText("");
            descripInput.setText("");


        });

        saveButton.disableProperty().bind(Bindings.isEmpty(monthInput.textProperty()));




        setRowIndex(listView, 9);
        setColumnIndex(listView, 0);

        getChildren().addAll(listView,eventGrid);

    }

    private void addEvents(Events x) {
        EventView view = new EventView(x);
       eventViews.add(view);
    }

}