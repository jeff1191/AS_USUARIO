package es.ucm.as_usuario.presentacion;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import es.ucm.as_usuario.R;
import es.ucm.as_usuario.negocio.usuario.TransferUsuario;
import es.ucm.as_usuario.presentacion.controlador.Controlador;
import es.ucm.as_usuario.presentacion.controlador.ListaComandos;
import es.ucm.as_usuario.presentacion.controlador.comandos.Command;
import es.ucm.as_usuario.presentacion.controlador.comandos.exceptions.commandException;
import es.ucm.as_usuario.presentacion.controlador.comandos.factoria.FactoriaComandos;


public class MainActivity extends Activity {

    private TextView nombrePrincipal;
    private TextView puntuacion;
    private ImageView imagenPerfil;
    private int request_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Se accede a los datos del usuario de la BBDD
        Command c = FactoriaComandos.getInstancia().getCommand(ListaComandos.CONSULTAR_USUARIO);
        TransferUsuario usuario = new TransferUsuario();
        try {
            usuario = (TransferUsuario) c.ejecutaComando(null);
        } catch (commandException e) {
            e.printStackTrace();
        }

        // Completa los datos del usuario que se muestran en esta pantalla
        Configuracion.temaActual = usuario.getColor();
        cargarTema();
        setContentView(R.layout.activity_main);
        nombrePrincipal=(TextView)findViewById(R.id.nombreUser);
        puntuacion = (TextView)findViewById(R.id.puntuacionUsuario);
        imagenPerfil= (ImageView) findViewById(R.id.avatar);
        if(usuario.getAvatar() != null && !usuario.getAvatar().equals(""))
            imagenPerfil.setImageBitmap(BitmapFactory.decodeFile(usuario.getAvatar()));
        else
            imagenPerfil.setImageResource(R.drawable.avatar);
        puntuacion.setText(usuario.getPuntuacion() + "/10");
        nombrePrincipal.setText(usuario.getNombre());
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString("editarUsuario") != null)
                nombrePrincipal.setText(bundle.getString("editarUsuario"));
            if (bundle.getString("editarAvatar") != null && !bundle.getString("editarAvatar").equals(""))
                imagenPerfil.setImageBitmap(BitmapFactory.decodeFile(bundle.getString("editarAvatar")));
        }


        // Esto es para solventar un error al enviar el correo
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void personalizacion(View v){
        Controlador.getInstancia().ejecutaComando(ListaComandos.CONFIGURACION, null);
    }

    public void ayuda(View v) {
        Controlador.getInstancia().ejecutaComando(ListaComandos.AYUDA, "principal");
    }

    public void verInforme(View v){
        Controlador.getInstancia().ejecutaComando(ListaComandos.GENERAR_PDF, null);
        Controlador.getInstancia().ejecutaComando(ListaComandos.VER_INFORME, null);
    }

    public void verEventos(View v){
       Controlador.getInstancia().ejecutaComando(ListaComandos.VER_EVENTOS, null);
    }

    public void verReto(View v){
        Controlador.getInstancia().ejecutaComando(ListaComandos.VER_RETO, null);
    }

    public void enviarCorreo(View v){
        Controlador.getInstancia().ejecutaComando(ListaComandos.GENERAR_PDF, null);
        Controlador.getInstancia().ejecutaComando(ListaComandos.ENVIAR_CORREO, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == request_code) && (resultCode == RESULT_OK)){
            Bundle bundle = data.getExtras();
            nombrePrincipal.setText(bundle.getString("nombreNuevo"));
        }
    }

    public void cargarTema(){
        switch (Configuracion.temaActual){
            case "AS_theme_azul":
                setTheme(R.style.AS_tema_azul);
                break;
            case "AS_theme_rojo":
                setTheme(R.style.AS_tema_rojo);
                break;
            case "AS_theme_rosa":
                setTheme(R.style.AS_tema_rosa);
                break;
            case "AS_theme_verde":
                setTheme(R.style.AS_tema_verde);
                break;
            case "AS_theme_negro":
                setTheme(R.style.AS_tema_negro);
                break;
        }
    }
}
