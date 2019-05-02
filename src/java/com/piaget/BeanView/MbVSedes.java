package com.piaget.BeanView;

import com.piaget.Dao.DaoComunas;
import com.piaget.Dao.DaoSedes;
import com.piaget.HibernateUtil.HibernateUtil;
import com.piaget.pojo.Sedes;
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
public class MbVSedes implements Serializable {

    /**
     * Creates a new instance of MbVSedes
     */
    private Sedes sedes;
    private List<Sedes> listaSedes;
    private List<SelectItem> lstSedes;

    private Session session;
    private Transaction transaction;

    private Short codComuna;

    private String nombreSede;
    private String domicilioSede;

    public MbVSedes() {
        sedes = new Sedes();
    }

    ///Se encarga de registrar una nueva sede ///
    public void guardarSede() {
        this.session = null;
        this.transaction = null;
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            if (!this.nombreSede.isEmpty() && !this.domicilioSede.isEmpty() && this.codComuna != null) {
                DaoSedes daoSedes = new DaoSedes();
                DaoComunas daoComunas = new DaoComunas();
                this.session = HibernateUtil.getSessionFactory().openSession();
                this.transaction = session.beginTransaction();

                this.sedes.setNamesede(nombreSede);
                this.sedes.setDireccion(domicilioSede);
                this.sedes.setComunas(daoComunas.getByIdComuna(session, codComuna));

                daoSedes.register(this.session, this.sedes);
                this.transaction.commit();
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sede Ingresada", "Se ha ingresado una nueva Sede.-"));
                sedes = new Sedes();
                this.nombreSede = "";
                this.domicilioSede = "";
                this.codComuna = 0;
            } else {
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falta información", "Se deben ingresar todos los datos solicitados.-"));
                return;
            }
        } catch (HibernateException eX) {
            if (this.transaction != null) {
                this.transaction.rollback();
            }
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal" + eX.getMessage(), "Pongase en contacto con el Administrador"));

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

    /* Retorna la lista de Sedes */
    public List<Sedes> getTodasSedes() {
        this.session = null;
        this.transaction = null;
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            DaoSedes daoSedes = new DaoSedes();
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            this.listaSedes = daoSedes.getAllSedes(this.session);
            this.transaction.commit();
            return this.listaSedes;
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

    //getter y setter
    /**
     * Get the value of listaSedes
     *
     * @return the value of listaSedes
     */
    public List<Sedes> getListaSedes() {
        return listaSedes;
    }

    /**
     * Set the value of listaSedes
     *
     * @param listaSedes new value of listaSedes
     */
    public void setListaSedes(List<Sedes> listaSedes) {
        this.listaSedes = listaSedes;
    }

    /**
     * Get the value of sedes
     *
     * @return the value of sedes
     */
    public Sedes getSedes() {
        return sedes;
    }

    /**
     * Set the value of sedes
     *
     * @param sedes new value of sedes
     */
    public void setSedes(Sedes sedes) {
        this.sedes = sedes;
    }

    /**
     * Get the value of codComuna
     *
     * @return the value of codComuna
     */
    public Short getCodComuna() {
        return codComuna;
    }

    /**
     * Set the value of codComuna
     *
     * @param codComuna new value of codComuna
     */
    public void setCodComuna(Short codComuna) {
        this.codComuna = codComuna;
    }

    /**
     * Get the value of nombreSede
     *
     * @return the value of nombreSede
     */
    public String getNombreSede() {
        return nombreSede;
    }

    /**
     * Set the value of nombreSede
     *
     * @param nombreSede new value of nombreSede
     */
    public void setNombreSede(String nombreSede) {
        this.nombreSede = nombreSede;
    }

    /**
     * Get the value of domicilioSede
     *
     * @return the value of domicilioSede
     */
    public String getDomicilioSede() {
        return domicilioSede;
    }

    /**
     * Set the value of domicilioSede
     *
     * @param domicilioSede new value of domicilioSede
     */
    public void setDomicilioSede(String domicilioSede) {
        this.domicilioSede = domicilioSede;
    }

    /**
     * Get the value of lstSedes
     *
     * @return the value of lstSedes
     */
    public List<SelectItem> getLstSedes() {
        this.session = null;
        this.transaction = null;
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            this.lstSedes = new ArrayList<>();
            DaoSedes daoSedes = new DaoSedes();

            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            List<Sedes> sede;
            sede = daoSedes.getAllSedes(session);
            this.transaction.commit();
            lstSedes.clear();

            sede.stream().map((sde) -> new SelectItem(sde.getIdsede(), sde.getNamesede())).forEach((SelectItem) -> {
                this.lstSedes.add(SelectItem);
            });
            return lstSedes;
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
     * Set the value of lstSedes
     *
     * @param lstSedes new value of lstSedes
     */
    public void setLstSedes(List<SelectItem> lstSedes) {
        this.lstSedes = lstSedes;
    }
}
