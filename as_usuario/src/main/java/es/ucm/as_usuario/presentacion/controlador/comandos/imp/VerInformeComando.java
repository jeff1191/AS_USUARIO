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
        List<Tarea> tareas = new ArrayList<>();
        List<TransferTarea> transferTareas = new ArrayList<>();

        Tarea unaSinMas = new Tarea();
        unaSinMas.setFrecuenciaTarea(Frecuencia.DIARIA);
        unaSinMas.setTextoAlarma("Dale los buenos días a mamá");
        unaSinMas.setTextoPregunta("¿Le has dado los buenos días a mamá?");
        unaSinMas.setFechaIni(new Timestamp(123345446));
        unaSinMas.setContador(0);
        unaSinMas.setHoraAlarma(new Timestamp(2255));
        unaSinMas.setHoraPregunta(new Timestamp(2266));
        Tarea unaSinMas2 = new Tarea();
        unaSinMas2.setFrecuenciaTarea(Frecuencia.SEMANAL);
        unaSinMas2.setTextoAlarma("Meter las cosas de clase en la mochila");
        unaSinMas2.setTextoPregunta("¿Has metido las cosas de clase en la mochila?");
        unaSinMas2.setFechaIni(new Timestamp(987654321));
        unaSinMas2.setContador(0);
        unaSinMas2.setHoraAlarma(new Timestamp(2255));
        unaSinMas2.setHoraPregunta(new Timestamp(2266));
        tareas.add(unaSinMas);
        tareas.add(unaSinMas2);

        TransferTarea unoSinMas = new TransferTarea();
        unoSinMas.setFrecuenciaTarea(Frecuencia.DIARIA);
        unoSinMas.setTextoAlarma("Dale los buenos días a mamá");
        unoSinMas.setTextoPregunta("¿Le has dado los buenos días a mamá?");
        unoSinMas.setFechaIni(new Timestamp(123345446));
        unoSinMas.setContador(0);
        unoSinMas.setHoraAlarma(new Timestamp(2255));
        unoSinMas.setHoraPregunta(new Timestamp(2266));
        TransferTarea unoSinMas2 = new TransferTarea();
        unoSinMas2.setFrecuenciaTarea(Frecuencia.SEMANAL);
        unoSinMas2.setTextoAlarma("Meter las cosas de clase en la mochila");
        unoSinMas2.setTextoPregunta("¿Has metido las cosas de clase en la mochila?");
        unoSinMas2.setFechaIni(new Timestamp(987654321));
        unoSinMas2.setContador(0);
        unoSinMas2.setHoraAlarma(new Timestamp(2255));
        unoSinMas2.setHoraPregunta(new Timestamp(2266));
        transferTareas.add(unoSinMas);
        transferTareas.add(unoSinMas2);
    //*/
        return transferTareas;
    }
}
