package com.teste.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import me.relex.circleindicator.CircleIndicator;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        final View btnProximo = findViewById(R.id.proximo);
        final View btnPular = findViewById(R.id.pular);
        btnProximo.setOnClickListener(this);
        btnPular.setOnClickListener(this);

        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new TutorialAdapter(getSupportFragmentManager()));
        final CircleIndicator indicator = findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.proximo:
                callNext();
                break;
            case R.id.pular:
                callMap();
                break;
        }
    }

    private void callMap() {
        final Intent intent = new Intent(StartActivity.this, MapsActivity.class);
        startActivity(intent);
        finish();
    }

    private void callNext() {
        int nextItem = viewPager.getCurrentItem() + 1;
        if (nextItem < viewPager.getChildCount()) {
            viewPager.setCurrentItem(nextItem, true);
        } else {
            callMap();
        }
    }
}
