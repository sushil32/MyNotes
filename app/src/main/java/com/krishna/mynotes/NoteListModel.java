package com.krishna.mynotes;

/**
 * Created by Krishna on 13/03/17.
 */

public class NoteListModel {
    String Title;
    boolean checkbox;


    public NoteListModel(String title, boolean checkbox) {
        Title = title;
        this.checkbox = checkbox;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }
}
