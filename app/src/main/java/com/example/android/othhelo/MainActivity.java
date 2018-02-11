package com.example.android.othhelo;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {
    int size = 8;
    public static final int blackPlayer = 0;
    public static final int whitePlayer = 1;
    public static final int noPlayer = -1;
    public int currentPlayer = blackPlayer;
//int dirX = 0;
//    int dirY = 0;
ArrayList<Integer> dirX =new ArrayList<Integer>();
    ArrayList<Integer> dirY =new ArrayList<Integer>();


    LinearLayout rootLayout;

    LinearLayout[] rows;
    CustomButton[][] board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        rootLayout = (LinearLayout) findViewById(R.id.rootLayout);
        rows = new LinearLayout[size];
        board = new CustomButton[size][size];
        setUpBoard();

    }

    public void setUpBoard() {


        //  final SharedPreferences sharedPreferences = getSharedPreferences("minesweeper",MODE_PRIVATE);
        //   Log.i("TAG", "setUpBoard: Rec"+sharedPreferences.getInt("level",-1));
        //if(sharedPreferences.getInt("level",0)!=0){
//            size = 5+sharedPreferences.getInt("level",0);
//
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putInt("level",0);}
        rootLayout = (LinearLayout) findViewById(R.id.rootLayout);
        rows = new LinearLayout[size];
        board = new CustomButton[size][size];
        rootLayout.removeAllViews();

        for (int i = 0; i < size; i++) {

            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
            linearLayout.setLayoutParams(params);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            rows[i] = linearLayout;
            rootLayout.addView(linearLayout);

        }


        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                CustomButton button = new CustomButton(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                button.setLayoutParams(params);
                button.setOnClickListener(this);
                //     button.setOnLongClickListener(this);
                board[i][j] = button;
                button.x = i;
                button.y = j;
                rows[i].addView(button);

            }
        }
        board[3][3].setBackgroundResource(R.drawable.button_drawable_black);
        board[3][3].setPlayer(blackPlayer);
        board[3][4].setBackgroundResource(R.drawable.button_drawable_white);
        board[3][4].setPlayer(whitePlayer);
        board[4][3].setBackgroundResource(R.drawable.button_drawable_white);
        board[4][3].setPlayer(whitePlayer);
        board[4][4].setBackgroundResource(R.drawable.button_drawable_black);
        board[4][4].setPlayer(blackPlayer);
    }

    boolean checkValidMove(int x, int y, int currentPlayer) {
        int dellX[] = {-1, -1, -1, 0, 0, 1, 1, 1};
        int dellY[] = {-1, 0, 1, -1, 1, -1, 0, 1};
        int count = 0;

        for (int i = 0; i < 8; i++) {
            int a = x + dellX[i];
            int b = y + dellY[i];
            if ((a >= 0) && (a < size) && (b >= 0) && (b < size)) {
                int oppPlayer = board[a][b].getPlayer();
                if (((currentPlayer == blackPlayer) && (oppPlayer == whitePlayer)) || ((currentPlayer == whitePlayer) && (oppPlayer == blackPlayer))) {

                    if ((checkValidMoveEnd(x, y, dellX[i], dellY[i], currentPlayer)) == true)
                 count++;
                }
            }

        }

        if(count>0)
            return true;
        else
            return false;
    }

    boolean checkValidMoveEnd(int x, int y, int dellX, int dellY, int currentPlayer) {
//    int oppPlayer = noPlayer;
//    if(currentPlayer==blackPlayer)
//        oppPlayer = whitePlayer;
//    else
//        oppPlayer  = blackPlayer;
        int i = x + dellX;
        int j = y + dellY;
        while ((i >= 0) && (i < size) && (j >= 0) && (j < size)) {
            if (board[i][j].getPlayer() == currentPlayer){
                dirX.add(dellX);
                dirY.add(dellY);
                return true;}
            if(board[i][i].getPlayer()==noPlayer)
                return false;
            i = i + dellX;
            j = j + dellY;

        }
        return false;
    }

    @Override
    public void onClick(View v) {
        CustomButton customButton = (CustomButton) v;
        int x = customButton.get_X();
        int y = customButton.get_Y();
        customButton.setPlayer(currentPlayer);
//currentPlayer = customButton.getPlayer();
        boolean valid = checkValidMove(x, y, currentPlayer);

        if (valid == true) {
            while(!dirX.isEmpty()){
            playTurn(x, y, dirX.remove(0),dirY.remove(0),currentPlayer);}
            currentPlayer = togglePlayer(currentPlayer);
        } else
            Toast.makeText(this, "invalid move", Toast.LENGTH_SHORT).show();
    }

    public void playTurn(int x,int y,int dir_X,int dir_Y,int currentPlayer){



        board[x][y].setPlayer(currentPlayer);
        if(currentPlayer==blackPlayer)
            board[x][y].setBackgroundResource(R.drawable.button_drawable_black);
        else
            board[x][y].setBackgroundResource(R.drawable.button_drawable_white);
        int i = x + dir_X;
        int j = y + dir_Y;
        while ((i >= 0) && (i < size) && (j >= 0) && (j < size)) {
            if (board[i][j].getPlayer() == currentPlayer) {
                return;
            }
            board[i][j].setPlayer(currentPlayer);

            if(currentPlayer==blackPlayer)
                board[i][j].setBackgroundResource(R.drawable.button_drawable_black);
            else
                board[i][j].setBackgroundResource(R.drawable.button_drawable_white);
            i = i + dir_X;
            j = j + dir_Y;

        }
        return;

    }

    public int togglePlayer(int prevPlayer) {
        if(prevPlayer==blackPlayer)
            return whitePlayer;
        else
            return blackPlayer;
    }
}
