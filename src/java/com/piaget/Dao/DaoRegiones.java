package com.piaget.Dao;

import com.piaget.Interface.InterfaceRegiones;
import com.piaget.pojo.Regiones;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;

/**
 *
 * @author Marcelo
 */
public class DaoRegiones implements InterfaceRegiones {

    @Override
    public boolean register(Session session, Regiones regiones) throws HibernateException {
        try {
            session.save(regiones);
            return true;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public List<Regiones> getAllRegion(Session session) throws HibernateException {
        try {
            Criteria criteria = session.createCriteria(Regiones.class);
            criteria.setProjection(Projections.projectionList()
                    //Definimos los campos a desplegar
                    .add(Projections.property("codregion"), "codregion")
                    .add(Projections.property("region"), "region")
                    .add(Projections.property("orden"), "orden")
            )
                    //Asignamos el orden de despliegue
                    .addOrder(Order.asc("orden"))
                    .setResultTransformer(Transformers.aliasToBean(Regiones.class));
            @SuppressWarnings("unchecked")
            ArrayList<Regiones> lista = (ArrayList<Regiones>) criteria.list();
            return lista;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public Regiones getRegionByCode(Session session, Short iCodRegion) throws HibernateException {
        try {
            return (Regiones) session.get(Regiones.class, iCodRegion);
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public boolean delRegiones(Session session, Regiones regiones) throws HibernateException {
        try {
            session.delete(regiones);
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
