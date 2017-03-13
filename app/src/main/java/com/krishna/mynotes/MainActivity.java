package com.krishna.mynotes;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {


    TextView new_note;
    Boolean fragload=false;
    ListView my_note_listview;
    ImageView btn_delete;
    NoteListAdapter adapter;
    String [] titles;
    ArrayList<NoteListModel> templist =new ArrayList<>();
    ArrayList<NoteListModel> copyselectedlist =new ArrayList<>();
   // ArrayList<NoteListModel> title =new ArrayList<>();
    ArrayList<NoteListModel> title =new ArrayList<>();
    Realm realm;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new_note=(TextView)findViewById(R.id.new_note);
        my_note_listview=(ListView)findViewById(R.id.my_note_listview);
        btn_delete=(ImageView) findViewById(R.id.btn_delete);
        realm=General.realmconfig(getBaseContext());
        RealmResults<SaveNoteModelRealm> results2 = realm.where(SaveNoteModelRealm.class).findAll();
       // int i=0;
        for(int i=0;i<results2.size();i++){

            NoteListModel noteListModel=new NoteListModel(results2.get(i).getNoteTitle(),false);
            title.add(noteListModel);
        }
        adapter=new NoteListAdapter(getBaseContext(),title);
        my_note_listview.setAdapter(adapter);

        //adapter.


       // my_note_listview.setAdapter(titles);



        new_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewNote newNote=new NewNote();
                Bundle b=new Bundle();
                b.putString("NoteType","new");
                loadFragment(newNote,b,R.id.Untitle,"");
                fragload=true;
            }
        });

        my_note_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if(adapter.check_visibility()){
                    adapter.setCheckBox(position);
                    if(checkStatus()==0) {
                        adapter.Hide_check1();
                    }
                }else {

                    NewNote newNote = new NewNote();
                    Bundle b = new Bundle();
                    b.putString("NoteType", "Old");
                    b.putString("Title", title.get(position).getTitle());
                    Log.i("data", "=== title === " + title.get(position));

                    loadFragment(newNote, b, R.id.Untitle, "");
                    fragload = true;
                }
            }
        });

        my_note_listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.Show_check1();
                return false;
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                templist.clear();
                // selectedlist.addAll(adapter.getAllData());
                for(NoteListModel hold: adapter.getAllData()){
                    if(hold.isCheckbox()){
                        templist.add(hold);
                        Log.i("selected1","selected building hold : "+hold.getTitle());
                    }

                }

                copyselectedlist.clear();
                copyselectedlist.addAll(title);

                for ( NoteListModel d : templist) {
                    Log.i("selected1","selected building : templist "+title.size()+" contains  "+title.contains(d));

                    for(NoteListModel c : copyselectedlist) {
                        if (d.getTitle() == c.getTitle()) {
                            Log.i("selected1", "selected building : c " + c.getTitle() + "d  " + d.getTitle());
                            title.remove(c);
                            break;
                        }


                    }

                    RealmResults<SaveNoteModelRealm> result = realm.where(SaveNoteModelRealm.class).findAll();

                   for(int i=0;i<result.size();i++) {

                    Log.i("selected1", "selected building : c22 " + result.get(i).getNoteTitle()+" d.getId(): ");
                    if (result.get(i).getNoteTitle().equalsIgnoreCase(d.getTitle())){
                        if (realm.isInTransaction())
                            realm.cancelTransaction();
                        realm.beginTransaction();
                        result.get(i).removeFromRealm();
                        realm.commitTransaction();

                        break;
                    }

                   }
                }
                copyselectedlist.clear();
                Log.i("selected1","selected building : "+title.size());
                adapter.notifyDataSetChanged();
            }
        });





    }



















    public int checkStatus(){
        int count=0;
        for(NoteListModel hold: adapter.getAllData()){
            if(hold.isCheckbox()){

                count++;

            }

        }
        return count;
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();

        if(fragload){
           // getSupportFragmentManager().popBackStackImmediate();
            getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.Untitle)).commit();
            fragload=false;
            adapter.notifyDataSetChanged();
        }else{
            finish();
        }


    }


    public void Close(){
       // getSupportFragmentManager().popBackStackImmediate();
        getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.Untitle)).commit();

        fragload=false;
        adapter.notifyDataSetChanged();
        recreate();
    }
    private void loadFragment(Fragment fragment, Bundle args, int containerId, String title)
    {
        fragment.setArguments(args);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(title);
        Log.i("SIGNUP_FLAG","SIGNUP_FLAG=========  loadFragment client "+getFragmentManager().getBackStackEntryCount());
        fragmentTransaction.replace(containerId, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }










}
