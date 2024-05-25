package com.jdauvergne.morpion.controllers;

import com.jdauvergne.morpion.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ResultController {
    @FXML
    private Button replayButton;
    private MorpionController morpionController;

    @FXML
    private ImageView imagePlayer1;
    @FXML
    private ImageView imagePlayer2;

    // Gérer le bouton de replay
    @FXML
    private void handleReplay() {
        morpionController.handleNewGame();
        ((Stage) replayButton.getScene().getWindow()).close();
    }

    // Gérer le bouton de changement de nom
    @FXML
    private void handleChangeName() {
        morpionController.handleNewGame();
        Main.setModalRename();
        morpionController.showModal();
    }

    // Charger les images des joueurs
    public void loadImagePlayers() {
        imagePlayer1.setImage(morpionController.getPlayer1().getImage());
        imagePlayer2.setImage(morpionController.getPlayer2().getImage());
    }

    // Définir le contrôleur Morpion
    public void setMorpionController(MorpionController morpionController) {
        this.morpionController = morpionController;
        loadImagePlayers();
    }

    // Réduire l'opacité de la fenêtre modale
    @FXML
    void setLowOpacity(MouseEvent event) {
        morpionController.setModalOpacity(0.2f);
    }

    // Rétablir l'opacité normale de la fenêtre modale
    @FXML
    void setNormalOpacity(MouseEvent event) {
        morpionController.setModalOpacity(1.0f);
    }

    // Gérer le bouton de fermeture
    @FXML
    private void handleQuit() {
        System.exit(0);
    }
}
