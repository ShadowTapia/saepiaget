package com.piaget.BeanView;

import com.piaget.Dao.DaoAnos;
import com.piaget.Dao.DaoCursos;
import com.piaget.Dao.DaoProfesores;
import com.piaget.Dao.DaoSedes;
import com.piaget.Dao.DaoTensenanza;
import com.piaget.HibernateUtil.HibernateUtil;
import com.piaget.pojo.Cursos;
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
public class MbVCursos implements Serializable {

    /**
     * Creates a new instance of MbVCursos
     */
    private Cursos cursos;
    private String codProfe;
    private Short codSede;
    private String codeEnse;
    private Short codAno;
    private Short codCurso;

    private List<Cursos> listaCursos;

    private List<SelectItem> lstCursos;

    private Session session;
    private Transaction transaction;

    @ManagedProperty(value = "#{mbVPlanes}")
    private MbVPlanes mbVPlanes = new MbVPlanes();

    @ManagedProperty(value = "#{mbVEnse}")
    private MbVEnse mbVEnse = new MbVEnse();

    public MbVCursos() {
        cursos = new Cursos();
    }

    ///Se encarga de registrar un curso
    public void registerCursos() {
        this.session = null;
        this.transaction = null;
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            DaoCursos daoCursos = new DaoCursos();
            DaoAnos daoAnos = new DaoAnos();
            DaoTensenanza daoTensenanza = new DaoTensenanza();
            DaoProfesores daoProfesores = new DaoProfesores();
            DaoSedes daoSedes = new DaoSedes();
            HttpSession httpSession = (HttpSession) ctx.getExternalContext().getSession(true);

            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            this.cursos.setIdcurso(this.codSede);
            this.cursos.setAnos(daoAnos.getByIdAno(session, (Short) httpSession.getAttribute("iAno")));
            this.cursos.setTensenanza(daoTensenanza.getEnsebycode(session, this.codeEnse));
            this.cursos.setProfesores(daoProfesores.getProfesById(session, this.codProfe));
            this.cursos.setSedes(daoSedes.getSedebycode(session, this.codSede));

            daoCursos.register(this.session, this.cursos);

            this.transaction.commit();
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Curso creado", "Se ha ingresado un nuevo Curso.-"));
            this.cursos = new Cursos();

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

    ///Se encarga de actualizar el curso
    public String updaCursos(Cursos xcurso) {
        this.session = null;
        this.transaction = null;
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            if (xcurso.getNomcurso() != null && xcurso.getLetra() != null && this.codProfe != null && this.codSede > 0 && xcurso.getIdcurso() > 0) {
                DaoCursos daoCursos = new DaoCursos();
                this.session = HibernateUtil.getSessionFactory().openSession();
                this.transaction = session.beginTransaction();
                daoCursos.updateCursos(this.session, xcurso.getNomcurso(), xcurso.getLetra(), this.codProfe, this.codSede, xcurso.getIdcurso());
                this.transaction.commit();
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Curso actualizado con exito", "Felicidades.-"));
                this.cursos = new Cursos();
            } else {
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al ingresar", "Faltan datos por completar"));
                return null;
            }
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
        return "cursos?faces-redirect=true";
    }

    ///Se encarga de iterar la tabla y enviar al metodo actualizador
    public void registerOrden() {
        for (int a = 0; a < listaCursos.size(); a++) {
            if (listaCursos.get(a).getOrden() != null) {
                //Aqui se coloca el procedimiento de actualizacion de orden               
                grabaOrden(listaCursos.get(a).getOrden(), listaCursos.get(a).getIdcurso());
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Orden de Cursos", "Se ha ordenado la lista de Cursos.-"));
    }

    ///Se encarga de borrar un curso
    public void borraCurso(Cursos cursoDel) {
        this.session = null;
        this.transaction = null;
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            DaoCursos daoCursos = new DaoCursos();
            this.cursos = cursoDel;

            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            daoCursos.deleteCur(this.session, this.cursos);

            this.transaction.commit();

            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El curso " + this.cursos.getNomcurso() + " fue Eliminado!!", "Se ha borrado un curso.-"));
            this.cursos = new Cursos();
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

    ///Se graba el nuevo orden de cursos
    public void grabaOrden(Byte orden, Short iCurso) {
        this.session = null;
        this.transaction = null;
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            DaoCursos daoCursos = new DaoCursos();

            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            daoCursos.modOrden(this.session, orden, iCurso);

            this.transaction.commit();
            this.cursos = new Cursos();

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

    public String buscaCursoEdit(Short iCurso) {
        this.session = null;
        this.transaction = null;
        FacesContext ctx = FacesContext.getCurrentInstance();
        Map<String, Object> sessionMapObject = ctx.getExternalContext().getSessionMap();
        try {
            DaoCursos daoCursos = new DaoCursos();
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            this.cursos = daoCursos.getByidcurso(this.session, iCurso);
            sessionMapObject.put("editRecObj", this.cursos);

            this.transaction.commit();
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
        return "cursosEdit?faces-redirect=true";
    }

    /* Se encarga de devolver la lista de cursos */
    public List<Cursos> getTodosCursos() {
        this.session = null;
        this.transaction = null;
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            DaoCursos daoCursos = new DaoCursos();
            HttpSession httpSession = (HttpSession) ctx.getExternalContext().getSession(true);
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            this.listaCursos = daoCursos.getAllCursos(this.session, (Short) httpSession.getAttribute("iAno"));
            this.transaction.commit();

            return listaCursos;
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

    //Propiedades getter and setter
    /**
     * Get the value of listaCursos
     *
     * @return the value of listaCursos
     */
    public List<Cursos> getListaCursos() {
        return listaCursos;
    }

    /**
     * Set the value of listaCursos
     *
     * @param listaCursos new value of listaCursos
     */
    public void setListaCursos(List<Cursos> listaCursos) {
        this.listaCursos = listaCursos;
    }

    /**
     * Get the value of cursos
     *
     * @return the value of cursos
     */
    public Cursos getCursos() {
        return cursos;
    }

    /**
     * Set the value of cursos
     *
     * @param cursos new value of cursos
     */
    public void setCursos(Cursos cursos) {
        this.cursos = cursos;
    }

    public MbVPlanes getMbVPlanes() {
        return mbVPlanes;
    }

    public void setMbVPlanes(MbVPlanes mbVPlanes) {
        this.mbVPlanes = mbVPlanes;
    }

    public MbVEnse getMbVEnse() {
        return mbVEnse;
    }

    public void setMbVEnse(MbVEnse mbVEnse) {
        this.mbVEnse = mbVEnse;
    }

    /**
     * Get the value of codProfe
     *
     * @return the value of codProfe
     */
    public String getCodProfe() {
        return codProfe;
    }

    /**
     * Set the value of codProfe
     *
     * @param codProfe new value of codProfe
     */
    public void setCodProfe(String codProfe) {
        this.codProfe = codProfe;
    }

    /**
     * Get the value of codSede
     *
     * @return the value of codSede
     */
    public Short getCodSede() {
        return codSede;
    }

    /**
     * Set the value of codSede
     *
     * @param codSede new value of codSede
     */
    public void setCodSede(Short codSede) {
        this.codSede = codSede;
    }

    /**
     * Get the value of codeEnse
     *
     * @return the value of codeEnse
     */
    public String getCodeEnse() {
        return codeEnse;
    }

    /**
     * Set the value of codeEnse
     *
     * @param codeEnse new value of codeEnse
     */
    public void setCodeEnse(String codeEnse) {
        this.codeEnse = codeEnse;
    }

    /**
     * Get the value of codAno
     *
     * @return the value of codAno
     */
    public Short getCodAno() {
        return codAno;
    }

    /**
     * Set the value of codAno
     *
     * @param codAno new value of codAno
     */
    public void setCodAno(Short codAno) {
        this.codAno = codAno;
    }

    /**
     * Get the value of lstCursos
     *
     * @return the value of lstCursos
     */
    public List<SelectItem> getLstCursos() {
        this.session = null;
        this.transaction = null;
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            this.lstCursos = new ArrayList<>();
            DaoCursos daoCursos = new DaoCursos();

            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            List<Cursos> curso;
            curso = daoCursos.getAllCursos(session, this.codAno);
            this.transaction.commit();

            lstCursos.clear();

            curso.stream().map((csr) -> new SelectItem(csr.getIdcurso(), csr.getNomcurso() + " " + csr.getLetra())).forEach((SelectItem) -> {
                this.lstCursos.add(SelectItem);
            });

            return lstCursos;

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
     * Set the value of lstCursos
     *
     * @param lstCursos new value of lstCursos
     */
    public void setLstCursos(List<SelectItem> lstCursos) {
        this.lstCursos = lstCursos;
    }

    /**
     * Get the value of codCurso
     *
     * @return the value of codCurso
     */
    public Short getCodCurso() {
        return codCurso;
    }

    /**
     * Set the value of codCurso
     *
     * @param codCurso new value of codCurso
     */
    public void setCodCurso(Short codCurso) {
        this.codCurso = codCurso;
    }
}
