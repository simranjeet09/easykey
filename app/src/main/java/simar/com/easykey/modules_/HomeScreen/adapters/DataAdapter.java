package simar.com.easykey.modules_.HomeScreen.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import simar.com.easykey.R;
import simar.com.easykey.modules_.HomeScreen.CatM;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {


    List<CatM> dataList = new ArrayList<>();
    Context context;

    public DataAdapter(Context context, List<CatM> dataList) {
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
        h.name.setText(dataList.get(pos).getName());
        h.itemView.setOnClickListener(v ->handleClick(dataList.get(pos)));

    }

    private void handleClick(CatM m) {



    }

    private void goForNextActivity(Class c,CatM m) {
        Intent intent=new Intent(context,c);
        intent.putExtra("name",m.getName());
        intent.putExtra("id",m.getId());
        intent.putExtra("from","");
        context.startActivity(intent);
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
