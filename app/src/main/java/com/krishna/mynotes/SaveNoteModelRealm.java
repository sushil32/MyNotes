package com.krishna.mynotes;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Krishna on 11/02/17.
 */

public class SaveNoteModelRealm extends RealmObject{
    @PrimaryKey
    private  String noteTitle;
    private String noteContent;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }
}
