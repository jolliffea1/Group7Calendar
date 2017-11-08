package com.NKU.group7calendar;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.YearMonth;
import java.util.Calendar;

public class EventPane extends GridPane {
    private ObservableList<EventView> eventViews = FXCollections.observableArrayList();
    private ListView<EventView> listView = new ListView<EventView>(eventViews);

    private ObservableList<String> amOrPm = FXCollections.observableArrayList("AM","PM");
    private int daysInMonth = 1;

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

        SpinnerValueFactory<String> startValues = new SpinnerValueFactory.ListSpinnerValueFactory<String>(createTimes());
        Spinner<String> startTimeInput = new Spinner<>(startValues);
        GridPane.setRowIndex(startTimeInput, 4);
        GridPane.setColumnIndex(startTimeInput, 3);

        SpinnerValueFactory<String> middayValFac = new SpinnerValueFactory.ListSpinnerValueFactory<String>(amOrPm);
        Spinner<String> startMeridies = new Spinner<>(middayValFac);
        GridPane.setRowIndex(startMeridies, 4);
        GridPane.setColumnIndex(startMeridies, 4);

        TextField endBox = new TextField("endTime:");
        endBox.setEditable(false);
        GridPane.setRowIndex(endBox, 4);
        GridPane.setColumnIndex(endBox, 5);

        SpinnerValueFactory<String> endValues = new SpinnerValueFactory.ListSpinnerValueFactory<String>(createTimes());
        Spinner<String> endTimeInput = new Spinner<>(endValues);
        GridPane.setRowIndex(endTimeInput, 4);
        GridPane.setColumnIndex(endTimeInput, 6);

        SpinnerValueFactory<String> endValFac = new SpinnerValueFactory.ListSpinnerValueFactory<String>(amOrPm);
        Spinner<String> endMeridies = new Spinner<>(endValFac);
        GridPane.setRowIndex(endMeridies, 4);
        GridPane.setColumnIndex(endMeridies, 7);

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

        eventGrid.getChildren().addAll(monthBox, dayBox, yearBox, startBox,
                endBox, descripBox, saveButton, monthInput, dayInput, yearInput,
                startTimeInput, startMeridies, endMeridies, endTimeInput,
                descripInput);

        saveButton.setOnAction(e -> {
            Events newEvent = new Events();
            newEvent.setEventDay(dayInput.getValue());
            newEvent.setEventMonth(monthInput.getValue());
            newEvent.setEventYear(yearInput.getValue());
            newEvent.setEventStartTime(startTimeInput.getValue()+startMeridies.getValue());
            newEvent.setEventEndTime(endTimeInput.getValue()+endMeridies.getValue());
            newEvent.setEventDescrip(descripInput.getText());
            addEvents(newEvent);



            descripInput.setText("");
        });

        saveButton.disableProperty().bind(Bindings.isEmpty(descripInput.textProperty()));

        setRowIndex(listView, 9);
        setColumnIndex(listView, 0);

        getChildren().addAll(listView,eventGrid);

    }

    public EventPane(int mon, int da, int yea){
        super();
        GridPane eventGrid = new GridPane();

        TextField dayBox = new TextField("day:");
        dayBox.setEditable(false);
        GridPane.setRowIndex(dayBox, 2);
        GridPane.setColumnIndex(dayBox, 6);

        Spinner<Integer> dayInput = new Spinner<>(1,daysInMonth,da, 1);
        GridPane.setRowIndex(dayInput, 2);
        GridPane.setColumnIndex(dayInput, 7);

        TextField yearBox = new TextField("year:");
        yearBox.setEditable(false);
        GridPane.setRowIndex(yearBox, 2);
        GridPane.setColumnIndex(yearBox, 2);

        Spinner<Integer> monthInput = new Spinner<>(1,12,mon,1);

        Spinner<Integer> yearInput = new Spinner<>(
                year -100, year +100,
                yea,1);
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

        SpinnerValueFactory<String> startValues = new SpinnerValueFactory.ListSpinnerValueFactory<String>(createTimes());
        Spinner<String> startTimeInput = new Spinner<>(startValues);
        GridPane.setRowIndex(startTimeInput, 4);
        GridPane.setColumnIndex(startTimeInput, 3);

        SpinnerValueFactory<String> middayValFac = new SpinnerValueFactory.ListSpinnerValueFactory<String>(amOrPm);
        Spinner<String> startMeridies = new Spinner<>(middayValFac);
        GridPane.setRowIndex(startMeridies, 4);
        GridPane.setColumnIndex(startMeridies, 4);

        TextField endBox = new TextField("endTime:");
        endBox.setEditable(false);
        GridPane.setRowIndex(endBox, 4);
        GridPane.setColumnIndex(endBox, 5);

        SpinnerValueFactory<String> endValues = new SpinnerValueFactory.ListSpinnerValueFactory<String>(createTimes());
        Spinner<String> endTimeInput = new Spinner<>(endValues);
        GridPane.setRowIndex(endTimeInput, 4);
        GridPane.setColumnIndex(endTimeInput, 6);

        SpinnerValueFactory<String> endValFac = new SpinnerValueFactory.ListSpinnerValueFactory<String>(amOrPm);
        Spinner<String> endMeridies = new Spinner<>(endValFac);
        GridPane.setRowIndex(endMeridies, 4);
        GridPane.setColumnIndex(endMeridies, 7);

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

        eventGrid.getChildren().addAll(monthBox, dayBox, yearBox, startBox,
                endBox, descripBox, saveButton, monthInput, dayInput, yearInput,
                startTimeInput, startMeridies, endMeridies, endTimeInput,
                descripInput);

        saveButton.setOnAction(e -> {
            Events newEvent = new Events();
            newEvent.setEventDay(dayInput.getValue());
            newEvent.setEventMonth(monthInput.getValue());
            newEvent.setEventYear(yearInput.getValue());
            newEvent.setEventStartTime(startTimeInput.getValue()+startMeridies.getValue());
            newEvent.setEventEndTime(endTimeInput.getValue()+endMeridies.getValue());
            newEvent.setEventDescrip(descripInput.getText());
            addEvents(newEvent);

            descripInput.setText("");
        });
        saveButton.disableProperty().bind(Bindings.isEmpty(descripInput.textProperty()));

        setRowIndex(listView, 9);
        setColumnIndex(listView, 0);

        getChildren().addAll(listView,eventGrid);

    }
    //Creates a string array of times in 15 min increments
    private ObservableList createTimes(){
        ObservableList<String> times = FXCollections.observableArrayList();
        times.add("12:00");
        times.add("12:15");
        times.add("12:30");
        times.add("12:45");
        for(int hours = 1; hours <= 12 ;hours++){
            for(int minutes = 0; minutes<60; minutes+=15) {
                times.add(String.format("%02d",hours) + ":" + String.format("%02d", minutes));
            }
        }
        return times;
    }
    private void addEvents(Events x) {
        EventView view = new EventView(x);
       eventViews.add(view);
    }

}