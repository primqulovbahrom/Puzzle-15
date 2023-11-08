package uz.gita.puzzle15.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.os.Bundle;

import uz.gita.puzzle15.R;

public class InfoActivity extends AppCompatActivity {
    private AppCompatImageButton arrowBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        arrowBack = findViewById(R.id.arrowBack);
        arrowBack.setOnClickListener(v -> finish());
    }
}