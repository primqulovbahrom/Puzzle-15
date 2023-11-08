package uz.gita.puzzle15.views;
//

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import uz.gita.puzzle15.R;
import uz.gita.puzzle15.data.Coordinate;
import uz.gita.puzzle15.data.LocalStorage;

public class GameActivity extends AppCompatActivity {
    private AppCompatImageButton audio_on;
    private AppCompatImageButton audio_off;
    private LocalStorage storage;
    private TextView[][] buttons;
    LinearLayout container;
    private final Coordinate empty;
    private final ArrayList<Integer> numbers;
    private TextView counterTextView;
    private TextView time;
    private boolean isStart = false;
    private TextView bestnumberTextView;
    private int counter = 0;
    private Timer timer = new Timer();
    private int timecount = 0;
    private static MediaPlayer player;
    private static MediaPlayer music;

    private String str = "00:00";

    {
        empty = new Coordinate(3, 3);

        numbers = new ArrayList<>(15);
        for (int i = 0; i < 15; i++) {
            numbers.add(i + 1);
        }

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        storage = LocalStorage.getInstance(this);
        if (music == null) {
            music = MediaPlayer.create(this, R.raw.sneaky_snitch);
            player = MediaPlayer.create(this, R.raw.click);
            music.start();
            music.setOnCompletionListener(v -> {
                music.start();

            });
        } else music.start();


        counterTextView = findViewById(R.id.counter);
        // bestnumberTextView = findViewById(R.id.bestnumber);


        counterTextView.setText("" + counter);
        AppCompatImageButton shuffle = findViewById(R.id.shuffle);
        shuffle.setOnClickListener(v -> {
            timer.cancel();
            timer = new Timer();
            timecount = 0;
            String s = "00:00";
            time.setText(s);
            shuffle();
            loadViews();
            counter = 0;
            timerCount();
            counterTextView.setText(counter + "");
        });

        AppCompatImageButton arrowBack = findViewById(R.id.arrowBack);
        arrowBack.setOnClickListener(v -> {
            finish();
            music.pause();
        });

        audio_on = findViewById(R.id.audio_on);
        audio_off = findViewById(R.id.audio_off);
        audio();


        time = findViewById(R.id.time);


        timerCount();


        initViews();
        loadViews();
        if (storage.getIsTrue()) {
            saveItems();
        } else {
            storage.setIsTrue(true);
        }


    }

    private void audio() {
        audio_off.setOnClickListener(v -> {
            audio_on.setVisibility(View.VISIBLE);
            audio_off.setVisibility(View.INVISIBLE);
            storage.setIsAudioOn(true);
            music.pause();
            isStart = true;
        });
        audio_on.setOnClickListener(v -> {
            audio_off.setVisibility(View.VISIBLE);
            audio_on.setVisibility(View.INVISIBLE);
            music.start();
            storage.setIsAudioOn(false);
            isStart = false;
        });
        if (storage.getIsAudioOn()) {
            audio_on.setVisibility(View.VISIBLE);
            Log.d("TTT", storage.getIsAudioOn().toString());
            audio_off.setVisibility(View.INVISIBLE);
            music.pause();
            isStart = true;
        } else {
            Log.d("TTT", storage.getIsAudioOn().toString());
            audio_off.setVisibility(View.VISIBLE);
            audio_on.setVisibility(View.INVISIBLE);
            music.start();
            isStart = false;
        }
    }

