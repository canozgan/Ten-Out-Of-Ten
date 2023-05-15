package com.canozgan.tenoutoften.model;

public class PlayerModel {
    public int id;
    public String name;
    public String overallScore;
    public String offensive;
    public String defensive;
    public String stability;
    public String imageUrl;

    public PlayerModel( String name, String overallScore, String offensive, String defensive, String stability, String imageUrl) {
        this.id = id;
        this.name = name;
        this.overallScore = overallScore;
        this.offensive = offensive;
        this.defensive = defensive;
        this.stability = stability;
        this.imageUrl = imageUrl;
    }
}
