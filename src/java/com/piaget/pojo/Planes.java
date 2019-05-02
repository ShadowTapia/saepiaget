package com.piaget.pojo;
// Generated Sep 26, 2018 5:23:27 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Planes generated by hbm2java
 */
public class Planes  implements java.io.Serializable {


     private int coddecreto;
     private String resolucion;
     private String descripcion;
     private Set tensenanzas = new HashSet(0);

    public Planes() {
    }

	
    public Planes(int coddecreto, String resolucion, String descripcion) {
        this.coddecreto = coddecreto;
        this.resolucion = resolucion;
        this.descripcion = descripcion;
    }
    public Planes(int coddecreto, String resolucion, String descripcion, Set tensenanzas) {
       this.coddecreto = coddecreto;
       this.resolucion = resolucion;
       this.descripcion = descripcion;
       this.tensenanzas = tensenanzas;
    }
   
    public int getCoddecreto() {
        return this.coddecreto;
    }
    
    public void setCoddecreto(int coddecreto) {
        this.coddecreto = coddecreto;
    }
    public String getResolucion() {
        return this.resolucion;
    }
    
    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Set getTensenanzas() {
        return this.tensenanzas;
    }
    
    public void setTensenanzas(Set tensenanzas) {
        this.tensenanzas = tensenanzas;
    }




}


