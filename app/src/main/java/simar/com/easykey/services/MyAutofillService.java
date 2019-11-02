package simar.com.easykey.services;

import android.app.assist.AssistStructure;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.service.autofill.AutofillService;
import android.service.autofill.Dataset;
import android.service.autofill.FillCallback;
import android.service.autofill.FillContext;
import android.service.autofill.FillRequest;
import android.service.autofill.SaveCallback;
import android.service.autofill.SaveRequest;

import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MyAutofillService  {


    void identifyEmailFields(AssistStructure.ViewNode node, List<AssistStructure.ViewNode> emailFields) {

        if(node.getClassName().contains("EditText")) {
            String viewId = node.getHint();
            if(viewId!=null &&
             (viewId.contains("email") ||
              viewId.contains("username")||
              viewId.contains("Email Address"))) {
                emailFields.add(node);
                return;
            }
        }

        for(int i=0; i<node.getChildCount();i++) {
            identifyEmailFields(node.getChildAt(i), emailFields);
        }
    }

/*
    @Override
    public void onFillRequest(@NonNull FillRequest request, @NonNull CancellationSignal cancellationSignal, @NonNull FillCallback callback) {
      *//*  List<AssistStructure.ViewNode> emailFields = new ArrayList<>();
        Log.e("called","onFillRequest");
        List<FillContext> context = request.getFillContexts();
        AssistStructure structure = context.get(context.size() - 1).getStructure();
        identifyEmailFields(structure.getWindowNodeAt(0).getRootViewNode(), emailFields);
        Log.e("called","onFillRequest"+structure.getWindowNodeAt(0).getRootViewNode().getHint());
        // Do nothing if no email fields found
        if(emailFields.size() == 0)
            return;
        // Load the email addresses from preferences
        String primaryEmail ="simar@gmail.com";// sharedPreferences.getString("PRIMARY_EMAIL", "");
        String secondaryEmail ="simar@gmail.com";// sharedPreferences.getString("SECONDARY_EMAIL", "");

        // Create remote views for both the email addresses
        RemoteViews rvPrimaryEmail = new RemoteViews(getPackageName(), R.layout.email_suggestion);
        rvPrimaryEmail.setTextViewText(R.id.email_suggestion_item, primaryEmail);
        RemoteViews rvSecondaryEmail = new RemoteViews(getPackageName(), R.layout.email_suggestion);
        rvSecondaryEmail.setTextViewText(R.id.email_suggestion_item, secondaryEmail);

        // Choose the first email field
        AssistStructure.ViewNode emailField = emailFields.get(0);

        // Create a dataset for the email addresses
        Dataset primaryEmailDataSet = new Dataset.Builder(rvPrimaryEmail)
                .setValue(
                        emailField.getAutofillId(),
                        AutofillValue.forText(primaryEmail)
                ).build();
        Dataset secondaryEmailDataSet = new Dataset.Builder(rvSecondaryEmail)
                .setValue(
                        emailField.getAutofillId(),
                        AutofillValue.forText(secondaryEmail)
                ).build();

        // Create and send response with both datasets
        FillResponse response = new FillResponse.Builder()
                .addDataset(primaryEmailDataSet)
                .addDataset(secondaryEmailDataSet)
                .build();
        callback.onSuccess(response);*//*

    }

     @Override
    public void onSaveRequest(@NonNull SaveRequest request, @NonNull SaveCallback callback) {

    }*/
}
