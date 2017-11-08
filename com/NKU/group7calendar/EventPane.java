package com.NKU.group7calendar;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.text.ParseException;
import java.text.SimpleDateFormat;


import java.sql.*;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class EventPane extends GridPane {
    private ObservableList<EventView> eventViews = FXCollections.observableArrayList();
    private ListView<EventView> listView = new ListView<EventView>(eventViews);

    public EventPane(String username)
    {

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
        if(username.equals(""))
            saveButton.setVisible(false);
        eventGrid.getChildren().addAll(monthBox, dayBox, yearBox, startBox, endBox, descripBox, saveButton, monthInput, dayInput, yearInput, startTimeInput, endTimeInput,descripInput);

        saveButton.setOnAction(e -> {
            String wasAdded = "Event was not able to be added";
            try {

                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@citdb.nku.edu:1521:csc450", "khulenberm1", "csc66");
                wasAdded = "event added";
                PreparedStatement pStmt = conn.prepareStatement("insert into events(Description,Date_on,Start_time,End_time,User_name) values(?,?,?,?,?)");//desc,date,start,end,username
                // assign values to parameters
                pStmt.setString(1, descripInput.getText());
                java.util.Date date = new Date(Integer.parseInt(yearInput.getText()),Integer.parseInt(monthInput.getText()),Integer.parseInt(dayInput.getText()));
                LocalDate EventDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                java.sql.Date sqlDate = java.sql.Date.valueOf( EventDate);
                pStmt.setDate(2, sqlDate);
                pStmt.setString(3, startTimeInput.getText());
                pStmt.setString(4, endTimeInput.getText());
                pStmt.setString(5, username);
                ///*

                try {
                    pStmt.execute();
                }
                catch (SQLException sqle) {
                    System.out.println("Could not insert tuple. " + sqle);
                    wasAdded = "Event was not able to be added";
                }
            }
            catch (SQLException sqle) {
                System.out.println("Could not insert tuple. " + sqle);
            }

            catch(ClassNotFoundException e1)
                {
                    System.out.println("ClassNotFoundException : " + e1);
                }

            monthInput.setText("");
            dayInput.setText("");
            yearInput.setText("");
            startTimeInput.setText("");
            endTimeInput.setText("");
            descripInput.setText("");

            /*Events newEvent = new Events();
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
            descripInput.setText("");*/
        });


        saveButton.disableProperty().bind(Bindings.isEmpty(monthInput.textProperty()));




        setRowIndex(listView, 9);
        setColumnIndex(listView, 0);

        getChildren().addAll(listView,eventGrid);

    }
    public EventPane(int month, int day, int year, String username)
    {

        super();
        showEvents(username,month,day,year);
        GridPane eventGrid = new GridPane();

        TextField monthBox = new TextField("month:");
        monthBox.setEditable(false);
        GridPane.setRowIndex(monthBox, 2);
        GridPane.setColumnIndex(monthBox, 2);

        TextField monthInput = new TextField("" + month);
        monthInput.setEditable(false);
        GridPane.setRowIndex(monthInput, 2);
        GridPane.setColumnIndex(monthInput, 3);

        TextField dayBox = new TextField("day:");
        dayBox.setEditable(false);
        GridPane.setRowIndex(dayBox, 2);
        GridPane.setColumnIndex(dayBox, 4);

        TextField dayInput = new TextField("" + day);
        dayInput.setEditable(false);
        GridPane.setRowIndex(dayInput, 2);
        GridPane.setColumnIndex(dayInput, 5);

        TextField yearBox = new TextField("year:");
        yearBox.setEditable(false);
        GridPane.setRowIndex(yearBox, 2);
        GridPane.setColumnIndex(yearBox, 6);

        TextField yearInput = new TextField("" + year);
        yearInput.setEditable(false);
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
        if(username.equals(""))
            saveButton.setVisible(false);
        eventGrid.getChildren().addAll(monthBox, dayBox, yearBox, startBox, endBox, descripBox, saveButton, monthInput, dayInput, yearInput, startTimeInput, endTimeInput, descripInput);

        saveButton.setOnAction(e -> {/*
            Events newEvent = new Events();
            newEvent.setEventDay(Integer.parseInt(dayInput.getText()));
            newEvent.setEventMonth(Integer.parseInt(monthInput.getText()));
            newEvent.setEventYear(Integer.parseInt(yearInput.getText()));
            newEvent.setEventStartTime(startTimeInput.getText());
            newEvent.setEventEndTime(endTimeInput.getText());
            newEvent.setEventDescrip(descripInput.getText());
            addEvents(newEvent);
            monthInput.setText("" + month);
            dayInput.setText("" + day);
            yearInput.setText("" + year);
            startTimeInput.setText("");
            endTimeInput.setText("");
            descripInput.setText("");*/
            String wasAdded = "Event was not able to be added";
            try {
                java.util.Date date = new Date(year,month,day);
                LocalDate EventDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                java.sql.Date sqlDate = java.sql.Date.valueOf( EventDate);
                wasAdded = "event added";
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@citdb.nku.edu:1521:csc450", "khulenberm1", "csc66");

                PreparedStatement pStmt = conn.prepareStatement("insert into events(Description,Date_on,Start_time,End_time,User_name) values(?,?,?,?,?)");//desc,date,start,end,username
                // assign values to parameters
                pStmt.setString(1, descripInput.getText());
                //date = monthInput.getText().trim() + "/"+dayInput.getText().trim() + "/"+yearInput.getText().trim();

                pStmt.setDate(2, sqlDate);
                pStmt.setString(3, startTimeInput.getText());
                pStmt.setString(4, endTimeInput.getText());
                pStmt.setString(5, username);
                ///*

                try {
                    pStmt.execute();
                } catch (SQLException sqle)
                {
                    System.out.println("Could not insert tuple. " + sqle);
                    wasAdded = "Event was not able to be added";
                }
            }
            catch (SQLException sqle) {
                System.out.println("Could not insert tuple. " + sqle);
            }

            catch(ClassNotFoundException e1)
            {
                System.out.println("ClassNotFoundException : " + e1);
            }
            addEvents(wasAdded);
            monthInput.setText("" + month);
            dayInput.setText("" + day);
            yearInput.setText("" + year);
            startTimeInput.setText("");
            endTimeInput.setText("");
            descripInput.setText("");


        });


        saveButton.disableProperty().bind(Bindings.isEmpty(monthInput.textProperty()));




        setRowIndex(listView, 9);
        setColumnIndex(listView, 0);

        getChildren().addAll(listView,eventGrid);
    }

    private void addEvents(String x) {
        EventView view = new EventView(x);
       eventViews.add(view);
    }
    private void showEvents(String username, int month, int day , int year)
    {
        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@citdb.nku.edu:1521:csc450", "khulenberm1", "csc66");
            PreparedStatement pStmt = conn.prepareStatement("select Description,Start_time,End_time from events where user_name = ? and Date_ON = ?");
            pStmt.setString(1, username);
            java.util.Date date = new Date(year, month, day);
            LocalDate EventDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            java.sql.Date sqlDate = java.sql.Date.valueOf( EventDate);
            pStmt.setDate(2, sqlDate);
            ResultSet rset = pStmt.executeQuery();
            while(rset.next())
            {
                addEvents("Start Time: " + rset.getString("Start_time") + " End Time: "+ rset.getString("End_time") + " Description: " + rset.getString("description"));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}