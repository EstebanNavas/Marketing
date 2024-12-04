package com.marketing;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ApiFacturacionElectronica {

    @Async
    public void ejecutarJar(int idLocal, int xIdTipoOrden, int xIdPeriodo, String ApiFE) {
        System.out.println("Ejecutando ApiFacturacionElectronica");

        Process process = null;
        try {
        	
            //Separador de archivos dependiendo el sistema OP
            String xCharSeparator = File.separator;

            // Comando para ejecutar, dependiendo del sistema operativo
            String[] cmd;

            if (xCharSeparator.equals("/")) { // Linux: SH
            	
                String scriptPath = "/home/sw/script/" + ApiFE;
                cmd = new String[]{scriptPath, String.valueOf(idLocal), String.valueOf(xIdTipoOrden), String.valueOf(xIdPeriodo)};
                System.out.println("Ejecutando script ===> " + cmd);
                
            } else { // Windows: JAR -- De momento no funciona 
            	
                String jarPath = "c:" + xCharSeparator + "Archivo_distribuicion" + xCharSeparator + "ApiSoenac.jar";
                cmd = new String[]{"java", "-jar", jarPath, String.valueOf(idLocal), String.valueOf(xIdTipoOrden), String.valueOf(xIdPeriodo)};
                System.out.println("Windows detectado. Ejecutando JAR.");
            }

            // Imprimir el comando que se ejecutará
            String cmdString = String.join(" ", cmd);
            System.out.println("Comando a ejecutar: " + cmdString);

            // Crear el ProcessBuilder y configurarlo
            ProcessBuilder processBuilder = new ProcessBuilder(cmd);
            processBuilder.redirectErrorStream(true); // Redirigir la salida de error al flujo de salida estándar

            // Iniciar el proceso
            process = processBuilder.start();

            // Leer la salida del proceso
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Salida del proceso: " + line);
            }
            reader.close();

            // Esperar a que el proceso termine
            int exitCode = process.waitFor();
            System.out.println("El proceso ha finalizado, código de salida: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
            // Liberar el puerto después de que el proceso se haya destruido (si aplica)
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
