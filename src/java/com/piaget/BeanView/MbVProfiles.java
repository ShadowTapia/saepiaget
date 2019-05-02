package com.piaget.BeanView;

import com.piaget.Dao.DaoProfiles;
import com.piaget.HibernateUtil.HibernateUtil;
import com.piaget.pojo.Profiles;
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
public class MbVProfiles implements Serializable {

    /**
     * Creates a new instance of MbVProfiles
     */
    private Profiles profiles;
    private List<Profiles> listaPerfiles;    
    private List<SelectItem> listPerfiles;    
    private Short codPrf;
    
    private Session session;
    private Transaction transaction;

    public MbVProfiles() {
        profiles = new Profiles();
    }

    //Se encarga de registrar los perfiles
    public void register() {
        this.session = null;
        this.transaction = null;
        try {
            DaoProfiles daoProfiles = new DaoProfiles();
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            daoProfiles.register(this.session, this.profiles);
            this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Perfil creado con exito", "Felicidades.-"));
            this.profiles = new Profiles();
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

    ///devuelve la lista completa de perfiles
    public List<Profiles> getAllProfiles() {
        this.session = null;
        this.transaction = null;
        try {
            DaoProfiles daoProfiles = new DaoProfiles();
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            this.listaPerfiles = daoProfiles.getPerfiles(this.session);
            this.transaction.commit();
            return this.listaPerfiles;
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

    public void delProfiles(Profiles delperfil) {
        this.session = null;
        this.transaction = null;
        try {
            DaoProfiles daoProfiles = new DaoProfiles();
            this.profiles = delperfil;
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            daoProfiles.delete(this.session, this.profiles);
            this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El perfil " + this.profiles.getTipo() + " fue Eliminado!!", "Se ha borrado un perfil.-"));
            profiles = new Profiles();
        } catch (Exception eX) {
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

    //metodos getter y setter
    /**
     * Get the value of listaPerfiles
     *
     * @return the value of listaPerfiles
     */
    public List<Profiles> getListaPerfiles() {
        return listaPerfiles;
    }

    /**
     * Set the value of listaPerfiles
     *
     * @param listaPerfiles new value of listaPerfiles
     */
    public void setListaPerfiles(List<Profiles> listaPerfiles) {
        this.listaPerfiles = listaPerfiles;
    }

    /**
     * Get the value of profiles
     *
     * @return the value of profiles
     */
    public Profiles getProfiles() {
        return profiles;
    }

    /**
     * Set the value of profiles
     *
     * @param profiles new value of profiles
     */
    public void setProfiles(Profiles profiles) {
        this.profiles = profiles;
    }
    
    /**
     * Get the value of listPerfiles
     *
     * @return the value of listPerfiles
     */
    public List<SelectItem> getListPerfiles() {
        this.session=null;
        this.transaction=null;
        try{
            this.listPerfiles=new ArrayList<>();
            DaoProfiles daoProfiles=new DaoProfiles();
            
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            
            List<Profiles> profiles;
            profiles=daoProfiles.getPerfiles(session);
            this.transaction.commit();
            listPerfiles.clear();
            
            profiles.stream().map((prf)-> new SelectItem(prf.getIdProfile(),prf.getTipo())).forEach((SelectItem)->{
              this.listPerfiles.add(SelectItem);
            });
            return listPerfiles;
        }catch (HibernateException eX) {
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

    /**
     * Set the value of listPerfiles
     *
     * @param listPerfiles new value of listPerfiles
     */
    public void setListPerfiles(List<SelectItem> listPerfiles) {
        this.listPerfiles = listPerfiles;
    }
    
    /**
     * Get the value of codPrf
     *
     * @return the value of codPrf
     */
    public Short getCodPrf() {
        return codPrf;
    }

    /**
     * Set the value of codPrf
     *
     * @param codPrf new value of codPrf
     */
    public void setCodPrf(Short codPrf) {
        this.codPrf = codPrf;
    }

}
