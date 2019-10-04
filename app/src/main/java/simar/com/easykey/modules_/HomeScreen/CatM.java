package simar.com.easykey.modules_.HomeScreen;

public class CatM {

    String id;
    String name;
    String lbl;

    public CatM(String id, String name,String lbl) {
        this.id = id;
        this.name = name;
        this.lbl= lbl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLbl() {
        return lbl;
    }

    public void setLbl(String lbl) {
        this.lbl = lbl;
    }
}
