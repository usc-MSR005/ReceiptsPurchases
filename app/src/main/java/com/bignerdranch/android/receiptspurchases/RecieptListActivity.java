package com.bignerdranch.android.receiptspurchases;

import android.support.v4.app.Fragment;

public class RecieptListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new RecieptListFragment();
    }
}
