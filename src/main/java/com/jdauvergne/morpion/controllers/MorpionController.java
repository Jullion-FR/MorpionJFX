package com.jdauvergne.morpion.controllers;

import com.jdauvergne.morpion.Main;
import com.jdauvergne.morpion.player.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.Random;

public class MorpionController {
    private Stage modalDialog;
    private Player player1;
    private Player player2;
    private Player[] playerOrder;
    private Player[] playerStartOrder;
    private final Player[] playerGrid = new Player[9];
    private Image emptyImage;
    private int playCount = 0;
    private static final int SIDE_PANEL_BIG = 300;
    private static final int SIDE_PANEL_SMALL = 20;
    private boolean isStartRandom, isPlaySolo, isHard;

    @FXML
    private ImageView leftGIF;
    @FXML
    private ImageView rightGIF;
    @FXML
    private SplitPane leftPane;
    @FXML
    private SplitPane rightPane;
    @FXML
    private GridPane grid;
    @FXML
    private Label player1NameLabel;
    @FXML
    private Label player2NameLabel;
    @FXML
    private RadioMenuItem radioMenuItemPlayer1;
    @FXML
    private RadioMenuItem radioMenuItemPlayer2;
    @FXML
    private Label scoreLabel;

    @FXML
    private void initialize() {
        // Initialisation des images et du plateau de jeu
        emptyImage = new Image(getRessourceURL("pictures/empty.png"));
        for (int i = 0; i < 9; i++) {
            playerGrid[i] = null;
            ((ImageView) grid.getChildren().get(i)).setImage(emptyImage);
            GridPane.setHalignment(grid.getChildren().get(i), HPos.CENTER);
        }
        player1 = new Player("Player 1", getRessourceURL("pictures/cross.png"));
        player2 = new Player("Player 2", getRessourceURL("pictures/circle.png"));
        updatePlayersLabel();
        playerStartOrder = new Player[]{player1, player2};
        loadSideGIF(getRessourceURL("pictures/think.gif"));
        isStartRandom = isPlaySolo = isHard = false;
        handleNewGame();
    }

    @FXML
    void handleTileClick(MouseEvent event) {
        // Gestion du clic sur une case du plateau
        ImageView imageView = ((ImageView) event.getSource());
        if (imageView.getImage().equals(emptyImage)) {
            imageView.setImage(playerOrder[0].getImage());
            playerGrid[grid.getChildren().indexOf(imageView)] = playerOrder[0];
            if(increasePlayCount()){
                return;
            }
            playerOrder = new Player[]{playerOrder[1], playerOrder[0]};
            if (isPlaySolo && playerOrder[0] == player2) {
                makePlayer2BotPlay();
            }
            handleSidePanel();
        }
    }

    private void makePlayer2BotPlay() {
        // Faire jouer l'algorithme pour player2
        int index;
        if (!isHard) {
            Random rand = new Random();
            index = rand.nextInt(9);
            while (playerGrid[index] != null) {
                index = rand.nextInt(9);
            }
        } else {
            index = findBestMove();
        }
        if (index != -1) {
            playerGrid[index] = player2;
            ((ImageView) (grid.getChildren()).get(index)).setImage(player2.getImage());
            increasePlayCount();
            playerOrder = new Player[]{player1, player2};
        }
    }

    private boolean increasePlayCount() {
        // Incrémente le compteur de coups joués et vérifie les conditions de victoire
        playCount++;
        if (playCount > 4) {
            Player winner = checkWinner(true);
            if (winner != null || playCount >= 9) {
                handleWin(winner);
                return true;
            }
        }
        return false;
    }

    private void handleSidePanel() {
        // Mise à jour de l'affichage des panneaux latéraux en fonction du joueur actuel
        if (playerOrder[0].equals(player1)) {
            rightGIF.setVisible(false);
            leftGIF.setVisible(true);
            rightPane.setPrefWidth(SIDE_PANEL_SMALL);
            leftPane.setPrefWidth(SIDE_PANEL_BIG);
            player2NameLabel.setOpacity(0.5f);
            player1NameLabel.setOpacity(1.0f);
        } else {
            leftGIF.setVisible(false);
            rightGIF.setVisible(true);
            leftPane.setPrefWidth(SIDE_PANEL_SMALL);
            rightPane.setPrefWidth(SIDE_PANEL_BIG);
            player1NameLabel.setOpacity(0.5f);
            player2NameLabel.setOpacity(1.0f);
        }
    }

