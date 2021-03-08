package com.lazday.crudjava;

import androidx.appcompat.app.AppCompatActivity;

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

public class UpdateActivity extends AppCompatActivity {
    private final String TAG = "UpdateActivity";

    private EditText editNote;
    private MaterialButton buttonSave;
    private String noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        setupView();
        setupListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getNote();
    }

    private void setupView(){
        editNote = findViewById(R.id.edit_note);
        buttonSave = findViewById(R.id.button_save);
        buttonSave.setText("Simpan Perubahan");
    }

    private void setupListener() {
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonSave.setEnabled(false);
                AndroidNetworking.post("http://192.168.1.4/api/crud/update.php")
                        .addBodyParameter("id", noteId)
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

    private void getNote(){
        NoteModel.Data data = (NoteModel.Data) getIntent().getSerializableExtra("intent_data");
        noteId = data.getId();
        editNote.setText( data.getNote() );
    }
}