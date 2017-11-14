/*
 * This class wraps a TodoItem into an HBox that can be used to decorate the item presented in the todo list.
 */

//package com.NKU.group7calendar;

import javafx.scene.layout.HBox;
import javafx.scene.control.Label;

public class TodoView extends HBox {
	private TodoItem item;
	
	public TodoItem getTodoItem() { return item; }
	
	public TodoView(TodoItem item) {
		super();
		
		this.item = item;
		
		getChildren().addAll(new Label(item.toString()));
	}
	
	@Override
	public String toString() {
		return item.toString();
	}
}