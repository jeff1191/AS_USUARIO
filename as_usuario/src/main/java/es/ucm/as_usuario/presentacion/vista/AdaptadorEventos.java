package es.ucm.as_usuario.presentacion.vista;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import es.ucm.as_usuario.R;

/**
 * Created by Jeffer on 14/04/2016.
 */
public class AdaptadorEventos extends BaseAdapter {

    private ArrayList<String> nombresUsuarios = new ArrayList<String>();
    private ArrayList<Boolean> usuariosActivos = new ArrayList<Boolean>();
    private LayoutInflater inflater;

    public AdaptadorEventos(Activity context) {
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void addItem(final String item, boolean activado, int i) {
        nombresUsuarios.add(item);
        //itemSelection[i]=activado;
        usuariosActivos.add(i,activado);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return nombresUsuarios.size();
    }
    public ArrayList<Boolean> getActivos() {
        return usuariosActivos;
    }

    @Override
    public String getItem(int position) {
        return nombresUsuarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.list_row_evento, null);
        final ViewHolder holder = new ViewHolder();
        holder.chkItem = (CheckBox)convertView.findViewById(R.id.chkItem);
        TextView nombreUsuario = (TextView) convertView.findViewById(R.id.nombreEvento);

        holder.chkItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                usuariosActivos.set(position, holder.chkItem.isChecked());
            }
        });
        holder.chkItem.setChecked(usuariosActivos.get(position));
        convertView.setTag(holder);
        nombreUsuario.setText(getItem(position));

        return convertView;
    }

    public int getItemsLength() {
        if(usuariosActivos == null) return 0;
        return usuariosActivos.size();
    }

    public void cambiaCheck(int i) {
        usuariosActivos.set(i,!usuariosActivos.get(i));
        notifyDataSetChanged();
    }

    public void setDatos(ArrayList<String> usuarioEventos) {
        nombresUsuarios=usuarioEventos;
        notifyDataSetChanged();
    }

    public void setDatosCheck(ArrayList<Boolean> activos) {
        usuariosActivos=activos;
        notifyDataSetChanged();
    }


    public static class ViewHolder {
        public CheckBox chkItem;
    }
}