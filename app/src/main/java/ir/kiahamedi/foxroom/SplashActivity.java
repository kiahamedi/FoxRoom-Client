package ir.kiahamedi.foxroom;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private Handler handler;
    private TextView txt;
    private ImageView img3,img4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        txt = (TextView) findViewById(R.id.txt);
        img3 = (ImageView) findViewById(R.id.img3);
        img4 = (ImageView) findViewById(R.id.img4);

        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/g.ttf");
        txt.setTypeface(typeface);

        Animation animationTXT = AnimationUtils.loadAnimation(this,R.anim.move_down);
        txt.setAnimation(animationTXT);

        Animation animationIMG3 = AnimationUtils.loadAnimation(this,R.anim.move_left);
        img3.setAnimation(animationIMG3);

        Animation animationIMG4 = AnimationUtils.loadAnimation(this,R.anim.move_right);
        img4.setAnimation(animationIMG4);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,Welcome.class));
                finish();

            }
        },5000);


    }
}
