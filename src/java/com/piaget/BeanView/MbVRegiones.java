package com.piaget.BeanView;

import com.piaget.Dao.DaoComunas;
import com.piaget.Dao.DaoProvincias;
import com.piaget.Dao.DaoRegiones;
import com.piaget.HibernateUtil.HibernateUtil;
import com.piaget.pojo.Comunas;
import com.piaget.pojo.Provincias;
import com.piaget.pojo.Regiones;
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
public class MbVRegiones implements Serializable {

    /**
     * Creates a new instance of MbVRegiones
     */
    private Regiones regiones;

    private List<Regiones> listaRegiones;

    private List<SelectItem> listRegiones;

    private List<SelectItem> listProvincias;

    private List<SelectItem> listComunas;

    private Short idRegion;

    private Short codProvincia;   

    private Session session;

    private Transaction transaction;

    public MbVRegiones() {
        this.regiones = new Regiones();
        this.idRegion = 1;
        this.codProvincia = 1;
    }

    ///Se encarga de listar todas las regiones ///
    public List<Regiones> getTodasRegiones() {
        this.session = null;
        this.transaction = null;
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            DaoRegiones daoRegiones = new DaoRegiones();
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            this.listaRegiones = daoRegiones.getAllRegion(this.session);
            this.transaction.commit();
            return listaRegiones;
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

    //propiedades getter y setter
    /**
     * Get the value of regiones
     *
     * @return the value of regiones
     */
    public Regiones getRegiones() {
        return regiones;
    }

    /**
     * Set the value of regiones
     *
     * @param regiones new value of regiones
     */
    public void setRegiones(Regiones regiones) {
        this.regiones = regiones;
    }

    /**
     * Get the value of listRegiones
     *
     * @return the value of listRegiones
     */
    public List<SelectItem> getListRegiones() {
        this.session = null;
        this.transaction = null;
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            this.listRegiones = new ArrayList<>();
            DaoRegiones daoRegiones = new DaoRegiones();
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            List<Regiones> region;
            region = daoRegiones.getAllRegion(session);
            this.transaction.commit();

            listRegiones.clear();

            region.stream().map((rgn) -> new SelectItem(rgn.getCodregion(), rgn.getRegion())).forEach((SelectItem) -> {
                this.listRegiones.add(SelectItem);
            });

            return listRegiones;
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
     * Set the value of listRegiones
     *
     * @param listRegiones new value of listRegiones
     */
    public void setListRegiones(List<SelectItem> listRegiones) {
        this.listRegiones = listRegiones;
    }

    /**
     * Get the value of listaRegiones
     *
     * @return the value of listaRegiones
     */
    public List<Regiones> getListaRegiones() {
        return listaRegiones;
    }

    /**
     * Set the value of listaRegiones
     *
     * @param listaRegiones new value of listaRegiones
     */
    public void setListaRegiones(List<Regiones> listaRegiones) {
        this.listaRegiones = listaRegiones;
    }

    /**
     * Get the value of idRegion
     *
     * @return the value of idRegion
     */
    public Short getIdRegion() {
        return idRegion;
    }

    /**
     * Set the value of idRegion
     *
     * @param idRegion new value of idRegion
     */
    public void setIdRegion(Short idRegion) {
        this.idRegion = idRegion;
    }

    /**
     * Get the value of listProvincias
     *
     * @return the value of listProvincias
     */
    public List<SelectItem> getListProvincias() {
        this.session = null;
        this.transaction = null;
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            this.listProvincias = new ArrayList<>();
            DaoRegiones daoRegiones = new DaoRegiones();
            DaoProvincias daoProvincias = new DaoProvincias();
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            List<Provincias> provi;
            provi = daoProvincias.getProvinciasByRegion(session, daoRegiones.getRegionByCode(session, this.idRegion));
            this.transaction.commit();

            this.listProvincias.clear();

            provi.stream().map((prv) -> new SelectItem(prv.getIdprovincia(), prv.getProvincia())).forEach((SelectItem) -> {
                this.listProvincias.add(SelectItem);
            });

            return listProvincias;
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
     * Set the value of listProvincias
     *
     * @param listProvincias new value of listProvincias
     */
    public void setListProvincias(List<SelectItem> listProvincias) {
        this.listProvincias = listProvincias;
    }

    /**
     * Get the value of codProvincia
     *
     * @return the value of codProvincia
     */
    public Short getCodProvincia() {
        return codProvincia;
    }

    /**
     * Set the value of codProvincia
     *
     * @param codProvincia new value of codProvincia
     */
    public void setCodProvincia(Short codProvincia) {
        this.codProvincia = codProvincia;
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
            DaoProvincias daoProvincias = new DaoProvincias();
            DaoComunas daoComunas = new DaoComunas();
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            List<Comunas> comu;
            comu = daoComunas.getComunasByProvincias(session, daoProvincias.getProvinciaById(session, this.codProvincia));
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
