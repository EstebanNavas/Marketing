package com.marketing.Projection;

public class TblDctosViewDTO {

    private Integer idLocal;
    private Integer idTipoOrden;
    private Double idDcto;
    private Integer idOrden;
    private String fechaRegistroTx;
    private Integer idPeriodo;
    private Double latitud;
    private Double longitud;
    private String rutaImagen; // 👈 para mostrar en la vista
    private String idCliente;

    // Constructor que recibe el DTO base
    public TblDctosViewDTO(TblDctosDTO base) {
        this.idLocal = base.getIdLocal();
        this.idTipoOrden = base.getIdTipoOrden();
        this.idDcto = base.getIdDcto();
        this.idOrden = base.getIdOrden();
        this.fechaRegistroTx = base.getFechaRegistroTx();
        this.idPeriodo = base.getIdPeriodo();
        this.latitud = base.getLatitud();
        this.longitud = base.getLongitud();
    }

    // Getters y setters
    public Integer getIdLocal() { return idLocal; }
    public void setIdLocal(Integer idLocal) { this.idLocal = idLocal; }

    public Integer getIdTipoOrden() { return idTipoOrden; }
    public void setIdTipoOrden(Integer idTipoOrden) { this.idTipoOrden = idTipoOrden; }

    public Double getIdDcto() { return idDcto; }
    public void setIdDcto(Double idDcto) { this.idDcto = idDcto; }

    public Integer getIdOrden() { return idOrden; }
    public void setIdOrden(Integer idOrden) { this.idOrden = idOrden; }

    public String getFechaRegistroTx() { return fechaRegistroTx; }
    public void setFechaRegistroTx(String fechaRegistroTx) { this.fechaRegistroTx = fechaRegistroTx; }

    public Integer getIdPeriodo() { return idPeriodo; }
    public void setIdPeriodo(Integer idPeriodo) { this.idPeriodo = idPeriodo; }

    public Double getLatitud() { return latitud; }
    public void setLatitud(Double latitud) { this.latitud = latitud; }

    public Double getLongitud() { return longitud; }
    public void setLongitud(Double longitud) { this.longitud = longitud; }

    public String getRutaImagen() { return rutaImagen; }
    public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }

    public String getIdCliente() {return idCliente; }
    public void setIdCliente(String idCliente) { this.idCliente = idCliente; }
}