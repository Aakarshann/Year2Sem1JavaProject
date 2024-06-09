/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leballs.model;

/**
 *
 * @author user
 */
public class ModelClass {
    private String Name;
    private String GamesPlayed;
    private String PointsPerGame;
    private String ReboundsPerGame;
    private String BlocksPerGame;
    private String AssistsPerGame;
    private String JerseyNumber;
    private static String SearchField = "Search by Name";
    
    
    

    public String getName() {
        return Name;
    }

    public String getJerseyNumber() {
        return JerseyNumber;
    }

    public void setJerseyNumber(String JerseyNumber) {
        this.JerseyNumber = JerseyNumber;
    }

    public static String getSearchField() {
        return SearchField;
    }

    public static void setSearchField(String SearchField) {
        ModelClass.SearchField = SearchField;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getGamesPlayed() {
        return GamesPlayed;
    }

    public void setGamesPlayed(String GamesPlayed) {
        this.GamesPlayed = GamesPlayed;
    }

    public String getPointsPerGame() {
        return PointsPerGame;
    }

    public void setPointsPerGame(String PointsPerGame) {
        this.PointsPerGame = PointsPerGame;
    }

    public String getReboundsPerGame() {
        return ReboundsPerGame;
    }

    public void setReboundsPerGame(String ReboundsPerGame) {
        this.ReboundsPerGame = ReboundsPerGame;
    }

    public String getBlocksPerGame() {
        return BlocksPerGame;
    }

    public void setBlocksPerGame(String BlocksPerGame) {
        this.BlocksPerGame = BlocksPerGame;
    }

    public String getAssistsPerGame() {
        return AssistsPerGame;
    }

    public void setAssistsPerGame(String AssistsPerGame) {
        this.AssistsPerGame = AssistsPerGame;
    }

}
