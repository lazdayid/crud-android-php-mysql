package com.lazday.crudjava;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private List<NoteModel.Data> dataList;
    private AdapterListener listener;

    public NoteAdapter(List<NoteModel.Data> dataList, AdapterListener listener) {
        this.dataList    = dataList ;
        this.listener   = listener ;
    }

    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_note,
                        parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final NoteModel.Data data = dataList.get(position);
        viewHolder.textNote.setText( data.getNote() );
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView container;
        TextView textNote;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textNote = itemView.findViewById(R.id.text_note);
        }
    }

    public void setData(List<NoteModel.Data> data) {
        dataList.addAll(data);
        notifyDataSetChanged();
    }

    public interface AdapterListener {
        void onClick(NoteModel.Data data);
    }
}