package com.lazday.crudjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";

    private NoteAdapter noteAdapter;
    private RecyclerView listNote;
    private FloatingActionButton fabCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupView();
        setupList();
        setupListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getNote();
    }

    private void setupView(){
        listNote = findViewById(R.id.list_note);
        fabCreate = findViewById(R.id.fab_create);

    }

    private void setupList(){
        noteAdapter = new NoteAdapter(new ArrayList<>(), new NoteAdapter.AdapterListener() {
            @Override
            public void onUpdate(NoteModel.Data data) {
                startActivity(
                        new Intent(HomeActivity.this, UpdateActivity.class)
                        .putExtra("intent_data", data)
                );
            }
            @Override
            public void onDelete(NoteModel.Data data) {
                AndroidNetworking.post("http://192.168.1.4/api/crud/delete.php")
                        .addBodyParameter("id", data.getId())
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsObject(SubmitResponse.class, new ParsedRequestListener<SubmitResponse>() {
                            @Override
                            public void onResponse(SubmitResponse response) {
                                Log.e(TAG, "response " + response.toString());
                                Toast.makeText(
                                        getApplicationContext(),
                                        response.getMessage(),
                                        Toast.LENGTH_SHORT
                                ).show();
                                getNote();
                            }
                            @Override
                            public void onError(ANError anError) {
                                Log.e(TAG, "response " + anError.toString());
                            }
                        });
            }
        });
        listNote.setAdapter( noteAdapter );
    }

    private void setupListener(){
        fabCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, CreateActivity.class));
            }
        });
    }

    private void getNote(){
        AndroidNetworking.get("http://192.168.1.4/api/crud/data.php")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObject(NoteModel.class, new ParsedRequestListener<NoteModel>() {
                    @Override
                    public void onResponse(NoteModel response) {
                        Log.e(TAG, "response " + response.toString());
                        List<NoteModel.Data> dataList = response.getNotes();
                        noteAdapter.setData( dataList );
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e(TAG, "response " + anError.toString());
                    }
                });
    }
}