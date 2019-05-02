package com.piaget.Dao;

import com.piaget.Interface.InterfacePlanes;
import com.piaget.pojo.Planes;
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
public class DaoPlanes implements InterfacePlanes {

    @Override
    public boolean register(Session session, Planes planes) throws HibernateException {
        try {
            session.save(planes);
            return true;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public List<Planes> getAllPlanes(Session session) throws HibernateException {
        try {
            Criteria planesCriteria = session.createCriteria(Planes.class);
            planesCriteria.setProjection(Projections.projectionList()
                    .add(Projections.property("coddecreto"), "coddecreto")
                    .add(Projections.property("resolucion"), "resolucion")
                    .add(Projections.property("descripcion"), "descripcion")
            )
                    //Asignamos el orden de salida
                    .addOrder(Order.asc("coddecreto"))
                    .setResultTransformer(Transformers.aliasToBean(Planes.class));
            @SuppressWarnings("unchecked")
            ArrayList<Planes> lis = (ArrayList<Planes>) planesCriteria.list();
            return lis;

        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }

    }

    @Override
    public Planes getByPlanes(Session session, int iddec) throws HibernateException {
        try {
            return (Planes) session.get(Planes.class, iddec);
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
