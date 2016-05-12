package es.ucm.as.presentacion.controlador.comandos.imp;

import android.util.Log;
import android.widget.Toast;

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
import es.ucm.as.presentacion.vista.Contexto;

/**
 * Created by msalitu on 10/03/2016.
 */
public class SincronizarComando implements Command{
    @Override
    public Object ejecutaComando(Object datos) throws commandException {

        boolean terminado = false;
        Mensaje msg = (Mensaje) datos;
        ConectionManager conectionManager;

        if(msg == null) {

            SAUsuario saUsuario = FactoriaSA.getInstancia().nuevoSAUsuario();
            TransferUsuario usuario = saUsuario.consultarUsuario();

            SASuceso saSuceso = FactoriaSA.getInstancia().nuevoSASuceso();
            List<TransferTarea> tareas = saSuceso.consultarTareas();

            List<TransferEvento> eventos = saSuceso.consultarEventos();

            TransferReto reto = saSuceso.consultarReto();

            Mensaje mensajeEnvio = new Mensaje(usuario, reto, eventos, tareas);

            conectionManager = new ConectionManager(mensajeEnvio);

        }else {
            conectionManager = new ConectionManager(msg);
            Log.e("prueba","Codigo envio -> "+ msg.getUsuario().getCodigoSincronizacion());
        }

        if(conectionManager.getResponse() != null) {

            if (conectionManager.getResponse().getUsuario() != null) {
                if (conectionManager.getResponse().getUsuario().getNombre() != null)
                    Log.e("prueba", "respuesta 2 -> " + conectionManager.getResponse().getUsuario().getNombre());
                else
                    Log.e("prueba", "transfer nombre nulo");
            }

            // Se procesa cada parte del mensaje
            Mensaje respuestaTutor = conectionManager.getResponse();
            sincronizarReto(respuestaTutor.getReto());
            sincronizarTareas(respuestaTutor.getTareas());
            sincronizarEventos(respuestaTutor.getEventos());

            //Fin sincronización correcta
            terminado = conectionManager.getResponse() != null;
            conectionManager.reset();
            Log.e("sync", "Sincronizacion acabada");
            Toast toast1 =
                    Toast.makeText(Contexto.getInstancia().getContext().getApplicationContext(),
                            "Sincronización correcta", Toast.LENGTH_LONG);
            toast1.show();
        }
        else{
            //Fin sincronización erronea
            Log.e("pruebaaa", "respuesta nula");
            Toast toast1 =
                    Toast.makeText(Contexto.getInstancia().getContext().getApplicationContext(),
                            "Error en la sincronización. Debes estar en la misma red WiFi que tu " +
                                    "profesor", Toast.LENGTH_LONG);
            toast1.show();
        }

        return terminado;
    }


    private void sincronizarReto(TransferReto retoTutor){
        TransferReto retoActual =  FactoriaSA.getInstancia().nuevoSASuceso().consultarReto();

        // Llega un reto desde el tutor
        if(retoTutor != null){
            if(retoActual != null){

                // Si se detecta algun cambio entre el reto que manda el tutor y el del usuario
                // se machaca el reto del usuario
                if (!retoTutor.getTexto().equals(retoActual.getTexto()) ||
                        !retoTutor.getPremio().equals(retoActual.getPremio())){
                    retoActual.setTexto(retoTutor.getTexto());
                    retoActual.setPremio(retoTutor.getPremio());
                    retoActual.setContador(0);
                    retoActual.setSuperado(false);
                    FactoriaSA.getInstancia().nuevoSASuceso().gestionarReto(retoActual);
                }

            } else{
                //Si no tiene un reto: A crear!!
                retoActual = new TransferReto();
                retoActual.setPremio(retoTutor.getPremio());
                retoActual.setTexto(retoTutor.getTexto());
                retoActual.setContador(0);
                retoActual.setSuperado(false);
                FactoriaSA.getInstancia().nuevoSASuceso().gestionarReto(retoActual);
            }

            // Si no llega reto del tutor hay que borrar el del usuario si lo hubiera
        } else if(retoTutor == null && retoActual != null){
            FactoriaSA.getInstancia().nuevoSASuceso().eliminarReto();
        }

    }


    private void sincronizarTareas(List<TransferTarea> tareas){

    }


    private void sincronizarEventos(List<TransferEvento> eventos) {
        FactoriaSA.getInstancia().nuevoSASuceso().crearEventos(eventos);
    }

}
