package br.com.congregationreport.util;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Random;

import org.apache.commons.io.IOUtils;

import br.com.congregationreport.R;

public class Util {

    public static int OK = 200;
    public static int NOT_FOUND = 404;


    public static boolean isDeviceOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    public static void createMessageAlert(Context context, String message) {
        try {
            // Cria o dialog
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_message);

            // Pega os componentes
            TextView text = (TextView) dialog.findViewById(R.id.txtMessage);
            text.setText(message);

            // Fecha o dialog
            Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void createToast(Context context, String message) {
        try {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static String parseInputStreamToString(InputStream inputStream) {
        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(inputStream, writer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

    public static int getRamdomBumber() {
        Random rand = new Random();
        return rand.nextInt();
    }

    public static int convertDps(Context context, int dps) {
        final float scale = context.getResources().getDisplayMetrics().density;
        int pixels = (int) (dps * scale + 0.5f);
        return pixels;
    }


}
