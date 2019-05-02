package com.piaget.BeanView;

import com.piaget.Clases.Utilidades;
import com.piaget.Dao.DaoPlanes;
import com.piaget.Dao.DaoTensenanza;
import com.piaget.HibernateUtil.HibernateUtil;
import com.piaget.pojo.Planes;
import com.piaget.pojo.Tensenanza;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
public class MbVPlanes implements Serializable {

    /**
     * Creates a new instance of MbVPlanes
     */
    private Planes planes;
    private List<Planes> listaAllPlanes;

    private List<SelectItem> listPlanes;

    private List<SelectItem> listPlanResol;

    private List<SelectItem> listEnsenanza;

    private Session session;
    private Transaction transaction;

    private String idEnse;

    private Date fechaPlan;
    private String desctextDecreto;
    private String nomTextDecreto;
    private int codtextDecreto;

    ///Constructor
    public MbVPlanes() {
        this.planes = new Planes();
    }

    //Se encarga de registrar los planes de estudio
    public void grabarPlanes() {
        this.session = null;
        this.transaction = null;
        int year;
        try {
            if (this.codtextDecreto != 0 && this.fechaPlan != null && !this.nomTextDecreto.isEmpty() && !this.desctextDecreto.isEmpty()) {
                DaoPlanes daoPlanes = new DaoPlanes();
                this.session = HibernateUtil.getSessionFactory().openSession();
                this.transaction = session.beginTransaction();
                year = Utilidades.getAnoFromDate(fechaPlan);
                this.planes.setCoddecreto(codtextDecreto);
                this.planes.setResolucion(this.nomTextDecreto + " - " + this.codtextDecreto);
                this.planes.setDescripcion(desctextDecreto);
                daoPlanes.register(this.session, this.planes);
                this.transaction.commit();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Plan ingresado", "Se ha ingresado un nuevo Plan de Estudios.-"));
                this.planes = new Planes();
                this.codtextDecreto = 0;
                this.desctextDecreto = "";
                this.nomTextDecreto = "";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Campos incompletos.-", "Se debe ingresar todos los datos solicitados.-"));
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

    //Devuelve la lista completa de planes
    public List<Planes> getAllPlanes() {
        this.session = null;
        this.transaction = null;
        try {
            DaoPlanes daoPlanes = new DaoPlanes();
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            this.listaAllPlanes = daoPlanes.getAllPlanes(this.session);
            this.transaction.commit();
            return listaAllPlanes;
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

    //propiedades getter y setter
    /**
     * Get the value of listaAllPlanes
     *
     * @return the value of listaAllPlanes
     */
    public List<Planes> getListaAllPlanes() {
        return listaAllPlanes;
    }

    /**
     * Set the value of listaAllPlanes
     *
     * @param listaAllPlanes new value of listaAllPlanes
     */
    public void setListaAllPlanes(List<Planes> listaAllPlanes) {
        this.listaAllPlanes = listaAllPlanes;
    }

    /**
     * Get the value of planes
     *
     * @return the value of planes
     */
    public Planes getPlanes() {
        return planes;
    }

    /**
     * Set the value of planes
     *
     * @param planes new value of planes
     */
    public void setPlanes(Planes planes) {
        this.planes = planes;
    }

    /**
     * Get the value of desctextDecreto
     *
     * @return the value of desctextDecreto
     */
    public String getDesctextDecreto() {
        return desctextDecreto;
    }

    /**
     * Set the value of desctextDecreto
     *
     * @param desctextDecreto new value of desctextDecreto
     */
    public void setDesctextDecreto(String desctextDecreto) {
        this.desctextDecreto = desctextDecreto;
    }

    /**
     * Get the value of nomTextDecreto
     *
     * @return the value of nomTextDecreto
     */
    public String getNomTextDecreto() {
        return nomTextDecreto;
    }

    /**
     * Set the value of nomTextDecreto
     *
     * @param nomTextDecreto new value of nomTextDecreto
     */
    public void setNomTextDecreto(String nomTextDecreto) {
        this.nomTextDecreto = nomTextDecreto;
    }

    /**
     * Get the value of codtextDecreto
     *
     * @return the value of codtextDecreto
     */
    public int getCodtextDecreto() {
        return codtextDecreto;
    }

    /**
     * Set the value of codtextDecreto
     *
     * @param codtextDecreto new value of codtextDecreto
     */
    public void setCodtextDecreto(int codtextDecreto) {
        this.codtextDecreto = codtextDecreto;
    }

    /**
     * Get the value of fechaPlan
     *
     * @return the value of fechaPlan
     */
    public Date getFechaPlan() {
        return fechaPlan;
    }

    /**
     * Set the value of fechaPlan
     *
     * @param fechaPlan new value of fechaPlan
     */
    public void setFechaPlan(Date fechaPlan) {
        this.fechaPlan = fechaPlan;
    }

    /**
     * Get the value of listPlanes
     *
     * @return the value of listPlanes
     */
    public List<SelectItem> getListPlanes() {
        this.session = null;
        this.transaction = null;
        try {
            this.listPlanes = new ArrayList<>();
            DaoPlanes daoPlanes = new DaoPlanes();
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            List<Planes> plan;
            plan = daoPlanes.getAllPlanes(session);
            this.transaction.commit();

            listPlanes.clear();

            plan.stream().map((pln) -> new SelectItem(pln.getCoddecreto(), pln.getDescripcion())).forEach((SelectItem) -> {
                this.listPlanes.add(SelectItem);
            });

            return listPlanes;
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

    /**
     * Set the value of listPlanes
     *
     * @param listPlanes new value of listPlanes
     */
    public void setListPlanes(List<SelectItem> listPlanes) {
        this.listPlanes = listPlanes;
    }

    /**
     * Get the value of listPlanResol
     *
     * @return the value of listPlanResol
     */
    public List<SelectItem> getListPlanResol() {
        this.session = null;
        this.transaction = null;
        try {
            this.listPlanResol = new ArrayList<>();
            DaoPlanes daoPlanes = new DaoPlanes();
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            List<Planes> plan;
            plan = daoPlanes.getAllPlanes(session);
            this.transaction.commit();

            listPlanResol.clear();

            plan.stream().map((pln) -> new SelectItem(pln.getCoddecreto(), pln.getResolucion())).forEach((SelectItem) -> {
                this.listPlanResol.add(SelectItem);
            });

            return listPlanResol;
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

    /**
     * Set the value of listPlanResol
     *
     * @param listPlanResol new value of listPlanResol
     */
    public void setListPlanResol(List<SelectItem> listPlanResol) {
        this.listPlanResol = listPlanResol;
    }

    /**
     * Get the value of idEnse
     *
     * @return the value of idEnse
     */
    public String getIdEnse() {
        return idEnse;
    }

    /**
     * Set the value of idEnse
     *
     * @param idEnse new value of idEnse
     */
    public void setIdEnse(String idEnse) {
        this.idEnse = idEnse;
    }

    /**
     * Get the value of listEnsenanza
     *
     * @return the value of listEnsenanza
     */
    public List<SelectItem> getListEnsenanza() {
        this.session = null;
        this.transaction = null;
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            this.listEnsenanza = new ArrayList<>();
            DaoTensenanza daoEnse = new DaoTensenanza();
            DaoPlanes daoPlanes = new DaoPlanes();
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            List<Tensenanza> ense;
            ense = daoEnse.getByCodPlan(session, daoPlanes.getByPlanes(session, codtextDecreto));
            this.transaction.commit();

            listEnsenanza.clear();
            ense.stream().map((ens) -> new SelectItem(ens.getIdense(), ens.getNamense())).forEach((SelectItem) -> {
                this.listEnsenanza.add(SelectItem);
            });

            return listEnsenanza;
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

    /**
     * Set the value of listEnsenanza
     *
     * @param listEnsenanza new value of listEnsenanza
     */
    public void setListEnsenanza(List<SelectItem> listEnsenanza) {
        this.listEnsenanza = listEnsenanza;
    }
}
