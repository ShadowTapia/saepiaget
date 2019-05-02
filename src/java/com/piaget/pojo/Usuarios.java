package com.piaget.pojo;
// Generated Oct 8, 2018 4:27:45 PM by Hibernate Tools 4.3.1



/**
 * Usuarios generated by hbm2java
 */
public class Usuarios  implements java.io.Serializable {


     private String idUser;
     private Anos anos;
     private Profesores profesores;
     private String userpass;
     private String bloqueada;

    public Usuarios() {
    }

	
    public Usuarios(String idUser, Profesores profesores, String userpass) {
        this.idUser = idUser;
        this.profesores = profesores;
        this.userpass = userpass;
    }
    public Usuarios(String idUser, Anos anos, Profesores profesores, String userpass, String bloqueada) {
       this.idUser = idUser;
       this.anos = anos;
       this.profesores = profesores;
       this.userpass = userpass;
       this.bloqueada = bloqueada;
    }
   
    public String getIdUser() {
        return this.idUser;
    }
    
    public void setIdUser(String idUser) {
        this.idUser = idUser;
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
    public String getUserpass() {
        return this.userpass;
    }
    
    public void setUserpass(String userpass) {
        this.userpass = userpass;
    }
    public String getBloqueada() {
        return this.bloqueada;
    }
    
    public void setBloqueada(String bloqueada) {
        this.bloqueada = bloqueada;
    }




}


