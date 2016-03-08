package es.ucm.as_usuario.presentacion.controlador.comandos.imp;

import android.util.Log;

import es.ucm.as_usuario.negocio.factoria.FactoriaSA;
import es.ucm.as_usuario.negocio.usuario.SAUsuario;
import es.ucm.as_usuario.negocio.usuario.imp.SAUsuarioImp;
import es.ucm.as_usuario.presentacion.controlador.comandos.Command;
import es.ucm.as_usuario.presentacion.controlador.comandos.exceptions.commandException;

/**
 * Created by msalitu on 08/03/2016.
 */
public class VerAyudaComando implements Command {
    @Override
    public Object ejecutaComando(Object datos) throws commandException {
        return datos;
    }
}
