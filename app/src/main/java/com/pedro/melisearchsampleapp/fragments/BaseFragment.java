package com.pedro.melisearchsampleapp.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;
import com.pedro.melisearchsampleapp.R;

/**
 * Clase base de la que heradan todos los fragmentos en la app. Contiene métodos generales
 * que pueden ser útiles en diferentes escenarios
 */
public class BaseFragment extends Fragment {
    /**
     * Despliega un diálogo de alerta al usuario
     * @param text Mesaje a mostrar en el diálogo
     * @param context Contexto
     */
    protected void showAlertDialog(String text, Context context) {
        showDialog(getString(R.string.alert_text), text, context, android.R.drawable.ic_dialog_alert);
    }

    /**
     * Despliega un diálogo de informació al usuario
     * @param text Mesaje a mostrar en el diálogo
     * @param context Contexto
     */
    protected void showInfoDialog(String text, Context context) {
        showDialog(getString(R.string.info_text), text, context, android.R.drawable.ic_dialog_info);
    }

    /**
     * Despliega un diálogo de error al usuario
     * @param text Mesaje a mostrar en el diálogo
     * @param context Contexto
     */
    protected void showErrorDialog(String text, Context context) {
        showDialog(getString(R.string.error_text), text, context, android.R.drawable.ic_dialog_alert);
    }

    private void showDialog(String title, String text, Context context, int icon) {
        new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.AlertDialogCustom))
                .setTitle(title)
                .setMessage(text)
                .setNegativeButton(R.string.accept, null)
                .setIcon(icon)
                .show();
    }
}
