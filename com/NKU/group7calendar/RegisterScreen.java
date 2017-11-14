//package com.NKU.group7calendar;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.*;

/**
 * Created by mkhul on 11/7/2017.
 */
public class RegisterScreen extends BorderPane {
    private String myUsername;
    private String myPassword;
    private String myEmail;
    public RegisterScreen()
    {
        super();
        BorderPane register = new BorderPane();

        TextField message = new TextField();
        message.setEditable(false);
        TextField user = new TextField("Username: ");
        user.setEditable(false);
        TextField username = new TextField("");
        username.setEditable(true);

        HBox usernameHBox = new HBox(user,username);

        TextField email = new TextField("email");
        email.setEditable(false);
        TextField emailEntry = new TextField("");
        emailEntry.setEditable(true);
        HBox emailHbox = new HBox(email,emailEntry);

        TextField pass = new TextField("Password: ");
        pass.setEditable(false);
        TextField password = new TextField("");
        password.setEditable(true);

        HBox passwordHBox = new HBox(pass,password);

        Button btRegister = new Button("Register");
        //HBox loginholder = new HBox(btLogin);
        VBox main = new VBox(message,usernameHBox,emailHbox,passwordHBox,btRegister);
        main.setAlignment(Pos.CENTER);
        main.setSpacing(10);
        //loginholder.setAlignment(Pos.CENTER);
        message.setAlignment(Pos.TOP_LEFT);
        register.setTop(main);
        //login.setCenter(loginholder);
        btRegister.setOnAction(e -> {
            myUsername = username.getText();
            myEmail = emailEntry.getText();
            myPassword = password.getText();
            try
            {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@citdb.nku.edu:1521:csc450", "khulenberm1", "csc66");
                PreparedStatement pStmt = conn.prepareStatement("insert into users(user_name,email,password) values(?,?,?)");//desc,date,start,end,username

                pStmt.setString(1, myUsername);
                pStmt.setString(2, myEmail);
                pStmt.setString(3,myPassword);
                message.setText("successful, You are now logged in, Please just close window");

                try
                {
                pStmt.execute();
                }
                catch (SQLException sqle)
                {
                System.out.println("Could not insert tuple. " + sqle);
                    message.setText("failed, Please Try again");
                    username.setText("");
                    password.setText("");
                    emailEntry.setText("");
                }
            }
            catch (SQLException sqle)
            {
                System.out.println("Could not insert tuple. " + sqle);
            }
            catch(ClassNotFoundException e1)
            {
                System.out.println("ClassNotFoundException : " + e1);
            }
        });
        register.setMinSize(400,400);
        this.getChildren().addAll(register);
    }
    public String getMyUsername()
    {
        return myUsername;
    }
}