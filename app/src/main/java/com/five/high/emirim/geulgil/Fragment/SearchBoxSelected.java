package com.five.high.emirim.geulgil.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.five.high.emirim.geulgil.R;

/**
 * Created by 유리 on 2017-08-28.
 */

public class SearchBoxSelected extends Fragment {
    public SearchBoxSelected(){
        //required
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_box_selected, container, false);
    }
}
