package com.marketing.Projection;

public class TblDctosViewDTO {
	
	 private TblDctosDTO data;
	    private String rutaImagen;

	    public TblDctosViewDTO(TblDctosDTO data, String rutaImagen) {
	        this.data = data;
	        this.rutaImagen = rutaImagen;
	    }

	    public TblDctosDTO getData() {
	        return data;
	    }

	    public String getRutaImagen() {
	        return rutaImagen;
	    }

}
