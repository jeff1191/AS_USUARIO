package es.ucm.as.presentacion.vista;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import es.ucm.as.R;
import es.ucm.as.negocio.suceso.TransferEvento;
import es.ucm.as.presentacion.controlador.Controlador;
import es.ucm.as.presentacion.controlador.ListaComandos;

/**
 * Clase para que se vean los activity_eventos temporales
 *
 */
public class VerEventos  extends Activity{
    private ListView listaEventos;
    private TextView titulo;
    private Button guardarCambios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        cargarTema();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);
        listaEventos = (ListView)findViewById(R.id.listViewEventos);
        titulo = (TextView)findViewById(R.id.tituloEventos);
        guardarCambios = (Button)findViewById(R.id.guardarEvento);
        Bundle bundle = getIntent().getExtras();

        if(bundle.getStringArrayList("listaEventos") != null){
            ArrayList<String> listaE = bundle.getStringArrayList("listaEventos");
            final ArrayList<Integer> listaIds = bundle.getIntegerArrayList("listaIds");
            ArrayList<String> listaAsistencia = bundle.getStringArrayList("listaAsistencia");
            final ArrayList<Boolean> asistencia= new ArrayList<>();

            for(int i=0; i < listaE.size(); i++) {
                 if(listaAsistencia.get(i).equals("SI"))
                        asistencia.add(true);
                 else
                        asistencia.add(false);
            }

            if(listaE.isEmpty()){
                TextView noHay = (TextView) findViewById(R.id.noHay);
                listaEventos.setVisibility(View.INVISIBLE);
                noHay.setText("No tienes ningún evento");
                noHay.setTextColor(Color.GRAY);
                Button evento = (Button) findViewById(R.id.evento);
                Button voy = (Button) findViewById(R.id.voy);
                guardarCambios.setEnabled(false);
                guardarCambios.setVisibility(View.INVISIBLE);
                evento.setVisibility(View.INVISIBLE);
                voy.setVisibility(View.INVISIBLE);
            }
            else{
                guardarCambios.setActivated(true);
                titulo.setText("Próximos eventos");
                final AdaptadorEventos adaptador = new AdaptadorEventos(this);
                adaptador.setDatos(listaE);
                adaptador.setDatosCheck(asistencia);
                listaEventos.setAdapter(adaptador);


                //Check
                listaEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        adaptador.cambiaCheck(position);
                    }
                });

                //Guardar cambios
                guardarCambios.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<TransferEvento> eventosModificados = new ArrayList<TransferEvento>();
                        for(int i=0; i < listaIds.size();i++){
                            TransferEvento eGuardar = new TransferEvento();
                            eGuardar.setId(listaIds.get(i));
                            if(adaptador.getActivos().get(i))
                                eGuardar.setAsistencia("SI");
                            else
                                eGuardar.setAsistencia("NO");
                            eventosModificados.add(eGuardar);
                        }
                    Controlador.getInstancia().ejecutaComando(ListaComandos.MODIFICAR_EVENTOS,eventosModificados);
                    }
                });
            }
        }
    }

    public void volver(View v){
       finish();
    }

    public void ayuda(View v){
        Controlador.getInstancia().ejecutaComando(ListaComandos.AYUDA, "activity_eventos");
    }


    public void cargarTema(){
        switch (Configuracion.temaActual){
            case "AS_theme_azul":
                setTheme(R.style.AS_tema_azul);
                break;
            case "AS_theme_rojo":
                setTheme(R.style.AS_tema_rojo);
                break;
            case "AS_theme_rosa":
                setTheme(R.style.AS_tema_rosa);
                break;
            case "AS_theme_verde":
                setTheme(R.style.AS_tema_verde);
                break;
            case "AS_theme_negro":
                setTheme(R.style.AS_tema_negro);
                break;
        }
    }
}
