package simar.com.easykey.modules_.room_d;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
@Dao
public interface CategoryDao {

    @Query("Select * from categories")
    List<Categories>getCategoryList();
    @Insert
    void insertCategory(Categories categories);

    @Update
    void updateCategories(Categories categories);

    @Delete
    void deleteCategories(Categories categories);


}
