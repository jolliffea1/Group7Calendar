package com.NKU.group7calendar;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Calendar;

public class CalendarApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // create an instance of the calendar, then get the current month and year
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        int day = c.get(Calendar.DAY_OF_MONTH);

        CalendarPane pane = new CalendarPane(month, year);
        MyCalendar mine = pane.getCal();
        Button dayButtons[] = pane.getDayButtons();
        BorderPane borderPane = new BorderPane(pane);

        // button to move to the previous month
        Button btPrevious = new Button("Previous");
        btPrevious.setOnAction(e -> pane.previousMonth());

        // button to move to the current month
        Button btCurr = new Button("Current");
        btCurr.setOnAction(e -> pane.currentMonth());

        // button to move to the next month
        Button btNext = new Button("Next");
        btNext.setOnAction(e -> pane.nextMonth());


        ObservableList<String> allMonths =
                FXCollections.observableArrayList(
                        "January", "February", "March", "April",
                        "May", "June", "July", "August",
                        "September", "October", "November", "December"
                );
        final ComboBox comboBox = new ComboBox(allMonths);



        HBox bottomPane = new HBox(btPrevious, btCurr, btNext);
        bottomPane.setSpacing(10);
        bottomPane.setPadding(new Insets(5));
        bottomPane.setAlignment(Pos.CENTER);

        borderPane.setBottom(bottomPane);

        Scene scene = new Scene(borderPane, pane.getPrefWidth(),320);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Calendar");
        primaryStage.show();
    }

    private class CalendarPane extends GridPane {

        MyCalendarGUI cal;
        Label lblMonthYear;
        Button dayButtons[];
        Label[] lblDayOfWeek = new Label[7];

        private CalendarPane(int month, int year) {

            cal = new MyCalendarGUI(year, month, 1);

            for (int i = 0; i < lblDayOfWeek.length; i++) {
                lblDayOfWeek[i] = new Label(MyCalendarGUI.getDayOfWeekName(i));
                add(lblDayOfWeek[i], i, 1);
            }

            // set gaps
            setHgap(10);
            setVgap(5);
            setPadding(new Insets(5));
            draw();
        }

        private void draw() {

            getChildren().clear();
            dayButtons = new Button[cal.daysInMonth()];
            for(int i = 0; i < dayButtons.length;i++)
                dayButtons[i] = new Button("" + (i + 1));
            // Title
            lblMonthYear = new Label(cal.getMonthName() + ", " + cal.get(Calendar.YEAR));
            HBox monthYearPane = new HBox(lblMonthYear);
            monthYearPane.setAlignment(Pos.CENTER);
            add(monthYearPane, 0, 0, 7, 1);

            // Days of week name
            for (int i = 0; i < lblDayOfWeek.length; i++) {
                add(lblDayOfWeek[i], i, 1);
            }

            // start day
            int startDay = cal.getStartDay();

            int row = 2;

            // add days prior to current month
            if (startDay > 0) {

                int days = MyCalendar.daysInMonth(
                        cal.get(Calendar.MONTH) - 1, cal.get(Calendar.YEAR));
                days -= startDay -1;

                for (int i = 0; i < startDay; i++) {
                    Label lblDay = new Label(days++ + "");
                    lblDay.setTextFill(Color.grayRgb(10, 0.5));

                    HBox dayHbox = new HBox(lblDay);
                    dayHbox.setAlignment(Pos.CENTER_LEFT);
                    add(dayHbox, i % 7, row);
                }

            }

            // add current days of month
            for (int day = 1; day <= cal.daysInMonth(); day++) {

               // Label lblDay = new Label(day + "");
                HBox dayHbox = new HBox(dayButtons[day-1]);
                dayHbox.setAlignment(Pos.CENTER_LEFT);


                add(dayHbox, startDay % 7, row);

                startDay++;
                if (startDay % 7 == 0) {
                    row++;
                }
            }

            // add next month's days
            int day = 1;
            while (startDay % 7 != 0) {
                Label lblDay = new Label(day++ + "");
                HBox dayHbox = new HBox(lblDay);
                dayHbox.setAlignment(Pos.CENTER_LEFT);
                add(dayHbox, startDay % 7, row);
                lblDay.setTextFill(Color.grayRgb(10, 0.5));

                startDay++;
            }

        }

        public void nextMonth() {
            cal.nextMonth();
            draw();
        }

        public void currentMonth() {
            cal.currentMonth();
            draw();
        }

        public void previousMonth() {
            cal.previousMonth();
            draw();
        }
        public MyCalendar getCal()
        {
            return cal;
        }
        public Button[] getDayButtons()
        {
            return dayButtons;
        }
    }
    public static void main(String[] args) {
        Application.launch(args);
    }
}
