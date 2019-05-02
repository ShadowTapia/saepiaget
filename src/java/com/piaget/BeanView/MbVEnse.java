package com.piaget.BeanView;

import com.piaget.Dao.DaoPlanes;
import com.piaget.Dao.DaoTensenanza;
import com.piaget.HibernateUtil.HibernateUtil;
import com.piaget.pojo.Tensenanza;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Marcelo
 */
@ManagedBean
@ViewScoped
public class MbVEnse implements Serializable {

    /**
     * Creates a new instance of MbVEnse
     */
    private Tensenanza tense;
    private List<Tensenanza> listaEnsenanza;
    private Short codEnse;
    private String nameEnse;    

    private int idPlan;
    private Date fechaResol;
    
    private Session session;
    private Transaction transaction;
    
    public MbVEnse() {
        tense = new Tensenanza();
        this.tense.setIdense("");
    }

    ///Retorna la lista de enseñañnzas
    public List<Tensenanza> getAllEnse() {
        this.session = null;
        this.transaction = null;
        try {
            DaoTensenanza daoEnse = new DaoTensenanza();
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            this.listaEnsenanza = daoEnse.getAllEnse(this.session);
            this.transaction.commit();
            return listaEnsenanza;
        } catch (HibernateException eX) {
            if (this.transaction != null) {
                this.transaction.rollback();
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal" + eX.getMessage(), "Pongase en contacto con el Administrador"));
            return null;
        } finally {
            if (this.session != null && this.session.isOpen()) {
                if (this.session.getTransaction() != null && this.session.getTransaction().isActive()) {
                    this.session.getTransaction().rollback();
                }
                this.session.close();
                this.session = null;
            }
        }
    }
    ///Se encarga de guardar un tipo de Enseñanza
    public void guardarEnse() {
        this.session = null;
        this.transaction = null;
        try {
            if (this.codEnse != 0 && !this.nameEnse.isEmpty() && this.fechaResol != null && this.idPlan != 0) {
                DaoTensenanza daoTense = new DaoTensenanza();
                DaoPlanes daoPlanes = new DaoPlanes();
                this.session = HibernateUtil.getSessionFactory().openSession();
                this.transaction = session.beginTransaction();
                
                this.tense.setIdnum(codEnse);
                this.tense.setNamense(nameEnse);
                this.tense.setDateresol(fechaResol);
                this.tense.setPlanes(daoPlanes.getByPlanes(this.session, idPlan));
                daoTense.register(this.session, this.tense);
                this.transaction.commit();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enseñanza Ingresada", "Se ha ingresado un nuevo tipo de Enseñanza.-"));
                tense = new Tensenanza();
                this.tense.setIdense("");
            }
        } catch (HibernateException eX) {
            if (this.transaction != null) {
                this.transaction.rollback();
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal" + eX.getMessage(), "Pongase en contacto con el Administrador"));
            
        } finally {
            if (this.session != null && this.session.isOpen()) {
                if (this.session.getTransaction() != null && this.session.getTransaction().isActive()) {
                    this.session.getTransaction().rollback();
                }
                this.session.close();
                this.session = null;
            }
        }
    }

    ///Getters y setters
    /**
     * Get the value of listaEnsenanza
     *
     * @return the value of listaEnsenanza
     */
    public List<Tensenanza> getListaEnsenanza() {
        return listaEnsenanza;
    }

    /**
     * Set the value of listaEnsenanza
     *
     * @param listaEnsenanza new value of listaEnsenanza
     */
    public void setListaEnsenanza(List<Tensenanza> listaEnsenanza) {
        this.listaEnsenanza = listaEnsenanza;
    }

    /**
     * Get the value of tense
     *
     * @return the value of tense
     */
    public Tensenanza getTense() {
        return tense;
    }

    /**
     * Set the value of tense
     *
     * @param tense new value of tense
     */
    public void setTense(Tensenanza tense) {
        this.tense = tense;
    }

    /**
     * Get the value of codEnse
     *
     * @return the value of codEnse
     */
    public Short getCodEnse() {
        return codEnse;
    }

    /**
     * Set the value of codEnse
     *
     * @param codEnse new value of codEnse
     */
    public void setCodEnse(Short codEnse) {
        this.codEnse = codEnse;
    }

    /**
     * Get the value of nameEnse
     *
     * @return the value of nameEnse
     */
    public String getNameEnse() {
        return nameEnse;
    }

    /**
     * Set the value of nameEnse
     *
     * @param nameEnse new value of nameEnse
     */
    public void setNameEnse(String nameEnse) {
        this.nameEnse = nameEnse;
    }

    /**
     * Get the value of idPlan
     *
     * @return the value of idPlan
     */
    public int getIdPlan() {
        return idPlan;
    }

    /**
     * Set the value of idPlan
     *
     * @param idPlan new value of idPlan
     */
    public void setIdPlan(int idPlan) {
        this.idPlan = idPlan;
    }

    /**
     * Get the value of fechaResol
     *
     * @return the value of fechaResol
     */
    public Date getFechaResol() {
        return fechaResol;
    }

    /**
     * Set the value of fechaResol
     *
     * @param fechaResol new value of fechaResol
     */
    public void setFechaResol(Date fechaResol) {
        this.fechaResol = fechaResol;
    }   
    
}
