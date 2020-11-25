package com.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<ListItem> items;

    DataAdapter(Context context, List<ListItem> items) {
        this.items = items;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {
        ListItem item = items.get(position);
        if(item.getName().length() != 0) {
            holder.nameView.setText(item.getName());
        } else {
            holder.nameView.setText(R.string.unnamed_item);
        }
        holder.statusView.setText(item.getCompleted() ? R.string.completed_item : R.string.not_completed_item);
        holder.buttonEdit.setTag(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final Button buttonEdit;
        final TextView nameView, statusView;
        ViewHolder(View view){
            super(view);
            statusView = (TextView) view.findViewById(R.id.status);
            nameView = (TextView) view.findViewById(R.id.name);
            buttonEdit = (Button) view.findViewById(R.id.button_edit);
        }
    }
}