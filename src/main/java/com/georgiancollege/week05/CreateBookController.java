package com.georgiancollege.week05;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateBookController implements Initializable {
    @FXML
    private TextField authorTextField;

    @FXML
    private CheckBox availabilityCheckBox;

    @FXML
    private TextField bookNameTextField;

    @FXML
    private Label finalLabel;

    @FXML
    private ComboBox<String> genreComboBox;

    @FXML
    private Spinner<Double> priceSpinner;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("I am in initialize method.");
        // make label invisible
        finalLabel.setVisible(false);

        // populate genres to combobox
        genreComboBox.getItems().addAll(Book.findGenres());

        // spinner setup
        SpinnerValueFactory<Double> spinnerValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, 20, 1);
        priceSpinner.setValueFactory(spinnerValueFactory);
        priceSpinner.setEditable(true);

        TextField priceTextField = priceSpinner.getEditor();

        // anonymous inner function - to listen to events (changeListener)
//        priceTextField.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
//                try{
//                    Double.parseDouble(newValue);
//                    finalLabel.setVisible(false);
//                } catch (Exception e){
//                    finalLabel.setVisible(true);
//                    finalLabel.setText("Please enter number values for price.");
//                }
//            }
//        });

        // lambda function - to listen to events (changeListener)
        priceTextField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            try{
                Double.parseDouble(newValue);
                finalLabel.setVisible(false);
            } catch (Exception e){
                finalLabel.setVisible(true);
                finalLabel.setText("Please enter number values for price.");
            }
        }));

    }

    @FXML
    void saveBook(ActionEvent event) {
        System.out.println("Button Clicked.");

        try {
            // store user data in variables
            String bookName = bookNameTextField.getText();
            String author = authorTextField.getText();
            String genre = genreComboBox.getSelectionModel().getSelectedItem();
            double price = priceSpinner.getValue();
            boolean isAvailable = availabilityCheckBox.isSelected();

            // create book object
            Book book = new Book(bookName, author, genre, price, isAvailable);

            // add the book object to db
            int newBookID = DBUtility.insertBookIntoDB(book);

            // show the values to user
            finalLabel.setVisible(true);
            finalLabel.setText("Book with ID: " + newBookID + " inserted.");
        } catch (Exception e){
            // show proper exception message to user
            finalLabel.setVisible(true);
            finalLabel.setText(e.getMessage());
        }
    }

    @FXML
    void viewTable_onClick(ActionEvent event) throws IOException {
        SceneChanger.changeScene(event, "book-table-view.fxml", "Book Table");
    }
}
