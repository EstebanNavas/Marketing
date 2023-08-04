package com.marketing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class CampaignTask {
	
	@Async
	public void ejecutarJar(int idLocal, int idCampaign) {
		
		System.out.println("prueba desde ejecutarJAR");
		
	    try {
	        // Ruta al directorio donde se encuentra el JAR de AltiriaSpringBoot
	        String jarPath = "C:\\Archivo_distribuicion\\AltiriaSpringBoot.jar";

	        	
	        //Se crea un array de Strings cmd que contiene los comandos y argumentos para ejecutar el JAR
//	        String[] cmd = {"java", "-jar", jarPath, String.valueOf(getIdLocal()), String.valueOf(getIdCampaign())};
	        String[] cmd = {"java", "-jar", jarPath, String.valueOf(idLocal), String.valueOf(idCampaign)};
	        
	        
	        String cmdString = String.join(" ", cmd);
	        System.out.println("Comando a ejecutar en CMD: " + cmdString);
	        
	        // Crear el ProcessBuilder y configurarlo
	        ProcessBuilder processBuilder = new ProcessBuilder(cmd);
	        processBuilder.redirectErrorStream(true); // Redirigir la salida de error al flujo de salida

	        // Iniciar el proceso
	        Process process = processBuilder.start();
	        

	        
	        // Leer la salida del proceso 
	        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	        String line;
	        while ((line = reader.readLine()) != null) {
	            System.out.println(line);
	        }

	        // Esperar a que el proceso termine
	        int exitCode = process.waitFor();
	        System.out.println("El JAR ha finalizado, código de salida: " + exitCode);
	    } catch (IOException | InterruptedException e) {
	        e.printStackTrace();
	    }

	}
}


