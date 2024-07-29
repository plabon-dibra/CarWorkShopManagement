package com.mypackage.carworkshopmanagement.manage.model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Popup;
import javafx.stage.Window;

public class AutoCompleteTextField extends TextField {
    private final ObservableList<String> entries;
    private final Popup popup;
    private final ListView<String> listView;

    public AutoCompleteTextField() {
        super();
        entries = FXCollections.observableArrayList();
        listView = new ListView<>(entries);
        popup = new Popup();
        popup.getContent().add(listView);

        textProperty().addListener((observable, oldValue, newValue) -> {
            if (textChangeListener != null) {
                textChangeListener.onTextChanged(getText());
            }
            if (newValue.isEmpty()) {
                popup.hide();
                return;
            }

            ObservableList<String> filteredEntries = FXCollections.observableArrayList();
            for (String entry : entries) {
                if (entry.toLowerCase().contains(newValue.toLowerCase())) {
                    filteredEntries.add(entry);
                }
            }

            if (!filteredEntries.isEmpty()) {
                listView.setItems(filteredEntries);
                if (!popup.isShowing()) {
                    Window window = getScene().getWindow();
                    popup.show(window, window.getX() + getLayoutX(), window.getY() + getLayoutY() + getHeight());
                }
            } else {
                popup.hide();
            }
        });

        listView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                selectItem();
            }
            if (listView.getSelectionModel().getSelectedItem() != null) {
                setText(listView.getSelectionModel().getSelectedItem());
                popup.hide();
                if (textChangeListener != null) {
                    textChangeListener.onTextChanged(getText());
                }
            }
        });

        listView.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                selectItem();
                if (textChangeListener != null) {
                    textChangeListener.onTextChanged(getText());
                }
            }
        });

        focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                popup.hide();
            }
        });

        setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                popup.hide();
            }
        });
    }

    private void selectItem() {
        String selectedItem = listView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            setText(selectedItem);
            popup.hide();
        }
    }

    public void setEntries(ObservableList<String> entries) {
        this.entries.setAll(entries);
    }

    private TextChangeListener textChangeListener;
    public void setOnTextChanged(TextChangeListener listener) {
        this.textChangeListener = listener;
    }

    public interface TextChangeListener {
        void onTextChanged(String newText);
    }
}
