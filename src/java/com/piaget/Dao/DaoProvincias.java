package com.piaget.Dao;

import com.piaget.Interface.InterfaceProvincias;
import com.piaget.pojo.Provincias;
import com.piaget.pojo.Regiones;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Marcelo
 */
public class DaoProvincias implements InterfaceProvincias {

    @Override
    public boolean register(Session session, Provincias provincias) throws HibernateException {
        try {
            session.save(provincias);
            return true;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public List<Provincias> getProvinciasByRegion(Session session, Regiones regiones) throws HibernateException {
        try {
            Criteria criteria = session.createCriteria(Provincias.class);
            criteria.add(Restrictions.eq("regiones", regiones));
            return criteria.list();
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public Provincias getProvinciaById(Session session, Short iProvi) throws HibernateException {
        try {
            return (Provincias) session.get(Provincias.class, iProvi);
            /* Retorna un solo valor de Provincias */
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public boolean delProvincia(Session session, Provincias provincias) throws HibernateException {
        try {
            session.delete(provincias);
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
