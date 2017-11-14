//package group7calendar;


import javafx.animation.Animation;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import java.time.Duration;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

//kingm13@yahoo.com




public class CalendarApp extends Application {
    String username = "";
    private boolean isShowingSecondaryWindow = false;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        // create an instance of the calendar, then get the current month and year
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        int day = c.get(Calendar.DAY_OF_MONTH);

        CalendarPane pane = new CalendarPane(month, year);
        //mydayButtons = pane.getDayButtons();
        MyCalendar mine = pane.getCal();
        BorderPane borderPane = new BorderPane(pane);
        //make event button
        //Button btMakeCalendarEvent = new Button("Make Event");
        Button btlogin = new Button("Login");
        Button btRegister = new Button("Register");

        // button to move to the previous month
        Button btPrevious = new Button("Previous");
        btPrevious.setOnAction(e -> pane.previousMonth());

        // button to move to the current month
        Button btCurr = new Button("Current");
        btCurr.setOnAction(e -> pane.currentMonth());

        // button to move to the next month
        Button btNext = new Button("Next");
        btNext.setOnAction(e -> pane.nextMonth());
        //btNext.setOnAction(e -> resetDayButtons(dayButtons,pane));



        btlogin.setOnAction(e -> {
            if(!this.isShowingSecondaryWindow){
                this.isShowingSecondaryWindow = true;
                LoginScreen secondaryLayout = new LoginScreen();
                Scene secondScene = new Scene(secondaryLayout, 600.0D, 200.0D);
                Stage secondStage = new Stage();
                secondStage.setTitle("Login Screen");
                secondStage.setScene(secondScene);
                secondStage.setX(primaryStage.getX() + 250.0D);
                secondStage.setY(primaryStage.getY() + 100.0D);
                secondStage.setOnCloseRequest((req) -> {
                    username = secondaryLayout.getMyUsername();
                    isShowingSecondaryWindow = false;
                });
                secondStage.show();}
        });
        btRegister.setOnAction(e -> {
            if(!this.isShowingSecondaryWindow){
            this.isShowingSecondaryWindow = true;
            RegisterScreen secondaryLayout = new RegisterScreen();
            Scene secondScene = new Scene(secondaryLayout, 600.0D, 200.0D);
            Stage secondStage = new Stage();
            secondStage.setTitle("Register");
            secondStage.setScene(secondScene);
            secondStage.setX(primaryStage.getX() + 250.0D);
            secondStage.setY(primaryStage.getY() + 100.0D);
            secondStage.setOnCloseRequest((req) -> {
            	isShowingSecondaryWindow = false;
                username = secondaryLayout.getMyUsername();
            });
            secondStage.show();}
        });
        /*
        btMakeCalendarEvent.setOnAction(e -> {
            if (!this.isShowingSecondaryWindow) {
                this.isShowingSecondaryWindow = true;
                EventPane secondaryLayout = new EventPane(username);
                Scene secondScene = new Scene(secondaryLayout, 800.0D, 600.0D);
                Stage secondStage = new Stage();
                secondStage.setTitle("Events");
                secondStage.setScene(secondScene);
                secondStage.setX(primaryStage.getX() + 250.0D);
                secondStage.setY(primaryStage.getY() + 100.0D);
                secondStage.setOnCloseRequest((req) -> {
                    this.isShowingSecondaryWindow = false;
                });
                secondStage.show();
            }
        });*/

        // button to open the TodoList window
        Button btTodo = new Button("Todo List");
        btTodo.setOnAction(e -> {
        	
        		if (isShowingSecondaryWindow) return;      	
        		isShowingSecondaryWindow = true;

        		TodoPane secondaryLayout = new TodoPane(username);
            Scene secondScene = new Scene(secondaryLayout, 250, 350);

            Stage secondStage = new Stage();
            secondStage.setTitle("Todo List");
            secondStage.setScene(secondScene);
             
            secondStage.setX(primaryStage.getX() + 250);
            secondStage.setY(primaryStage.getY() + 100);
            
            secondStage.setOnCloseRequest(req -> {
            	isShowingSecondaryWindow = false;
            });

            secondStage.show();
        });

