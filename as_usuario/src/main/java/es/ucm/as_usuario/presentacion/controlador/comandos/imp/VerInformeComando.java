package es.ucm.as_usuario.presentacion.controlador.comandos.imp;

import android.util.Log;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import es.ucm.as_usuario.negocio.factoria.FactoriaSA;
import es.ucm.as_usuario.negocio.suceso.Evento;
import es.ucm.as_usuario.negocio.suceso.SASuceso;
import es.ucm.as_usuario.negocio.suceso.Tarea;
import es.ucm.as_usuario.negocio.suceso.TransferTarea;
import es.ucm.as_usuario.negocio.utils.Frecuencia;
import es.ucm.as_usuario.presentacion.controlador.comandos.Command;
import es.ucm.as_usuario.presentacion.controlador.comandos.exceptions.commandException;

/**
 * Created by Jeffer on 05/03/2016.
 */
public class VerInformeComando implements Command {
    @Override
    public Object ejecutaComando(Object datos) throws commandException {
        SASuceso saSuceso= FactoriaSA.getInstancia().nuevoSASuceso();
       //List<TransferTarea> tareas= saSuceso.consultarTareas();

        // Esto no hay que hacerlo porque lo coge de la BBDD
        List<TransferTarea> tareas = new ArrayList<>();
        TransferTarea unoSinMas = new TransferTarea();
        unoSinMas.setFrecuenciaTarea(Frecuencia.DIARIA);
        unoSinMas.setTextoAlarma("Dale los buenos días a mamá");
        unoSinMas.setTextoPregunta("¿Le has dado los buenos días a mamá?");
        unoSinMas.setFechaIni(new Timestamp(123345446));
        TransferTarea unoSinMas2 = new TransferTarea();
        unoSinMas2.setFrecuenciaTarea(Frecuencia.SEMANAL);
        unoSinMas2.setTextoAlarma("Meter las cosas de clase en la mochila");
        unoSinMas2.setTextoPregunta("¿Has metido las cosas de clase en la mochila?");
        unoSinMas2.setFechaIni(new Timestamp(987654321));
        tareas.add(unoSinMas);
        tareas.add(unoSinMas2);
    //*/

        return tareas;
    }
}
