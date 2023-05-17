package com.example.projetofabrica;

import android.os.Parcel;
import android.os.Parcelable;

public class Visit implements Parcelable {
    private String id;
    private String name;
    private String dateTime;

    public Visit() {
    }

    public Visit(String id, String name, String dateTime) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
    }

    protected Visit(Parcel in) {
        id = in.readString();
        name = in.readString();
        dateTime = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(dateTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Visit> CREATOR = new Creator<Visit>() {
        @Override
        public Visit createFromParcel(Parcel in) {
            return new Visit(in);
        }

        @Override
        public Visit[] newArray(int size) {
            return new Visit[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}

