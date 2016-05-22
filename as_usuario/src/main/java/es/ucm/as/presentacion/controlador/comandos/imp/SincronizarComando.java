package es.ucm.as.presentacion.controlador.comandos.imp;

import java.util.List;

import es.ucm.as.negocio.conexion.ConectionManager;
import es.ucm.as.negocio.conexion.msg.Mensaje;
import es.ucm.as.negocio.factoria.FactoriaSA;
import es.ucm.as.negocio.suceso.SASuceso;
import es.ucm.as.negocio.suceso.TransferEvento;
import es.ucm.as.negocio.suceso.TransferReto;
import es.ucm.as.negocio.suceso.TransferTarea;
import es.ucm.as.negocio.usuario.SAUsuario;
import es.ucm.as.negocio.usuario.TransferUsuario;
import es.ucm.as.presentacion.controlador.comandos.Command;
import es.ucm.as.presentacion.controlador.comandos.exceptions.commandException;

/**
 * Created by msalitu on 10/03/2016.
 */
public class SincronizarComando implements Command{
    @Override
    public Object ejecutaComando(Object datos) throws commandException {

        boolean terminado = false;
        ConectionManager conectionManager;

        // Se crea el mensaje consultando la info en BBDD
        SAUsuario saUsuario = FactoriaSA.getInstancia().nuevoSAUsuario();
        TransferUsuario usuario = saUsuario.consultarUsuario();

        SASuceso saSuceso = FactoriaSA.getInstancia().nuevoSASuceso();
        List<TransferTarea> tareas = saSuceso.consultarTareas();

        List<TransferEvento> eventos = saSuceso.consultarEventos();
        TransferReto reto = saSuceso.consultarReto();

        Mensaje mensajeEnvio = new Mensaje(usuario, reto, eventos, tareas);

        conectionManager = new ConectionManager(mensajeEnvio);

        // Se procesa la respuesta de la aplicacion tutor
        if(conectionManager.getResponse() != null) {

            Mensaje respuestaTutor = conectionManager.getResponse();
            saSuceso.cargarReto(respuestaTutor.getReto());
            saSuceso.cargarTareas(respuestaTutor.getTareas());
            saSuceso.crearEventos(respuestaTutor.getEventos());

            terminado = conectionManager.getResponse() != null;
            conectionManager.reset();
        }

        if (terminado){
            Mensaje info = new Mensaje();
            info.setUsuario(saUsuario.consultarUsuario());
            info.setTareas(saSuceso.consultarTareasNotificaciones());
            info.setReto(saSuceso.consultarReto());
            info.setEventos(saSuceso.consultarEventosNotificaciones());
            return info;
        }
        else
            return null;
    }

}
