package es.ucm.as_usuario.presentacion.controlador.comandos.imp;

import android.util.Log;

import es.ucm.as_usuario.negocio.factoria.FactoriaSA;
import es.ucm.as_usuario.negocio.suceso.SASuceso;
import es.ucm.as_usuario.presentacion.controlador.comandos.Command;
import es.ucm.as_usuario.presentacion.controlador.comandos.exceptions.commandException;

/**
 * Created by msalitu on 14/03/2016.
 */
public class CargarBBDDComando implements Command {
    @Override
    public Object ejecutaComando(Object datos) throws commandException {
        SASuceso saSuceso = FactoriaSA.getInstancia().nuevoSASuceso();
        saSuceso.cargarTareasBBDD();
        saSuceso.cargarRetoBBDD();
        saSuceso.cargarEventosBBDD();
        return null;
    }
}
