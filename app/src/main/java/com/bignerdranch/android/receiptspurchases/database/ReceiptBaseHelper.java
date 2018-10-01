package com.bignerdranch.android.receiptspurchases.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bignerdranch.android.receiptspurchases.Receipt;

public class ReceiptBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "crimeBase.db";
    public ReceiptBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ReceiptDbSchema.ReceiptTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                ReceiptDbSchema.ReceiptTable.Cols.UUID + ", " +
                ReceiptDbSchema.ReceiptTable.Cols.TITLE + ", " +
                ReceiptDbSchema.ReceiptTable.Cols.DATE + ", " +
                ReceiptDbSchema.ReceiptTable.Cols.SOLVED + ", " +
                ReceiptDbSchema.ReceiptTable.Cols.SUSPECT +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
