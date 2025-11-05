package com.smartcalc.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.smartcalc.convterter.HistoryActivity;
import com.smartcalc.convterter.ConverterActivity;

public class MainActivity extends AppCompatActivity {

    Button btnConverter, btnHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnConverter = findViewById(R.id.btnConverter);
        btnHistory = findViewById(R.id.btnHistory);

        btnConverter.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ConverterActivity.class))
        );

        btnHistory.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, HistoryActivity.class))
        );
    }
}
