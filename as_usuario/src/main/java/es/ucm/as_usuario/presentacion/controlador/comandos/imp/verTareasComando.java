package es.ucm.as_usuario.presentacion.controlador.comandos.imp;

import android.util.Log;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import es.ucm.as_usuario.negocio.factoria.FactoriaSA;
import es.ucm.as_usuario.negocio.suceso.Evento;
import es.ucm.as_usuario.negocio.suceso.SASuceso;
import es.ucm.as_usuario.presentacion.controlador.comandos.Command;
import es.ucm.as_usuario.presentacion.controlador.comandos.exceptions.commandException;

/**
 * Created by Jeffer on 05/03/2016.
 */
public class verTareasComando implements Command {
    @Override
    public Object ejecutaComando(Object datos) throws commandException {
        SASuceso saSuceso= FactoriaSA.getInstancia().nuevoSASuceso();
       //List<Evento> eventosList= saSuceso.consultarEventos();
        List<Evento> eventosList = new ArrayList<>();
        Evento unoSinMas = new Evento();
        unoSinMas.setTextoPregunta("Ir al sitio A");
        unoSinMas.setFechaIni(new Timestamp(123345446));
        Evento unoSinMas2 = new Evento();
        unoSinMas2.setTextoPregunta("Ir al sitio B");
        unoSinMas2.setFechaIni(new Timestamp(987654321));
        eventosList.add(unoSinMas);
        eventosList.add(unoSinMas2);

        Log.d("Info", "ejecutaComando verEventosCommand");
        return eventosList;
    }
}
