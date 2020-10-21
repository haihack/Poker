package com.atone.poker.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import lombok.Data;

@Data
public class Result extends RealmObject implements Parcelable  {

    @PrimaryKey
    private long id;
    private long timeStamp;

    @SerializedName("card")
    private String card;
    @SerializedName("hand")
    private String hand;
    @SerializedName("best")
    @Ignore
    private boolean best;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.card);
        dest.writeString(this.hand);
        dest.writeByte(this.best ? (byte) 1 : (byte) 0);
    }

    public Result() {
    }

    protected Result(Parcel in) {
        this.card = in.readString();
        this.hand = in.readString();
        this.best = in.readByte() != 0;
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel source) {
            return new Result(source);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };
}
