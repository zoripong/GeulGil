package com.five.high.emirim.geulgil.Adapter;

import android.view.View;
import android.widget.CheckBox;

import com.five.high.emirim.geulgil.R;

import static com.five.high.emirim.geulgil.M.sCatCheck;
import static com.five.high.emirim.geulgil.M.sPartCheck;

/**
 * Created by 유리 on 2017-08-03.
 */

public class CheckBoxManager {
    public void setCheckBox(){
        sPartCheck[0]=true;
        sCatCheck[0]=true;

        for(int i=1;i<sPartCheck.length;i++)
            sPartCheck[i]=false;

        for(int i=1;i<sCatCheck.length;i++)
            sCatCheck[i]=false;
    }

    public void onClickCheckBox(View view){
        boolean checked=((CheckBox) view).isChecked();
        switch (view.getId()) {
            case R.id.checkbox_pos_all: //품사 전체
                sPartCheck[0] = checked;
                break;

            case R.id.checkbox_pos_noun: //품사 명사
                sPartCheck[1] = checked;
                break;

            case R.id.checkbox_pos_verb: //품사 동사
                sPartCheck[2] = checked;
                break;

            case R.id.checkbox_pos_adjective: //품사 형용사
                sPartCheck[3] = checked;
                break;

            case R.id.checkbox_pos_adverb: //품사 부사
                sPartCheck[4] = checked;
                break;

            case R.id.checkbox_cat_all: //전문분야 전체
                sCatCheck[0] = checked;
                break;

            case R.id.checkbox_cat_construction: //전문분야 건설
                sCatCheck[1] = checked;
                break;

            case R.id.checkbox_cat_management: //전문분야 경영
                sCatCheck[2] = checked;
                break;

            case R.id.checkbox_cat_economy: //전문분야 경제
                sCatCheck[3] = checked;
                break;

            case R.id.checkbox_cat_propername: //전문분야 고유명 일반
                sCatCheck[4] = checked;
                break;

        }
    }

}
