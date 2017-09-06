package com.five.high.emirim.geulgil.Adapter;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.five.high.emirim.geulgil.R;

/**
 * Created by 유리 on 2017-09-04.
 */

public class SearchBarManager {

    Activity activity;
    Context context;

    // Start of Connection ID
    FrameLayout mRootLayout;

    View mSearchBox;
    ImageView mFilterButton;


    View mSearchBoxSelected;
    View mSelectKeyword;
    RadioButton mMeanKeywordButton;
    RadioButton mSimilarKeywordButton;

    TextView mBlur;

    View mSearchView;
    EditText mSearchBar;
    ImageView mBackButton;
    ImageView mSearchButton;
    //

    InputMethodManager im;
    private boolean isClicked = false;
    private boolean isMeanKeyword;


    public SearchBarManager(Activity activity, Context context){
        this.activity = activity;
        this.context = context;
        im = (InputMethodManager) activity.getSystemService(Service.INPUT_METHOD_SERVICE);
    }

    public void findViewId() {
        mRootLayout = (FrameLayout)activity.findViewById(R.id.frame_root);

        mSearchBox = activity.findViewById(R.id.search_box);
        mSearchBoxSelected = activity.findViewById(R.id.search_box_selected);
        mBlur = (TextView)activity.findViewById(R.id.blur);

        mFilterButton = (ImageView)mSearchBox.findViewById(R.id.iv_filterBtn);

        mSelectKeyword = mSearchBoxSelected.findViewById(R.id.select_keyword);
        mMeanKeywordButton = (RadioButton)mSelectKeyword.findViewById(R.id.radioMean);
        mSimilarKeywordButton = (RadioButton)mSelectKeyword.findViewById(R.id.radioSimilar);

        mSearchView = mSearchBoxSelected.findViewById(R.id.search_keyword);
        mSearchBar = (EditText)mSearchView.findViewById(R.id.et_searchBox);
        mBackButton = (ImageView)mSearchView.findViewById(R.id.iv_filterBtn);
        mSearchButton = (ImageView)mSearchView.findViewById(R.id.iv_searchBtn);
        clickListener();
    }

    private void clickListener() {
        mMeanKeywordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isMeanKeyword = true;
                changeInnerView(false);
            }
        });

        mSimilarKeywordButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                isMeanKeyword = false;
                changeInnerView(false);
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                changeInnerView(true);
                mMeanKeywordButton.setChecked(false);
                mSimilarKeywordButton.setChecked(false);
            }
        });

        mFilterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showSelectedView();
            }
        });

        mSearchBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectedView();
            }
        });

        mBlur.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                im.hideSoftInputFromWindow(mSearchBar.getWindowToken(),0);

            }
        });


    }


    public void showSelectedView() {
        if (isClicked) {
            isClicked = false;
            mSearchBoxSelected.setVisibility(View.INVISIBLE);
            mBlur.setVisibility(View.INVISIBLE);
            mSearchBox.setVisibility(View.VISIBLE);
            mSearchView.setVisibility(View.INVISIBLE);
            mSimilarKeywordButton.setChecked(false);
            mMeanKeywordButton.setChecked(false);
        } else {
            isClicked = true;
            im.showSoftInputFromInputMethod(mSearchBar.getWindowToken(), 0);
            mBlur.setVisibility(View.VISIBLE);
            mSearchBoxSelected.setVisibility(View.VISIBLE);
            mSearchBox.setVisibility(View.INVISIBLE);
        }
    }

    private void changeInnerView(boolean isBackBtn) {
        if(isBackBtn){
            mSearchView.setVisibility(View.INVISIBLE);
        }
        else{
            mSearchView.setVisibility(View.VISIBLE);
            mBackButton.setImageResource(R.drawable.back);
            mSearchBar.requestFocus();
        }
    }


    public Activity getActivity() {
        return activity;
    }

    public Context getContext() {
        return context;
    }

    public FrameLayout getmRootLayout() {
        return mRootLayout;
    }

    public View getmSearchBox() {
        return mSearchBox;
    }

    public ImageView getmFilterButton() {
        return mFilterButton;
    }

    public View getmSearchBoxSelected() {
        return mSearchBoxSelected;
    }

    public View getmSelectKeyword() {
        return mSelectKeyword;
    }

    public RadioButton getmMeanKeywordButton() {
        return mMeanKeywordButton;
    }

    public RadioButton getmSimilarKeywordButton() {
        return mSimilarKeywordButton;
    }

    public TextView getmBlur() {
        return mBlur;
    }

    public View getmSearchView() {
        return mSearchView;
    }

    public EditText getmSearchBar() {
        return mSearchBar;
    }

    public ImageView getmBackButton() {
        return mBackButton;
    }

    public ImageView getmSearchButton() {
        return mSearchButton;
    }

    public InputMethodManager getIm() {
        return im;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public boolean isMeanKeyword() {
        return isMeanKeyword;
    }
}
