package es.ucm.as.presentacion.vista;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import es.ucm.as.R;
import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;


public class Ayuda extends Activity{
    ImageViewTouch mImage;

    protected void onCreate(Bundle savedInstanceState) {
        cargarTema();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);
        Bundle bundle = getIntent().getExtras();
        String pantalla = bundle.getString("pantalla");
        mImage = (ImageViewTouch)findViewById(R.id.imageView);

        mImage.setSingleTapListener(
                new ImageViewTouch.OnImageViewTouchSingleTapListener() {

                    @Override
                    public void onSingleTapConfirmed() {
                        //Log.d("LOG_TAG", "onSingleTapConfirmed");
                    }
                }
        );

        mImage.setDoubleTapListener(
                new ImageViewTouch.OnImageViewTouchDoubleTapListener() {

                    @Override
                    public void onDoubleTap() {
                        //Log.d("LOG_TAG", "onDoubleTap");
                    }
                }
        );

        mImage.setOnDrawableChangedListener(
                new ImageViewTouchBase.OnDrawableChangeListener() {

                    @Override
                    public void onDrawableChanged(Drawable drawable) {
                        //Log.i("LOG_TAG", "onBitmapChanged: " + drawable);
                    }
                }
        );

        switch (pantalla){
            case "principal":
                mImage.setImageResource(R.drawable.ayuda_main);
                break;
            case "activity_reto":
                mImage.setImageResource(R.drawable.ayuda_reto);
                break;
            case "activity_eventos":
                mImage.setImageResource(R.drawable.ayuda_evento);
                break;
            case "configuracion":
                mImage.setImageResource(R.drawable.ayuda_conf);
                break;
            case "activity_informe":
                mImage.setImageResource(R.drawable.ayuda_tareas);
                break;
        }
    }

    public void volver(View v){
        finish();
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
