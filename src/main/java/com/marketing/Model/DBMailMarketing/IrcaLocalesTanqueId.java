package com.marketing.Model.DBMailMarketing;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class IrcaLocalesTanqueId implements Serializable {

    private Integer IDLOCAL;
    private Integer idTanque;
    private String nombreTanque;
    private Double largo;
    private Double ancho;
    private Double profundo;
    

    public IrcaLocalesTanqueId() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IrcaLocalesTanqueId)) return false;

        IrcaLocalesTanqueId that = (IrcaLocalesTanqueId) o;

        return Objects.equals(IDLOCAL, that.IDLOCAL) &&
               Objects.equals(idTanque, that.idTanque);
               
    }

    @Override
    public int hashCode() {
        return Objects.hash(IDLOCAL, idTanque);
    }
}