    private void handleWin(Player winner) {
        // Gestion de la victoire ou de l'égalité
        if (winner != null) {
            winner.setScore(winner.getScore() + 1);
            System.out.println(winner.getName() + " a gagné!");
            scoreLabel.setText(winner.getName().toUpperCase() + " gagne !");
        } else {
            scoreLabel.setText("EGALITE !");
        }
        hideSideGIF();
        player2NameLabel.setOpacity(1.0f);
        player1NameLabel.setOpacity(1.0f);
        Main.setModalResult();
        showModal();
    }

    public void handleNewGame() {
        // Initialisation d'une nouvelle partie
        clearGrid();
        if (isStartRandom) {
            changeFirst(new Random().nextInt(1, 3));
        }
        playerOrder = playerStartOrder;
        if (isPlaySolo && playerStartOrder[0] == player2) {
            makePlayer2BotPlay();
        }
        handleSidePanel();
        hideSideGIF();
        scoreLabel.setText(player1.getScore() + " x " + player2.getScore());
    }

    public void updatePlayersLabel() {
        // Mise à jour des labels des joueurs
        String p1 = player1.getName();
        String p2 = player2.getName();
        player1NameLabel.setText(p1);
        player2NameLabel.setText(p2);
        radioMenuItemPlayer1.setText(p1);
        radioMenuItemPlayer2.setText(p2);
        updatePlayersLabelCSS();
    }

    private void updatePlayersLabelCSS() {
        // Mise à jour du style des labels des joueurs
        player1NameLabel.setStyle("-fx-border-color:black;");
        player1NameLabel.setTextFill(player1.getForegroundColor());
        player1NameLabel.setBackground(Background.fill(player1.getBackgroundColor()));
        player2NameLabel.setStyle("-fx-border-color:black;");
        player2NameLabel.setTextFill(player2.getForegroundColor());
        player2NameLabel.setBackground(Background.fill(player2.getBackgroundColor()));
    }

    private void loadSideGIF(String path) {
        // Chargement des GIF pour les panneaux latéraux
        Image gifThink = new Image(path);
        leftGIF.setImage(gifThink);
        rightGIF.setImage(gifThink);
        rightGIF.setFitWidth(SIDE_PANEL_BIG);
        leftGIF.setFitWidth(SIDE_PANEL_BIG);
    }

    private void hideSideGIF() {
        // Cacher les GIF des panneaux latéraux
        leftGIF.setVisible(false);
        rightGIF.setVisible(false);
        leftPane.setPrefWidth(SIDE_PANEL_SMALL);
        rightPane.setPrefWidth(SIDE_PANEL_SMALL);
    }

    private String getRessourceURL(String fileName) {
        // Obtenir l'URL d'une ressource
        return Objects.requireNonNull(Main.class.getResource(fileName)).toString();
    }

    @FXML
    void clearGrid(ActionEvent event) {
        handleNewGame();
    }

    private void clearGrid() {
        // Réinitialisation de la grille de jeu
        playCount = 0;
        for (int i = 0; i < 9; i++) {
            playerGrid[i] = null;
            ((ImageView) grid.getChildren().get(i)).setImage(emptyImage);
            (grid.getChildren().get(i)).setOpacity(1.0f);
        }
    }

    private void opacityWinLine(int first, int second, int third) {
        // Changer l'opacité des cases de la ligne gagnante
        (grid.getChildren().get(first)).setOpacity(0.5f);
        (grid.getChildren().get(second)).setOpacity(0.5f);
        (grid.getChildren().get(third)).setOpacity(0.5f);
    }

