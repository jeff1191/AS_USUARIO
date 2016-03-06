package es.ucm.as_usuario.presentacion.controlador.imp;

import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import es.ucm.as_usuario.presentacion.Contexto;
import es.ucm.as_usuario.negocio.suceso.Evento;
import es.ucm.as_usuario.presentacion.VerEventos;
import es.ucm.as_usuario.presentacion.controlador.Dispatcher;
import es.ucm.as_usuario.presentacion.controlador.ListaComandos;

/**
 * Created by Jeffer on 02/03/2016.
 */
public class DispatcherImp extends Dispatcher{
    @Override
    public void dispatch(String accion, Object datos) {

        switch(accion){
            case ListaComandos.VER_EVENTOS:
                Intent intent = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), VerEventos.class);
                List<Evento> eventosModelo= (List<Evento>) datos;
                Log.d("Info", "LLEGA: " + eventosModelo.get(0).getTextoPregunta());
                ArrayList<String> listaActividad= new ArrayList<String>();

                for(int i=0; i < eventosModelo.size(); i++){
                   // eventosModelo.get(i).getFechaIni()
                    String addEvento= eventosModelo.get(i).getTextoPregunta() + " a las " + eventosModelo.get(i).getFechaIni();
                    listaActividad.add(addEvento);
                }
                intent.putStringArrayListExtra("listaEventos", listaActividad);
                Contexto.getInstancia().getContext().startActivity(intent);
                break;
        }

    }
}
