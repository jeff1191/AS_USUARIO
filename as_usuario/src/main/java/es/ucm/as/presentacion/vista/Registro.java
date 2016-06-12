package es.ucm.as.presentacion.vista;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import es.ucm.as.R;
import es.ucm.as.negocio.conexion.msg.Mensaje;
import es.ucm.as.negocio.usuario.TransferUsuario;
import es.ucm.as.presentacion.controlador.Controlador;
import es.ucm.as.presentacion.controlador.ListaComandos;
import es.ucm.as.presentacion.notificaciones.ServicioNotificaciones;

public class Registro extends Activity {

    private EditText nombreUsuario;
    private EditText correoUsuario;
    private EditText claveUsuario;
    private String nombre="";
    private String correo="";
    private String clave="";
    private static final String PATRON_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_registro);
        nombreUsuario = (EditText) findViewById(R.id.nombreRegistro);
        correoUsuario = (EditText) findViewById(R.id.emailRegistro);
        claveUsuario = (EditText) findViewById(R.id.claveRegistro);

        if(getIntent().getExtras() != null) {
            TransferUsuario sincroCorrecta = (TransferUsuario) getIntent().getExtras().getSerializable("usuario");
            if (sincroCorrecta != null) {
                Controlador.getInstancia().ejecutaComando(ListaComandos.CREAR_USUARIO, sincroCorrecta);
                startService(new Intent(this, ServicioNotificaciones.class));
                Controlador.getInstancia().ejecutaComando(ListaComandos.ACTUALIZAR_PUNTUACION, null);
                Controlador.getInstancia().ejecutaComando(ListaComandos.CONSULTAR_USUARIO, null);
            }
        }
    }

    public void realizarRegistro(View v){

        //Leer los datos del "fomulario"
        nombre = String.valueOf(nombreUsuario.getText());
        correo = String.valueOf(correoUsuario.getText());
        clave = String.valueOf(claveUsuario.getText());

        if (datosValidos()) {

            TransferUsuario crearUsuario = new TransferUsuario();
            crearUsuario.setNombre(nombre);
            crearUsuario.setAvatar("");
            crearUsuario.setColor("AS_theme_azul");
            crearUsuario.setPuntuacion(0);
            crearUsuario.setPuntuacionAnterior(0);
            crearUsuario.setCorreo(correo);
            crearUsuario.setTono(R.raw.defecto);
            crearUsuario.setCodigoSincronizacion(clave);

            Mensaje msg = new Mensaje("registro");
            msg.setUsuario(crearUsuario);
            Log.e("REGISTRO", "A SINCRONIZAR!");
            Controlador.getInstancia().ejecutaComando(ListaComandos.SINCRONIZAR_REGISTRO, msg);
        }
    }

    private boolean datosValidos() {

        if(!nombre.toString().matches("") &&
                !correo.toString().matches("")&&
                !clave.toString().matches("")) {

            if(correo.toString().matches(PATRON_EMAIL)){
                    return true;
            }else
                mostrarMensajeError("Campo email inválido");
        }else
            mostrarMensajeError("Algún campo es vacío");

        return false;
    }

    private void mostrarMensajeError(String msg){
        Toast errorNombre =
                Toast.makeText(getApplicationContext(),
                        msg, Toast.LENGTH_LONG);
        errorNombre.show();
    }
}
