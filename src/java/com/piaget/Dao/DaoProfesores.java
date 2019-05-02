package com.piaget.Dao;

import com.piaget.Interface.InterfaceProfesores;
import com.piaget.pojo.Profesores;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Marcelo
 */
public class DaoProfesores implements InterfaceProfesores {

    @Override
    public boolean register(Session session, Profesores profesores) throws HibernateException {
        try {
            session.save(profesores);
            return true;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public List<Profesores> getAllProfes(Session session) throws HibernateException {
        try {
            String hql = "SELECT p FROM Profesores p join fetch p.profiles r ORDER BY p.paterProfe, p.materProfe";
            Query query = session.createQuery(hql);
            @SuppressWarnings("unchecked")
            ArrayList<Profesores> lista = (ArrayList<Profesores>) query.list();
            return lista;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public List<Profesores> getOprofes(Session session) throws HibernateException {
        try {
            Short value = 2;
            String hql = "SELECT p FROM Profesores p join fetch p.profiles r WHERE (r.idProfile=:value) ORDER BY p.paterProfe, p.materProfe";
            Query query = session.createQuery(hql);
            query.setParameter("value", value);
            @SuppressWarnings("unchecked")
            ArrayList<Profesores> lista = (ArrayList<Profesores>) query.list();
            return lista;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }
    
    

    @Override
    public Profesores getProfesById(Session session, String idProfe) throws HibernateException {
        try {
            return (Profesores) session.get(Profesores.class, idProfe);
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public Profesores getProfesByRun(Session session, String runProfe) throws HibernateException {
        try {
            Criteria criteria = session.createCriteria(Profesores.class);
            criteria.add(Restrictions.eq("runProfe", runProfe));
            Profesores profesores = (Profesores) criteria.setMaxResults(1).uniqueResult();
            return profesores;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public Profesores getProfesByMail(Session session, String mailProfe) throws HibernateException {
        try {
            Criteria criteria = session.createCriteria(Profesores.class);
            criteria.add(Restrictions.eq("emailProfe", mailProfe));
            Profesores profesores = (Profesores) criteria.setMaxResults(1).uniqueResult();
            return profesores;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public boolean updateProfe(Session session, String iProfe, String iNom, String iPater, String iMater, String iDire, int iFono, String iMail) throws HibernateException {
        try {
            String hql = "UPDATE Profesores set nomProfe=:iNom, paterProfe=:iPater,materProfe=:iMater,fonoProfe=:iFono,addressProfe=:iDire,emailProfe=:iMail WHERE idProfe=:iProfe";
            Query query = session.createQuery(hql);
            query.setParameter("iProfe", iProfe);
            query.setParameter("iNom", iNom);
            query.setParameter("iPater", iPater);
            query.setParameter("iMater", iMater);
            query.setParameter("iDire", iDire);
            query.setParameter("iFono", iFono);
            query.setParameter("iMail", iMail);
            int resul = query.executeUpdate();
            return true;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    /* Se encarga de manejar las excepciones en el Dao */
    private void manejaExcepcion(HibernateException he) throws HibernateException {
        throw new HibernateException("Ocurrio un error en la capa de acceso de datos " + he.getMessage(), he);
    }

}
