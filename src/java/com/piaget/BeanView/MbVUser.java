package com.piaget.BeanView;

import com.piaget.Clases.Encrypt;
import com.piaget.Dao.DaoProfesores;
import com.piaget.Dao.DaoUsuarios;
import com.piaget.HibernateUtil.HibernateUtil;
import com.piaget.pojo.Usuarios;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
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
public class MbVUser implements Serializable {

    /**
     * Creates a new instance of MbVUser
     */
    private Usuarios usuarios;
    private List<Usuarios> listaUsuarios;

    private String actualPass;
    private String newPass;
    private String confirmPass;

    private Session session;
    private Transaction transaction;

    @ManagedProperty(value = "#{mbVProfes}")
    private MbVProfes mbVProfes = new MbVProfes();

    public MbVUser() {
        this.usuarios = new Usuarios();
        this.usuarios.setIdUser("");
    }

    /* Se encarga de registrar la contraseña del usuario */
    public void registerPass() {
        this.session = null;
        this.transaction = null;
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            /* Validamos en primer lugar que las contraseñas sean iguales */
            if (!this.usuarios.getUserpass().equals(this.confirmPass)) {
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error de contraseñas.", "Las contraseñas no coinciden"));
                return;
            }
            DaoUsuarios daoUsuarios = new DaoUsuarios();
            DaoProfesores daoProfesores = new DaoProfesores();
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            /* Validamos que el usuario no exista en la tabla */
            if (daoUsuarios.getByIdUser(session, mbVProfes.getCodProfe()) != null) {
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error Usuario: ", "Este usuario ya tiene asignada una contraseña.-"));
                return;
            }
            this.usuarios.setProfesores(daoProfesores.getProfesById(session, mbVProfes.getCodProfe()));
            this.usuarios.setUserpass(Encrypt.sha512(this.usuarios.getUserpass()));
            daoUsuarios.register(this.session, this.usuarios);
            this.transaction.commit();
            
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ingreso exitoso:", "Contraseña creada satisfactoriamente.-"));
            this.usuarios = new Usuarios();
            this.confirmPass = "";
            
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

    ///Se encarga de actualizar la contraseña del usuario
    public void updatePass() {
        this.session = null;
        this.transaction = null;
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletRequest rq = (HttpServletRequest) ctx.getExternalContext().getRequest();
        HttpSession miSession = rq.getSession();
        String mail = (String) miSession.getAttribute("userMail");
        try {
            DaoUsuarios daoUsuarios = new DaoUsuarios();
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            this.usuarios = daoUsuarios.getUser(this.session, mail);
            if (usuarios != null) {
                if (usuarios.getUserpass() != null && usuarios.getUserpass().equals(Encrypt.sha512(this.actualPass))) {
                    if (this.newPass.equals(this.confirmPass)) {
                        this.usuarios.setUserpass(Encrypt.sha512(this.newPass));
                        daoUsuarios.update(this.session, this.usuarios);
                        ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualización exitosa: ", "Contraseña cambiada.-"));
                        this.actualPass = "";
                        this.newPass = "";
                        this.confirmPass = "";
                    } else {
                        ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error de tipeado: ", "Las Contraseñas no coinciden.-"));
                        this.newPass = "";
                        this.confirmPass = "";
                        return;
                    }
                } else {
                    ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Contraseña incorrecta.-"));
                    return;
                }
                this.transaction.commit();
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

    // getters y setters
    /**
     * Get the value of listaUsuarios
     *
     * @return the value of listaUsuarios
     */
    public List<Usuarios> getListaUsuarios() {
        return listaUsuarios;
    }

    /**
     * Set the value of listaUsuarios
     *
     * @param listaUsuarios new value of listaUsuarios
     */
    public void setListaUsuarios(List<Usuarios> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    /**
     * Get the value of usuarios
     *
     * @return the value of usuarios
     */
    public Usuarios getUsuarios() {
        return usuarios;
    }

    /**
     * Set the value of usuarios
     *
     * @param usuarios new value of usuarios
     */
    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * Get the value of actualPass
     *
     * @return the value of actualPass
     */
    public String getActualPass() {
        return actualPass;
    }

    /**
     * Set the value of actualPass
     *
     * @param actualPass new value of actualPass
     */
    public void setActualPass(String actualPass) {
        this.actualPass = actualPass;
    }

    /**
     * Get the value of confirmPass
     *
     * @return the value of confirmPass
     */
    public String getConfirmPass() {
        return confirmPass;
    }

    /**
     * Set the value of confirmPass
     *
     * @param confirmPass new value of confirmPass
     */
    public void setConfirmPass(String confirmPass) {
        this.confirmPass = confirmPass;
    }

    /**
     * Get the value of newPass
     *
     * @return the value of newPass
     */
    public String getNewPass() {
        return newPass;
    }

    /**
     * Set the value of newPass
     *
     * @param newPass new value of newPass
     */
    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    /**
     * Get the value of mbVProfes
     *
     * @return the value of mbVProfes
     */
    public MbVProfes getMbVProfes() {
        return mbVProfes;
    }

    /**
     * Set the value of mbVProfes
     *
     * @param mbVProfes new value of mbVProfes
     */
    public void setMbVProfes(MbVProfes mbVProfes) {
        this.mbVProfes = mbVProfes;
    }
}
