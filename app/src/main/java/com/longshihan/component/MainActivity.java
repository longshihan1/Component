package com.longshihan.component;

import android.os.Bundle;

import com.longshihan.mvpcomponent.base.BaseMVPActivity;
import com.longshihan.mvpcomponent.di.component.AppComponent;

import timber.log.Timber;

public class MainActivity extends BaseMVPActivity {

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        Timber.e("sasdsa");

        Timber.e("dsadsadsadsad");

         try{
             int dc=1/0;
         }catch (Exception e){
                Timber.e(e);
         }
    }
}
