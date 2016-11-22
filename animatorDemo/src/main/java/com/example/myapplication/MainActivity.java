package com.example.myapplication;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_translate;
    private Button btn_alpha;
    private Button btn_scale;
    private Button btn_rotate;
    private Button btn_set;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_translate = (Button) findViewById(R.id.btn_translate);
        btn_alpha = (Button) findViewById(R.id.btn_alpha);
        btn_scale = (Button) findViewById(R.id.btn_scale);
        btn_rotate = (Button) findViewById(R.id.btn_rotate);
        btn_set = (Button) findViewById(R.id.btn_set);
        iv = (ImageView) findViewById(R.id.iv);

        btn_translate.setOnClickListener(this);
        btn_alpha.setOnClickListener(this);
        btn_scale.setOnClickListener(this);
        btn_rotate.setOnClickListener(this);
        btn_set.setOnClickListener(this);



    }


    @Override
    public void onClick(View v) {

       switch (v.getId()){
           case R.id.btn_translate:
               //平移动画
//               initValueAnimator();
               initObjectAnimator(0);
               break;
           case R.id.btn_alpha:
               //透明
               initObjectAnimator(1);
               break;
           case R.id.btn_scale:
               //缩放
               initObjectAnimator(2);
               break;
           case R.id.btn_rotate:
               //旋转
               initObjectAnimator(3);
               break;
           case R.id.btn_set:
               //一起飞
               initObjectAnimator(4);
               break;
           default:
               break;
       }
    }

    /**
     * ValueAnimator动画使用
     */
    public void initValueAnimator(){
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 500f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float deltaY = (float)animation.getAnimatedValue();
                iv.setTranslationY(deltaY);
            }
        });
        //默认duration是300毫秒
        valueAnimator.setDuration(3000);
        valueAnimator.start();
    }

    /**
     * ObjectAnimator动画使用
     */
    public void initObjectAnimator(int i){

        if (i == 0){
            //平移
            float value1 = 0f;
            float value2 = 500f;
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv, "translationY", value1, value2);
            objectAnimator.setDuration(3000);
            objectAnimator.start();
        }else if (i == 1){
            //透明
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv, "alpha", 1f, 0,1f);
            objectAnimator.setDuration(3000);
            objectAnimator.start();

        }else if (i == 2){
            //缩放
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv, "scaleY", 1f, 3f,1f);
            objectAnimator.setDuration(3000);
            objectAnimator.start();
        }else if (i == 3){
            //旋转
            ObjectAnimator animator = ObjectAnimator.ofFloat(iv, "rotation", 0f, 360f);
            animator.setDuration(5000);
            animator.start();
        }else if (i == 4){
            //一起
            ObjectAnimator translationY = ObjectAnimator.ofFloat(iv, "translationY", 0f, 500f,0f);
            ObjectAnimator alpha = ObjectAnimator.ofFloat(iv, "alpha", 1f, 0,1f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(iv, "scaleY", 1f, 3f,1f);
            ObjectAnimator rotation = ObjectAnimator.ofFloat(iv, "rotation", 0f, 360f);
            AnimatorSet animSet = new AnimatorSet();
            animSet.play(alpha).with(scaleY).with(rotation).after(translationY);
            animSet.setDuration(5000);
            animSet.start();

        }

    }
}
