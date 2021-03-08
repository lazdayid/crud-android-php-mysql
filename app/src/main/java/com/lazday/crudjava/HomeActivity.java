package com.lazday.crudjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";

    private NoteAdapter noteAdapter;
    private RecyclerView listNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getNote();
    }

    private void setupView(){
        listNote = findViewById(R.id.list_note);
        noteAdapter = new NoteAdapter(new ArrayList<>(), new NoteAdapter.AdapterListener() {
            @Override
            public void onClick(NoteModel.Data data) {

            }
        });
        listNote.setAdapter( noteAdapter );
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