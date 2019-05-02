package com.piaget.BeanView;

import com.piaget.Clases.Utilidades;
import com.piaget.Dao.DaoAlumnos;
import com.piaget.Dao.DaoAnos;
import com.piaget.Dao.DaoComunas;
import com.piaget.Dao.DaoCursos;
import com.piaget.Dao.DaoMatricula;
import com.piaget.HibernateUtil.HibernateUtil;
import com.piaget.pojo.Alumnos;
import com.piaget.pojo.Anos;
import com.piaget.pojo.Cursos;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
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
@ViewScoped
public class MbVAlumnos implements Serializable {

    /**
     * Creates a new instance of MbVAlumnos
     */
    private Alumnos alumnos;
    private Cursos cursos;
    private Anos anos;

    private List<Alumnos> listaAlumnos;
    private String cursoSelected;
    private int numMasculino;
    private int numFemenino;
    private String nomProfesor;
    private Short codComuna;
    private String txtRuna;
    private Short nmat;

    private Session session;
    private Transaction transaction;

    @ManagedProperty(value = "#{mbVCursos}")
    private MbVCursos mbVCursos = new MbVCursos();

    public MbVAlumnos() {
        alumnos = new Alumnos();
        this.alumnos.setIdalumno("");
    }

    ///Función que devuelve la cantidad de alumnos según su sexo 1=Masculino 2=Femenino
    public int countSex(List<Alumnos> listaRecorrer, Object value) {
        int sexReg = 0;
        try {
            for (int i = 0; i < listaRecorrer.size(); i++) {
                if (listaRecorrer.get(i).getSexo().equals(value)) {
                    sexReg++;
                }
            }
        } catch (Exception eX) {
            /* Do something */
        }
        return sexReg;
    }
    
