package com.piaget.pojo;
// Generated Nov 6, 2018 11:22:05 AM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Cursos generated by hbm2java
 */
public class Cursos  implements java.io.Serializable {


     private Short idcurso;
     private Anos anos;
     private Profesores profesores;
     private Sedes sedes;
     private Tensenanza tensenanza;
     private String nomcurso;
     private Character letra;
     private Byte orden;
     private String bloque;
     private Set matriculas = new HashSet(0);

    public Cursos() {
    }

    public Cursos(Anos anos, Profesores profesores, Sedes sedes, Tensenanza tensenanza, String nomcurso, Character letra, Byte orden, String bloque, Set matriculas) {
       this.anos = anos;
       this.profesores = profesores;
       this.sedes = sedes;
       this.tensenanza = tensenanza;
       this.nomcurso = nomcurso;
       this.letra = letra;
       this.orden = orden;
       this.bloque = bloque;
       this.matriculas = matriculas;
    }
   
    public Short getIdcurso() {
        return this.idcurso;
    }
    
    public void setIdcurso(Short idcurso) {
        this.idcurso = idcurso;
    }
    public Anos getAnos() {
        return this.anos;
    }
    
    public void setAnos(Anos anos) {
        this.anos = anos;
    }
    public Profesores getProfesores() {
        return this.profesores;
    }
    
    public void setProfesores(Profesores profesores) {
        this.profesores = profesores;
    }
    public Sedes getSedes() {
        return this.sedes;
    }
    
    public void setSedes(Sedes sedes) {
        this.sedes = sedes;
    }
    public Tensenanza getTensenanza() {
        return this.tensenanza;
    }
    
    public void setTensenanza(Tensenanza tensenanza) {
        this.tensenanza = tensenanza;
    }
    public String getNomcurso() {
        return this.nomcurso;
    }
    
    public void setNomcurso(String nomcurso) {
        this.nomcurso = nomcurso;
    }
    public Character getLetra() {
        return this.letra;
    }
    
    public void setLetra(Character letra) {
        this.letra = letra;
    }
    public Byte getOrden() {
        return this.orden;
    }
    
    public void setOrden(Byte orden) {
        this.orden = orden;
    }
    public String getBloque() {
        return this.bloque;
    }
    
    public void setBloque(String bloque) {
        this.bloque = bloque;
    }
    public Set getMatriculas() {
        return this.matriculas;
    }
    
    public void setMatriculas(Set matriculas) {
        this.matriculas = matriculas;
    }




}


