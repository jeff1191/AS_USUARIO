package es.ucm.as_usuario.presentacion.controlador.comandos.imp;

import android.util.Log;

import es.ucm.as_usuario.negocio.factoria.FactoriaSA;
import es.ucm.as_usuario.negocio.usuario.SAUsuario;
import es.ucm.as_usuario.negocio.usuario.TransferUsuario;
import es.ucm.as_usuario.presentacion.controlador.comandos.Command;
import es.ucm.as_usuario.presentacion.controlador.comandos.exceptions.commandException;

/**
 * Created by msalitu on 11/03/2016.
 */
public class ConsultarUsuarioComando implements Command {
    @Override
    public Object ejecutaComando(Object datos) throws commandException {
        SAUsuario su = FactoriaSA.getInstancia().nuevoSAUsuario();
        TransferUsuario transferUsuario = su.consultarUsuario();
        return transferUsuario;
    }
}
