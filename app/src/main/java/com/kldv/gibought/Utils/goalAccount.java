package com.kldv.gibought.Utils;
public class goalAccount {
    private int id;
    private String nameGoal;
    private int idImage;
    private int typeGoal;
    private int idBank;
    private Double valueGoal;
    private Double valGoal;

    public goalAccount(int id, String nameGoal, int idImage, Double valueGoal, Double valGoal,int typeGoal,int idBank) {
        this.id = id;
        this.nameGoal = nameGoal;
        this.idImage = idImage;
        this.valueGoal = valueGoal;
        this.valGoal = valGoal;
        this.typeGoal = typeGoal;
        this.idBank = idBank;
    }
    public goalAccount(String nameGoal, int idImage, Double valueGoal, Double valGoal,int typeGoal,int idBank) {
        this.nameGoal = nameGoal;
        this.idImage = idImage;
        this.valueGoal = valueGoal;
        this.valGoal = valGoal;
        this.typeGoal = typeGoal;
        this.idBank = idBank;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameGoal() {
        return nameGoal;
    }


    public int getIdImage() {
        return idImage;
    }

    public int getTypeGoal() {
        return typeGoal;
    }
    public int getIdBank() {
        return idBank;
    }


    public Double getValueGoal() {
        return valueGoal;
    }


    public Double getValGoal() {
        return valGoal;
    }
}