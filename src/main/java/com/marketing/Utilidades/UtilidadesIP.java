package com.marketing.Utilidades;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

public class UtilidadesIP {

	String ipTx   = null;
    private String ipServidor;
    
    //
    public String getIpTx(HttpServletRequest request) throws UnknownHostException {

           String ipTx = request.getRemoteAddr();

           return ipTx;
    }

    public String getIpServidor() {
        try {

            InetAddress servidor = InetAddress.getLocalHost();

            this.ipServidor = servidor.getHostAddress();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ipServidor;
    }
}
