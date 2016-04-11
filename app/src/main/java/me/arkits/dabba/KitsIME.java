package me.arkits.dabba;

import android.inputmethodservice.InputMethodService;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by archi on 4/10/2016.
 */
public class KitsIME extends InputMethodService implements View.OnClickListener {
    private static final String TAG = "KitsIME";

    @Override
    public View onCreateInputView() {
        ViewGroup keyboardLayout = new KeyboardLayout(this, getBaseContext());
        return keyboardLayout;
    }

    @Override
    public void onClick(View v) {
        String string = ((TextView)v).getText().toString();
        //Log.d(TAG, string);
        getCurrentInputConnection().commitText(string, 1);
    }

    public void clearText() {
        getCurrentInputConnection().deleteSurroundingText(1000,0);
    }
}