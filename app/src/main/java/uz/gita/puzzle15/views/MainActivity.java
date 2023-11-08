package uz.gita.puzzle15.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;

import uz.gita.puzzle15.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout btnPlay = findViewById(R.id.btnPlay);

        btnPlay.setOnClickListener(v -> {
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        });
        // LinearLayout btnStatistic = findViewById(R.id.btnStatistic);
      /*  btnStatistic.setOnClickListener(v -> {
            Intent intent = new Intent(this, StatisticsActivity.class);
            startActivity(intent);
        });*/
        LinearLayout btnInfo = findViewById(R.id.btnInfo);
        btnInfo.setOnClickListener(v -> {
            Intent intent = new Intent(this, InfoActivity.class);
            startActivity(intent);
        });
        LinearLayout btnShare = findViewById(R.id.btnShare);
        btnShare.setOnClickListener(v -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            String s = "https://play.google.com/store/apps/details?id=" + this.getApplicationContext().getPackageName() + "";
            sendIntent.putExtra(Intent.EXTRA_TEXT, s);
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        });

      /*  String playStoreUrl =
                "Hoziroq yuklab oling!: " + "https://play.google.com/store/apps/details?id=${context?.applicationContext?.packageName}";*/
    }
}