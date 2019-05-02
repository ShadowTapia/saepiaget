package com.piaget.BeanView;

import com.piaget.pojo.Matricula;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Marcelo
 */
@ManagedBean
@ViewScoped
public class MbVMatricula implements Serializable {

    /**
     * Creates a new instance of MbVMatricula
     */
    private Matricula matricula;
    private List<Matricula> listaAlumnos;

    private Session session;
    private Transaction transaction;

    
    public MbVMatricula() {
        matricula = new Matricula();
    }

    

    //propiedades getter y setter
    /**
     * Get the value of matricula
     *
     * @return the value of matricula
     */
    public Matricula getMatricula() {
        return matricula;
    }

    /**
     * Set the value of matricula
     *
     * @param matricula new value of matricula
     */
    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }
   

}
