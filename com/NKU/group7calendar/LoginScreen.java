//package com.NKU.group7calendar;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.*;

/**
 * Created by mkhul on 11/6/2017.
 */
public class LoginScreen extends BorderPane
{
   private String myUsername;
    private String myPassword;
    public LoginScreen()
    {
        super();
        BorderPane login = new BorderPane();

        TextField message = new TextField("Go ahead and Try to Login");
        message.setEditable(false);
        TextField user = new TextField("Username: ");
        user.setEditable(false);
        TextField username = new TextField("");
        username.setEditable(true);

        HBox usernameHBox = new HBox(user,username);
        TextField pass = new TextField("Password: ");
        pass.setEditable(false);
        TextField password = new TextField("");
        password.setEditable(true);

        HBox passwordHBox = new HBox(pass,password);

        Button btLogin = new Button("Login");
        //HBox loginholder = new HBox(btLogin);
        VBox main = new VBox(message,usernameHBox,passwordHBox,btLogin);
        main.setAlignment(Pos.CENTER);
        main.setSpacing(10);
        //loginholder.setAlignment(Pos.CENTER);
        message.setAlignment(Pos.TOP_LEFT);
        login.setTop(main);
        //login.setCenter(loginholder);
        btLogin.setOnAction(e -> {
            myUsername = username.getText();
            myPassword = password.getText();
            try
            {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@citdb.nku.edu:1521:csc450", "khulenberm1", "csc66");
                PreparedStatement pStmt = conn.prepareStatement("select user_name from users where password = ?");
                pStmt.setString(1, myPassword);

                ResultSet rset = pStmt.executeQuery();
                while(rset.next())
                {
                    if (rset.getString("user_name").equals(myUsername))
                    {
                        message.setText("Login in successful Please just close window");
                    }
                    else
                        message.setText("Login failed, Please Try again, or exit and register your account");
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
        login.setMinSize(400,400);
        this.getChildren().addAll(login);
    }
        public String getMyUsername()
        {
            return myUsername;
        }
    }

