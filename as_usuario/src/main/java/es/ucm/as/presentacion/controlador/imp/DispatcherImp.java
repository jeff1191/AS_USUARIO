package es.ucm.as.presentacion.controlador.imp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


import es.ucm.as.negocio.conexion.msg.Mensaje;
import es.ucm.as.negocio.suceso.TransferEvento;
import es.ucm.as.negocio.suceso.TransferReto;
import es.ucm.as.negocio.suceso.TransferTarea;
import es.ucm.as.negocio.usuario.TransferUsuario;
import es.ucm.as.presentacion.controlador.Dispatcher;
import es.ucm.as.presentacion.controlador.ListaComandos;
import es.ucm.as.presentacion.notificaciones.ServicioNotificaciones;
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
                intentConfiguracion.setFlags(intentConfiguracion.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_TASK);
                Contexto.getInstancia().getContext().startActivity(intentConfiguracion);
                break;

            case ListaComandos.CONSULTAR_USUARIO:
                TransferUsuario transferUsuario = (TransferUsuario)datos;
                Intent consultarUsuario = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), MainActivity.class);
                consultarUsuario.putExtra("usuario", transferUsuario);
                consultarUsuario.setFlags(consultarUsuario.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_TASK );
                Contexto.getInstancia().getContext().startActivity(consultarUsuario);
                break;

            case ListaComandos.HAY_USUARIO:
                Boolean usuario = (Boolean) datos;
                Intent hayUsuario = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), Decision.class);
                hayUsuario.putExtra("usuario", usuario);
                hayUsuario.setFlags(hayUsuario.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_TASK);
                Contexto.getInstancia().getContext().startActivity(hayUsuario);
                break;

            case ListaComandos.SINCRONIZAR:
                Mensaje info = (Mensaje) datos;
                if(info != null) {
                    Intent intentSincro = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), MainActivity.class);
                    intentSincro.putExtra("usuario", info.getUsuario());
                    intentSincro.setFlags(intentSincro.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_TASK);
                    Toast toast =
                            Toast.makeText(Contexto.getInstancia().getContext().getApplicationContext(),
                                    "Sincronización correcta", Toast.LENGTH_LONG);
                    toast.show();

                    // Si la sincronizacion ha sido correcta se relanza el servicio de notificaciones
                    Log.e("DISPATCHER", "SE VA A REINICIAR_SERVICIO_NOTIFICACIONES comando");
                    Intent service = new Intent(Contexto.getInstancia().getContext(),  ServicioNotificaciones.class);
                    service.putExtra("info", info);
                    Contexto.getInstancia().getContext().stopService(service);
                    Contexto.getInstancia().getContext().startService(service);

                    // Se vuelve a la MainActivity
                    Contexto.getInstancia().getContext().startActivity(intentSincro);
                }else{
                    Toast errorNombre =
                            Toast.makeText(Contexto.getInstancia().getContext().getApplicationContext(),
                                    "Error en la sincronización", Toast.LENGTH_LONG);
                    errorNombre.show();
                }
                break;

            case ListaComandos.SINCRONIZAR_REGISTRO:
                Mensaje infoRegistro = (Mensaje) datos;
                if(infoRegistro != null) {
                    Intent intentSincroPrim = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), Registro.class);
                    intentSincroPrim.putExtra("usuario", infoRegistro.getUsuario());
                    intentSincroPrim.setFlags(intentSincroPrim.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);

                    Log.e("DISPATCHER", "SE VA A INICIAR_SERVICIO_NOTIFICACIONES Regisrtro");
                    Intent serviceRegistro = new Intent(Contexto.getInstancia().getContext(),  ServicioNotificaciones.class);
                    serviceRegistro.putExtra("info", infoRegistro);
                    Contexto.getInstancia().getContext().stopService(serviceRegistro);
                    Contexto.getInstancia().getContext().startService(serviceRegistro);

                    Toast toast =
                            Toast.makeText(Contexto.getInstancia().getContext().getApplicationContext(),
                                    "Registro completado correctamente", Toast.LENGTH_LONG);
                    toast.show();

                    // Se vuelve a la clase registro para que compruebe si la creacion fue bien
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
                intentAyuda.setFlags(intentAyuda.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                Contexto.getInstancia().getContext().startActivity(intentAyuda);
                break;

            // Reto

            case ListaComandos.VER_RETO:
                Intent intentR = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), VerReto.class);
                TransferReto transferReto = (TransferReto)datos;
                intentR.putExtra("reto", transferReto);
                intentR.setFlags(intentR.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
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
                intentTareas.setFlags(intentTareas.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY |Intent.FLAG_ACTIVITY_NEW_TASK);
                Contexto.getInstancia().getContext().startActivity(intentTareas);
                break;


            // Eventos

            case ListaComandos.MODIFICAR_EVENTOS:
                Intent iGuardarEvento = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), MainActivity.class);
                iGuardarEvento.setFlags(iGuardarEvento.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
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
                intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                Contexto.getInstancia().getContext().startActivity(intent);
                break;
        }
    }
}
