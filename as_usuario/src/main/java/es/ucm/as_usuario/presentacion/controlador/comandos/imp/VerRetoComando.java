package es.ucm.as_usuario.presentacion.controlador.comandos.imp;

import android.util.Log;

import es.ucm.as_usuario.negocio.factoria.FactoriaSA;
import es.ucm.as_usuario.negocio.suceso.Reto;
import es.ucm.as_usuario.negocio.suceso.SASuceso;
import es.ucm.as_usuario.presentacion.controlador.comandos.Command;
import es.ucm.as_usuario.presentacion.controlador.comandos.exceptions.commandException;

/**
 * Created by msalitu on 06/03/2016.
 */
public class VerRetoComando implements Command {
    @Override
    public Object ejecutaComando(Object datos) throws commandException {
        SASuceso ss= FactoriaSA.getInstancia().nuevoSASuceso();
        Reto reto = ss.verReto();
        return reto;
    }
}
