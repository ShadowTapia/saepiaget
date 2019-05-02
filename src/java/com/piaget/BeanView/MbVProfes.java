package com.piaget.BeanView;

import com.piaget.Clases.Utilidades;
import com.piaget.Dao.DaoProfesores;
import com.piaget.Dao.DaoProfiles;
import com.piaget.HibernateUtil.HibernateUtil;
import com.piaget.pojo.Profesores;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Marcelo
 */
@ManagedBean
@ViewScoped
public class MbVProfes implements Serializable {

    /**
     * Creates a new instance of MbVProfes
     */
    private Profesores profesores;
    private List<Profesores> listaProfesores;
    private List<SelectItem> lstProfesores;
    private List<SelectItem> lstOnlyprofes;

    private String txtRun;
    private String txtMail;
    private String codProfe;
    private Short iProfile;    
    
    private Session session;
    private Transaction transaction;

    @ManagedProperty(value = "#{mbVProfiles}")
    private MbVProfiles mbVProfiles = new MbVProfiles();

    public MbVProfes() {
        this.profesores = new Profesores();
        this.profesores.setIdProfe("");
        this.iProfile = 2;
    }

    /* se encarga de grabar un usuario */
    public void registrarProfe() {
        this.session = null;
        this.transaction = null;
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            /* validamos el run */
            if (Utilidades.ValiRutInt(txtRun) != 0) {
                DaoProfesores daoProfesores = new DaoProfesores();
                DaoProfiles daoProfiles = new DaoProfiles();
                this.session = HibernateUtil.getSessionFactory().openSession();
                this.transaction = session.beginTransaction();
                /* validamos si existe el docente */
                if (daoProfesores.getProfesByRun(this.session, this.txtRun) != null) {
                    ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario encontrado", "favor de verificar.-"));
                    return;
                }
                /* Validamos q tampoco exista el email */
                if (daoProfesores.getProfesByMail(this.session, this.txtMail) != null) {
                    ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "E-mail encontrado", "Este correo ya se encuentra registrado.-"));
                    return;
                }
                /* si el Run no existe en la BBDD y es valido */
                this.profesores.setRunProfe(this.txtRun);
                this.profesores.setEmailProfe(this.txtMail);
                this.profesores.setProfiles(daoProfiles.getPerfilByCod(this.session, mbVProfiles.getCodPrf()));

                daoProfesores.register(this.session, this.profesores);
                this.transaction.commit();
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ingreso exitoso!!", "Se ha agrgado un nuevo usuario.-"));

                this.txtRun = "";
                this.txtMail = "";

