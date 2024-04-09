package com.example.todo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editText;
    private RadioButton radioButtonLow;
    private RadioButton radioButtonMiddle;
    private Button saveButton;
    private int priority;
    private AddNoteViewModel addNoteViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_note);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addNoteViewModel = new ViewModelProvider(this).get(AddNoteViewModel.class);

        addNoteViewModel.getShouldCloseScreen().observe(
                this,
                shouldClose ->
                {
                    if (shouldClose) {
                    finish();
            }
        });
        initViews();
        saveButton.setOnClickListener(v -> saveNote());
    }

    private void saveNote() {
        String text = editText.getText().toString().trim();
        if (text.isEmpty()) {
            Toast.makeText(
                    this,
                    "Can't save empty note",
                    Toast.LENGTH_SHORT
            ).show();
        } else {
            priority = getPriority();
            Note note = new Note(text, priority);
            addNoteViewModel.saveNote(note);
        }
    }

    private int getPriority() {
        if (radioButtonLow.isChecked()) {
            return 0;
        } else if (radioButtonMiddle.isChecked()) {
            return 1;
        } else {
            return 2;
        }
    }

    private void initViews() {
        editText = findViewById(R.id.editText);
        radioButtonLow = findViewById(R.id.radioLowButton);
        radioButtonMiddle = findViewById(R.id.radioMiddleButton);
        saveButton = findViewById(R.id.saveButton);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, AddNoteActivity.class);
    }

}