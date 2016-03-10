package es.ucm.as_usuario.presentacion.controlador.imp;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import es.ucm.as_usuario.negocio.usuario.TransferUsuario;
import es.ucm.as_usuario.negocio.usuario.Usuario;
import es.ucm.as_usuario.presentacion.Configuracion;
import es.ucm.as_usuario.presentacion.Contexto;
import es.ucm.as_usuario.negocio.suceso.Evento;
import es.ucm.as_usuario.presentacion.MainActivity;
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
            case ListaComandos.CONFIGURACION:
                Intent intentConfiguracion = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), Configuracion.class);
                TransferUsuario conf = (TransferUsuario) datos;
                intentConfiguracion.putExtra("nombreConfiguracion", conf.getNombre());
                intentConfiguracion.putExtra("frecuenciaInformeConfiguracion", conf.getFrecuenciaRecibirInforme());
                Contexto.getInstancia().getContext().startActivity(intentConfiguracion);
                break;
            case ListaComandos.EDITAR_USUARIO:
                Intent intentEditarUsuario = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), MainActivity.class);
                TransferUsuario editarUsuario = (TransferUsuario) datos;
                intentEditarUsuario.putExtra("editarUsuario", editarUsuario.getNombre());
                Contexto.getInstancia().getContext().startActivity(intentEditarUsuario);
                break;
        }

    }
}
