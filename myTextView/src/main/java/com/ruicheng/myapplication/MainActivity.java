package com.ruicheng.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.style.ImageSpan;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ruicheng.myapplication.TextHtml.LinkMovementMethodExt;
import com.ruicheng.myapplication.TextHtml.MImageGetter;
import com.ruicheng.myapplication.TextHtml.MessageSpan;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        ScrollView sv = (ScrollView) findViewById(R.id.sv);

        final String html = "下面是第一张图片了 " + "<img src='https://ss3.baidu.com/-fo3dSag_xI4khGko9WTAnF6hhy/image/h%3D200/sign=1870ec20a96eddc439e7b3fb09dab6a2/dbb44aed2e738bd4a59870f4a58b87d6267ff9be.jpg'/>" +
                "这也是第二张图片" + "<img src='https://ss1.baidu.com/9vo3dSag_xI4khGko9WTAnF6hhy/image/h%3D200/sign=9d3833093f292df588c3ab158c305ce2/d788d43f8794a4c274c8110d0bf41bd5ad6e3928.jpg'/>" +
                "最后一张" + "<img src = 'http://f.hiphotos.baidu.com/image/h%3D200/sign=3d746172a4efce1bf52bcfca9f50f3e8/bba1cd11728b47101489df48c0cec3fdfd03238b.jpg'/>";

        tv.setText(Html.fromHtml(html, new MImageGetter(tv, getApplicationContext()), null));
        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                int what = msg.what;
                if (what == 200) {
                    MessageSpan ms = (MessageSpan) msg.obj;
                    Object[] spans = (Object[]) ms.getObj();
                    final ArrayList<String> list = new ArrayList<>();
                    for (Object span : spans) {
                        if (span instanceof ImageSpan) {
                            Log.i("picUrl==", ((ImageSpan) span).getSource());
                            list.add(((ImageSpan) span).getSource());
                            Intent intent = new Intent(getApplicationContext(), ImageGalleryActivity.class);
                            intent.putStringArrayListExtra("images", list);
                            startActivity(intent);
                        }
                    }
                }
            }
        };
        tv.setMovementMethod(LinkMovementMethodExt.getInstance(handler, ImageSpan.class));

    }


}
