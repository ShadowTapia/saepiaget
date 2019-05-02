package com.piaget.Dao;

import com.piaget.Interface.InterfaceTensenanza;
import com.piaget.pojo.Planes;
import com.piaget.pojo.Tensenanza;
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
public class DaoTensenanza implements InterfaceTensenanza {

    @Override
    public boolean register(Session session, Tensenanza tense) throws HibernateException {
        try {
            session.save(tense);
            return true;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public List<Tensenanza> getAllEnse(Session session) throws HibernateException {
        try {
            String hql = "SELECT t FROM Tensenanza t join fetch t.planes p";
            Query query = session.createQuery(hql);
            @SuppressWarnings("unchecked")
            ArrayList<Tensenanza> lista = (ArrayList<Tensenanza>) query.list();
            return lista;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public List<Tensenanza> getByCodPlan(Session session, Planes iPlan) throws HibernateException {
        try {
            Criteria criteria = session.createCriteria(Tensenanza.class);
            criteria.add(Restrictions.eq("planes", iPlan));
            @SuppressWarnings("unchecked")
            ArrayList<Tensenanza> lista = (ArrayList<Tensenanza>) criteria.list();
            return lista;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public Tensenanza getEnsebycode(Session session, String code) throws HibernateException {
        try {
            return (Tensenanza) session.get(Tensenanza.class, code);
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }

    }

    @Override
    public boolean delEnse(Session session, Tensenanza tense) throws HibernateException {
        try {
            session.delete(tense);
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
