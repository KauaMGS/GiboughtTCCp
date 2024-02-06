package com.kldv.gibought.Utils;

public class User {
    private int id;
    private String nameCli;
    private String emailCli;
    private String passCli;

    public User(String nameCli, String emailCli, String passCli) {
        this.nameCli = nameCli;
        this.emailCli = emailCli;
        this.passCli = passCli;
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameCli() {
        return nameCli;
    }

    public void setNameCli(String nameCli) {
        this.nameCli = nameCli;
    }

    public String getEmailCli() {
        return emailCli;
    }
    public void setEmailCli(String emailCli) {
        this.emailCli = emailCli;
    }

    public String getPassCli() {
        return passCli;
    }
    public void setPassCli(String passCli) {
        this.passCli = passCli;
    }
}
