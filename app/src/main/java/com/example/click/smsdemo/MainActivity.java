package com.example.click.smsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.Random;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class MainActivity extends AppCompatActivity {
    /**
     * mob.com创建应用时候生成APP_KEY
     */
    private static String APP_KEY = "a7de22b14b9d";
    /**
     * 　APP_SECRET
     */
    private static String APP_SECRET = "29c2af302ab8c9f39d714d5e17b6c583";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1>初始化SMSSDK
        SMSSDK.initSDK(MainActivity.this, APP_KEY, APP_SECRET, true);

        //2>创建手机号注册界面
        RegisterPage registerPage = new RegisterPage();
        //3>获取注册界面信息
        registerPage.setRegisterCallback(new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                //判断是否成功
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //返回的Object类型的data是一个HashMap集合
                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                    //获取返回的信息
                    String country = (String) phoneMap.get("country");
                    String phone = (String) phoneMap.get("phone");
                    //4>提交验证信息
                    submitUserInfo(country, phone);
                }
            }
        });
        //5>验证成功，显示成功
        registerPage.show(MainActivity.this);
    }

    /***
     * 提交用户信息
     *
     * @param country 　选择的国家
     * @param phone   电话号码
     */
    public void submitUserInfo(String country, String phone) {
        String uid = Math.abs(new Random().nextInt()) + "";
        String nickName = "SMSDemo";
        String avatar = "头像";
        SMSSDK.submitUserInfo(country, phone, uid, nickName, avatar);

    }
}