    //Se encarga de registrar un alumno
    public void regisAlumno() {
        this.session = null;
        this.transaction = null;
        FacesContext ctx = FacesContext.getCurrentInstance();        
        try {
            if (Utilidades.ValiRutInt(txtRuna) != 0) {
                DaoAlumnos daoAlumnos = new DaoAlumnos();
                DaoComunas daoComunas = new DaoComunas();
                DaoAnos daoAnos = new DaoAnos();
                this.session = HibernateUtil.getSessionFactory().openSession();
                this.transaction = session.beginTransaction();
                HttpSession miSession = (HttpSession) ctx.getExternalContext().getSession(true);
                //Validamos si el N° de matricula ya fue asignado a otro alumno dentro del año en curso
                if (daoAlumnos.getByFoundMat(session, (Short) miSession.getAttribute("iAno"), nmat) != null) {
                    ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Este N° de Matrícula ya existe en este año.", " Favor de verificar.-"));
                } else {
                    if (daoAlumnos.getBymatricula(session, (Short) miSession.getAttribute("iAno"), Utilidades.ValiRutInt(txtRuna)) != null) {
                        ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Alumno encontrado", "Ya posee un N° de matrícula para este año, Favor de verificar.-"));
                    } else {
                        this.alumnos.setAnos(daoAnos.getByIdAno(session, (Short) miSession.getAttribute("iAno")));
                        this.alumnos.setRunalumno(Utilidades.ValiRutInt(txtRuna));
                        this.alumnos.setDigitoal(Utilidades.ValDigito(txtRuna));
                        this.alumnos.setNummatricula(nmat);
                        this.alumnos.setComunas(daoComunas.getByIdComuna(session, codComuna));

                        daoAlumnos.register(this.session, this.alumnos);
                        this.transaction.commit();
                        ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ingreso exitoso!!", "Se ha agregado un nuevo alumno(a).-"));
                                                
                        this.alumnos = new Alumnos();
                        this.alumnos.setIdalumno("");
                        
                        this.txtRuna = "";
                        this.nmat = 0;
                    }
                }
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

    ///Procedimiento de recorrer la lista para ordenarla
    public void regisOrden(List<Alumnos> listax, Anos anox) {

        FacesContext ctx = FacesContext.getCurrentInstance();
        for (int a = 0; a < listax.size(); a++) {
            if (listax.get(a).getMatricula().getIdlista() != null) {
                //Colocar procedimiento actualización de la lista
                grabaLista(listax.get(a).getMatricula().getIdlista(), listax.get(a).getIdalumno(), anox.getIdAno());
            }
        }
        ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Orden de Alumnos", "Se ha ordenado la lista de Alumnos.-"));
    }

    ///Procedimiento que graba el orden de lista
    public void grabaLista(Short xlista, String xalumno, Short xAno) {
        this.session = null;
        this.transaction = null;
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            DaoMatricula daoMatricula = new DaoMatricula();

            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            daoMatricula.updateList(this.session, xlista, xalumno, xAno);

            this.transaction.commit();

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

    ///Se encarga de cargar la lista de alumnos
    public void chargeListalus() {
        this.session = null;
        this.transaction = null;
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            DaoAlumnos daoAlumnos = new DaoAlumnos();
            DaoCursos daoCursos = new DaoCursos();
            HttpSession httpSession = (HttpSession) ctx.getExternalContext().getSession(true);

            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            this.setListaAlumnos(daoAlumnos.getAlumnosbycurso(session, mbVCursos.getCodAno(), mbVCursos.getCodCurso()));
            this.cursos = daoCursos.getByicursoiano(this.session, mbVCursos.getCodCurso(), mbVCursos.getCodAno());
            this.nomProfesor = this.cursos.getProfesores().getNomProfe() + " " + this.cursos.getProfesores().getPaterProfe() + " " + this.cursos.getProfesores().getMaterProfe();
            this.cursoSelected = "Listado Curso " + Utilidades.labelSelected(mbVCursos.getLstCursos(), mbVCursos.getCodCurso());
            this.setNumMasculino(countSex(listaAlumnos, "1"));
            this.setNumFemenino(countSex(listaAlumnos, "2"));
            this.transaction.commit();
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

    ///Se encarga de llenar la lista de alumnos a editar
    public String buscaPupilEdit(Short iano, Short icurso) {
        this.session = null;
        this.transaction = null;
        FacesContext ctx = FacesContext.getCurrentInstance();
        Map<String, Object> sesMapobject = ctx.getExternalContext().getSessionMap();
        try {
            DaoAnos daoAnos = new DaoAnos();
            DaoCursos daoCursos = new DaoCursos();
            DaoAlumnos daoAlumnos = new DaoAlumnos();

            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            this.anos = daoAnos.getByIdAno(this.session, iano);
            this.cursos = daoCursos.getByicursoiano(session, icurso, iano);
            this.listaAlumnos = daoAlumnos.getAlumnosbycurso(this.session, iano, icurso);

            sesMapobject.put("editAnos", this.anos);
            sesMapobject.put("editCursos", this.cursos);
            sesMapobject.put("editAlumnos", this.listaAlumnos);

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
        return "editOrdenpupils?faces-redirect=true";
    }

    //Propiedades getter y setter
    /**
     * Get the value of alumnos
     *
     * @return the value of alumnos
     */
    public Alumnos getAlumnos() {
        return alumnos;
    }

    /**
     * Set the value of alumnos
     *
     * @param alumnos new value of alumnos
     */
    public void setAlumnos(Alumnos alumnos) {
        this.alumnos = alumnos;
    }

    /**
     * Get the value of listaAlumnos
     *
     * @return the value of listaAlumnos
     */
    public List<Alumnos> getListaAlumnos() {
        return listaAlumnos;
    }

    /**
     * Set the value of listaAlumnos
     *
     * @param listaAlumnos new value of listaAlumnos
     */
    public void setListaAlumnos(List<Alumnos> listaAlumnos) {
        this.listaAlumnos = listaAlumnos;
    }

    public MbVCursos getMbVCursos() {
        return mbVCursos;
    }

    public void setMbVCursos(MbVCursos mbVCursos) {
        this.mbVCursos = mbVCursos;
    }

    /**
     * Get the value of cursoSelected
     *
     * @return the value of cursoSelected
     */
    public String getCursoSelected() {
        return cursoSelected;
    }

    /**
     * Set the value of cursoSelected
     *
     * @param cursoSelected new value of cursoSelected
     */
    public void setCursoSelected(String cursoSelected) {
        this.cursoSelected = cursoSelected;
    }

    /**
     * Get the value of numMasculino
     *
     * @return the value of numMasculino
     */
    public int getNumMasculino() {
        return numMasculino;
    }

    /**
     * Set the value of numMasculino
     *
     * @param numMasculino new value of numMasculino
     */
    public void setNumMasculino(int numMasculino) {
        this.numMasculino = numMasculino;
    }

    /**
     * Get the value of numFemenino
     *
     * @return the value of numFemenino
     */
    public int getNumFemenino() {
        return numFemenino;
    }

    /**
     * Set the value of numFemenino
     *
     * @param numFemenino new value of numFemenino
     */
    public void setNumFemenino(int numFemenino) {
        this.numFemenino = numFemenino;
    }

    /**
     * Get the value of nomProfesor
     *
     * @return the value of nomProfesor
     */
    public String getNomProfesor() {
        return nomProfesor;
    }

    /**
     * Set the value of nomProfesor
     *
     * @param nomProfesor new value of nomProfesor
     */
    public void setNomProfesor(String nomProfesor) {
        this.nomProfesor = nomProfesor;
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
     * Get the value of txtRuna
     *
     * @return the value of txtRuna
     */
    public String getTxtRuna() {
        return txtRuna;
    }

    /**
     * Set the value of txtRuna
     *
     * @param txtRuna new value of txtRuna
     */
    public void setTxtRuna(String txtRuna) {
        this.txtRuna = txtRuna;
    }

    /**
     * Get the value of nmat
     *
     * @return the value of nmat
     */
    public Short getNmat() {
        return nmat;
    }

    /**
     * Set the value of nmat
     *
     * @param nmat new value of nmat
     */
    public void setNmat(Short nmat) {
        this.nmat = nmat;
    }

}
