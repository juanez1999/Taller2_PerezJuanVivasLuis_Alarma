package com.juan.perez.alarma;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TextView texto;
    TextView update_text;
    TimePicker reloj;
    AlarmManager alarmManager;
    Button on;
    Button off;
    Context context;
    PendingIntent pending_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context=this;


        alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
        update_text=findViewById(R.id.tv_main_update_text);
        reloj=findViewById(R.id.tp_main_reloj);
        on=findViewById(R.id.btn_main_on);
        off=findViewById(R.id.btn_main_off);

        //Crear una instancia de calendario
        final Calendar calendario= Calendar.getInstance();

        //Inicializar boton encender alarm
        on=findViewById(R.id.btn_main_on);

        //Crear un intent para ir a Alarm_Receiver class
        final Intent i= new Intent(this.context,Alarm_Receiver.class);


        //Crear el onClickListener para empezar la alarma
        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               /* Intent i = new Intent(this, Services.class);
                i.putExtra("","");
                startService(i); */



                //Set la instancia de calendario con la hora y minutos que el usuario escoja
                calendario.set(Calendar.HOUR_OF_DAY,reloj.getHour());
                calendario.set(Calendar.MINUTE,reloj.getMinute());

                //obtener el valor del string con la hora y los minutos
                int hora = reloj.getHour();
                int min= reloj.getMinute();

                // convertir los int a string
                String hora_string= String.valueOf(hora);
                String min_string= String.valueOf(min);

               /* //Convertir formato 24 horas a 12 horas tiempo
                if(hora>12){
                    hora_string=String.valueOf(hora - 12);
                } */

                //Metodo que cambia el texto mostrado del estado de la alarma
                set_alarm_text("Alarma puesta a la:" +" "+ hora_string + ":" + min_string);

                //Poner un extra dentro de mi intent
                //Que le diga a la alarma que se presiono el boton on
                i.putExtra("extra","alarm on");

                //Crear un intent pendiente que demore el intento
                //hasta especificar la hora y minuto
                pending_intent = PendingIntent.getBroadcast(MainActivity.this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);

                //Poner la Alarm Manager
                alarmManager.set(AlarmManager.RTC_WAKEUP,calendario.getTimeInMillis(),pending_intent);
            }
        });

        //Inicializar boton apagar alarma
        off=findViewById(R.id.btn_main_off);
        //Crear el onClickListener para apagar la alarma
        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Metodo que cambia el texto mostrado del estado de la alarma
                set_alarm_text("Alarma apagada!");

                //Poner un extra dentro de mi intent
                //Que le diga a la alarma que se presiono el boton off
                i.putExtra("extra","alarm off");

                //Cancelar la alarma
                alarmManager.cancel(pending_intent);

                //Parar el ringtone
                sendBroadcast(i);
            }
        });


       /* reloj.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                texto.setText("Alarma puesta a la: "+hourOfDay+":"+minute);
            }
        });*/
    }

    private void  set_alarm_text(String output){
        update_text.setText(output);
    }
}
