package com.piaget.BeanSession;

import com.piaget.Clases.Encrypt;
import com.piaget.Clases.Utilidades;
import com.piaget.Dao.DaoAnos;
import com.piaget.Dao.DaoProfesores;
import com.piaget.Dao.DaoUsuarios;
import com.piaget.HibernateUtil.HibernateUtil;
import com.piaget.pojo.Anos;
import com.piaget.pojo.Profesores;
import com.piaget.pojo.Usuarios;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Marcelo
 */
@ManagedBean
@SessionScoped
public class SesBean implements Serializable {

    /**
     * Creates a new instance of SesBean
     */
    private Anos anos;
    private Profesores profesor;
    private Usuarios user;

    private String strMail;
    private String userPass;

    private Session session;
    private Transaction transaction;

    public SesBean() {
        HttpSession miSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        miSession.setMaxInactiveInterval(1200000);
        anos = new Anos();
        profesor = new Profesores();
    }

    ///Se encarga de loguear en el sistema
    public void autenticar() throws IOException {
        this.session = null;
        this.transaction = null;
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            DaoUsuarios daoUsuarios = new DaoUsuarios();
            DaoAnos daoAnos = new DaoAnos();
            DaoProfesores daoProfesores = new DaoProfesores();
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            //Recuperar aquí el último año ingresado al sistema
            anos = daoAnos.getLastAno(this.session);
            //Aquí se recupera el usuario logueado
            user = daoUsuarios.getUser(this.session, this.strMail);
            if (user != null) {
                /* compara la contraseña guardada en la BBDD con la tipeada */
                if (user.getUserpass() != null && user.getUserpass().equals(Encrypt.sha512(this.userPass))) {
                    HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(true);
                    httpSession.setAttribute("userMail", this.strMail);
                    httpSession.setAttribute("iProfile", user.getIdUser());
                    httpSession.setAttribute("iAno", anos.getIdAno());
                    httpSession.setAttribute("ano", anos.getAno());
                    profesor = daoProfesores.getProfesByMail(this.session, this.strMail);
                    httpSession.setAttribute("nameUser", profesor.getNomProfe() + " " + profesor.getPaterProfe());

                    context.getExternalContext().redirect(Utilidades.basePathLogin() + "pages/home.piaget");
                } else {
                    this.strMail = null;
                    this.userPass = null;
                    context.getExternalContext().redirect(Utilidades.basePathLogin() + "errorLogin.piaget");
                }
                this.transaction.commit();
            } else {
                this.strMail = null;
                this.userPass = null;
                context.getExternalContext().redirect(Utilidades.basePathLogin() + "errorLogin.piaget");
            }
        } catch (HibernateException eX) {
            if (this.transaction != null) {
                this.transaction.rollback();
            }
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal" + eX.getMessage(), "Pongase en contacto con el Administrador"));

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

    /* Se encarga de cerrar la session */
    public String closeSession() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            this.strMail = null;
            this.userPass = null;

            HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(true);
            /* Borra las sesiones q se han almacenado */
            httpSession.invalidate();

        } catch (Exception eX) {

        }
        return "/index";
    }

    ///getter and setters
    /**
     * Get the value of anos
     *
     * @return the value of anos
     */
    public Anos getAnos() {
        return anos;
    }

    /**
     * Set the value of anos
     *
     * @param anos new value of anos
     */
    public void setAnos(Anos anos) {
        this.anos = anos;
    }

    /**
     * Get the value of profesor
     *
     * @return the value of profesor
     */
    public Profesores getProfesor() {
        return profesor;
    }

    /**
     * Set the value of profesor
     *
     * @param profesor new value of profesor
     */
    public void setProfesor(Profesores profesor) {
        this.profesor = profesor;
    }

    /**
     * Get the value of user
     *
     * @return the value of user
     */
    public Usuarios getUser() {
        return user;
    }

    /**
     * Set the value of user
     *
     * @param user new value of user
     */
    public void setUser(Usuarios user) {
        this.user = user;
    }

    /**
     * Get the value of strMail
     *
     * @return the value of strMail
     */
    public String getStrMail() {
        return strMail;
    }

    /**
     * Set the value of strMail
     *
     * @param strMail new value of strMail
     */
    public void setStrMail(String strMail) {
        this.strMail = strMail;
    }

    /**
     * Get the value of userPass
     *
     * @return the value of userPass
     */
    public String getUserPass() {
        return userPass;
    }

    /**
     * Set the value of userPass
     *
     * @param userPass new value of userPass
     */
    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

}
