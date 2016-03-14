package es.ucm.as_usuario.presentacion;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import es.ucm.as_usuario.R;
import es.ucm.as_usuario.negocio.usuario.TransferUsuario;
import es.ucm.as_usuario.negocio.usuario.Usuario;
import es.ucm.as_usuario.negocio.utils.Frecuencia;
import es.ucm.as_usuario.presentacion.controlador.Controlador;
import es.ucm.as_usuario.presentacion.controlador.ListaComandos;
import es.ucm.as_usuario.presentacion.notificaciones.ServicioNotificaciones;

public class Bienvenido extends Activity {
    private static final long DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_bienvenido);

        Toast toast1 =
                Toast.makeText(getApplicationContext(),
                        "Bienvenido", Toast.LENGTH_SHORT);

        toast1.show();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent().setClass(
                        Bienvenido.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, DELAY);

        startService(new Intent(this, ServicioNotificaciones.class));


        //MODIFICARR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        Usuario.getInstancia().setNombre("Albertin");
        Usuario.getInstancia().setAvatar("");
        Usuario.getInstancia().setColor("blue");
        Usuario.getInstancia().setTono("Ringtone 1");
        Usuario.getInstancia().setFrecuenciaRecibirInforme(Frecuencia.SEMANAL);
        Usuario.getInstancia().setPuntuacion(6);
        //////////////////////////////////////////////////////////////////////
/*
        TransferUsuario transferUsuario = new TransferUsuario();
        transferUsuario.setPuntuacion(5);
        transferUsuario.setPuntuacionAnterior(8);
        transferUsuario.setNombre("Jeff");
        Controlador.getInstancia().ejecutaComando(ListaComandos.CREAR_USUARIO, transferUsuario);*/
    }
}
