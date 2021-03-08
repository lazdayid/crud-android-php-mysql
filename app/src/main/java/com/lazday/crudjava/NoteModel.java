package com.lazday.crudjava;

import java.io.Serializable;
import java.util.List;

public class NoteModel {

    private List<Data> notes;

    public List<Data> getNotes() {
        return notes;
    }

    public void setNotes(List<Data> notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "MainModel{" +
                "notes=" + notes +
                '}';
    }

    public class Data implements Serializable {

        private String id;
        private String note;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "id='" + id + '\'' +
                    ", note='" + note + '\'' +
                    '}';
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }
    }

}
