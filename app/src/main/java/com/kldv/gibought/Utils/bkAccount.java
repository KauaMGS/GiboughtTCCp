package com.kldv.gibought.Utils;
public class bkAccount {
    private int id;
    private final String nameBank;
    private int idImage;
    private Double valueBank;
    private Integer typeBank;

    public bkAccount(int id, String nameBank, int idImage, Double valueBank, Integer typeBank) {
        this.id = id;
        this.nameBank = nameBank;
        this.idImage = idImage;
        this.valueBank = valueBank;
        this.typeBank = typeBank;
    }

    public bkAccount(String nameBank, int idImage, Integer typeBank, double valueBank) {
        this.nameBank = nameBank;
        this.idImage = idImage;
        this.valueBank = valueBank;
        this.typeBank = typeBank;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameBank() {
        return nameBank;
    }

    public int getIdImage() {
        return idImage;
    }

    public Double getValueBank() {
        return valueBank;
    }

    public Integer getTypeBank() {
        return typeBank;
    }
}