        Button btCreateEvent = new Button("Create Event");
        btCreateEvent.setOnAction(e -> {
            if (!this.isShowingSecondaryWindow) {
                this.isShowingSecondaryWindow = true;
                EventPane secondaryLayout = new EventPane(username);
                Scene secondScene = new Scene(secondaryLayout, 500.0D, 500.0D);
                Stage secondStage = new Stage();
                secondStage.setTitle("Events");
                secondStage.setScene(secondScene);
                secondStage.setX(primaryStage.getX() + 100.0D);
                secondStage.setY(primaryStage.getY() + 100.0D);
                secondStage.setOnCloseRequest((req) -> {
                    this.isShowingSecondaryWindow = false;
                });
                secondStage.show();
            }
        });

        ObservableList<String> allMonths =
                FXCollections.observableArrayList(
                        "January", "February", "March", "April",
                        "May", "June", "July", "August",
                        "September", "October", "November", "December"
                );
        final ComboBox comboBox = new ComboBox(allMonths);

        // add the buttons to the GUI
        HBox bottomPane = new HBox(btPrevious, btCurr, btNext, btTodo, btCreateEvent);

        //VBox rightPane = new VBox(btMakeCalendarEvent);
        VBox leftPane = new VBox(btlogin,btRegister);
        leftPane.setAlignment(Pos.CENTER);
        leftPane.setSpacing(16);
        leftPane.setPadding(new Insets(16));
        //HBox bottomPane = new HBox(btPrevious, btCurr, btNext,btTodo);

        bottomPane.setSpacing(10);
        bottomPane.setPadding(new Insets(5));
        bottomPane.setAlignment(Pos.CENTER);
        //borderPane.setRight(rightPane);
        borderPane.setLeft(leftPane);
        borderPane.setBottom(bottomPane);

        Scene scene = new Scene(borderPane, pane.getPrefWidth(), 320);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Calendar");

        
        // close all windows when the primary stage closes
        primaryStage.setOnCloseRequest(e -> javafx.application.Platform.exit());
        

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

            //displays and updates current system time
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            Label timeLabel = new Label(LocalTime.now(ZoneId.systemDefault()).format(dtf));
            currentTimeFormatter ct = new currentTimeFormatter();        


            final Timeline timeline = new Timeline();
            timeline.setCycleCount(Animation.INDEFINITE);
            KeyFrame kf = new KeyFrame(javafx.util.Duration.seconds(1),
                    new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent event) {
                            timeLabel.setText(ct.getCurrentTime(LocalTime.now(ZoneId.systemDefault()).format(dtf)));
                        }
            });
            timeline.getKeyFrames().add(kf);
            timeline.play();


            HBox leftPane = new HBox(timeLabel);
            leftPane.setAlignment(Pos.TOP_LEFT);
            add(leftPane, 0, 0, 7, 1);

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
            for(int i = 0; i < dayButtons.length; i++)
            {
                int myday = i + 1;
                dayButtons[i].setOnAction(e -> {
                        //this.isShowingEvents = true;
                        EventPane secondaryLayout = new EventPane(MyCalendarGUI.MONTH+1, myday ,MyCalendarGUI.YEAR,username);
                        Scene secondScene = new Scene(secondaryLayout, 800.0D, 600.0D);
                        Stage secondStage = new Stage();
                        secondStage.setTitle("Events");
                        secondStage.setScene(secondScene);
                        secondStage.setX(400);
                        secondStage.setY(400);
                        //secondStage.setOnCloseRequest((req) -> {
                          //  this.isShowingEvents = false;
                        //});
                        secondStage.show();
                });
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
        public MyCalendarGUI getCal()
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