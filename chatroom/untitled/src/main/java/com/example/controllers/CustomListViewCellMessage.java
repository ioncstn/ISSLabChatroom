package com.example.controllers;

import com.example.domain.Message;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class CustomListViewCellMessage extends ListCell<Message> {
    private VBox content;
    private Text from;
    private Text to;
    private Text text;

    public CustomListViewCellMessage(){
        super();
        from = new Text();
        from.setFill(Paint.valueOf("grey"));
        from.setOpacity(50);
        to = new Text();
        to.setFill(Paint.valueOf("grey"));
        to.setOpacity(50);
        HBox hBox = new HBox(from, to);
        hBox.setSpacing(150);
        text = new Text();
        content = new VBox(hBox, text);
        content.setSpacing(10);
    }

    @Override
    protected void updateItem(Message item, boolean empty) {
        super.updateItem(item, empty);
        if(item != null && !empty){
            from.setText(item.getUsername());
            text.setText(item.getText());
            to.setText(item.getReceiver());
            setGraphic(content);
        }
        else{
            setGraphic(null);
        }
    }
}
