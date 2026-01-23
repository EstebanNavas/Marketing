package com.marketing;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;

import org.springframework.stereotype.Component;

@Component
public class FacturaMedidor {

	  public void ejecutarJar(Integer idPeriodo, Integer idLocal, Integer xIdUsuario, String contrasena) {
	        System.out.println("Ejecutando JAR desde MailjetTask");

	        try {
	            String xCharSeparator = File.separator;
	            String xFilePathJAR;

	            // Linux
	            if (xCharSeparator.equals("/")) {
	                xFilePathJAR = "/home/sw/jar/FacturaMedidor/target/FacturaMedidor.jar";
	            } else {
	                // Windows
	                xFilePathJAR = "C:\\Archivo_distribuicion\\FacturaMedidor.jar";
	            }

	            ProcessBuilder pb = new ProcessBuilder(
	                "java", "-jar",
	                xFilePathJAR,
	                idPeriodo.toString(),
	                idLocal.toString(),
	                xIdUsuario.toString(),
	                contrasena
	            );

	            // 🔥 IMPORTANTE
	            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
	            pb.redirectError(ProcessBuilder.Redirect.INHERIT);

	            // 🚀 LANZA Y SALE
	            pb.start();

	            System.out.println("JAR lanzado correctamente (NO BLOQUEANTE)");

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}
