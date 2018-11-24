package com.appreciateapps.android.jotter;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private NoteManager noteMan;
    private int notePosn;
    private RecyclerView mRecycleView;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecycleView = (RecyclerView) findViewById(R.id.my_recyclerview);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        noteMan = new NoteManager(getApplicationContext());
        List<Note> existingNotes = noteMan.getNotes();

        ArrayAdapter<Note> noteAdapt = new ArrayAdapter<Note>(
                this, android.R.layout.simple_list_item_1, existingNotes);
        setListAdapter(noteAdapt);

        theList = getListView();
        theList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                notePosn = position;
                AlertDialog.Builder deleteAlert = new AlertDialog.Builder(MainActivity.this);
                deleteAlert.setTitle("Delete Note");
                deleteAlert.setMessage("Do you want to delete this note?");

                deleteAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        ArrayAdapter<Note> noteAdapt =
                                (ArrayAdapter<Note>) MainActivity.this.getListAdapter();
                        Note clickedNote = (Note) noteAdapt.getItem(notePosn);
                        int noteDeleted = noteMan.deleteNote
                                (clickedNote.getNoteID());
                        noteAdapt.remove(clickedNote);
                        noteAdapt.notifyDataSetChanged();
                        setListAdapter(noteAdapt);
                    }
                });

                deleteAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });

                deleteAlert.show();
            }
        });
    }

    public void addNewNote(View view) {
        AlertDialog.Builder addAlert = new AlertDialog.Builder(this);
        addAlert.setTitle("New Note");
        addAlert.setMessage("Enter your note:");

        final EditText noteIn = new EditText(this);
        addAlert.setView(noteIn);

        addAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String noteInput = noteIn.getText().toString();
                Note inputNote = new Note();
                inputNote.setNoteText(noteInput);
                long addedID = noteMan.addNewNote(inputNote);
                inputNote.setNoteID(addedID);

                ArrayAdapter<Note> noteAdapt = (ArrayAdapter<Note>)
                        MainActivity.this.getListAdapter();
                noteAdapt.add(inputNote);
            }
        });

        addAlert.setNegativeButton("Cancel", new Dialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });

        addAlert.show();
    }
}