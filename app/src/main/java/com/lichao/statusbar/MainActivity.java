package com.lichao.statusbar;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends BaseActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("沉浸式设计");
        setSupportActionBar(toolbar);
        View nav = findViewById(R.id.nav);
        setToolBarStyle(toolbar,nav, Color.GREEN);
    }
}
