package com.juan.perez.alarma;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;

public class Alarm_Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("ESTOY ACA", "wiii");

        //recuperar el extra string del intent
        String get_your_string= intent.getExtras().getString("extra");

        Log.e("La llave es", get_your_string);

        //Crear un intent para ir a la clase RingtonePlayingService
        Intent service_intent = new Intent(context, RingtonePlayingService.class);

        //pasar el extra string desde la MainActivity a la Ringtone Service
        service_intent.putExtra("extra",get_your_string);

        //Empezar the RingtoneService
        context.startService(service_intent);

    }
}
