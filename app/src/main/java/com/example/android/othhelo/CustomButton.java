package com.example.android.othhelo;

import android.content.Context;
import android.widget.Button;

/**
 * Created by shruti on 30-08-2017.
 */



    public class CustomButton extends Button {
int x = 0;
    int y = 0 ;
int player ;

    public CustomButton(Context context) {


        super(context);
        setBackgroundResource(R.drawable.button_drawable);
player = MainActivity.noPlayer;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getPlayer() {
        return player;
    }

    public int get_Y() {
        return y;
    }

    public int get_X() {
        return x;
    }
}