package p.com.flashlight;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class OnScreen extends AppCompatActivity {
    TextView screen;
    int value = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_screen);
        screen = (TextView ) findViewById(R.id.OnScreen);

    }

    public void gotoMain(View view) {
        Bundle bndlanimation =
                ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1,R.anim.animation2).toBundle();
        startActivity(new Intent(this,MainActivity.class),bndlanimation);
        finish();
    }

    public void changeColor(View view) {
        switch (value){
            case 0:
                screen.setBackgroundColor(getResources().getColor(R.color.lightwhite));
                value =1;
                break;
            case 1:
                screen.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                value =2;
                break;
            case 2:
                screen.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                value =3;
                break;
            case 3:
                screen.setBackgroundColor(getResources().getColor(R.color.green));
                value = 0;
                break;
            default:

        }
    }

}
