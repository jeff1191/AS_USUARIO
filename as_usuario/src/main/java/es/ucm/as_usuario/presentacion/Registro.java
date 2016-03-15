package es.ucm.as_usuario.presentacion;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import es.ucm.as_usuario.R;

/**
 * Created by Juan Lu on 15/03/2016.
 */
public class Registro extends Activity {

    private EditText nombreUsuario;
    private EditText correoUsuario;
    private EditText claveUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        //De momento va a sacar un mensaje y pasara a la main activity
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cuidado!")
                .setMessage("No se ha podido establecer una conexion del usuario " + nombre + "con el correo" + correo +
                        " y la clave " + clave + "con tu tutor asignado. No estas sincronizado, como estamos en " +
                        "testing pasamos directamente a la Main Activity").show();

        //startActivity(new Intent(this, MainActivity.class));
    }
}
