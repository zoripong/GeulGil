package com.five.high.emirim.geulgil.Adapter;

import android.app.ProgressDialog;

/**
 * Created by 유리 on 2017-09-16.
 */

public class ProgressDialogManager {

    public void showDialog(ProgressDialog progressDialog){
        // Set up progress before call
        progressDialog.setMessage("단어를 가져오는 중입니다");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDialog.show();
    }

    public void dismissDialog(ProgressDialog progressDialog){
        progressDialog.dismiss();
    }
}
