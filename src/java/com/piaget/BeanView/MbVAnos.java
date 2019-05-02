package com.piaget.BeanView;

import com.piaget.Dao.DaoAnos;
import com.piaget.HibernateUtil.HibernateUtil;
import com.piaget.pojo.Anos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
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
@RequestScoped
public class MbVAnos implements Serializable {

    /**
     * Creates a new instance of MbVAnos
     */
    private Anos anos;
    private List<Anos> listaAnos;    
    private List<SelectItem> lstAnos;
    
    private Session session;
    private Transaction transaction;

    public MbVAnos() {
        anos = new Anos();
    }

    ///Se encarga devolver la lista completa de años
    public List<Anos> getAllAnos() {
        this.session = null;
        this.transaction = null;
        try {
            DaoAnos daoAnos = new DaoAnos();
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            this.listaAnos = daoAnos.getAnos(this.session);
            this.transaction.commit();
            return this.listaAnos;
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

    ///Se encarga de actualizar el año
    public String UpdateAno(Anos anos) {
        this.session = null;
        this.transaction = null;
        try {
            DaoAnos daoAnos = new DaoAnos();
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            daoAnos.updateAno(this.session, anos.getFinicio(), anos.getFtermino(), anos.getSituacion(), anos.getIdAno());
            this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Año actualizado con exito", "Felicidades.-"));

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
        return "anos?faces-redirect=true";
    }

    ///Se encarga de buscar el año segun su id
    public String buscaAnoEdit(Short iAno) throws Exception {
        this.session = null;
        this.transaction = null;
        Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        try {
            DaoAnos daoAnos = new DaoAnos();
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            this.anos = daoAnos.getByIdAno(this.session, iAno);

            sessionMapObj.put("editRecordObj", this.anos);

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
        return "anosEdit?faces-redirect=true";
    }

    /**
     * Get the value of listaAnos
     *
     * @return the value of listaAnos
     */
    public List<Anos> getListaAnos() {
        return listaAnos;
    }

    /**
     * Set the value of listaAnos
     *
     * @param listaAnos new value of listaAnos
     */
    public void setListaAnos(List<Anos> listaAnos) {
        this.listaAnos = listaAnos;
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
     * Get the value of lstAnos
     *
     * @return the value of lstAnos
     */
    public List<SelectItem> getLstAnos() {
        this.session=null;
        this.transaction=null;
        FacesContext ctx = FacesContext.getCurrentInstance();
        try{
            this.lstAnos = new ArrayList<>();
            DaoAnos daoAnos = new DaoAnos();
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            
            List<Anos> xanos;
            xanos=daoAnos.getAnos(session);
            this.transaction.commit();
            
            lstAnos.clear();
            //ojo aca se ocupa un converter para cambiar el objeto ano.getAno a String
            xanos.stream().map((Anos ano)-> new SelectItem(ano.getIdAno(),Integer.toString(ano.getAno()))).forEach((SelectItem)->{
                this.lstAnos.add(SelectItem);
            });
            
            return lstAnos;
        }catch (HibernateException eX) {
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
     * Set the value of lstAnos
     *
     * @param lstAnos new value of lstAnos
     */
    public void setLstAnos(List<SelectItem> lstAnos) {
        this.lstAnos = lstAnos;
    }

}
