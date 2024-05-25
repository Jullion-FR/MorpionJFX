module com.jdauvergne.morpion {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.jdauvergne.morpion to javafx.fxml;
    exports com.jdauvergne.morpion;
    exports com.jdauvergne.morpion.controllers;
    opens com.jdauvergne.morpion.controllers to javafx.fxml;
    exports com.jdauvergne.morpion.player;
    opens com.jdauvergne.morpion.player to javafx.fxml;
}