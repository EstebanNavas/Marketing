package com.marketing.Utilidades;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class GeneradorZip {

	public void AgregarPdfAZip(Integer idLocal, String sistema, Integer idDcto, String xPathPDF) throws FileNotFoundException, IOException {

        // TODO code application logic here
        String xCharSeparator = File.separator;

        //
        String pathZippdfxml = xPathPDF + sistema + xCharSeparator + "zippdfxml" + xCharSeparator + idLocal + xCharSeparator + idDcto + ".zip";
        String filePathZip = xPathPDF + sistema + xCharSeparator + "zip" + xCharSeparator + idLocal + xCharSeparator + idDcto + ".xml";
        String filePathPdf = xPathPDF + sistema + xCharSeparator + "BDMailFactura" + xCharSeparator + idLocal + xCharSeparator + idDcto + ".pdf";

        System.out.println("Ingresó a AgregarPdfAZip");
        System.out.println("pathZippdfxml " + pathZippdfxml);
        System.out.println("filePathZip " + filePathZip);
        System.out.println("filePathPdf " + filePathPdf);
        
        
        
        byte[] buffer = new byte[1024];

        //--- Crea directorio 
        File filexmlZip = new File(filePathZip);
        
        //
        if (filexmlZip.isFile()) {

            //
            String[] srcFiles = {filePathZip, filePathPdf};

            FileOutputStream fos = new FileOutputStream(pathZippdfxml);

            ZipOutputStream zos = new ZipOutputStream(fos);

            //
            for (int i = 0; i < srcFiles.length; i++) {

                File srcFile = new File(srcFiles[i]);

                FileInputStream fis = new FileInputStream(srcFile);

                // begin writing a new ZIP entry, positions the stream to the start of the entry data
                zos.putNextEntry(new ZipEntry(srcFile.getName()));

                int length;

                while ((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }

                zos.closeEntry();

                // close the InputStream
                fis.close();

            }
            
            // close the ZipOutputStream
            zos.close();            
        }
    }
}
