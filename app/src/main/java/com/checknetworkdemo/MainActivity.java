package com.checknetworkdemo;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button button;
    private Button network;
    private TextView textView;

    private final int SUCCESS = 1;
    private final int FAILURE = 0;
    private final int ERRORCODE = 2;

    private static final String PATH = "http://api.k780.com/?app=weather.today&weaid=1&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=json";

    protected String weatherResult;

//    private Handler handler = new Handler() {
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case SUCCESS:
//
//                    Toast.makeText(MainActivity.this, "获取数据成功", Toast.LENGTH_SHORT).show();
//                    break;
//                case FAILURE:
//                    Toast.makeText(MainActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
//                    break;
//                case ERRORCODE:
//                    Toast.makeText(MainActivity.this, "获取的CODE码不为200！", Toast.LENGTH_SHORT).show();
//                    break;
//                default:
//                    break;
//            }
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textViewContent);
        button = (Button) findViewById(R.id.button);
        network = (Button) findViewById(R.id.network);

        network.setOnClickListener(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.network:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            HttpURLConnection conn = (HttpURLConnection) new URL(PATH).openConnection();
                            conn.setRequestMethod("GET");
                            conn.setConnectTimeout(5000);
                            int code = conn.getResponseCode();
                            if(code == 200){
                                InputStream is = conn.getInputStream();
                                String content = GetJson.readStream(is);
                                Message msg = new Message();
                                msg.obj = content;
                                handler.sendMessage(msg);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            textView.setText(msg.obj+"");
        }
    };
}

























