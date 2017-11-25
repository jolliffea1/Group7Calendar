package group7calendar;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;



import java.sql.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class EventPane extends GridPane {
    private ObservableList<EventView> eventViews = FXCollections.observableArrayList();
    private ListView<EventView> listView = new ListView<EventView>(eventViews);

    private ObservableList<String> amOrPm = FXCollections.observableArrayList("AM","PM");
    private int daysInMonth = 1;

    private int year = Calendar.getInstance().get(Calendar.YEAR);

    public EventPane(String username)
    {
        super();
        GridPane eventGrid = new GridPane();
        eventGrid.setAlignment(Pos.CENTER);
        eventGrid.setHgap(10);
        eventGrid.setVgap(10);
        eventGrid.setPadding(new Insets(5, 15, 5, 5));
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(16.66);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(16.66);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(16.66);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(16.66);
        ColumnConstraints col5 = new ColumnConstraints();
        col5.setPercentWidth(16.66);
        ColumnConstraints col6 = new ColumnConstraints();
        col6.setPercentWidth(16.67);
        eventGrid.getColumnConstraints().addAll(col1,col2,col3,col4,col5,col6);

        Label dayBox = new Label("day:");
        GridPane.setHalignment(dayBox, HPos.RIGHT);
        GridPane.setRowIndex(dayBox, 2);
        GridPane.setColumnIndex(dayBox, 4);

        Spinner<Integer> dayInput = new Spinner<>(1,daysInMonth,1, 1);
        GridPane.setRowIndex(dayInput, 2);
        GridPane.setColumnIndex(dayInput, 5);

        Label yearBox = new Label("year:");
        GridPane.setHalignment(yearBox, HPos.RIGHT);
        GridPane.setRowIndex(yearBox, 2);
        GridPane.setColumnIndex(yearBox, 0);

        Spinner<Integer> monthInput = new Spinner<>(1,12,1,1);

        Spinner<Integer> yearInput = new Spinner<>(
                year -100, year +100,
                year,1);
        GridPane.setRowIndex(yearInput, 2);
        GridPane.setColumnIndex(yearInput, 1);
        yearInput.valueProperty().addListener((e) -> {
            YearMonth yearMonthObject = YearMonth.of((int)yearInput.getValue(),(int)monthInput.getValue());
            daysInMonth = yearMonthObject.lengthOfMonth();
            dayInput.setValueFactory( new SpinnerValueFactory.IntegerSpinnerValueFactory(1, daysInMonth, 1,1));
        });

        Label monthBox = new Label("month:");
        GridPane.setHalignment(monthBox, HPos.RIGHT);
        GridPane.setRowIndex(monthBox, 2);
        GridPane.setColumnIndex(monthBox, 2);

        GridPane.setRowIndex(monthInput, 2);
        GridPane.setColumnIndex(monthInput, 3);
        monthInput.valueProperty().addListener((e) -> {
            YearMonth yearMonthObject = YearMonth.of((int)yearInput.getValue(),(int)monthInput.getValue());
            daysInMonth = yearMonthObject.lengthOfMonth();
            dayInput.setValueFactory( new SpinnerValueFactory.IntegerSpinnerValueFactory(1, daysInMonth, 1,1));
        });

        Label startBox = new Label("startTime:");
        GridPane.setHalignment(startBox, HPos.RIGHT);
        GridPane.setRowIndex(startBox, 4);
        GridPane.setColumnIndex(startBox, 0);

        SpinnerValueFactory<String> startValues = new SpinnerValueFactory.ListSpinnerValueFactory<String>(createTimes());
        Spinner<String> startTimeInput = new Spinner<>(startValues);
        GridPane.setRowIndex(startTimeInput, 4);
        GridPane.setColumnIndex(startTimeInput, 1);

        SpinnerValueFactory<String> middayValFac = new SpinnerValueFactory.ListSpinnerValueFactory<String>(amOrPm);
        Spinner<String> startMeridies = new Spinner<>(middayValFac);
        GridPane.setRowIndex(startMeridies, 4);
        GridPane.setColumnIndex(startMeridies, 2);

        Label endBox = new Label("endTime:");
        GridPane.setHalignment(endBox, HPos.RIGHT);
        GridPane.setRowIndex(endBox, 4);
        GridPane.setColumnIndex(endBox, 3);

        SpinnerValueFactory<String> endValues = new SpinnerValueFactory.ListSpinnerValueFactory<String>(createTimes());
        Spinner<String> endTimeInput = new Spinner<>(endValues);
        GridPane.setRowIndex(endTimeInput, 4);
        GridPane.setColumnIndex(endTimeInput, 4);

        SpinnerValueFactory<String> endValFac = new SpinnerValueFactory.ListSpinnerValueFactory<String>(amOrPm);
        Spinner<String> endMeridies = new Spinner<>(endValFac);
        GridPane.setRowIndex(endMeridies, 4);
        GridPane.setColumnIndex(endMeridies, 5);

        Label descripBox = new Label("descrip:");
        GridPane.setHalignment(descripBox, HPos.RIGHT);
        GridPane.setRowIndex(descripBox, 6);
        GridPane.setColumnIndex(descripBox, 0);

        TextField descripInput = new TextField();
        GridPane.setRowIndex(descripInput, 6);
        GridPane.setRowSpan(descripInput,2);
        GridPane.setColumnIndex(descripInput, 1);
        GridPane.setColumnSpan(descripInput, 5);
        descripInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (descripInput.getText().length() > 140) {
                    String s = descripInput.getText().substring(0, 140);
                    descripInput.setText(s);
                }
            }
        });

        Button saveButton = new Button("Save Event");
        GridPane.setRowIndex(saveButton, 8);
        GridPane.setColumnIndex(saveButton, 3);

        eventGrid.getChildren().addAll(monthBox, dayBox, yearBox, startBox,
                endBox, descripBox, saveButton, monthInput, dayInput, yearInput,
                startTimeInput, startMeridies, endMeridies, endTimeInput,
                descripInput);

        if(username.equals(""))
            saveButton.setVisible(false);

        saveButton.setOnAction(e -> {
            String wasAdded = "Event was not able to be added";
            try {

                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@citdb.nku.edu:1521:csc450", "khulenberm1", "csc66");
                wasAdded = "event added";
                PreparedStatement pStmt = conn.prepareStatement("insert into events(Description,Date_on,Start_time,End_time,User_name) values(?,?,?,?,?)");//desc,date,start,end,username
                // assign values to parameters
                pStmt.setString(1, descripInput.getText());
                java.util.Date date = new Date(yearInput.getValue(),monthInput.getValue(),dayInput.getValue());
                LocalDate EventDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                java.sql.Date sqlDate = java.sql.Date.valueOf( EventDate);
                pStmt.setDate(2, sqlDate);
                pStmt.setString(3, startTimeInput.getValue()+startMeridies.getValue());
                pStmt.setString(4, endTimeInput.getValue()+endMeridies.getValue());
                pStmt.setString(5, username);


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
            addEvents(wasAdded);
            descripInput.setText("");


        });
        //Making sure the user has entered input before savebutton works
        if(descripInput.getText().isEmpty()
                ||monthInput.valueProperty() == null
                ||dayInput.valueProperty() == null
                ||yearInput.valueProperty() == null
                ||startTimeInput.valueProperty() == null
                ||startMeridies.valueProperty() == null
                ||endMeridies.valueProperty() == null
                ||endTimeInput.valueProperty() == null)
            saveButton.disableProperty();




        setRowIndex(listView, 9);
        setColumnIndex(listView, 0);

        getChildren().addAll(listView,eventGrid);

    }
    public EventPane(int month, int day, int inYear, String username)
    {

        super();
        GridPane eventGrid = new GridPane();
        eventGrid.setAlignment(Pos.CENTER);
        eventGrid.setHgap(10);
        eventGrid.setVgap(10);
        eventGrid.setPadding(new Insets(5, 15, 5, 5));
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(16.66);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(16.66);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(16.66);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(16.66);
        ColumnConstraints col5 = new ColumnConstraints();
        col5.setPercentWidth(16.66);
        ColumnConstraints col6 = new ColumnConstraints();
        col6.setPercentWidth(16.67);
        eventGrid.getColumnConstraints().addAll(col1,col2,col3,col4,col5,col6);

        Label dayBox = new Label("day:");
        GridPane.setHalignment(dayBox, HPos.RIGHT);
        GridPane.setRowIndex(dayBox, 2);
        GridPane.setColumnIndex(dayBox, 4);

        Spinner<Integer> dayInput = new Spinner<>(1,daysInMonth,day, 1);
        GridPane.setRowIndex(dayInput, 2);
        GridPane.setColumnIndex(dayInput, 5);

        Label yearBox = new Label("year:");
        GridPane.setHalignment(yearBox, HPos.RIGHT);
        GridPane.setRowIndex(yearBox, 2);
        GridPane.setColumnIndex(yearBox, 0);

        Spinner<Integer> monthInput = new Spinner<>(1,12,month,1);

        Spinner<Integer> yearInput = new Spinner<>(
                year -100, year +100,
                inYear,1);
        GridPane.setRowIndex(yearInput, 2);
        GridPane.setColumnIndex(yearInput, 1);
        yearInput.valueProperty().addListener((e) -> {
            YearMonth yearMonthObject = YearMonth.of((int)yearInput.getValue(),(int)monthInput.getValue());
            daysInMonth = yearMonthObject.lengthOfMonth();
            dayInput.setValueFactory( new SpinnerValueFactory.IntegerSpinnerValueFactory(1, daysInMonth, 1,1));
        });

        Label monthBox = new Label("month:");
        GridPane.setHalignment(monthBox, HPos.RIGHT);
        GridPane.setRowIndex(monthBox, 2);
        GridPane.setColumnIndex(monthBox, 2);

        GridPane.setRowIndex(monthInput, 2);
        GridPane.setColumnIndex(monthInput, 3);
        monthInput.valueProperty().addListener((e) -> {
            YearMonth yearMonthObject = YearMonth.of((int)yearInput.getValue(),(int)monthInput.getValue());
            daysInMonth = yearMonthObject.lengthOfMonth();
            dayInput.setValueFactory( new SpinnerValueFactory.IntegerSpinnerValueFactory(1, daysInMonth, 1,1));
        });

        Label startBox = new Label("startTime:");
        GridPane.setHalignment(startBox, HPos.RIGHT);
        GridPane.setRowIndex(startBox, 4);
        GridPane.setColumnIndex(startBox, 0);

        SpinnerValueFactory<String> startValues = new SpinnerValueFactory.ListSpinnerValueFactory<String>(createTimes());
        Spinner<String> startTimeInput = new Spinner<>(startValues);
        GridPane.setRowIndex(startTimeInput, 4);
        GridPane.setColumnIndex(startTimeInput, 1);

        SpinnerValueFactory<String> middayValFac = new SpinnerValueFactory.ListSpinnerValueFactory<String>(amOrPm);
        Spinner<String> startMeridies = new Spinner<>(middayValFac);
        GridPane.setRowIndex(startMeridies, 4);
        GridPane.setColumnIndex(startMeridies, 2);

        Label endBox = new Label("endTime:");
        GridPane.setHalignment(endBox, HPos.RIGHT);
        GridPane.setRowIndex(endBox, 4);
        GridPane.setColumnIndex(endBox, 3);

        SpinnerValueFactory<String> endValues = new SpinnerValueFactory.ListSpinnerValueFactory<String>(createTimes());
        Spinner<String> endTimeInput = new Spinner<>(endValues);
        GridPane.setRowIndex(endTimeInput, 4);
        GridPane.setColumnIndex(endTimeInput, 4);

        SpinnerValueFactory<String> endValFac = new SpinnerValueFactory.ListSpinnerValueFactory<String>(amOrPm);
        Spinner<String> endMeridies = new Spinner<>(endValFac);
        GridPane.setRowIndex(endMeridies, 4);
        GridPane.setColumnIndex(endMeridies, 5);

        Label descripBox = new Label("descrip:");
        GridPane.setHalignment(descripBox, HPos.RIGHT);
        GridPane.setRowIndex(descripBox, 6);
        GridPane.setColumnIndex(descripBox, 0);

        TextField descripInput = new TextField();
        GridPane.setRowIndex(descripInput, 6);
        GridPane.setRowSpan(descripInput,2);
        GridPane.setColumnIndex(descripInput, 1);
        GridPane.setColumnSpan(descripInput, 5);
        descripInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (descripInput.getText().length() > 140) {
                    String s = descripInput.getText().substring(0, 140);
                    descripInput.setText(s);
                }
            }
        });

        Button saveButton = new Button("Save Event");
        GridPane.setRowIndex(saveButton, 8);
        GridPane.setColumnIndex(saveButton, 3);

        eventGrid.getChildren().addAll(monthBox, dayBox, yearBox, startBox,
                endBox, descripBox, saveButton, monthInput, dayInput, yearInput,
                startTimeInput, startMeridies, endMeridies, endTimeInput,
                descripInput);

        if(username.equals(""))
            saveButton.setVisible(false);

        saveButton.setOnAction(e -> {
            String wasAdded = "Event was not able to be added";
            try {

                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@citdb.nku.edu:1521:csc450", "khulenberm1", "csc66");
                wasAdded = "event added";
                PreparedStatement pStmt = conn.prepareStatement("insert into events(Description,Date_on,Start_time,End_time,User_name) values(?,?,?,?,?)");//desc,date,start,end,username
                // assign values to parameters
                pStmt.setString(1, descripInput.getText());
                java.util.Date date = new Date(yearInput.getValue(),monthInput.getValue(),dayInput.getValue());
                LocalDate EventDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                java.sql.Date sqlDate = java.sql.Date.valueOf( EventDate);
                pStmt.setDate(2, sqlDate);
                pStmt.setString(3, startTimeInput.getValue()+startMeridies.getValue());
                pStmt.setString(4, endTimeInput.getValue()+endMeridies.getValue());
                pStmt.setString(5, username);


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
            addEvents(wasAdded);
            descripInput.setText("");


        });
        //Making sure the user has entered input before savebutton works
        if(descripInput.getText().isEmpty()
                ||monthInput.valueProperty() == null
                ||dayInput.valueProperty() == null
                ||yearInput.valueProperty() == null
                ||startTimeInput.valueProperty() == null
                ||startMeridies.valueProperty() == null
                ||endMeridies.valueProperty() == null
                ||endTimeInput.valueProperty() == null)
            saveButton.disableProperty();




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

}