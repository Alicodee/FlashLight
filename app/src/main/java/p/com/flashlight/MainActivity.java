package p.com.flashlight;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    Boolean isOn = false;
    Boolean hasFlash;
    ImageView lightOnImage;
    ImageView lightOnCircle;
    ImageView lightOnCircle2;
    ImageView lightOnCircle3;
    Camera cam;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
       // MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        lightOnImage =(ImageView) findViewById(R.id.turnImage);
        lightOnCircle =(ImageView)findViewById(R.id.turnImageInnerCircle);
        lightOnCircle2 =(ImageView)findViewById(R.id.turnImageInnerCircle2);
        lightOnCircle3 =(ImageView)findViewById(R.id.turnImageInnerCircle4);

        grantPermissions();
        hasFlash = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (!hasFlash) {
            // device doesn't support flash
            // Show alert message and close the application
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            builder.setTitle("Error");
            builder.setMessage("Sorry, your device doesn't support flash light!");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.show();
        }

        AdView mAdMobAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("E8F47887998C65C786857B9A930BCC1D")
                .build();
        mAdMobAdView.loadAd(adRequest);

    }

    private void grantPermissions() {
        PackageManager pm = this.getPackageManager();
        int hasPerm = pm.checkPermission(
                Manifest.permission.CAMERA,
                this.getPackageName());
        if (hasPerm != PackageManager.PERMISSION_GRANTED) {
            // do stuff
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.INTERNET},
                    1);
        }
    }

    public void turnOn(View view) {

        if(isOn){
            lightOnImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_power_off2));
            lightOnCircle2.setVisibility(View.INVISIBLE);
            lightOnCircle.setVisibility(View.VISIBLE);
            lightOnCircle3.setVisibility(View.INVISIBLE);
            turnOffCamera();
            isOn = false;
        }else {
            lightOnImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_power_off1));
            lightOnCircle.setVisibility(View.INVISIBLE);
            lightOnCircle2.setVisibility(View.VISIBLE);
            lightOnCircle3.setVisibility(View.VISIBLE);
            turnOnCamera();
            isOn= true;

        }
    }
    public void turnOnCamera(){
        cam = Camera.open();
        Camera.Parameters p = cam.getParameters();
        p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
      //  p.setFlashMode(Camera.Parameters.FLASH_MODE_RED_EYE);
        cam.setParameters(p);
        cam.startPreview();
    }
    public void turnOffCamera(){
        cam.stopPreview();
        cam.release();
    }

    public void gotoScreen(View view) {
        Bundle bndlanimation =
                ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1,R.anim.animation2).toBundle();

        startActivity(new Intent(this,OnScreen.class),bndlanimation);
        finish();
    }
}
