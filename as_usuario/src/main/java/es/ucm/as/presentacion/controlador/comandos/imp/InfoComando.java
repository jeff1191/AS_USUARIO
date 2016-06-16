package es.ucm.as.presentacion.controlador.comandos.imp;


import java.util.Calendar;
import es.ucm.as.negocio.conexion.msg.Mensaje;
import es.ucm.as.negocio.factoria.FactoriaSA;
import es.ucm.as.negocio.suceso.SASuceso;
import es.ucm.as.negocio.usuario.SAUsuario;
import es.ucm.as.presentacion.controlador.comandos.Command;
import es.ucm.as.presentacion.controlador.comandos.exceptions.commandException;

/**
 * Created by msalitu on 10/03/2016.
 */
public class InfoComando implements Command{
    @Override
    public Object ejecutaComando(Object datos) throws commandException {
        // Se crea el mensaje consultando la info en BBDD
        SAUsuario saUsuario = FactoriaSA.getInstancia().nuevoSAUsuario();
        SASuceso saSuceso = FactoriaSA.getInstancia().nuevoSASuceso();

        Mensaje info = new Mensaje();
        info.setUsuario(saUsuario.consultarUsuario());
        info.setReto(saSuceso.consultarReto());
        info.setEventos(saSuceso.consultarEventosNotificaciones());

        if(Calendar.SATURDAY != Calendar.getInstance().get(Calendar.DAY_OF_WEEK) &&
                Calendar.SUNDAY != Calendar.getInstance().get(Calendar.DAY_OF_WEEK) )
            info.setTareas(saSuceso.consultarTareasNotificaciones());
        else {
            info.setTareas(saSuceso.consultarTareasNotificacionesFinde());

        }
        return info;
    }
}
