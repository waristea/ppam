package com.example.william.alarmy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ActiveAlarmActivity extends AppCompatActivity {

    Intent serviceIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_alarm);

        // get extra of ringtone and game from intent
        serviceIntent = getIntent();
        String ringtone = serviceIntent.getStringExtra("RINGTONE");
        String game = serviceIntent.getStringExtra("GAME");

        Button turnOffAlarmButton = (Button) findViewById(R.id.turnOffAlarmButton);

        serviceIntent = new Intent(this, RingtonePlayingService.class);
        serviceIntent.putExtra("extra", "yes");
        //serviceIntent.putExtra("GAME", game);
        serviceIntent.putExtra("RINGTONE", ringtone); // Change later with dynamic

        startService(serviceIntent);

        switch(game){
            case("default_game"):{
                // nanti disini dikirim intent ke game, yang kalau selesai
                // baru muncul halaman 'turnOff' (halaman ini)
                Log.d("GAME", "playing default game");
                /*
                Intent startIntent = new Intent(this, FlashCard.class);
                startActivity(startIntent);
                */
                Log.d("GAME", "default game ends");
                break;
            }
            case("flashcard"):{
                Log.d("GAME", "playing flashcard game");
                // nanti disini dikirim intent ke game, yang kalau selesai
                // baru muncul halaman 'turnOff' (halaman ini)
                /*
                Intent startIntent = new Intent(this, FlashCard.class);
                startActivity(startIntent);
                */
                Log.d("GAME", "flashcard game ends");
                break;
            }
            case("poopy"):{
                Log.d("GAME", "playing poopy game");
                // nanti disini dikirim intent ke game, yang kalau selesai
                // baru muncul halaman 'turnOff' (halaman ini)
                /*
                Intent startIntent = new Intent(this, FlashCard.class);
                startActivity(startIntent);
                */
                Log.d("GAME", "poopy game ends");
                break;
            }
            case("no"):{
                Log.d("GAME", "No game is played");
                break;
            }
            default:{
                Log.d("GAME", "String unidentifiable");
                break;
            }
        }


        turnOffAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceIntent.putExtra("extra", "no");
                startService(serviceIntent);
                Log.d("ALARM-OFF", "ITS TURNED OFF");
                finish();
            }
        });

    }
}
