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

public class TodoPane extends GridPane {
	private ObservableList<TodoView> todoViews = FXCollections.observableArrayList();
    private ListView<TodoView> listView = new ListView<TodoView>(todoViews);

    public TodoPane() {
    		super();
    	
    		Button addButton = new Button("Add");

        TextField inputField = new TextField();
        
        addButton.setOnAction(e -> {
        		addItem(inputField.getText());
            inputField.setText("");
            inputField.requestFocus();
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
    
}