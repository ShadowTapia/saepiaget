package com.piaget.pojo;
// Generated Nov 18, 2018 2:42:44 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Regiones generated by hbm2java
 */
public class Regiones  implements java.io.Serializable {


     private Short codregion;
     private String region;
     private Short orden;
     private Set provinciases = new HashSet(0);

    public Regiones() {
    }

	
    public Regiones(Short codregion, String region) {
        this.codregion = codregion;
        this.region = region;
    }
    public Regiones(Short codregion, String region, Short orden, Set provinciases) {
       this.codregion = codregion;
       this.region = region;
       this.orden = orden;
       this.provinciases = provinciases;
    }
   
    public Short getCodregion() {
        return this.codregion;
    }
    
    public void setCodregion(Short codregion) {
        this.codregion = codregion;
    }
    public String getRegion() {
        return this.region;
    }
    
    public void setRegion(String region) {
        this.region = region;
    }
    public Short getOrden() {
        return this.orden;
    }
    
    public void setOrden(Short orden) {
        this.orden = orden;
    }
    public Set getProvinciases() {
        return this.provinciases;
    }
    
    public void setProvinciases(Set provinciases) {
        this.provinciases = provinciases;
    }

}


