package com.ldm.quicktask.activities;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

import com.ldm.quicktask.R;

public class SoundManager {

    private SoundPool soundPool;
    private int clickSoundId;

    public SoundManager(Context context) {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(2) // Number of simultaneous sound streams
                .setAudioAttributes(attributes)
                .build();

        // Load the click sound
        clickSoundId = soundPool.load(context, R.raw.click, 1);
    }

    public void playClickSound() {
        if (clickSoundId != 0) {
            soundPool.play(clickSoundId, 1.0f, 1.0f, 1, 0, 1.0f);
        }
    }

    public void release() {
        soundPool.release();
    }
}
