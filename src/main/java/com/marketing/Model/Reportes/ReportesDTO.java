package com.marketing.Model.Reportes;

import java.io.ByteArrayInputStream;

public class ReportesDTO {

	// Esta variable se usará para alamacenar el nombre del archivo
		private String fileName;
		
		// Esta variable de tipo ByteArrayInputStream se usará para almacenar el contenido del archivo en Bytes
		private ByteArrayInputStream stream;
		
		// Esta variable se usará para almacenar la longitud del archivo en Bytes
		private int length;

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public ByteArrayInputStream getStream() {
			return stream;
		}

		public void setStream(ByteArrayInputStream stream) {
			this.stream = stream;
		}

		public int getLength() {
			return length;
		}

		public void setLength(int length) {
			this.length = length;
		}
}
