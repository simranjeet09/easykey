package simar.com.easykey.modules_.view_forms;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import simar.com.easykey.R;



public class AllFormsAdapter extends RecyclerView.Adapter<AllFormsAdapter.MyViewHolder> {


    List<FormModel> dataList = new ArrayList<>();
    Context context;

    public AllFormsAdapter(Context context, List<FormModel> dataList) {
        this.context = context;
        this.dataList = dataList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cat_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder h, int pos) {
        h.name.setText(dataList.get(pos).getTitle());
        h.itemView.setOnClickListener(v ->goForNextActivity(dataList.get(pos)));

    }



    private void goForNextActivity(FormModel m) {
        Intent intent=new Intent(context,ViewFormActivity.class);
        intent.putExtra("name",m.getTitle());
        intent.putExtra("id",m.getId());
        intent.putExtra("tbl_name",((ViewFormsList)context).getIntent().getStringExtra("tbl_name"));
        ((ViewFormsList)context).startActivityForResult(intent,100);
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
