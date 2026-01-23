package com.marketing;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;



@Component
public class CampaignWpTask {

    @Async
    public void ejecutarJar(String numeroCelular, String mensaje,  int idLocal, String idCliente, int idPeriodo, int idDcto) {
        System.out.println("Ejecutando JAR desde CampaignWpTask");

        Process process = null;
        try {
            // Ruta al directorio donde se encuentra el JAR de AltiriaSpringBoot
            String jarPath = "C:\\Archivo_distribuicion\\whatsapp-avisos.jar";
            
            
            // TODO code application logic here
            String xCharSeparator = File.separator;
 
            
            String xFilePathJAR = "";    
            

                
            // Linux
            if (xCharSeparator.compareTo("/") == 0) {

                // Linux /home/sw/FileGral/aquamovil              
            	xFilePathJAR = "/home/sw" + xCharSeparator + "jar" + xCharSeparator + "WhatsAppPseAvisos" + xCharSeparator + "target" + xCharSeparator + "whatsapp-avisos.jar";

            } else {

                // Windows                     
            	xFilePathJAR = "c:" + xCharSeparator + "Archivo_distribuicion" + xCharSeparator + "whatsapp-avisos.jar";
            	


            }             
            
                
            //Se crea un array de Strings cmd que contiene los comandos y argumentos para ejecutar el JAR
            String[] cmd = {"java", "-jar", xFilePathJAR, String.valueOf(numeroCelular),String.valueOf(mensaje), String.valueOf(idLocal), String.valueOf(idCliente), String.valueOf(idPeriodo), String.valueOf(idDcto) };
            
            String cmdString = String.join(" ", cmd);
            System.out.println("Comando a ejecutar en CMD: " + cmdString);
            
            // Crear el ProcessBuilder y configurarlo
            ProcessBuilder processBuilder = new ProcessBuilder(cmd);
            processBuilder.redirectErrorStream(true); // Redirigir la salida de error al flujo de salida

            // Iniciar el proceso
            process = processBuilder.start();

            // Leer la salida del proceso 
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Salida del proceso: " + line);
            }
            reader.close(); // Cerrar el flujo del lector
            
            // Esperar a que el proceso termine
            int exitCode = process.waitFor();
            System.out.println("El JAR EnvioAvisosWhatsapp ha finalizado, código de salida: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
            // Liberar el puerto después de que el proceso se haya destruido
            releasePort(8082);
        }
    }

    private void releasePort(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.close();
        } catch (IOException e) {
            System.err.println("Error al liberar el puerto " + port + ": " + e.getMessage());
        }
    }
}