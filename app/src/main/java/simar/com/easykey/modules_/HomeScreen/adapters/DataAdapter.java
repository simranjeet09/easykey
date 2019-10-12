package simar.com.easykey.modules_.HomeScreen.adapters;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import simar.com.easykey.R;
import simar.com.easykey.modules_.HomeScreen.CatM;

import simar.com.easykey.modules_.view_forms.ViewFormsList;
import simar.com.easykey.sqlite_mod.FeedReaderDbHelper;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {


    List<CatM> dataList = new ArrayList<>();
    Context context;
    Boolean isEditEnabled=false;
    FeedReaderDbHelper feedReaderDbHelper;
    public DataAdapter(Context context, List<CatM> dataList, Boolean isEditEnabled, FeedReaderDbHelper feedReaderDbHelper) {
        this.context = context;
        this.dataList = dataList;
        this.isEditEnabled=isEditEnabled;
        this.feedReaderDbHelper=feedReaderDbHelper;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cat_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder h, int pos) {
        h.name.setText(dataList.get(pos).getLbl());
        if (isEditEnabled){
            h.name.setCompoundDrawablesWithIntrinsicBounds(null,null,ContextCompat.getDrawable(context,R.drawable.ic_delete_black_24dp),null);
        }
        h.itemView.setOnClickListener(v ->handleClick(dataList.get(pos),pos));
    }

    private void handleClick(CatM m,int pos) {


        if (!isEditEnabled){

            Intent intent= new Intent(context, ViewFormsList.class);
            intent.putExtra("tbl_name",m.getName());
            context.startActivity(intent);

        }else {

            AlertDialog.Builder builder= new AlertDialog.Builder(context);
            builder.setMessage("Do you want to delete "+m.getLbl()+" from the app.\nPlease note: It would not be recoverable!");
            builder.setTitle("WARNING!");
            builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    feedReaderDbHelper.deleteCatFromDb(m.getName());
                    dataList.remove(pos);
                    notifyDataSetChanged();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
        }
    }
}
