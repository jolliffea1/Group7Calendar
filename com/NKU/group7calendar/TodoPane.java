/*
 * This class presents items in the todo list to the user.
 * The new window opened to show the todo list instantiates its stage with a TodoPane.
 */
package com.NKU.group7calendar;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class TodoPane extends GridPane {
	private ObservableList<TodoView> todoViews = FXCollections.observableArrayList();
    private ListView<TodoView> listView = new ListView<TodoView>(todoViews);

    public TodoPane(String username) {
    		super();
    	    showTodo(username);
    		Button addButton = new Button("Add");

        TextField inputField = new TextField();
        if(username.equals(""))
            addButton.setVisible(false);
        addButton.setOnAction(e -> {
        		addItem(inputField.getText());
            try
            {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@citdb.nku.edu:1521:csc450", "khulenberm1", "csc66");
                PreparedStatement pStmt = conn.prepareStatement("insert into todo(description,user_name) values(?,?)");//desc,date,start,end,username

                pStmt.setString(1, inputField.getText());
                pStmt.setString(2,username);

                try
                {
                    pStmt.execute();
                }
                catch (SQLException sqle)
                {
                    System.out.println("Could not insert tuple. " + sqle);
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
            inputField.setText("");
        });

        addButton.disableProperty().bind(Bindings.isEmpty(inputField.textProperty()));

        HBox entryBox = new HBox();
        entryBox.getChildren().addAll(inputField, addButton);
        
        setRowIndex(entryBox, 1);
	    setColumnIndex(entryBox, 0);
	    setRowIndex(listView, 0);
	    setColumnIndex(listView, 0);

        getChildren().addAll(listView, entryBox);
    }
    
    private void addItem(String item) {
    		TodoView view = new TodoView(new TodoItem(item));
        todoViews.add(view);
    }
    private void showTodo(String username)
    {
        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@citdb.nku.edu:1521:csc450", "khulenberm1", "csc66");
            PreparedStatement pStmt = conn.prepareStatement("select Description from todo where user_name = ?");
            pStmt.setString(1, username);
            ResultSet rset = pStmt.executeQuery();
            while(rset.next())
            {
                addItem(rset.getString("Description"));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}