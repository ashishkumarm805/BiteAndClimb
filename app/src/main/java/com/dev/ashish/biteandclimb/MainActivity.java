/*
   Author:M.Ashish Kumar
   Thanks to www.gamecodeschool.com and its forum for the tip to eliminate error using SoundPool
   Thanks to Freesound.org and music/sound creators for providing various sound effects
 */

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

import java.util.List;


public class MainActivity extends AppCompatActivity {
  private static final String TAG = "MainActivity";
    protected int dice_num;
    int diceIndex ;
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
              diceIndex = dice_num-1;
            setDice();

            createSound();
            loadDiceRollSound();
            //This code sets the time for loading the sample.Eliminates the error"Sample 1 not ready"
            android.os.SystemClock.sleep(1000);
            //Checks whether the samples have been loaded. If true,plays the sound
            if(loaded){
                soundPool.play(diceRoll,1,1,0,0,1);
            }



            Log.d(TAG, "onClick: On Click is working");
         }
        });




    }
    //Method for generating random numbers from 1 to 6
    protected int diceRollRandom(){
        return (int) (Math.random() * ((6 - 1) + 1)) + 1;

    }
    //Method for setting the image of the dice based on diceIndex
    //DiceImageAssets : Uses a class to load the List of Dice Images
    protected void setDice(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            diceView.setImageDrawable(getResources().getDrawable(setDiceId(DiceImageAssets.getDices())));
            Log.d(TAG, "setDice: " + dice_num);
        }
        else{
            diceView.setImageDrawable(getResources().getDrawable(setDiceId(DiceImageAssets.getDices())));
        }
    }
    //Sets new Audio Attributes for any SoundID
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
    //Loads Audio Attributes for DiceRoll SoundID and Uses OnLoadComplete Method to keep track of sample

    protected void loadDiceRollSound(){
      diceRoll = soundPool.load(this, R.raw.dice_roll, 1);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            public void onLoadComplete(SoundPool soundPool, int sampleId,int status) {
                loaded = true;
            }
        });

    Log.d(TAG, "loadDiceRollSound: done");
    }

//To get rid of looping and playing even after moving to new Activity
    @Override
    protected void onPause() {
        super.onPause();
        soundPool.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        createSound();
        loadDiceRollSound();
    }

    public int setDiceId(List<Integer> diceId) {
          return diceId.get(diceIndex);

    }
}
