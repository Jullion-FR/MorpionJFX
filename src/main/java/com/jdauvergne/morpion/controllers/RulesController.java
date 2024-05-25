package com.jdauvergne.morpion.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RulesController {
    @FXML
    private AnchorPane pane;

    // Fermer la fenêtre
    @FXML
    void onExit(ActionEvent event) {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }
}
