package simar.com.easykey.home_dialog;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import simar.com.easykey.R;
import simar.com.easykey.lockmodule.activity.EnterPinActivityDialog;
import simar.com.easykey.modules_.social_form.SocialFormActivity;
import simar.com.easykey.modules_.social_form.SocialFormDetails;
import simar.com.easykey.modules_.social_form.SocialModel;


public class EmailsAdapter extends RecyclerView.Adapter<EmailsAdapter.MyViewHolder> {


    List<EmailPassModel> dataList = new ArrayList<>();
    Context context;

    public EmailsAdapter(Context context, List<EmailPassModel> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.emails_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder h, int pos) {
        h.title.setText(dataList.get(pos).getTitle());
        h.email.setText(dataList.get(pos).getEmail());
        h.password.setText(dataList.get(pos).getEmail());
        h.email.setOnClickListener(v -> setClipboard(context, dataList.get(pos).getEmail()));
        h.password.setOnClickListener(v -> setString(context, dataList.get(pos).getPassword()));

    }

    private void setString(Context context, String password) {
        AlertDialogActivity.STRINGTOCOPY=password;
        ((AlertDialogActivity)context).startActivityForResult(new Intent(context, EnterPinActivityDialog.class),100);

    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView email;
        TextView title;
        TextView password;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.email);
            password = itemView.findViewById(R.id.password);
            title = itemView.findViewById(R.id.title);
        }
    }
    private void setClipboard(Context context, String text) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
        Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show();
        ((AlertDialogActivity) context).finish();
    }


}
