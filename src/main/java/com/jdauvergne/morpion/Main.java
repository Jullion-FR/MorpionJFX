package com.jdauvergne.morpion;

import com.jdauvergne.morpion.controllers.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    private static Stage modalDialog;
    private static MorpionController controller;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlMorpionLoader = new FXMLLoader(Main.class.getResource("fxml/game.fxml"));
        Scene scene = new Scene(fxmlMorpionLoader.load());
        stage.setResizable(false);
        stage.setTitle("Morpion");
        stage.setScene(scene);

        // Initialisation de la fenêtre modale
        initModalDialog(stage, fxmlMorpionLoader);

        stage.show();
    }

    // Initialisation de la fenêtre modale
    public static void initModalDialog(Stage stage, FXMLLoader loader) {
        modalDialog = new Stage(StageStyle.UNDECORATED);
        modalDialog.initModality(Modality.WINDOW_MODAL);
        modalDialog.initOwner(stage);
        controller = (MorpionController) loader.getController();
        controller.setModalDialog(modalDialog);
    }

    // Affichage de la fenêtre modale des résultats
    public static void setModalResult() {
        try {
            FXMLLoader fxmlResultLoader = new FXMLLoader(Main.class.getResource("fxml/result.fxml"));
            Scene sceneResult = new Scene(fxmlResultLoader.load());
            ResultController resultController = (ResultController) fxmlResultLoader.getController();
            resultController.setMorpionController(controller);
            modalDialog.setScene(sceneResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Affichage de la fenêtre modale de renommage
    public static void setModalRename() {
        try {
            FXMLLoader fxmlRenameLoader = new FXMLLoader(Main.class.getResource("fxml/rename.fxml"));
            Scene sceneRename = new Scene(fxmlRenameLoader.load());
            RenameController renameController = fxmlRenameLoader.getController();
            renameController.setMorpionController(controller);
            renameController.initializeChoiceBox();
            modalDialog.setScene(sceneRename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Affichage de la fenêtre modale de sélection de couleur
    public static void setModalColor() {
        try {
            FXMLLoader fxmlColorLoader = new FXMLLoader(Main.class.getResource("fxml/color.fxml"));
            Scene sceneColor = new Scene(fxmlColorLoader.load());
            ColorController colorController = fxmlColorLoader.getController();
            colorController.setMorpionController(controller);
            colorController.initializeChoiceBox();
            modalDialog.setScene(sceneColor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Affichage de la fenêtre modale de sélection d'image
    public static void setModalPicture() {
        try {
            FXMLLoader fxmlPictureLoader = new FXMLLoader(Main.class.getResource("fxml/picture.fxml"));
            Scene scenePicture = new Scene(fxmlPictureLoader.load());
            PictureController pictureController = fxmlPictureLoader.getController();
            pictureController.setMorpionController(controller);
            pictureController.initializeChoiceBox();
            modalDialog.setScene(scenePicture);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Affichage de la fenêtre modale des règles
    public static void setModalRules() {
        try {
            FXMLLoader fxmlRulesLoader = new FXMLLoader(Main.class.getResource("fxml/rules.fxml"));
            Scene sceneRules = new Scene(fxmlRulesLoader.load());
            modalDialog.setScene(sceneRules);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
