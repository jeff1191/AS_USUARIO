package es.ucm.as_usuario.presentacion.controlador.imp;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import es.ucm.as_usuario.R;
import es.ucm.as_usuario.negocio.base_datos.Contexto;
import es.ucm.as_usuario.negocio.tarea.Evento;
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

               // ListView listaEventos = (ListView) Contexto.getInstancia().getActividadPrincipal().findViewById(R.id.eventos).findViewById(R.id.listViewEventos);
                View listaEventos = Contexto.getInstancia().getActividadPrincipal().findViewById(R.id.eventos);
               //listaEventos.set
                TextView texxx=(TextView) Contexto.getInstancia().getActividadPrincipal().findViewById(R.id.tituloEventos);
                TextView a=(TextView)listaEventos.findViewById(R.id.tituloEventos);
                //TextView a=(TextView)listaEventos.findViewById(listaEventos.getId());
                texxx.setText("HOHOHOOHOHO");
                //         a.chan
                List<Evento> eventosModelo= (List<Evento>) datos;
                Log.d("Info","LLEGA: " + eventosModelo.get(0).getTextoPregunta());
                 String[] sistemas = {"Ubuntu", "Android", "iOS", "Windows", "Mac OSX",
                        "Google Chrome OS", "Debian", "Mandriva", "Solaris", "Unix"};

                ArrayAdapter<String> adaptador = new ArrayAdapter<String>(Contexto.getInstancia().getActividadPrincipal().getApplicationContext(), android.R.layout.simple_list_item_1, sistemas);
             //   listaEventos.setAdapter(adaptador);
                Log.d("Info", "Saliendo de verEventos" );
                break;
        }

    }
}
