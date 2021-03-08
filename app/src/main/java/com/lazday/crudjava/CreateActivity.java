package com.lazday.crudjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.android.material.button.MaterialButton;

public class CreateActivity extends AppCompatActivity {
    private final String TAG = "CreateActivity";

    private EditText editNote;
    private MaterialButton buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        setupView();
        setupListener();
    }

    private void setupView(){
        editNote = findViewById(R.id.edit_note);
        buttonSave = findViewById(R.id.button_save);
    }

    private void setupListener(){
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonSave.setEnabled(false);
                AndroidNetworking.post("http://192.168.1.4/api/crud/create.php")
                        .addBodyParameter("note", editNote.getText().toString())
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsObject(SubmitResponse.class, new ParsedRequestListener<SubmitResponse>() {
                            @Override
                            public void onResponse(SubmitResponse response) {
                                Toast.makeText(
                                        getApplicationContext(),
                                        response.getMessage(),
                                        Toast.LENGTH_SHORT
                                ).show();
                                finish();
                            }
                            @Override
                            public void onError(ANError anError) {
                                Log.e(TAG, "response " + anError.toString());
                            }
                        });

            }
        });
    }
}