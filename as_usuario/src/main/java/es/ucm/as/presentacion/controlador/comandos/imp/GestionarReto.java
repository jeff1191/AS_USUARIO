package es.ucm.as.presentacion.controlador.comandos.imp;

import android.util.Log;

import es.ucm.as.negocio.factoria.FactoriaSA;
import es.ucm.as.negocio.suceso.SASuceso;
import es.ucm.as.negocio.suceso.TransferReto;
import es.ucm.as.presentacion.controlador.comandos.Command;
import es.ucm.as.presentacion.controlador.comandos.exceptions.commandException;

/**
 * Created by Juan Lu on 11/05/2016.
 */
public class GestionarReto implements Command {

    @Override
    public Object ejecutaComando(Object datos) throws commandException {
        SASuceso saSuceso = FactoriaSA.getInstancia().nuevoSASuceso();
        Log.e("sync", "seguimos");
        saSuceso.crearReto((TransferReto) datos);
        return null;
    }
}
