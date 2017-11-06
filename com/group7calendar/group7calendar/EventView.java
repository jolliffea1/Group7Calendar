package group7calendar.group7calendar;

//EventView

import javafx.scene.layout.HBox;
import javafx.scene.control.Label;

public class EventView extends HBox {
    private Events item;

    public Events getEvent() { return item; }

    public EventView(Events item) {
        super();

        this.item = item;

        getChildren().addAll(new Label(item.printEvent()));
    }


}
