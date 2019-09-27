package simar.com.easykey.modules_.room_d;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class Categories {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "cat_name")
    private String car_name;


    public Categories(int id, String car_name) {
        this.id = id;
        this.car_name = car_name;
    }

    @Ignore
    public Categories(String car_name) {
        this.car_name = car_name;
    }

    public int getId() {
        return id;
    }

    public String getCar_name() {
        return car_name;
    }

}
