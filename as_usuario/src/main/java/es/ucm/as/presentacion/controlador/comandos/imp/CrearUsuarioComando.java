package es.ucm.as.presentacion.controlador.comandos.imp;

import android.util.Log;

import es.ucm.as.negocio.conexion.msg.Mensaje;
import es.ucm.as.negocio.factoria.FactoriaSA;
import es.ucm.as.negocio.usuario.SAUsuario;
import es.ucm.as.negocio.usuario.TransferUsuario;
import es.ucm.as.presentacion.controlador.comandos.Command;
import es.ucm.as.presentacion.controlador.comandos.exceptions.commandException;

/**
 * Created by msalitu on 10/03/2016.
 */
public class CrearUsuarioComando implements Command{
    @Override
    public Object ejecutaComando(Object datos) throws commandException {
        SAUsuario su = FactoriaSA.getInstancia().nuevoSAUsuario();
        su.crearUsuario((TransferUsuario)datos);
        return null;
    }
}
