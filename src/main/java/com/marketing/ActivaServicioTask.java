package com.marketing;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;

import org.springframework.stereotype.Component;

@Component
public class ActivaServicioTask {

	public void ejecutarJar(int idLocal, String cc_nit, String idCliente, String telefonoCelular, String email, int estado) {
        System.out.println("Ejecutando JAR desde ActivaServicioTask");

        Process process = null;
        try {
            // Ruta al directorio donde se encuentra el JAR de AltiriaSpringBoot
            String jarPath = "C:\\Archivo_distribuicion\\ActivacionWpp.jar";
            
            

            String xCharSeparator = File.separator;
 
            
            String xFilePathJAR = "";    
            
           // Linux
           if (xCharSeparator.compareTo("/") == 0) {

               // Linux /home/sw/FileGral/aquamovil              
           	xFilePathJAR = "/home/sw" + xCharSeparator + "jar" + xCharSeparator + "ActivacionWpp" + xCharSeparator + "target" + xCharSeparator + "ActivacionWpp.jar";

           } else {

               // Windows                     
        	   xFilePathJAR = "c:" + xCharSeparator + "Archivo_distribuicion" + xCharSeparator + "ActivacionWpp.jar";
           	


           }     
            
                
            //Se crea un array de Strings cmd que contiene los comandos y argumentos para ejecutar el JAR
            String[] cmd = {"java", "-jar", xFilePathJAR, String.valueOf(idLocal), String.valueOf(cc_nit), String.valueOf(idCliente), String.valueOf(telefonoCelular), String.valueOf(email),
            		String.valueOf(estado)};
            
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
            System.out.println("El JAR ha finalizado, código de salida: " + exitCode);
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
