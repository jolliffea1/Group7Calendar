package group7calendar;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class EventPane extends GridPane {
    private ObservableList<EventView> todoViews = FXCollections.observableArrayList();
    private ListView<EventView> listView = new ListView<EventView>(todoViews);

    public EventPane() {
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
        EventView view = new EventView(new Events());
        todoViews.add(view);
    }

}