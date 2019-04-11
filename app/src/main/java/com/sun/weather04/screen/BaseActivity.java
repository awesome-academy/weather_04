package com.sun.weather04.screen;

import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
        public abstract void initView();

        public abstract void initData();

        public abstract void initListener();
}
