package com.example.todolist;

import android.os.Parcel;
import android.os.Parcelable;

public class ListItem implements Parcelable {
    private int id;
    private String name;
    private boolean completed;

    public static final Creator<ListItem> CREATOR = new Creator<ListItem>() {
        @Override
        public ListItem createFromParcel(Parcel source) {
            int id = source.readInt();
            String name = source.readString();
            boolean completed = source.readInt() == 1;
            return new ListItem(id, name, completed);
        }

        @Override
        public ListItem[] newArray(int size) {
            return new ListItem[size];
        }
    };

    public ListItem(int id, String name, boolean completed) {
        this.id = id;
        this.name = name;
        this.completed = completed;
    }

    public ListItem() {
        this.id = 0;
        this.name = "";
        this.completed = false;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getCompleted() {
        return this.completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getId() {
        return this.id;
    }

    public boolean isNew() {
        return this.id == 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(completed ? 1 : 0);
    }
}
