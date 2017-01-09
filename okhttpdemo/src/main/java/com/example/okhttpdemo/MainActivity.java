package com.example.okhttpdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

        Button btn_get = (Button) findViewById(R.id.btn_get);
        Button btn_post = (Button) findViewById(R.id.btn_post);

        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initGetConection();
            }
        });


        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开启一个线程，做联网操作
                new Thread() {
                    @Override
                    public void run() {

                        initPstConection();
                    }
                }.start();
            }
        });


    }

    private void initPstConection() {
//        MediaType MEDIA_TYPE_MARKDOWN
//                = MediaType.parse("text/x-markdown; charset=utf-8");
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String json = "{\n" +
                "  \"md5\": \"\"\n" +
                "}";
        OkHttpClient okHttpClient = new OkHttpClient();
        //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
        RequestBody requestBody = RequestBody.create(JSON, json);
        //创建一个请求对象
        Request request = new Request.Builder()
                .url("http://115.28.61.196:8080/danglaoshi-web-app/basic/knowledge")
                .post(requestBody)
                .addHeader("accept-language","zh")
                .addHeader("sessionId","5521bde9-2f36-4d16-9fb8-e75df74154f9")
                .addHeader("userId","qE1uiD6wHve=")
                .build();
        //发送请求获取响应
        try {
            Response response=okHttpClient.newCall(request).execute();
            //判断请求是否成功
            if(response.isSuccessful()){
                //打印服务端返回结果
                Log.e("看看数据",response.body().string());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    /**
     * 网络连接
     */
    private void initGetConection() {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
//                .url("https://www.baidu.com/")
                .url("http://api.danglaoshi.net/operation/advertise/splash")
                .header("User-Agent", "OkHttp Headers")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                System.out.println(response.body().string());
                System.out.println(response.headers());
                System.out.println(response.header("User-Agent"));

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
