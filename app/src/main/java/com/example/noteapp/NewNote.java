package com.example.noteapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.util.HashSet;

import static com.example.noteapp.MainActivity.notes;
import static com.example.noteapp.MainActivity.set;

public class NewNote extends AppCompatActivity implements TextWatcher {

    int noteId;
    EditText editText_note_id;
    String fillerText;
    SharedPreferences sharedPreferences;
    Intent mainActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editText_note_id = (EditText) findViewById(R.id.editText_note_id);

        Intent i = getIntent();
        noteId = i.getIntExtra("noteId", -1);

        if(noteId != -1){
            fillerText = notes.get(noteId);
            editText_note_id.setText(fillerText);
        }

        editText_note_id.addTextChangedListener(this);



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainActivity);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        notes.set(noteId, String.valueOf(s));
        MainActivity.arrayAdapter.notifyDataSetChanged();

        sharedPreferences = this.getSharedPreferences("NoteApp", Context.MODE_PRIVATE);

        if(set == null){
            set = new HashSet<String>();
        }else{
            set.clear();
        }
        set.clear();

        set.addAll(notes);
        sharedPreferences.edit().remove("notes").apply();
        sharedPreferences.edit().putStringSet("notes", set).apply();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
