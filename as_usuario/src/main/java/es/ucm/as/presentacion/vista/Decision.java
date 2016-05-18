package es.ucm.as.presentacion.vista;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import es.ucm.as.negocio.suceso.TransferReto;
import es.ucm.as.negocio.usuario.TransferUsuario;
import es.ucm.as.presentacion.controlador.Controlador;
import es.ucm.as.presentacion.controlador.ListaComandos;
import es.ucm.as.presentacion.controlador.comandos.Command;
import es.ucm.as.presentacion.controlador.comandos.exceptions.commandException;
import es.ucm.as.presentacion.controlador.comandos.factoria.FactoriaComandos;

/**
 * Created by msalitu on 17/03/2016.
 */
public class Decision extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Se guarda el contexto en el singleton
        Contexto.getInstancia().setContext(this);

        // Se ejecuta el comando que comprueba si hay un usuario existente en la BBDD
        Controlador.getInstancia().ejecutaComando(ListaComandos.HAY_USUARIO, null);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            // Existe usuario en BBDD -> Consultar usuario y MainActivity
            if (bundle.getBoolean("usuario"))
                Controlador.getInstancia().ejecutaComando(ListaComandos.CONSULTAR_USUARIO, null);

                // No existe usuario en BBDD -> Registro
            else {
                Intent intent = new Intent().setClass(Decision.this, Registro.class);
                intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        }
        finish();
    }
}
