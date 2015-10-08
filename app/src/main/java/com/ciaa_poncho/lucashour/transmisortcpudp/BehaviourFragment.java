package com.ciaa_poncho.lucashour.transmisortcpudp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BehaviourFragment extends Fragment implements View.OnClickListener{

    private TextView ip_address;
    private TextView port_number;
    private Button up;
    private Button down;

    public BehaviourFragment(){}

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v =  inflater.inflate(R.layout.fragment_behaviour, container, false);

        if(v != null){

            LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.linearLayout);
            up = ((Button) v.findViewById(R.id.up_button));
            down = ((Button) v.findViewById(R.id.down_button));
            ip_address = ((TextView) v.findViewById(R.id.ip_address_tv));
            ip_address.setText(GlobalData.getInstance().getIpAddress());
            port_number = ((TextView) v.findViewById(R.id.port_number_tv));
            port_number.setText(String.valueOf(GlobalData.getInstance().getPortNumber()));
        }

        return v;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        up.setOnClickListener(this);
        down.setOnClickListener(this);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(false); //Indicamos que este Fragment no tiene su propio menu de opciones
    }

    public void onClick(View view) {

        String ip = GlobalData.getInstance().getIpAddress();
        String port = String.valueOf(GlobalData.getInstance().getPortNumber());

        HttpRequestAsyncTask http_request;

        if (GlobalData.getInstance().getIpAddress() == null){
            Toast.makeText(this.getActivity().getApplicationContext(),"Configuración de dirección IP destino requerida.",Toast.LENGTH_SHORT).show();
        }
        else switch (view.getId()){
            case R.id.up_button:
                http_request = new HttpRequestAsyncTask(this.getActivity().getApplicationContext(),"up",ip,port);
                http_request.executeOnExecutor(HttpRequestAsyncTask.THREAD_POOL_EXECUTOR);
                break;
            case R.id.down_button:
                http_request = new HttpRequestAsyncTask(this.getActivity().getApplicationContext(),"down",ip,port);
                http_request.executeOnExecutor(HttpRequestAsyncTask.THREAD_POOL_EXECUTOR);
                break;
        }

    }
}