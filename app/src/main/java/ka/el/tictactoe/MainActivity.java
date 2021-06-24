package ka.el.tictactoe;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private int[] ceilsID = {
            R.id.ceil1,
            R.id.ceil2,
            R.id.ceil3,
            R.id.ceil4,
            R.id.ceil5,
            R.id.ceil6,
            R.id.ceil7,
            R.id.ceil8,
            R.id.ceil9,
    };

    private int currentVars = 9;
    private ImageView reload;
    private boolean isProgressGame = false;
    private boolean isWin = false;

    private String[] gameStatusField = new String[]{
            "", "", "",
            "", "", "",
            "", "", ""
    };
    private int[][] vars = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8},

            {0, 3, 6},
            {1, 4, 7},
            {2, 5, 8},

            {0, 4, 8},
            {2, 4, 6},
    };

    private ArrayList<ImageView> ceils;
    private String nowWalk = "X";
    private TextView nowWalkTV;

    private int emptyIMG, x_img, o_img, x_win_img, o_win_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emptyIMG = R.drawable.empty;
        x_img = R.drawable.x;
        o_img = R.drawable.o;
        x_win_img = R.drawable.x_win;
        o_win_img = R.drawable.o_win;

        nowWalkTV = findViewById(R.id.nowWalk);

        reload = findViewById(R.id.reload);
        reload.setVisibility(View.INVISIBLE);
        reload.setOnClickListener(this);

        initCeils();
        startGame();
    }

    private void initCeils() {
        ceils = new ArrayList<ImageView>();
        for (int ceilID : ceilsID) {
            ImageView ceil = findViewById(ceilID);
            ceil.setOnClickListener(this);
            ceils.add(ceil);

        }
    }

    private void startGame() {
        reload.setVisibility(View.INVISIBLE);
        isProgressGame = true;
        isWin = false;
        currentVars = 9;
        nowWalk = "X";

        nowWalkTV.setText(R.string.nowWalk);
        nowWalkTV.setText(nowWalkTV.getText() + " " + nowWalk);
        gameStatusField = new String[]{
                "", "", "",
                "", "", "",
                "", "", ""
        };

        for (ImageView ceil : ceils) {
            doEmptyCeil(ceil);
        }

    }

    private void doEmptyCeil(ImageView ceil) {
        ceil.setImageResource(emptyIMG);
    }

    private void doXCeil(ImageView ceil) {
        ceil.setImageResource(x_img);
    }

    private void doOCeil(ImageView ceil) {
        ceil.setImageResource(o_img);
    }

    private void doXWinCeil(ImageView ceil) {
        ceil.setImageResource(x_win_img);
    }

    private void doOWinCeil(ImageView ceil) {
        ceil.setImageResource(o_win_img);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ceil1: {
                updateStatusField(1);
                break;
            }

            case R.id.ceil2: {
                updateStatusField(2);
                break;
            }

            case R.id.ceil3: {
                updateStatusField(3);
                break;
            }

            case R.id.ceil4: {
                updateStatusField(4);
                break;
            }

            case R.id.ceil5: {
                updateStatusField(5);
                break;
            }

            case R.id.ceil6: {
                updateStatusField(6);
                break;
            }

            case R.id.ceil7: {
                updateStatusField(7);
                break;
            }

            case R.id.ceil8: {
                updateStatusField(8);
                break;
            }

            case R.id.ceil9: {
                updateStatusField(9);
                break;
            }

            case R.id.reload: {
                startGame();
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void updateStatusField(int ceilIndex) {
        if (gameStatusField[ceilIndex - 1].equals("") && isProgressGame) {
            gameStatusField[ceilIndex - 1] = nowWalk;
            currentVars = currentVars - 1;
            Log.d("MyLog", "CV = " + currentVars);
            hasWin();

            if (!isWin) {
                if (nowWalk.equals("X")) {
                    nowWalk = "O";
                } else if (nowWalk.equals("O")) {
                    nowWalk = "X";
                }
                nowWalkTV.setText(R.string.nowWalk);
                nowWalkTV.setText(nowWalkTV.getText() + " " + nowWalk);
            } else if (isWin){
                nowWalkTV.setText(R.string.winner);
                nowWalkTV.setText(nowWalkTV.getText() + " " + nowWalk);
                reload.setVisibility(View.VISIBLE);
            }
            if (currentVars <= 0 && !isWin && isProgressGame) {
                nowWalkTV.setText(R.string.nowinner);
                reload.setVisibility(View.VISIBLE);
            }

            updateAll();
        } else {
//            nowWalkTV.setText(R.string.nowinner);
//            reload.setVisibility(View.VISIBLE);
        }
    }

    private void hasWin() {
        for (int[] var : vars) {
            int var_i0 = var[0], var_i1 = var[1], var_i2 = var[2];

            if (gameStatusField[var_i0].equals(nowWalk) && gameStatusField[var_i1].equals(nowWalk) && gameStatusField[var_i2].equals(nowWalk)) {
                isProgressGame = false;
                isWin = true;

                String status = "";
                if (nowWalk.equals("X")) {
                    status = "X-WIN";
                } else if (nowWalk.equals("O")) {
                    status = "O-WIN";
                }
                gameStatusField[var_i0] = status;
                gameStatusField[var_i1] = status;
                gameStatusField[var_i2] = status;
                break;
            }
        }
    }

    private void updateAll() {
        for (int i = 0; i < ceils.size(); i++) {
            String statusCeil = gameStatusField[i];
            ImageView ceil = ceils.get(i);

            switch (statusCeil) {
                case "": {
                    doEmptyCeil(ceil);
                    break;
                }

                case "X": {
                    doXCeil(ceil);
                    break;
                }

                case "O": {
                    doOCeil(ceil);
                    break;
                }

                case "X-WIN": {
                    doXWinCeil(ceil);
                    break;
                }

                case "O-WIN": {
                    doOWinCeil(ceil);
                    break;
                }
            }
        }
    }
}