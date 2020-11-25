package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onResume() {
        super.onResume();
        DatabaseAdapter adapter = new DatabaseAdapter(this);
        adapter.open();

        List<ListItem> items = adapter.getItems();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        DataAdapter dataAdapter = new DataAdapter(this, items);
        recyclerView.setAdapter(dataAdapter);
        recyclerView.setVisibility(items.size() == 0 ? View.GONE : View.VISIBLE);

        TextView textViewListStub = (TextView) findViewById(R.id.listStub);
        textViewListStub.setVisibility(items.size() != 0 ? View.GONE : View.VISIBLE);

        adapter.close();
    }

    public void onClickEditItem(View view) {
        ListItem item = (ListItem) view.getTag();
        startActivityManageItem(item);
    }

    public void onClickAddItem(View view) {
        ListItem item = new ListItem();
        startActivityManageItem(item);
    }

    private void startActivityManageItem(ListItem item) {
        Intent intent = new Intent(this, ManageItemActivity.class);
        intent.putExtra(ListItem.class.getSimpleName(), item);
        startActivity(intent);
    }
}