package com.dev.ashish.biteandclimb;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {
  private static final String TAG = "MainActivity";
    protected int dice_num;
    SoundPool soundPool;
    MediaPlayer mp;
    SoundPool.Builder soundPoolBuilder;
    AudioAttributes audioAttributes;
    AudioAttributes.Builder audioAttributesBuilder;
    int diceRoll;
    boolean loaded = false;
    ImageView diceView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        diceView = findViewById(R.id.dice_view);
        mp = MediaPlayer.create(getApplicationContext(), R.raw.dice_roll);
    diceView.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            dice_num = diceRollRandom();
            setDice();

            /*createSound();
            loadSounds();
            if(loaded){
                soundPool.play(diceRoll,1,1,0,0,1);
            }
            */
              mp.start();

            Log.d(TAG, "onClick: On Click is working");
         }
        });
    



    }
    protected int diceRes(int a){
        int resId = 0;
        switch(a){
            case 1:
                resId = R.drawable.dice1;
        Log.d(TAG, "diceRes: 1");
                break;
            case 2:
                resId = R.drawable.dice2;
                Log.d(TAG, "diceRes: 1");
                break;
            case 3:
                resId = R.drawable.dice3;
                Log.d(TAG, "diceRes: 1");
                break;
            case 4:
                resId = R.drawable.dice4;
                Log.d(TAG, "diceRes: 1");
                break;
            case 5:
                resId = R.drawable.dice5;
                Log.d(TAG, "diceRes: 1");
                break;
            case 6:
                resId = R.drawable.dice6;
                Log.d(TAG, "diceRes: 1");
                break;
            default:
                break;

        }
        return resId;
        }


    protected int diceRollRandom(){
        return (int) (Math.random() * ((6 - 1) + 1)) + 1;

    }
    protected void setDice(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            diceView.setImageDrawable(getResources().getDrawable(diceRes(dice_num)));
            Log.d(TAG, "setDice: " + dice_num);
        }
        else{
            diceView.setImageDrawable(getResources().getDrawable(diceRes(dice_num)));
        }
    }
    protected void createSound(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            audioAttributesBuilder = new AudioAttributes.Builder();
            audioAttributesBuilder.setUsage(AudioAttributes.USAGE_GAME);
            audioAttributesBuilder.setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION);
            audioAttributes=audioAttributesBuilder.build();

            soundPoolBuilder = new SoundPool.Builder();
            soundPoolBuilder.setAudioAttributes(audioAttributes);
            soundPool = soundPoolBuilder.build();

      Log.d(TAG, "createSound: done ");
        }
        else{
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        }
    }
    protected void loadSounds(){
      diceRoll = soundPool.load(this, R.raw.dice_roll, 1);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            public void onLoadComplete(SoundPool soundPool, int sampleId,int status) {
                loaded = true;
            }
        });

    Log.d(TAG, "loadSounds: done");
    }


    @Override
    protected void onPause() {
        super.onPause();
        soundPool.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        createSound();
        loadSounds();
    }
}
