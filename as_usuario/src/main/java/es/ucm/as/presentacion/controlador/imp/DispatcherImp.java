package es.ucm.as.presentacion.controlador.imp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.CertPathTrustManagerParameters;

import es.ucm.as.negocio.suceso.TransferEvento;
import es.ucm.as.negocio.suceso.TransferReto;
import es.ucm.as.negocio.suceso.TransferTarea;
import es.ucm.as.negocio.usuario.TransferUsuario;
import es.ucm.as.presentacion.controlador.Dispatcher;
import es.ucm.as.presentacion.controlador.ListaComandos;
import es.ucm.as.presentacion.vista.Ayuda;
import es.ucm.as.presentacion.vista.Configuracion;
import es.ucm.as.presentacion.vista.Contexto;
import es.ucm.as.presentacion.vista.Decision;
import es.ucm.as.presentacion.vista.MainActivity;
import es.ucm.as.presentacion.vista.Registro;
import es.ucm.as.presentacion.vista.VerEventos;
import es.ucm.as.presentacion.vista.VerInforme;
import es.ucm.as.presentacion.vista.VerReto;

/**
 * Created by Jeffer on 02/03/2016.
 */
public class DispatcherImp extends Dispatcher{


    @Override
    public void dispatch(String accion, Object datos) {

        switch(accion){

            // Usuario

            case ListaComandos.CONFIGURACION:
                Intent intentConfiguracion = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), Configuracion.class);
                TransferUsuario conf = (TransferUsuario) datos;
                intentConfiguracion.putExtra("usuario", conf);
                Contexto.getInstancia().getContext().startActivity(intentConfiguracion);
                break;

            case ListaComandos.CONSULTAR_USUARIO:
                TransferUsuario transferUsuario = (TransferUsuario)datos;
                Intent consultarUsuario = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), MainActivity.class);
                consultarUsuario.putExtra("usuario", transferUsuario);
                Contexto.getInstancia().getContext().startActivity(consultarUsuario);
                break;

            case ListaComandos.HAY_USUARIO:
                Boolean usuario = (Boolean) datos;
                Intent hayUsuario = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), Decision.class);
                hayUsuario.putExtra("usuario", usuario);
                Contexto.getInstancia().getContext().startActivity(hayUsuario);
                break;

            case ListaComandos.SINCRONIZAR:
                TransferUsuario terminado = (TransferUsuario) datos;
                if(terminado != null) {
                    Intent intentSincro = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), MainActivity.class);
                    intentSincro.putExtra("usuario", terminado);
                    Toast toast =
                            Toast.makeText(Contexto.getInstancia().getContext().getApplicationContext(),
                                    "Sincronización correcta", Toast.LENGTH_LONG);
                    toast.show();
                    Contexto.getInstancia().getContext().startActivity(intentSincro);
                }else{
                    Toast errorNombre =
                            Toast.makeText(Contexto.getInstancia().getContext().getApplicationContext(),
                                    "Error en la sincronización", Toast.LENGTH_LONG);
                    errorNombre.show();
                }
                break;

            case ListaComandos.SINCRONIZAR_REGISTRO:
                TransferUsuario terminadoPrim = (TransferUsuario) datos;
                if(terminadoPrim != null) {
                    Intent intentSincroPrim = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), Registro.class);
                    intentSincroPrim.putExtra("usuario", terminadoPrim);
                    Toast toast =
                            Toast.makeText(Contexto.getInstancia().getContext().getApplicationContext(),
                                    "Registro completado correctamente", Toast.LENGTH_LONG);
                    toast.show();
                    Contexto.getInstancia().getContext().startActivity(intentSincroPrim);
                }else{
                    Toast errorNombre =
                            Toast.makeText(Contexto.getInstancia().getContext().getApplicationContext(),
                                    "Error en la sincronización. Debes estar en la misma red WiFi que " +
                                            "tu profesor o la clave es incorrecta", Toast.LENGTH_LONG);
                    errorNombre.show();
                }
                break;

            // Ayuda

            case ListaComandos.AYUDA:
                Intent intentAyuda = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), Ayuda.class);
                intentAyuda.putExtra("pantalla", (String)datos);
                Contexto.getInstancia().getContext().startActivity(intentAyuda);
                break;

            // Reto

            case ListaComandos.VER_RETO:
                Intent intentR = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), VerReto.class);
                TransferReto transferReto = (TransferReto)datos;
                intentR.putExtra("reto", transferReto);
                Contexto.getInstancia().getContext().startActivity(intentR);
                break;

            // Tareas

            case ListaComandos.VER_INFORME:
                Intent intentTareas = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), VerInforme.class);
                ArrayList<Object> a = (ArrayList<Object>)datos;
                TransferUsuario tu = (TransferUsuario) a.get(0);

                ArrayList<String> titulos = new ArrayList<String>();
                ArrayList<Integer> si = new ArrayList<Integer>();
                ArrayList<Integer> no = new ArrayList<Integer>();

                for(int j = 1; j < a.size(); j++){
                    TransferTarea tt = (TransferTarea)a.get(j);
                    titulos.add(tt.getTextoAlarma());
                    no.add(tt.getNumNo());
                    si.add(tt.getNumSi());
                }

                intentTareas.putExtra("puntuacion",tu.getPuntuacion() );
                intentTareas.putExtra("puntuacionAnterior", tu.getPuntuacionAnterior());
                intentTareas.putStringArrayListExtra("titulos", titulos);
                intentTareas.putIntegerArrayListExtra("no", no);
                intentTareas.putIntegerArrayListExtra("si", si);
                Contexto.getInstancia().getContext().startActivity(intentTareas);
                break;

            // Eventos

            case ListaComandos.MODIFICAR_EVENTOS:
                Intent iGuardarEvento = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), MainActivity.class);
                Contexto.getInstancia().getContext().startActivity(iGuardarEvento);
                break;

            case ListaComandos.VER_EVENTOS:
                Intent intent = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), VerEventos.class);
                List<TransferEvento> eventosModelo= (List<TransferEvento>) datos;
                ArrayList<String> listaActividad= new ArrayList<String>();
                ArrayList<Integer> listaIds = new ArrayList<Integer>();
                ArrayList<String> listaActivos = new ArrayList<String>();
                SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm");
                for(int i=0; i < eventosModelo.size(); i++){
                    String addEvento= eventosModelo.get(i).getNombre() + " el " + formatFecha.format(eventosModelo.get(i).getFecha()) + " a las " + formatHora.format(eventosModelo.get(i).getHoraEvento());
                    listaActividad.add(addEvento);
                    listaIds.add(eventosModelo.get(i).getId());
                    listaActivos.add(eventosModelo.get(i).getAsistencia());
                }
                Bundle b = new Bundle();
                b.putIntegerArrayList("listaIds", listaIds);
                b.putStringArrayList("listaEventos", listaActividad);
                b.putStringArrayList("listaAsistencia", listaActivos);
                intent.putExtras(b);
                Contexto.getInstancia().getContext().startActivity(intent);
                break;

        }
    }
}
