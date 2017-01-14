package com.ruicheng.javascript2android;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private WebView mWebView;
    private Button mButton;
    private WebAppInterface mAppInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = (WebView) findViewById(R.id.wb);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(this);
        mAppInterface = new WebAppInterface(this);
        initWeb();
    }

    private void initWeb() {
        mWebView.loadUrl("file:///android_asset/index.html");//加载本地的html

        WebSettings settings = mWebView.getSettings();//获取WebSettings对象，利用WebSettings配置WebView

        settings.setJavaScriptEnabled(true);//设置允许执行JS脚本

        mWebView.addJavascriptInterface(mAppInterface,"app");//添加HTML与AAndroid的通讯接口

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button){
            mAppInterface.showName("测试成功");
        }
    }

    class WebAppInterface{
        private Context context;

        public WebAppInterface(Context context) {
            this.context = context;
        }

        //-----------下面是JS调用安卓的的方法

        @JavascriptInterface
        public void sayHello(String name){
            if (name.equals("")){
                Toast.makeText(context,"还没点本地按钮", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context,name, Toast.LENGTH_SHORT).show();
            }

        }
        @JavascriptInterface
        public void back(){
            Toast.makeText(context,"点击了h5的返回按钮", Toast.LENGTH_SHORT).show();
        }

        //--------------下面是安卓调用js的的方法
        @JavascriptInterface
        public void showName(final String name){
            runOnUiThread(new Runnable() {//Android更新UI需要在主线程
                @Override
                public void run() {
                    mWebView.loadUrl("javascript:showName('"+name+"')");
                }
            });
        }

    }
}
