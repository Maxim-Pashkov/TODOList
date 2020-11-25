package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ManageItemActivity extends AppCompatActivity {

    private ListItem item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_item);

        Intent intent = getIntent();
        item = intent.getParcelableExtra(ListItem.class.getSimpleName());

        TextView textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        if(!item.isNew()) {
            textViewTitle.setText(R.string.edit_item_process);
        }

        EditText editTextName = (EditText) findViewById(R.id.editTextName);
        editTextName.setText(item.getName());

        CheckBox checkboxCompleted = (CheckBox) findViewById(R.id.checkboxCompleted);
        checkboxCompleted.setChecked(item.getCompleted());

        if(item.isNew()) {
            Button buttonDelete = (Button) findViewById(R.id.buttonDelete);
            buttonDelete.setVisibility(View.GONE);
        }
    }

    public void onClickButton(View view) {
        Button button = (Button) view;
        DatabaseAdapter adapter = new DatabaseAdapter(this);
        adapter.open();

        if(button.getId() == R.id.buttonSave) {
            EditText editTextName = findViewById(R.id.editTextName);
            item.setName(editTextName.getText().toString());

            CheckBox checkBoxCompleted = findViewById(R.id.checkboxCompleted);
            item.setCompleted(checkBoxCompleted.isChecked());

            adapter.save(item);

            Toast.makeText(this, R.string.saved_item, Toast.LENGTH_LONG).show();
        } else if(button.getId() == R.id.buttonDelete) {

            adapter.delete(item);

            Toast.makeText(this, R.string.deleted_item, Toast.LENGTH_LONG).show();
        }

        adapter.close();

        finish();
    }
}
