package com.ciaa_poncho.lucashour.transmisortcpudp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Vibrator;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

public class HttpRequestAsyncTask extends AsyncTask<Void, Void, Void> {

    private String ipAddress, portNumber;
    private String parameter;
    private String parameterValue;
    private Context context;

    public HttpRequestAsyncTask(Context context, String parameterValue, String ipAddress, String portNumber, String parameter){
        this.context = context;
        this.ipAddress = ipAddress;
        this.parameterValue = parameterValue;
        this.portNumber = portNumber;
        this.parameter = parameter;
    }

     /* Método a ejecutar en background por tarea asincrónica */
    protected Void doInBackground(Void... params) {
        String serverResponse = sendRequest(parameterValue,ipAddress,portNumber, parameter);
        if (serverResponse != null && serverResponse != "ERROR"){
            Vibrator vibrator = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(50);
        }
        return null;
    }

    /*  Método de envío de mensaje HTTP al servidor
        Retorna la respuesta del servidor
    */
    private String sendRequest(String parameterValue, String ipAddress, String portNumber, String parameterName) {
        String serverResponse = "ERROR";

        try {
            /* Creación de cliente HTTP */
            HttpClient httpclient = new DefaultHttpClient();

            /* Definición de URL - Estructura: http://IP_ADDRESS:PORT/?parameter=SOMETHING */
            URI website = new URI("http://"+ipAddress+":"+portNumber+"/?"+parameterName+"="+parameterValue);
            /* Construcción de la solicitud HTTP */
            HttpGet getRequest = new HttpGet(); // Creación de HTTP GET
            getRequest.setURI(website); // Seteo de URL para el HTTP GET

            /* Envío de la solicitud HTTP */
            HttpResponse response = httpclient.execute(getRequest);

            /* Recepción de respuesta HTTP del servidor */
            InputStream server_reply = null;
            server_reply = response.getEntity().getContent();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    server_reply
            ));
            serverResponse = in.readLine(); // Respuesta del servidor

            /* Cierre de la conexión */
            server_reply.close();

        } catch (ClientProtocolException e) {
            // Error de HTTP
            serverResponse = e.getMessage();
            e.printStackTrace();
        } catch (IOException e) {
            // Error de IO
            serverResponse = e.getMessage();
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // Error sintáctico de URL
            serverResponse = e.getMessage();
            e.printStackTrace();
        }

        return serverResponse; // Retorno de la respuesta del servidor
    }

}
