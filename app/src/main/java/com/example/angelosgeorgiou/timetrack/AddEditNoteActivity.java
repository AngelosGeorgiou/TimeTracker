package com.example.angelosgeorgiou.timetrack;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

public class AddEditNoteActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.example.angelosgeorgiou.timetrack.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.example.angelosgeorgiou.timetrack.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.example.angelosgeorgiou.timetrack.EXTRA_DESCRIPTION";
    public static final String EXTRA_TIME =
            "com.example.angelosgeorgiou.timetrack.EXTRA_TIME";

    private AutoCompleteTextView autocompleteTextTitle;
    private EditText editTextDescription;
    private TimePicker timePickerTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        autocompleteTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        timePickerTime = findViewById(R.id.time_picker_time);


        Intent intent = getIntent();
        String [] allTitles = intent.getStringArrayExtra(MainActivity.EXTRA_ALLTITLES);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, allTitles);


        autocompleteTextTitle.setSelectAllOnFocus(true);
        autocompleteTextTitle.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        autocompleteTextTitle.setThreshold(0);
        autocompleteTextTitle.setAdapter(adapter);




        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        //Triggers only if it contains ID => update condition
        if (intent.hasExtra(EXTRA_ID)) {

            setTitle("Edit Note");
            autocompleteTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            timePickerTime.setMinute(intent.getIntExtra(EXTRA_TIME,1)%100);
            timePickerTime.setHour(intent.getIntExtra(EXTRA_TIME,1)/100);
        } else {
            setTitle("Add Note");
        }

    }

    private void saveNote() {
        String title = autocompleteTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString();
        int time = timePickerTime.getMinute() + 100 * timePickerTime.getHour();

        if (title.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title", Toast.LENGTH_LONG).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_TIME, time);

        int id = getIntent().getIntExtra(EXTRA_ID,-1);
        if (id != -1){
            data.putExtra(EXTRA_ID,id);
        }

        setResult(RESULT_OK, data);
        finish();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
