package es.ucm.as_usuario.presentacion;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import es.ucm.as_usuario.R;

public class ListViewAdapter extends BaseAdapter {
    // Declare Variables
    Context context;
    String[] titulos;
    Integer[] si;
    Integer[] no;
    LayoutInflater inflater;

    public ListViewAdapter(Context context, String[] titulos, Integer[] si, Integer[] no) {
        this.context = context;
        this.titulos = titulos;
        this.si = si;
        this.no = no;
    }

    @Override
    public int getCount() {
        return titulos.length;
    }

    @Override
    public Object getItem(int position) {
        return titulos[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // Declare Variables
        TextView txtTitle;
        TextView sit;
        TextView not;
        TextView total;
        //ImageView imgImg;

        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.list_row, parent, false);

        // Locate the TextViews in listview_item.xml
        txtTitle = (TextView) itemView.findViewById(R.id.tarea);
        sit = (TextView) itemView.findViewById(R.id.si);
        not = (TextView) itemView.findViewById(R.id.no);
        total = (TextView) itemView.findViewById(R.id.total);
        //imgImg = (ImageView) itemView.findViewById(R.id.list_row_image);

        // Capture position and set to the TextViews
        txtTitle.setText(titulos[position]);
        sit.setText(si[position].toString());
        not.setText(no[position].toString());
        Integer t = si[position]-no[position];
        if(t >= 0)
            total.setTextColor(Color.GREEN);
        else
            total.setTextColor(Color.RED);
        total.setText(t.toString());

        return itemView;
    }
}