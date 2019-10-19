package simar.com.easykey.modules_.social_form;

import java.io.Serializable;

public class SocialModel implements Serializable {
    int id;
    String title;
    String email;
    String password;
    String cat;
    String note;

    public SocialModel(int id, String title, String email, String password, String cat, String note) {
        this.id = id;
        this.title = title;
        this.email = email;
        this.password = password;
        this.cat = cat;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
