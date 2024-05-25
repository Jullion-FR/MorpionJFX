package com.jdauvergne.morpion.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.File;

public class PictureController {
    private static final String TEXT = "Choisissez une image";
    private MorpionController morpionController;
    private FileChooser fileChooser;
    private File file;
    private Image image;
    private String j1;

    @FXML
    private ChoiceBox<String> playerChoiceBox;
    @FXML
    private ImageView display;
    @FXML
    private Label confirmLabel;
    @FXML
    private Button imagePicker;

    // Initialiser le contrôleur
    @FXML
    public void initialize() {
        fileChooser = new FileChooser();
        fileChooser.setTitle(TEXT);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
    }

    // Définir le contrôleur Morpion
    public void setMorpionController(MorpionController morpionController) {
        this.morpionController = morpionController;
    }

    // Initialiser la ChoiceBox avec les noms des joueurs
    public void initializeChoiceBox() {
        j1 = morpionController.getPlayer1().getName();
        String j2 = morpionController.getPlayer2().getName();
        playerChoiceBox.getItems().addAll(j1, j2);
        playerChoiceBox.setValue(j1);
    }

    // Choisir un fichier image
    @FXML
    void chooseFile(ActionEvent event) {
        File selectedFile = fileChooser.showOpenDialog(morpionController.getModalDialog());
        if (selectedFile != null) {
            file = selectedFile;
            image = new Image(file.toURI().toString());
            display.setImage(image);
            ((Button) event.getSource()).setText(file.getName());
        } else {
            ((Button) event.getSource()).setText(TEXT);
        }
    }

    // Soumettre le fichier sélectionné
    @FXML
    void submitFile(ActionEvent event) {
        if (image != null) {
            if (playerChoiceBox.getValue().equals(j1)) {
                morpionController.getPlayer1().setImage(image);
            } else {
                morpionController.getPlayer2().setImage(image);
            }
            confirmLabel.setText("OK :D");
            confirmLabel.setBackground(MorpionController.getBackground(Color.TURQUOISE));
            imagePicker.setText(TEXT);
            image = null;
            file = null;
        } else {
            confirmLabel.setText("Image pour");
            confirmLabel.setBackground(MorpionController.getBackground(Color.WHEAT));
        }
    }

    // Fermer la fenêtre
    @FXML
    void onExit() {
        Stage stage = (Stage) playerChoiceBox.getScene().getWindow();
        stage.close();
    }
}
