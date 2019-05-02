package com.piaget.pojo;
// Generated Sep 26, 2018 5:23:27 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Apoderados generated by hbm2java
 */
public class Apoderados  implements java.io.Serializable {


     private String idapoderado;
     private Anos anos;
     private int rutapoderado;
     private Character digitoap;
     private String nomapo;
     private String paternoap;
     private String maternoap;
     private Integer celular;
     private Integer fono;
     private String relacion;
     private String email;
     private String direccionap;
     private Set alumnoses = new HashSet(0);

    public Apoderados() {
    }

	
    public Apoderados(String idapoderado, Anos anos, int rutapoderado) {
        this.idapoderado = idapoderado;
        this.anos = anos;
        this.rutapoderado = rutapoderado;
    }
    public Apoderados(String idapoderado, Anos anos, int rutapoderado, Character digitoap, String nomapo, String paternoap, String maternoap, Integer celular, Integer fono, String relacion, String email, String direccionap, Set alumnoses) {
       this.idapoderado = idapoderado;
       this.anos = anos;
       this.rutapoderado = rutapoderado;
       this.digitoap = digitoap;
       this.nomapo = nomapo;
       this.paternoap = paternoap;
       this.maternoap = maternoap;
       this.celular = celular;
       this.fono = fono;
       this.relacion = relacion;
       this.email = email;
       this.direccionap = direccionap;
       this.alumnoses = alumnoses;
    }
   
    public String getIdapoderado() {
        return this.idapoderado;
    }
    
    public void setIdapoderado(String idapoderado) {
        this.idapoderado = idapoderado;
    }
    public Anos getAnos() {
        return this.anos;
    }
    
    public void setAnos(Anos anos) {
        this.anos = anos;
    }
    public int getRutapoderado() {
        return this.rutapoderado;
    }
    
    public void setRutapoderado(int rutapoderado) {
        this.rutapoderado = rutapoderado;
    }
    public Character getDigitoap() {
        return this.digitoap;
    }
    
    public void setDigitoap(Character digitoap) {
        this.digitoap = digitoap;
    }
    public String getNomapo() {
        return this.nomapo;
    }
    
    public void setNomapo(String nomapo) {
        this.nomapo = nomapo;
    }
    public String getPaternoap() {
        return this.paternoap;
    }
    
    public void setPaternoap(String paternoap) {
        this.paternoap = paternoap;
    }
    public String getMaternoap() {
        return this.maternoap;
    }
    
    public void setMaternoap(String maternoap) {
        this.maternoap = maternoap;
    }
    public Integer getCelular() {
        return this.celular;
    }
    
    public void setCelular(Integer celular) {
        this.celular = celular;
    }
    public Integer getFono() {
        return this.fono;
    }
    
    public void setFono(Integer fono) {
        this.fono = fono;
    }
    public String getRelacion() {
        return this.relacion;
    }
    
    public void setRelacion(String relacion) {
        this.relacion = relacion;
    }
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    public String getDireccionap() {
        return this.direccionap;
    }
    
    public void setDireccionap(String direccionap) {
        this.direccionap = direccionap;
    }
    public Set getAlumnoses() {
        return this.alumnoses;
    }
    
    public void setAlumnoses(Set alumnoses) {
        this.alumnoses = alumnoses;
    }




}