    private void saveItems() {
        ArrayList<String> list = new ArrayList<>(16);
        for (int i = 0; i < 16; i++) {
            list.add(storage.getItemNumber("item" + i));
            //Log.d("TTT", storage.getItemNumber("item" + i));
            // Log.d("TTT", list.get(i));
        }
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals("")) {
                buttons[i / 4][i % 4].setVisibility(View.INVISIBLE);
                buttons[i / 4][i % 4].setText("");
                empty.setX(i / 4);
                empty.setY(i % 4);
            } else {
                buttons[i / 4][i % 4].setVisibility(View.VISIBLE);
                buttons[i / 4][i % 4].setText(list.get(i));
            }
        }

        counter = storage.getCounter();
        counterTextView.setText(counter + "");
        timecount = storage.getTimeCount();
        str = storage.getTimeStr();
        time.setText(str);
        isWin();
    }

    private void timerCount() {
        time.setText(str);
        timer.schedule(new TimerTask() {


            @Override
            public void run() {
                timecount++;
                int min = timecount / 60;
                int sec = timecount % 60;

                if (sec < 10 && min < 10) {
                    str = "0" + min + " : 0" + sec;
                } else if (sec >= 10 && min < 10) {
                    str = "0" + min + " : " + sec;
                } else if (sec < 10 && min >= 10) {
                    str = min + " : 0" + sec;
                } else {
                    str = min + " : " + sec;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time.setText(str);
                    }
                });
            }
        }, 1000, 1000);
    }


    private void initViews() {
        container = findViewById(R.id.container);
        int rawCount = container.getChildCount();
        int columnCount = ((LinearLayout) (container.getChildAt(0))).getChildCount();

        buttons = new TextView[rawCount][columnCount];
        for (int i = 0; i < rawCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                buttons[i][j] = (TextView) (((LinearLayout) (container.getChildAt(i))).getChildAt(j));
                buttons[i][j].setTag(new Coordinate(i, j));
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView clickedBtn = (TextView) view;
                        Coordinate clickedCoodinate = (Coordinate) clickedBtn.getTag();

                        int clickX = clickedCoodinate.getX();
                        int clickY = clickedCoodinate.getY();

                        move(clickX, clickY);
                    }
                });
            }
        }
        shuffle();
    }


    private void move(int clickX, int clickY) {
        int emptyX = empty.getX();
        int emptyY = empty.getY();

        if (Math.abs(emptyX - clickX) + Math.abs(emptyY - clickY) == 1) {
            buttons[emptyX][emptyY].setVisibility(View.VISIBLE);
            buttons[emptyX][emptyY].setText(buttons[clickX][clickY].getText());
            buttons[clickX][clickY].setVisibility(View.INVISIBLE);
            buttons[clickX][clickY].setText("");

            empty.setX(clickX);
            empty.setY(clickY);


            counterTextView.setText(++counter + "");
            if (isStart) {
                Log.d("TTTT","isStart1");
                player.pause();
            } else {
                Log.d("TTTT",player.toString());
                player.start();
            }
            isWin();
        }
    }

    private void loadViews() {
        buttons[empty.getX()][empty.getY()].setVisibility(View.VISIBLE);
        buttons[empty.getX()][empty.getY()].setText("");
        empty.setX(3);
        empty.setY(3);
        buttons[empty.getX()][empty.getY()].setVisibility(View.INVISIBLE);
        for (int i = 0; i < 15; i++) {
            buttons[i / 4][i % 4].setText(numbers.get(i) + "");

        }
    }

    private void shuffle() {
        Collections.shuffle(numbers);
        while (!isSolvable(numbers)) {
            Collections.shuffle(numbers);
        }
        loadViews();
    }

    private boolean isSolvable(List<Integer> list) {
        int countInvertions = 0;
        for (int i = 0; i < 15; i++) {
            for (int j = i + 1; j < 15; j++) {
                if (list.get(i) > list.get(j) && j > i) {
                    countInvertions++;
                }
            }
        }
        return countInvertions % 2 == 0;
    }

    private void isWin() {
        int count = 0;
        for (int i = 0; i < 15; i++) {
            if (buttons[i / 4][i % 4].getText().equals(String.valueOf(i + 1))) {
                count++;
            }
        }
        if (count == 15) {
            View view = LayoutInflater.from(this).inflate(R.layout.activity_dialog, null);

            LinearLayout dialog_home = view.findViewById(R.id.dialog_home);
            dialog_home.setOnClickListener(v -> {
                finish();
            });

            TextView dialog_counter = view.findViewById(R.id.dialog_counter);
            dialog_counter.setText(counter + "");
            TextView dialog_timer = view.findViewById(R.id.dialog_timer);
            dialog_timer.setText(str);
            timer.cancel();
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setView(view)

                    .setCancelable(false)
                    .create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();


            LinearLayout dialog_next = view.findViewById(R.id.dialog_next);
            dialog_next.setOnClickListener(v -> {
                dialog.dismiss();
//                bestnumberTextView.setText(counter + "");
                counter = 0;
                timecount = 0;
                str = "00:00";
                timer = new Timer();
                timerCount();
                counterTextView.setText(counter + "");
                shuffle();
            });

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        audio();
    }

    @Override
    protected void onPause() {
        super.onPause();
        music.pause();
        ArrayList<String> arr = new ArrayList<>();
        int columnCount = ((LinearLayout) (container.getChildAt(0))).getChildCount();
        for (int i = 0; i < container.getChildCount(); i++) {
            for (int j = 0; j < columnCount; j++) {
                TextView textView = (TextView) (((LinearLayout) (container.getChildAt(i))).getChildAt(j));
                arr.add(textView.getText().toString());
            }
        }
        for (int i = 0; i < arr.size(); i++) {
            //Log.d("TTT", arr.get(i));

            storage.setItemNumber("item" + i, arr.get(i));
        }
        // Log.d("TTT", "HHHHHHHHHH");

        storage.setCounter(counter);
        storage.setTimeCount(timecount);
        storage.setTimeStr(str);
    }
}