package com.jdauvergne.morpion.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;

public class RenameController {
    private MorpionController morpionController;
    @FXML
    private TitledPane pane;
    @FXML
    private ChoiceBox<String> playerChoiceBox;
    @FXML
    private TextField renamePlayerTextField;
    private String j1, j2;

    // Définir le contrôleur Morpion
    public void setMorpionController(MorpionController morpionController) {
        this.morpionController = morpionController;
    }

    // Confirmation du nouveau nom
    @FXML
    void confirmRename(ActionEvent event) {
        String text = renamePlayerTextField.getText();
        if (!text.isBlank()) {
            if (text.length() > 10) {
                renamePlayerTextField.clear();
                pane.setText("Nom trop long (>10)");
                renamePlayerTextField.setPromptText("Nom trop long, max = 10");
                return;
            }
            if (playerChoiceBox.getValue().equals(j1)) {
                morpionController.getPlayer1().setName(text);
                playerChoiceBox.setValue(j2);
                pane.setText("Ça c'est un nom VRAIMENT génial");
            } else {
                morpionController.getPlayer2().setName(text);
                playerChoiceBox.setValue(j1);
                pane.setText("Ça c'est un nom SUPER cool");
            }
            morpionController.updatePlayersLabel();
            renamePlayerTextField.clear();
        }
    }

    // Initialiser la ChoiceBox avec les noms des joueurs
    public void initializeChoiceBox() {
        j1 = morpionController.getPlayer1().getName();
        j2 = morpionController.getPlayer2().getName();
        playerChoiceBox.getItems().addAll(j1, j2);
        playerChoiceBox.setValue(j1);
    }

    // Fermer la fenêtre
    @FXML
    void onExit(ActionEvent event) {
        ((Stage) playerChoiceBox.getScene().getWindow()).close();
    }
}