    public Player checkWinner(boolean colorLine) {
        // Vérification des conditions de victoire
        // Vérification des lignes
        for (int i = 0; i < 3; i++) {
            if (playerGrid[i * 3] != null && playerGrid[i * 3].equals(playerGrid[i * 3 + 1])
                    && playerGrid[i * 3 + 1].equals(playerGrid[i * 3 + 2])) {
                if (colorLine) opacityWinLine(i * 3, i * 3 + 1, i * 3 + 2);
                return playerGrid[i * 3];
            }
        }
        // Vérification des colonnes
        for (int i = 0; i < 3; i++) {
            if (playerGrid[i] != null && playerGrid[i].equals(playerGrid[i + 3])
                    && playerGrid[i + 3].equals(playerGrid[i + 6])) {
                if (colorLine) opacityWinLine(i, i + 3, i + 6);
                return playerGrid[i];
            }
        }
        // Vérification des diagonales
        if (playerGrid[0] != null && playerGrid[0].equals(playerGrid[4])
                && playerGrid[4].equals(playerGrid[8])) {
            if (colorLine) opacityWinLine(0, 4, 8);
            return playerGrid[0];
        }
        if (playerGrid[2] != null && playerGrid[2].equals(playerGrid[4])
                && playerGrid[4].equals(playerGrid[6])) {
            if (colorLine) opacityWinLine(2, 4, 6);
            return playerGrid[2];
        }
        // Pas de gagnant
        return null;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    @FXML
    void changeFirst(ActionEvent event) {
        // Changer l'ordre des joueurs pour commencer
        RadioMenuItem source = ((RadioMenuItem) event.getSource());
        int num = 0;
        isStartRandom = false;
        if (source.equals(radioMenuItemPlayer1)) {
            num = 1;
        } else if (source.equals(radioMenuItemPlayer2)) {
            num = 2;
        } else {
            isStartRandom = true;
        }
        changeFirst(num);
        if (playCount == 0) {
            handleNewGame();
        }
    }

    public void changeFirst(int playerNumber) {
        // Définir l'ordre des joueurs pour commencer
        if (playerNumber == 1) {
            playerStartOrder = new Player[]{player1, player2};
        } else if (playerNumber == 2) {
            playerStartOrder = new Player[]{player2, player1};
        }
    }

    @FXML
    void quit(ActionEvent event) {
        // Quitter le jeu
        System.exit(0);
    }

    @FXML
    void showColor(ActionEvent event) {
        Main.setModalColor();
        showModal();
    }

    @FXML
    void showRules(ActionEvent event) {
        Main.setModalRules();
        showModal();
    }

    @FXML
    void showPicture(ActionEvent event) {
        Main.setModalPicture();
        showModal();
    }

    @FXML
    void showRename(ActionEvent event) {
        Main.setModalRename();
        showModal();
    }

    public void showModal() {
        // Afficher la fenêtre modale
        setModalOpacity(1.0f);
        modalDialog.show();
    }

    public void setModalOpacity(float opacity) {
        // Définir l'opacité de la fenêtre modale
        modalDialog.setOpacity(opacity);
    }

    public Stage getModalDialog() {
        return modalDialog;
    }

    public void setModalDialog(Stage modal) {
        this.modalDialog = modal;
    }

    @FXML
    void handlePlayMode(ActionEvent event) {
        // Gestion des modes de jeu (facile, difficile, etc.)
        RadioMenuItem source = ((RadioMenuItem) event.getSource());
        switch (source.getText()) {
            case "Facile":
                isPlaySolo = true;
                isHard = false;
                player2.setName("Easy BOT");
                break;
            case "Difficile":
                isPlaySolo = true;
                isHard = true;
                player2.setName("Hard BOT");
                break;
            default:
                isPlaySolo = false;
                player2.setName("Player 2");
                break;
        }
        updatePlayersLabel();
        handleNewGame();
    }

    // Trouver le meilleur coup pour le joueur2
    public int findBestMove() {
        int bestValue = Integer.MIN_VALUE;
        int bestMove = -1;

        for (int i = 0; i < 9; i++) {
            if (playerGrid[i] == null) {
                playerGrid[i] = player2;
                int moveValue = minimax(playerGrid, 0, false);
                playerGrid[i] = null;

                if (moveValue > bestValue) {
                    bestValue = moveValue;
                    bestMove = i;
                }
            }
        }
        return bestMove;
    }

    // Fonction Minimax
    private int minimax(Player[] board, int depth, boolean isMaximizing) {
        Player winner = checkWinner(false);
        if (winner == player2) {
            return 10 - depth;
        }
        if (winner == player1) {
            return depth - 10;
        }
        if (isBoardFull(board)) {
            return 0;
        }

        int bestValue = (isMaximizing) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < 9; i++) {
            if (board[i] == null) {
                board[i] = isMaximizing ? player2 : player1;
                int currentValue = minimax(board, depth + 1, !isMaximizing);
                board[i] = null;

                if (isMaximizing) {
                    bestValue = Math.max(bestValue, currentValue);
                } else {
                    bestValue = Math.min(bestValue, currentValue);
                }
            }
        }
        return bestValue;
    }

    private boolean isBoardFull(Player[] board) {
        // Vérification si la grille est pleine
        for (Player player : board) {
            if (player == null) {
                return false;
            }
        }
        return true;
    }
}
