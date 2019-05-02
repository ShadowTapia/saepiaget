package com.piaget.pojo;
// Generated Sep 26, 2018 5:23:27 PM by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * Matricula generated by hbm2java
 */
public class Matricula  implements java.io.Serializable {


     private String idalumno;
     private Alumnos alumnos;
     private Anos anos;
     private Cursos cursos;
     private Date fmatricula;
     private String observaciones;
     private String cursosrepetidos;
     private Short nummatricula;
     private String causasretiro;
     private String ret;
     private Date fecharetiroalumno;
     private Short idlista;

    public Matricula() {
    }

	
    public Matricula(Alumnos alumnos, Anos anos) {
        this.alumnos = alumnos;
        this.anos = anos;
    }
    public Matricula(Alumnos alumnos, Anos anos, Cursos cursos, Date fmatricula, String observaciones, String cursosrepetidos, Short nummatricula, String causasretiro, String ret, Date fecharetiroalumno, Short idlista) {
       this.alumnos = alumnos;
       this.anos = anos;
       this.cursos = cursos;
       this.fmatricula = fmatricula;
       this.observaciones = observaciones;
       this.cursosrepetidos = cursosrepetidos;
       this.nummatricula = nummatricula;
       this.causasretiro = causasretiro;
       this.ret = ret;
       this.fecharetiroalumno = fecharetiroalumno;
       this.idlista = idlista;
    }
   
    public String getIdalumno() {
        return this.idalumno;
    }
    
    public void setIdalumno(String idalumno) {
        this.idalumno = idalumno;
    }
    public Alumnos getAlumnos() {
        return this.alumnos;
    }
    
    public void setAlumnos(Alumnos alumnos) {
        this.alumnos = alumnos;
    }
    public Anos getAnos() {
        return this.anos;
    }
    
    public void setAnos(Anos anos) {
        this.anos = anos;
    }
    public Cursos getCursos() {
        return this.cursos;
    }
    
    public void setCursos(Cursos cursos) {
        this.cursos = cursos;
    }
    public Date getFmatricula() {
        return this.fmatricula;
    }
    
    public void setFmatricula(Date fmatricula) {
        this.fmatricula = fmatricula;
    }
    public String getObservaciones() {
        return this.observaciones;
    }
    
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    public String getCursosrepetidos() {
        return this.cursosrepetidos;
    }
    
    public void setCursosrepetidos(String cursosrepetidos) {
        this.cursosrepetidos = cursosrepetidos;
    }
    public Short getNummatricula() {
        return this.nummatricula;
    }
    
    public void setNummatricula(Short nummatricula) {
        this.nummatricula = nummatricula;
    }
    public String getCausasretiro() {
        return this.causasretiro;
    }
    
    public void setCausasretiro(String causasretiro) {
        this.causasretiro = causasretiro;
    }
    public String getRet() {
        return this.ret;
    }
    
    public void setRet(String ret) {
        this.ret = ret;
    }
    public Date getFecharetiroalumno() {
        return this.fecharetiroalumno;
    }
    
    public void setFecharetiroalumno(Date fecharetiroalumno) {
        this.fecharetiroalumno = fecharetiroalumno;
    }
    public Short getIdlista() {
        return this.idlista;
    }
    
    public void setIdlista(Short idlista) {
        this.idlista = idlista;
    }




}


