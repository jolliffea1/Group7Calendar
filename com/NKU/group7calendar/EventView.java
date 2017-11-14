//package com.NKU.group7calendar;

/*
 * This class wraps a TodoItem into an HBox that can be used to decorate the item presented in the todo list.
		*/

import javafx.scene.layout.HBox;
import javafx.scene.control.Label;

public class EventView extends HBox {
    private String item;

    public String getEvent() { return item; }

    public EventView(String item) {
        super();

        this.item = item;
        getChildren().addAll(new Label(item));
    }


}
