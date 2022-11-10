package edu.upc.dsa.models;

public class Credentials {
    String mail;
    String password;

    public Credentials() {}

    public Credentials(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }

    public String getMail() { return this.mail; }

    public void setMail(String mail) { this.mail = mail; }

    public String getPassword() { return this.password; }

    public void setPassword(String password) { this.password = password; }
}
