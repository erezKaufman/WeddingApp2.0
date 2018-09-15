package com.example.erez0_000.weddingapp;

import android.app.ProgressDialog;
import android.content.Context;

public class StaticMethods {
    private static ProgressDialog mprogressDialog;

    public static void showProgressDialog(String text, Context context) {
        if (mprogressDialog == null) {
            mprogressDialog = new ProgressDialog(context);
            mprogressDialog.setCancelable(false);
            mprogressDialog.setMessage(text);
        }
        mprogressDialog.show();
    }

    public static void hideProgressDialog() {
        if (mprogressDialog != null && mprogressDialog.isShowing()) {

            mprogressDialog.dismiss();
        }
    }
}