                this.profesores = new Profesores();
                this.profesores.setIdProfe("");
            } else {
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Run Invalido", "Favor de verificar.-"));
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

    ///Retorna la lista completa de profesores
    public List<Profesores> getAllProfes() {
        this.session = null;
        this.transaction = null;
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            DaoProfesores daoProfesores = new DaoProfesores();

            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            this.listaProfesores = daoProfesores.getAllProfes(this.session);

            this.transaction.commit();
            return listaProfesores;
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

    /* Se encarga de devolver los datos del usuario logueado */
    public Profesores getByMail() {
        this.session = null;
        this.transaction = null;
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            DaoProfesores daoProfesores = new DaoProfesores();
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            HttpSession miSession = (HttpSession) ctx.getExternalContext().getSession(true);
            this.profesores = daoProfesores.getProfesByMail(this.session, miSession.getAttribute("userMail").toString());
            this.transaction.commit();

            return this.profesores;
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

    /* Se encarga de actualizar los datos de los usuarios logueados */
    public String updateUserLogueado(Profesores profesores) {
        this.session = null;
        this.transaction = null;
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            DaoProfesores daoProfesores = new DaoProfesores();

            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            daoProfesores.updateProfe(this.session, profesores.getIdProfe(), profesores.getNomProfe(), profesores.getPaterProfe(), profesores.getMaterProfe(), profesores.getAddressProfe(), profesores.getFonoProfe(), profesores.getEmailProfe());
            this.transaction.commit();
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
        return "updateUserByMail?faces-redirect=true";
    }

    public String BuscaUserEdit(String idUser) {
        this.session = null;
        this.transaction = null;
        FacesContext ctx = FacesContext.getCurrentInstance();
        Map<String, Object> sessionMapObj = ctx.getExternalContext().getSessionMap();
        try {
            DaoProfesores daoProfesores = new DaoProfesores();
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            this.profesores = daoProfesores.getProfesById(this.session, idUser);
            sessionMapObj.put("editRecordObj", this.profesores);
            this.transaction.commit();

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
        return "userEdit?faces-redirect=true";
    }

    ///getters y setter
    /**
     * Get the value of profesores
     *
     * @return the value of profesores
     */
    public Profesores getProfesores() {
        return profesores;
    }

    /**
     * Set the value of profesores
     *
     * @param profesores new value of profesores
     */
    public void setProfesores(Profesores profesores) {
        this.profesores = profesores;
    }

    /**
     * Get the value of listaProfesores
     *
     * @return the value of listaProfesores
     */
    public List<Profesores> getListaProfesores() {
        return listaProfesores;
    }

    /**
     * Set the value of listaProfesores
     *
     * @param listaProfesores new value of listaProfesores
     */
    public void setListaProfesores(List<Profesores> listaProfesores) {
        this.listaProfesores = listaProfesores;
    }

    /**
     * Get the value of txtRun
     *
     * @return the value of txtRun
     */
    public String getTxtRun() {
        return txtRun;
    }

    /**
     * Set the value of txtRun
     *
     * @param txtRun new value of txtRun
     */
    public void setTxtRun(String txtRun) {
        this.txtRun = txtRun;
    }

    /**
     * Get the value of txtMail
     *
     * @return the value of txtMail
     */
    public String getTxtMail() {
        return txtMail;
    }

    /**
     * Set the value of txtMail
     *
     * @param txtMail new value of txtMail
     */
    public void setTxtMail(String txtMail) {
        this.txtMail = txtMail;
    }

    public MbVProfiles getMbVProfiles() {
        return mbVProfiles;
    }

    public void setMbVProfiles(MbVProfiles mbVProfiles) {
        this.mbVProfiles = mbVProfiles;
    }

    /**
     * Get the value of lstProfesores
     *
     * @return the value of lstProfesores
     */
    public List<SelectItem> getLstProfesores() {
        this.session = null;
        this.transaction = null;
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            this.lstProfesores = new ArrayList<>();
            DaoProfesores daoProfesores = new DaoProfesores();

            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            List<Profesores> profes;
            profes = daoProfesores.getAllProfes(session);
            this.transaction.commit();
            lstProfesores.clear();

            profes.stream().map((prf) -> new SelectItem(prf.getIdProfe(), prf.getNomProfe() + " " + prf.getPaterProfe() + " " + prf.getMaterProfe())).forEach((SelectItem) -> {
                this.lstProfesores.add(SelectItem);
            });
            return lstProfesores;
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
     * Set the value of lstProfesores
     *
     * @param lstProfesores new value of lstProfesores
     */
    public void setLstProfesores(List<SelectItem> lstProfesores) {
        this.lstProfesores = lstProfesores;
    }

    public String getCodProfe() {
        return codProfe;
    }

    public void setCodProfe(String codProfe) {
        this.codProfe = codProfe;
    }

    /**
     * Get the value of lstOnlyprofes
     *
     * @return the value of lstOnlyprofes
     */
    public List<SelectItem> getLstOnlyprofes() {
        this.session = null;
        this.transaction = null;
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            this.lstOnlyprofes = new ArrayList<>();
            DaoProfesores daoProfesores = new DaoProfesores();

            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            List<Profesores> Oprofes;
            Oprofes = daoProfesores.getOprofes(session);
            this.transaction.commit();
            lstOnlyprofes.clear();           

            Oprofes.stream().map((prf) -> new SelectItem(prf.getIdProfe(), prf.getPaterProfe() + " " + prf.getMaterProfe() + " " + prf.getNomProfe())).forEach((SelectItem) -> {
                this.lstOnlyprofes.add(SelectItem);
            });
            return lstOnlyprofes;
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
     * Set the value of lstOnlyprofes
     *
     * @param lstOnlyprofes new value of lstOnlyprofes
     */
    public void setLstOnlyprofes(List<SelectItem> lstOnlyprofes) {
        this.lstOnlyprofes = lstOnlyprofes;
    }

    /**
     * Get the value of iProfile
     *
     * @return the value of iProfile
     */
    public Short getiProfile() {
        return iProfile;
    }

    /**
     * Set the value of iProfile
     *
     * @param iProfile new value of iProfile
     */
    public void setiProfile(Short iProfile) {
        this.iProfile = iProfile;
    }  
    

}
