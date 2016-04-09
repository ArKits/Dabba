package me.arkits.dabba;

import android.widget.EditText;

/**
 * Created by archi on 4/8/2016.
 */
public class Emoji {

    int _id;
    String _label;
    String _text;

    public Emoji(){

    }

    public Emoji(int id, String label, String _text){
        this._id = id;
        this._label = label;
        this._text = _text;
    }

    public Emoji(String label, String _text){
        this._label = label;
        this._text = _text;
    }




    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id=id;
    }

    public String getLabel(){
        return this._label;
    }

    public void setLabel (String label){
        this._label = label;
    }

    public String getText(){
        return this._text;
    }

    public void setText (String text){
        this._text = text;
    }




}
