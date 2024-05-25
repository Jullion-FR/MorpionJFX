package com.jdauvergne.morpion.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.event.ActionEvent;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ColorController {
    private MorpionController morpionController;
    private Color backgroundColor, foregroundColor;

    @FXML
    private ChoiceBox<String> playerChoiceBox;
    @FXML
    private Label backLabel;
    @FXML
    private Label foreLabel;
    private String j1, j2;

    // Initialisation du contrôleur
    public void setMorpionController(MorpionController morpionController) {
        this.morpionController = morpionController;
    }

    @FXML
    public void initialize() {
        backgroundColor = new Color(1, 1, 1, 1);
        foregroundColor = new Color(0, 0, 0, 1);
    }

    public void initializeChoiceBox() {
        j1 = morpionController.getPlayer1().getName();
        j2 = morpionController.getPlayer2().getName();
        playerChoiceBox.getItems().addAll(j1, j2);
        playerChoiceBox.setValue(j1);
    }

    // Confirmation des couleurs sélectionnées
    @FXML
    void confirmColor(ActionEvent event) {
        if (playerChoiceBox.getValue().equals(j1)) {
            morpionController.getPlayer1().setBackgroundColor(backgroundColor);
            morpionController.getPlayer1().setForegroundColor(foregroundColor);
        } else {
            morpionController.getPlayer2().setBackgroundColor(backgroundColor);
            morpionController.getPlayer2().setForegroundColor(foregroundColor);
        }
        morpionController.updatePlayersLabel();
    }

    // Fermeture de la fenêtre
    @FXML
    void onExit(ActionEvent event) {
        ((Stage) playerChoiceBox.getScene().getWindow()).close();
    }

    // Mise à jour de la couleur de fond
    @FXML
    void setBackgroundColor(ActionEvent event) {
        backgroundColor = ((ColorPicker) event.getSource()).getValue();
        updateLabels();
    }

    // Mise à jour de la couleur de premier plan
    @FXML
    void setForegroundColor(ActionEvent event) {
        foregroundColor = ((ColorPicker) event.getSource()).getValue();
        updateLabels();
    }

    // Mise à jour des labels avec les nouvelles couleurs
    private void updateLabels() {
        backLabel.setBackground(MorpionController.getBackground(backgroundColor));
        foreLabel.setBackground(MorpionController.getBackground(backgroundColor));
        foreLabel.setTextFill(foregroundColor);
        backLabel.setTextFill(foregroundColor);
    }
}
