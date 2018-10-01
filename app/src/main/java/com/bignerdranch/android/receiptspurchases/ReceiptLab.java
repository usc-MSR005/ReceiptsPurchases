package com.bignerdranch.android.receiptspurchases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.android.receiptspurchases.database.ReceiptBaseHelper;
import com.bignerdranch.android.receiptspurchases.database.ReceiptCursorWrapper;
import com.bignerdranch.android.receiptspurchases.database.ReceiptDbSchema;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReceiptLab {
    private static ReceiptLab sReceiptLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static ReceiptLab get(Context context) {
        if (sReceiptLab == null) {
            sReceiptLab = new ReceiptLab(context);
        }
        return sReceiptLab;
    }

    private ReceiptLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new ReceiptBaseHelper(mContext)
                .getWritableDatabase();
    }

    public void addCrime(Receipt c) {
        ContentValues values = getContentValues(c);

        mDatabase.insert(ReceiptDbSchema.ReceiptTable.NAME, null, values);
    }

    public void deleteCrime(Receipt c) {
        String uuidString = c.getId().toString();
        ContentValues values = getContentValues(c);

        mDatabase.delete(ReceiptDbSchema.ReceiptTable.NAME, ReceiptDbSchema.ReceiptTable.Cols.UUID + " = " + uuidString , null);
    }

    public List<Receipt> getReceipts() {
        List<Receipt> receipts = new ArrayList<>();

        ReceiptCursorWrapper cursor = queryCrimes(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                receipts.add(cursor.getReceipt());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return receipts;
    }

    public Receipt getReceipts(UUID id) {
        ReceiptCursorWrapper cursor = queryCrimes(
                ReceiptDbSchema.ReceiptTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getReceipt();
        } finally {
            cursor.close();
        }
    }

    public File getPhotoFile(Receipt receipt) {
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, receipt.getPhotoFilename());
    }

    public void updateCrime(Receipt receipt) {
        String uuidString = receipt.getId().toString();
        ContentValues values = getContentValues(receipt);
        mDatabase.update(ReceiptDbSchema.ReceiptTable.NAME, values,
                ReceiptDbSchema.ReceiptTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    private ReceiptCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ReceiptDbSchema.ReceiptTable.NAME,
                null, // columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new ReceiptCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Receipt receipt) {
        ContentValues values = new ContentValues();
        values.put(ReceiptDbSchema.ReceiptTable.Cols.UUID, receipt.getId().toString());
        values.put(ReceiptDbSchema.ReceiptTable.Cols.TITLE, receipt.getTitle());
        values.put(ReceiptDbSchema.ReceiptTable.Cols.DATE, receipt.getDate().getTime());
        values.put(ReceiptDbSchema.ReceiptTable.Cols.SOLVED, receipt.isSolved() ? 1 : 0);
        values.put(ReceiptDbSchema.ReceiptTable.Cols.SUSPECT, receipt.getSuspect());
        return values;
    }
}
