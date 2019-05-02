package com.piaget.Dao;

import com.piaget.Interface.InterfaceComunas;
import com.piaget.pojo.Comunas;
import com.piaget.pojo.Provincias;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

/**
 *
 * @author Marcelo
 */
public class DaoComunas implements InterfaceComunas {

    @Override
    public boolean register(Session session, Comunas comunas) throws HibernateException {
        try {
            session.save(comunas);
            return true;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public List<Comunas> getComunasByProvincias(Session session, Provincias provincias) throws HibernateException {
        try {
            Criteria criteria = session.createCriteria(Comunas.class);
            criteria.add(Restrictions.eq("provincias", provincias));
            return criteria.list();
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public List<Comunas> getAllComunas(Session session) throws HibernateException {
        try {
            Criteria criteria = session.createCriteria(Comunas.class);
            criteria.setProjection(Projections.projectionList()
                    //definimos los campos a desplegar
                    .add(Projections.property("codcomuna"), "codcomuna")
                    .add(Projections.property("comuna"), "comuna")
            )
                    .setResultTransformer(Transformers.aliasToBean(Comunas.class));
            @SuppressWarnings("unchecked")
            ArrayList<Comunas> lista = (ArrayList<Comunas>) criteria.list();
            return lista;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public Comunas getByIdComuna(Session session, Short iComuna) throws HibernateException {
        try {
            return (Comunas) session.get(Comunas.class, iComuna);
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public boolean delComunas(Session session, Comunas comunas) throws Exception {
        try {
            session.delete(comunas);
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
