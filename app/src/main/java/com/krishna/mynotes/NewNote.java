package com.krishna.mynotes;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import io.realm.Realm;


public class NewNote extends Fragment {

View v;
    EditText title,content;
    TextView save;
    String text_title,NoteType="";
    Realm realm;
Bundle b;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_new_note, container, false);

        title=(EditText)v.findViewById(R.id.ed_title);
        content=(EditText)v.findViewById(R.id.ed_content);
        save=(TextView) v.findViewById(R.id.save);

        realm=General.realmconfig(getContext());
        b=new Bundle();
        b=getArguments();
        if(b!=null&&b.containsKey("NoteType")) {
            NoteType = b.getString("NoteType");


            Log.i("Entry_point", "Entry_point read   :"+ text_title);
        }
        if(b!=null&&b.containsKey("Title")) {

            text_title = b.getString("Title");
        }
        Log.i("data","====== "+ NoteType+" "+text_title+" "+title+ " "+content);

        if(NoteType.equalsIgnoreCase("Old")){
            Log.i("data","====== "+ NoteType+" "+text_title+" "+title+ " "+content);

            SaveNoteModelRealm results2 = realm.where(SaveNoteModelRealm.class).equalTo("noteTitle",text_title).findFirst();
            if(results2!=null) {
                title.setText(results2.getNoteTitle());
                content.setText(results2.getNoteContent());
            }
        }



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveNoteModelRealm saveNoteModelRealm =new SaveNoteModelRealm();
                saveNoteModelRealm.setId(title.getText().toString()+"1");
                saveNoteModelRealm.setNoteTitle(title.getText().toString());
                saveNoteModelRealm.setNoteContent(content.getText().toString());
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(saveNoteModelRealm);
                realm.commitTransaction();

                ((MainActivity)getActivity()).Close();

            }
        });





        return v;

    }

}
