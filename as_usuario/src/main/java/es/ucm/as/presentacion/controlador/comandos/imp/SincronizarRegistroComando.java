package es.ucm.as.presentacion.controlador.comandos.imp;

import es.ucm.as.negocio.conexion.ConectionManager;
import es.ucm.as.negocio.conexion.msg.Mensaje;
import es.ucm.as.negocio.factoria.FactoriaSA;
import es.ucm.as.negocio.suceso.SASuceso;
import es.ucm.as.presentacion.controlador.comandos.Command;
import es.ucm.as.presentacion.controlador.comandos.exceptions.commandException;

/**
 * Created by msalitu on 10/03/2016.
 */
public class SincronizarRegistroComando implements Command{
    @Override
    public Object ejecutaComando(Object datos) throws commandException {

        boolean terminado = false;
        Mensaje msg = (Mensaje) datos;
        ConectionManager conectionManager;
        conectionManager = new ConectionManager(msg);
        SASuceso saSuceso = FactoriaSA.getInstancia().nuevoSASuceso();

        if(conectionManager.getResponse() != null) {
            // Se procesa cada parte del mensaje
            Mensaje respuestaTutor = conectionManager.getResponse();
            saSuceso.cargarReto(respuestaTutor.getReto());
            saSuceso.cargarTareas(respuestaTutor.getTareas());
            saSuceso.crearEventos(respuestaTutor.getEventos());

            terminado = conectionManager.getResponse() != null;
            conectionManager.reset();
        }

        if(terminado) {
            Mensaje info = new Mensaje();
            info.setUsuario(msg.getUsuario());
            info.setTareas(saSuceso.consultarTareasNotificaciones());
            info.setReto(saSuceso.consultarReto());
            info.setEventos(saSuceso.consultarEventosNotificaciones());
            return info;
        }
        else
            return null;
    }
}
