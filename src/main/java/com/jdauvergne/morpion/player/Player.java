package com.jdauvergne.morpion.player;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Player {
    private String name;
    private Color backgroundColor;
    private Color foregroundColor;
    private int score;
    private String imageURL;
    private Image image;

    // Constructeur
    public Player(String name, String imageURL) {
        this.name = name;
        this.imageURL = imageURL;
        this.backgroundColor = new Color(1, 1, 1, 1);
        this.foregroundColor = new Color(0, 0, 0, 1);
        this.score = 0;
        try {
            image = new Image(imageURL);
        } catch (Exception e) {
            System.err.println("Probleme de chargement de l'image :\n" + e.getMessage());
        }
    }

    // Obtenir l'image du joueur
    public Image getImage() {
        return image;
    }

    // Définir l'image du joueur
    public void setImage(Image image) {
        this.image = image;
        this.imageURL = image.getUrl();
    }

    // Obtenir l'URL de l'image du joueur
    public String getImageURL() {
        return imageURL;
    }

    // Définir l'URL de l'image du joueur
    public void setImageURL(String imageURL) {
        try {
            image = new Image(imageURL);
            this.imageURL = imageURL;
        } catch (Exception e) {
            System.err.println("Probleme de chargement de l'image :\n" + e.getMessage());
        }
    }

    // Obtenir le score du joueur
    public int getScore() {
        return score;
    }

    // Définir le score du joueur
    public void setScore(int score) {
        this.score = score;
    }

    // Obtenir le nom du joueur
    public String getName() {
        return name;
    }

    // Définir le nom du joueur
    public void setName(String name) {
        this.name = name;
    }

    // Obtenir la couleur de premier plan du joueur
    public Color getForegroundColor() {
        return foregroundColor;
    }

    // Définir la couleur de premier plan du joueur
    public void setForegroundColor(Color foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    // Obtenir la couleur de fond du joueur
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    // Définir la couleur de fond du joueur
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
