package com.bignerdranch.android.receiptspurchases.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdranch.android.receiptspurchases.Receipt;

import java.util.Date;
import java.util.UUID;

public class ReceiptCursorWrapper extends CursorWrapper {
    public ReceiptCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Receipt getReceipt() {
        String uuidString = getString(getColumnIndex(ReceiptDbSchema.ReceiptTable.Cols.UUID));
        String title = getString(getColumnIndex(ReceiptDbSchema.ReceiptTable.Cols.TITLE));
        long date = getLong(getColumnIndex(ReceiptDbSchema.ReceiptTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(ReceiptDbSchema.ReceiptTable.Cols.SOLVED));
        String suspect = getString(getColumnIndex(ReceiptDbSchema.ReceiptTable.Cols.SUSPECT));

        Receipt receipt = new Receipt(UUID.fromString(uuidString));
        receipt.setTitle(title);
        receipt.setDate(new Date(date));
        receipt.setSolved(isSolved != 0);
        receipt.setSuspect(suspect);

        return receipt;
    }
}
