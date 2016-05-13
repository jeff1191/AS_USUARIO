package es.ucm.as.presentacion.vista;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import es.ucm.as.R;
import es.ucm.as.negocio.usuario.TransferUsuario;
import es.ucm.as.presentacion.controlador.Controlador;
import es.ucm.as.presentacion.controlador.ListaComandos;
import es.ucm.as.presentacion.controlador.comandos.Command;
import es.ucm.as.presentacion.controlador.comandos.exceptions.commandException;
import es.ucm.as.presentacion.controlador.comandos.factoria.FactoriaComandos;


public class MainActivity extends Activity {

    private TextView nombrePrincipal;
    private TextView puntuacion;
    private ImageView imagenPerfil;
    private int request_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Controlador.getInstancia().ejecutaComando(ListaComandos.ACTUALIZAR_PUNTUACION, null);
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
        nombrePrincipal.setText(usuario.getNombre());
        puntuacion = (TextView)findViewById(R.id.puntuacionUsuario);
        puntuacion.setText(usuario.getPuntuacion()+"/10");
        imagenPerfil= (ImageView) findViewById(R.id.avatar);
        if(usuario.getAvatar() != null && !usuario.getAvatar().equals(""))
            imagenPerfil.setImageBitmap(BitmapFactory.decodeFile(usuario.getAvatar()));
        else
            imagenPerfil.setImageResource(R.drawable.avatar);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if(bundle.getString("sincronizacion") == null) {
                if (bundle.getString("editarUsuario") != null)
                    nombrePrincipal.setText(bundle.getString("editarUsuario"));
                if (bundle.getString("editarAvatar") != null && !bundle.getString("editarAvatar").equals(""))
                    imagenPerfil.setImageBitmap(BitmapFactory.decodeFile(bundle.getString("editarAvatar")));
            }else{
                if(bundle.getString("sincronizacion").equals("true"))
                    mostrarMensaje("Sincronización correcta");
                else
                    mostrarMensaje("Error en la sincronización. Debes estar en la misma red WiFi que tu profesor");
            }
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
        Controlador.getInstancia().ejecutaComando(ListaComandos.ACTUALIZAR_PUNTUACION, null);
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

    public void sincronizar(View v){
        Toast toast1 =
                Toast.makeText(getApplicationContext(),
                        "Sincronizando...", Toast.LENGTH_LONG);
        toast1.show();

        Controlador.getInstancia().ejecutaComando(ListaComandos.SINCRONIZAR, null);
    }

    private void mostrarMensaje(String msg){
        Toast toast =
                Toast.makeText(getApplicationContext(),
                        msg, Toast.LENGTH_LONG);
        toast.show();
    }
}
