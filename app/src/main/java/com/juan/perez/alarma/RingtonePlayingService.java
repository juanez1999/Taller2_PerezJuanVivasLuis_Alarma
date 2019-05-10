package com.juan.perez.alarma;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class RingtonePlayingService extends Service {

    //Crear una instancia de Media Player
    MediaPlayer media_song;
    boolean isRunning;
    Comunicacion com;

    int startId;

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("Estoy en el rigntone","funciona");
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId){
        Log.e("Estoy en el rigntone","funciona");
        Log.i("LocalService","Receive start id"+ startId + ":" + intent);

        //obtener el valor extra string
        String state= intent.getExtras().getString("extra");

        Log.e("Ringtone state:extra is",state);

        //Convierte los extras string del intent en start IDs, valores 0 o 1
        assert state != null;
        switch (state){
            case "alarm on":
                startId=1;
                break;
            case "alarm off":
                startId=0;
                break;
                default:
                    startId=0;
                    break;
        }


        //Condiciones para prender o no el ringtone

        //si no esta sonando la musica y el usuario presiona empezar alarma la musica empieza
        if(!this.isRunning && startId == 1){
            media_song = MediaPlayer.create(getApplicationContext() , R.raw.song);

            //crear una instancia de media player
            System.out.println(media_song);
            //Empezar el ringtone
            media_song.start();

            this.isRunning=true;
            this.startId = 0;
        }
        //Si esta sonando la musica y el usuario presiona apagar la alarma la musica se para
        else if(this.isRunning && startId == 0){
            //Parar el ringtone
            media_song.stop();
            media_song.reset();

            this.isRunning = false;
            this.startId = 0;

        }
        //Si la musica no esta sonando y el usuario presiona apagar la alarma no sucede nada
        else if(!this.isRunning && startId == 0){

            this.isRunning=false;
            this.startId=0;
        }
        //Si la musica  esta sonando y el usuario presiona empezar la alarma no sucede nada
        else if(this.isRunning && startId == 1){
            this.isRunning=true;
            this.startId=1;
        }
        else{

        }

        return START_NOT_STICKY;
    }

    public void onDestroy(){
        //Decirle al usuario que ha parado
        //super.onDestroy();
        //this.isRunning = false;
       // Toast.makeText(this,"on Destroy called", Toast.LENGTH_SHORT).show();
    }


}
