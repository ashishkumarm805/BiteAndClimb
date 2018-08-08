package com.dev.ashish.biteandclimb;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DiceImageAssets {
  private static final String TAG = "DiceImageAssets";

        private static List<Integer> dices = new ArrayList<Integer>(){
            {
                add(R.drawable.dice1);
                add(R.drawable.dice2);
                add(R.drawable.dice3);
                add(R.drawable.dice4);
                add(R.drawable.dice5);
                add(R.drawable.dice6);

            }
        };

        public static List<Integer> getDices(){
            return dices;
        }
    }





