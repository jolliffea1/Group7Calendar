/*
 * This class presents items in the todo list to the user.
 * The new window opened to show the todo list instantiates its stage with a TodoPane.
 */
package group7calendar;

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
import java.util.ArrayList;
import java.util.Date;

public class TodoPane extends GridPane {
	private ObservableList<TodoView> todoViews = FXCollections.observableArrayList();
    private ListView<TodoView> listView = new ListView<TodoView>(todoViews);
    private ArrayList<String> desc = new ArrayList<>();
    private String username;
    public TodoPane(String username) {
    		super();
            this.username = username;
    	    showTodo(username);
    		Button addButton = new Button("Add");
            Button delete = new Button("Delete");
            TextField deleteField = new TextField();
        TextField inputField = new TextField();
        if(username.equals(""))
        {
            addButton.setVisible(false);
            delete.setVisible(false);
            inputField.setText("You Must Login!");
            inputField.setEditable(false);
            deleteField.setVisible(false);
        }
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
            showTodo(username);
        });
        delete.setOnAction(e ->
        {
            int mine = -1;
            try {
                mine = Integer.parseInt(deleteField.getText());
            }
            catch (Exception exx)
            {

            }
            if(mine != -1) {
                deleteTodo(mine);
                deleteField.setText("");
            }
        });
        addButton.disableProperty().bind(Bindings.isEmpty(inputField.textProperty()));

        HBox entryBox = new HBox();
        entryBox.getChildren().addAll(inputField, addButton,deleteField,delete);
        
        setRowIndex(entryBox, 1);
	    setColumnIndex(entryBox, 0);
	    setRowIndex(listView, 0);
	    setColumnIndex(listView, 0);

        getChildren().addAll(listView, entryBox);
    }
    
    private void addItem(String item)
    {
    		TodoView view = new TodoView(new TodoItem(item));
        todoViews.add(view);
    }
    private void showTodo(String username)
    {
        try {
            todoViews.clear();
            desc.clear();
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@citdb.nku.edu:1521:csc450", "khulenberm1", "csc66");
            PreparedStatement pStmt = conn.prepareStatement("select Description from todo where user_name = ?");
            pStmt.setString(1, username);
            ResultSet rset = pStmt.executeQuery();
            int c = 1;
            while(rset.next())
            {
                desc.add(rset.getString("Description"));
                addItem(c +". "+rset.getString("Description"));
                c++;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void deleteTodo(int position)
    {
        position = position -1;
        boolean b = true;
        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@citdb.nku.edu:1521:csc450", "khulenberm1", "csc66");
            PreparedStatement pStmt = conn.prepareStatement("DELETE FROM todo WHERE Description = ?");//desc,date,start,end,username
            // assign values to parameters
            pStmt.setString(1, desc.get(position));
            try
            {
                pStmt.execute();
            }
            catch (SQLException sqle)
            {
                System.out.println("Could not delete tuple. " + sqle);
                b = false;
            }
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            b = false;
        }
        catch (SQLException e) {
            e.printStackTrace();
            b = false;
        }
        catch(Exception e)
        {

        }
        if(b)
        {
            todoViews.clear();
            desc.clear();
            showTodo(username);
        }
    }
}