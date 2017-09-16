package com.five.high.emirim.geulgil.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.five.high.emirim.geulgil.R;

/**
 * Created by 유리 on 2017-09-16.
 */

public class DialogManager {
    Context mContext;
    public DialogManager(Context mContext) {
        this.mContext = mContext;
    }

    public void showDialog(String message, int type){
        final Dialog dialog = new Dialog(mContext, R.style.MyDialog);
        switch (type) {
            case 1:
                dialog.setContentView(R.layout.dialog_style1);
                break;
            case 2:
                dialog.setContentView(R.layout.dialog_style2);
                break;
        }
        dialog.show();

        TextView textView = (TextView)dialog.findViewById(R.id.dialog_text);
        textView.setText(message);

        Button mYesButton =(Button)dialog.findViewById(R.id.dialog_button_yes);

        mYesButton.setEnabled(true);

        mYesButton.setOnClickListener(new View.OnClickListener(){
            public  void onClick(View v){
                dialog.dismiss();
            }
        });
    }
}
