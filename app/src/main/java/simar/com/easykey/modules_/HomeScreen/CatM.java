package simar.com.easykey.modules_.HomeScreen;

import java.io.Serializable;

public class CatM implements Serializable {

    String id;
    String tbl_name;
    String lbl;

    public CatM(String id, String tbl_name,String lbl) {
        this.id = id;
        this.tbl_name = tbl_name;
        this.lbl= lbl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return tbl_name;
    }

    public void setName(String name) {
        this.tbl_name = name;
    }

    public String getLbl() {
        return lbl;
    }

    public void setLbl(String lbl) {
        this.lbl = lbl;
    }
}
