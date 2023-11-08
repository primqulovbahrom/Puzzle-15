package uz.gita.puzzle15.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.os.Bundle;
import android.widget.Toast;

import uz.gita.puzzle15.R;

public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        AppCompatImageButton arrowBack = findViewById(R.id.arrowBack);
        arrowBack.setOnClickListener(v -> finish());

    }
}