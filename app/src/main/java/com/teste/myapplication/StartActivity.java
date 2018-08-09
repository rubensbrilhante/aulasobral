package com.teste.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        final View btn = findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(StartActivity.this, MapsActivity.class);
                Bundle bundle = new Bundle();
//                -3.6880315,-40.3498383
                bundle.putFloat("latitude", -3.6880315f);
                bundle.putFloat("longitude", -40.3498383f);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
