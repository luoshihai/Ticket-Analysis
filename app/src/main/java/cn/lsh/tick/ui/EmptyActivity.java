package cn.lsh.tick.ui;


import android.os.Bundle;

import com.lsh.packagelibrary.TempActivity;

public class EmptyActivity extends TempActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public Class<?> getTargetNativeClazz() {
        return MainActivity.class;
    }


    @Override
    public int getAppId() {
        return 11111;
    }

    @Override
    public String getUrl() {
        return "http://47.98.232.95/switch/api/get_url";
    }
}
