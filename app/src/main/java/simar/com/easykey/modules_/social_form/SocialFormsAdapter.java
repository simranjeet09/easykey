package simar.com.easykey.modules_.social_form;

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
import simar.com.easykey.modules_.view_forms.FormModel;
import simar.com.easykey.modules_.view_forms.ViewFormActivity;
import simar.com.easykey.modules_.view_forms.ViewFormsList;


public class SocialFormsAdapter extends RecyclerView.Adapter<SocialFormsAdapter.MyViewHolder> {


    List<SocialModel> dataList = new ArrayList<>();
    Context context;

    public SocialFormsAdapter(Context context, List<SocialModel> dataList) {
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
        h.itemView.setOnClickListener(v -> goForNextActivity(dataList.get(pos)));

    }


    private void goForNextActivity(SocialModel m) {
        Intent intent = new Intent(context, SocialFormDetails.class);
        intent.putExtra("data", m);
        intent.putExtra("cat", ((SocialFormActivity) context).getIntent().getStringExtra("cat"));
        ((SocialFormActivity) context).startActivityForResult(intent, 100);

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
