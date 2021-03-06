package com.cbre.tsandford.cbreinspector.model.notes;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.cbre.tsandford.cbreinspector.R;

import java.util.List;

public class NotesSectionController{

    private static String TAG = "NotesSectionController";

    public interface SectionButtonCallback{
        void OnSectionButtonClicked(LinearLayout sectionLayout);
    }

    private SectionButtonCallback callback;

    private String mName;
    private List<NotesContentController> mContentControllers;
    private Button mButton;
    private LinearLayout mContentLayout;
    private Context mContext;

    public NotesSectionController(String name, Context context){
        this.mName = name;
        this.mContext = context;
        this.mButton = getSectionButton(name);
        this.mContentLayout = generateContentLayout();
    }

    private LinearLayout generateContentLayout() {
        LinearLayout result = new LinearLayout(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        result.setOrientation(LinearLayout.VERTICAL);
        result.setLayoutParams(params);
        result.setPadding(5,5,5,5);
        return result;
    }

    public LinearLayout getContentLayout() {
        return mContentLayout;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public void setContentControllers(List<NotesContentController> contentControllers) {
        this.mContentControllers = contentControllers;
    }

    public Button getButton(){
        return this.mButton;
    }

    public void setCallback(SectionButtonCallback callback) {
        this.callback = callback;
    }

    private void populateContentLayout(){
        mContentLayout.removeAllViews();
        for(NotesContentController contentController : mContentControllers){
            mContentLayout.addView(contentController);
        }
    }

    private Button getSectionButton(String sectionName){
        Button button = new Button(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(0,0,0,20);
        Drawable background = mContext.getResources().getDrawable(R.drawable.custom_button);
        button.setBackground(background);
        button.setTextSize(15);
        button.setTypeface(getFont(true));
        button.setLayoutParams(params);
        button.setText(sectionName);
        button.setPadding(15,15,15,15);
        button.setTextColor(mContext.getResources().getColor(R.color.cbreDarkGrey));

        // when button is clicked send the linear layout to view flipper to include
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callback != null && mContentLayout != null) {
                    populateContentLayout();
                    callback.OnSectionButtonClicked(mContentLayout);
                }else{
                    Log.d(TAG, "Section button callback failed.");
                }
            }
        });

        return button;
    }

    private Typeface getFont(boolean boldText){
        return boldText ? ResourcesCompat.getFont(mContext, R.font.futura_heavy) :
                ResourcesCompat.getFont(mContext, R.font.futura_book);
    }


}
