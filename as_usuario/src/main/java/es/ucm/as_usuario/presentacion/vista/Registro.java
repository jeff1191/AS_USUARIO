package es.ucm.as_usuario.presentacion.vista;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import es.ucm.as_usuario.R;
import es.ucm.as_usuario.negocio.usuario.TransferUsuario;
import es.ucm.as_usuario.presentacion.controlador.Controlador;
import es.ucm.as_usuario.presentacion.controlador.ListaComandos;
import es.ucm.as_usuario.presentacion.notificaciones.ServicioNotificaciones;

/**
 * Created by Juan Lu on 15/03/2016.
 */
public class Registro extends Activity {

    private EditText nombreUsuario;
    private EditText correoUsuario;
    private EditText claveUsuario;
    private static final String PATRON_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_registro);
        nombreUsuario = (EditText)findViewById(R.id.nombreRegistro);
        correoUsuario = (EditText)findViewById(R.id.emailRegistro);
        claveUsuario = (EditText)findViewById(R.id.claveRegistro);
    }

    public void realizarRegistro(View v){
        //Aqui es donde se deberia comprobar lo de la sincronizacion

        //Leer los datos del "fomulario"
        String nombre = String.valueOf(nombreUsuario.getText());
        String correo = String.valueOf(correoUsuario.getText());
        String clave = String.valueOf(claveUsuario.getText());

        if(datosValidos(nombre,correo,clave)){
            TransferUsuario crearUsuario = new TransferUsuario();
            crearUsuario.setNombre(nombre);
            crearUsuario.setAvatar("");
            crearUsuario.setColor("AS_theme_azul");
            crearUsuario.setPuntuacion(0);
            crearUsuario.setPuntuacionAnterior(0);
            crearUsuario.setCorreo(correo);
            
            Controlador.getInstancia().ejecutaComando(ListaComandos.CREAR_USUARIO, crearUsuario);
            Controlador.getInstancia().ejecutaComando(ListaComandos.CARGAR_BBDD, null);
            startService(new Intent(this, ServicioNotificaciones.class));
            
            startActivity(new Intent(this, MainActivity.class));
    }
        /*
        //De momento va a sacar un mensaje y pasara a la main activity
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cuidado!")
                .setMessage("No se ha podido establecer una conexion del usuario " + nombre + "con el correo" + correo +
                        " y la clave " + clave + "con tu tutor asignado. No estas sincronizado, como estamos en " +
                        "testing pasamos directamente a la Main Activity").show();*/


    }

    private boolean datosValidos(String nombre, String correo, String clave) {
        ////////////////////FALTARIA VALIDAR EL CÓDIGO //////////////////////////
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
                        msg, Toast.LENGTH_SHORT);
        errorNombre.show();
    }
}
