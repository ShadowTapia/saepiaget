package com.piaget.BeanView;

import com.piaget.Dao.DaoComunas;
import com.piaget.HibernateUtil.HibernateUtil;
import com.piaget.pojo.Comunas;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Marcelo
 */
@ManagedBean
@ViewScoped
public class MbVComunas implements Serializable {

    /**
     * Creates a new instance of MbVComunas
     */
    private Comunas comunas;     

    private List<SelectItem> listComunas;

    private Session session;
    private Transaction transaction;    

    public MbVComunas() {
        comunas = new Comunas();
    }

    // getter y setter
    /**
     * Get the value of comunas
     *
     * @return the value of comunas
     */
    public Comunas getComunas() {
        return comunas;
    }

    /**
     * Set the value of comunas
     *
     * @param comunas new value of comunas
     */
    public void setComunas(Comunas comunas) {
        this.comunas = comunas;
    }

    /**
     * Get the value of listComunas
     *
     * @return the value of listComunas
     */
    public List<SelectItem> getListComunas() {
        this.session = null;
        this.transaction = null;
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            this.listComunas = new ArrayList<>();
            DaoComunas daoComunas = new DaoComunas();
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            List<Comunas> comu;
            comu = daoComunas.getAllComunas(session);
            this.transaction.commit();

            listComunas.clear();

            comu.stream().map((cmn) -> new SelectItem(cmn.getCodcomuna(), cmn.getComuna())).forEach((SelectItem) -> {
                this.listComunas.add(SelectItem);
            });

            return listComunas;
        } catch (HibernateException eX) {
            if (this.transaction != null) {
                this.transaction.rollback();
            }
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal" + eX.getMessage(), "Pongase en contacto con el Administrador"));
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

    /**
     * Set the value of listComunas
     *
     * @param listComunas new value of listComunas
     */
    public void setListComunas(List<SelectItem> listComunas) {
        this.listComunas = listComunas;
    }  
    

}
