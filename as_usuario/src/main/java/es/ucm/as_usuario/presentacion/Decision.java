package es.ucm.as_usuario.presentacion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import es.ucm.as_usuario.negocio.usuario.TransferUsuario;
import es.ucm.as_usuario.presentacion.controlador.ListaComandos;
import es.ucm.as_usuario.presentacion.controlador.comandos.Command;
import es.ucm.as_usuario.presentacion.controlador.comandos.exceptions.commandException;
import es.ucm.as_usuario.presentacion.controlador.comandos.factoria.FactoriaComandos;

/**
 * Created by msalitu on 17/03/2016.
 */
public class Decision extends Activity {

    boolean nuevoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Contexto.getInstancia().setContext(this);

        Command c = FactoriaComandos.getInstancia().getCommand(ListaComandos.CONSULTAR_USUARIO);
        TransferUsuario cargarUsuario = new TransferUsuario();
        try {
            cargarUsuario = (TransferUsuario) c.ejecutaComando(null);

            if (cargarUsuario==null)
                nuevoUsuario = true;
            else
                nuevoUsuario = false;

        } catch (commandException e) {
            e.printStackTrace();
        }

        if (nuevoUsuario) {
            Intent intent = new Intent().setClass(Decision.this, Registro.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent().setClass(Decision.this, MainActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
