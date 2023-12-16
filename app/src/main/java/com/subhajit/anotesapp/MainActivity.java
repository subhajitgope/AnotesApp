package com.subhajit.anotesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String PREFS_NAME="notesPrefs";
    private static final String KEY_NOTE_CONTENT="NoteCount";
    LinearLayout ll_one;
    private List<Notes> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ll_one=(LinearLayout) findViewById(R.id.ll_one);
        Button Note_btn=(Button) findViewById(R.id.Note_btn);
        notesList=new ArrayList<>();
        Note_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });
        loadNotesFromPreferences();
        displayNotes();

    }

    private void displayNotes() {
        for (Notes notes:notesList){
            createView(notes);
        }
    }

    private void loadNotesFromPreferences() {
        SharedPreferences sharedPreferences=getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        int noteCount=sharedPreferences.getInt(KEY_NOTE_CONTENT,0);
        for (int i=0;i<noteCount;i++){
            String title=sharedPreferences.getString("note_title"+i,"");
            String content=sharedPreferences.getString("note_content"+i,"");
            Notes notes=new Notes(title,content);
            notes.setTitle(title);
            notes.setContent(content);
            notesList.add(notes);

        }

    }

    public void saveNote(){
        EditText et_title=(EditText) findViewById(R.id.et_title);
        EditText et_content=(EditText) findViewById(R.id.et_content);
        String title=et_title.getText().toString();
        String content=et_content.getText().toString();
        if(!title.isEmpty()  && !content.isEmpty()){
            Notes notes=new Notes(title,content);
            notes.setTitle(title);
            notes.setContent(content);
            notesList.add(notes);
            saveNotesToPreferences();
            createView(notes);
            clearinnputfield();


        }

    }

    private void clearinnputfield() {
        EditText titleEdittext= findViewById(R.id.et_title);
        EditText contentEdittext=findViewById(R.id.et_content);
        titleEdittext.getText().clear();
        contentEdittext.getText().clear();
    }

    public  void createView(final Notes notes){
        View noteView=getLayoutInflater().inflate(R.layout.note_item,null);
        TextView titletextview=(TextView) noteView.findViewById(R.id.tv_notetitle);
        TextView contenttextview=(TextView) noteView.findViewById(R.id.tv_notecontent);
        titletextview.setText(notes.getTitle());
        contenttextview.setText(notes.getContent());
        noteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showdeletedialog(notes);

            }
        });
ll_one.addView(noteView);
    }
    public  void showdeletedialog(final Notes notes){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Delete this note");
        builder.setMessage("are you sure want to delete this note");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
deleteNoteAndRefresh(notes);
            }
        });
builder.setNegativeButton("cancel",null);
builder.show();
    }
    public  void deleteNoteAndRefresh(Notes notes){
   notesList.remove(notes);
   saveNotesToPreferences();
   refreshNoteView();

    }

    private void refreshNoteView() {
        ll_one.removeAllViews();
        displayNotes();
    }

    public void saveNotesToPreferences(){
        SharedPreferences sharedPreferences=getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putInt(KEY_NOTE_CONTENT, notesList.size());
        for(int i=0;i< notesList.size();i++){
            Notes notes=notesList.get(i);
            editor.putString("note_title"+i, notes.getTitle());
            editor.putString("note_content"+i, notes.getContent());
        }
        editor.apply();
    }
}