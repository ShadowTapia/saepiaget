package com.piaget.Dao;

import com.piaget.Interface.InterfaceProfiles;
import com.piaget.pojo.Profiles;
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
public class DaoProfiles implements InterfaceProfiles {

    @Override
    public boolean register(Session session, Profiles profiles) throws HibernateException {
        try {
            session.save(profiles);
            return true;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public List<Profiles> getPerfiles(Session session) throws HibernateException {
        try {
            Criteria criteria = session.createCriteria(Profiles.class);
            criteria.setProjection(Projections.projectionList()
                    /*Definimos los campos a desplegar */
                    .add(Projections.property("idProfile"), "idProfile")
                    .add(Projections.property("tipo"), "tipo")
            )
                    //Asignamos el orden en que se desplegaran los registros
                    .addOrder(Order.asc("tipo"))
                    .setResultTransformer(Transformers.aliasToBean(Profiles.class));
            @SuppressWarnings("unchecked")
            ArrayList<Profiles> lista = (ArrayList<Profiles>) criteria.list();
            return lista;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public Profiles getPerfilByCod(Session session, Short idPerfil) throws HibernateException {
        try {
            return (Profiles) session.get(Profiles.class, idPerfil);
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }

    }

    @Override
    public boolean delete(Session session, Profiles profiles) throws HibernateException {
        try {
            session.delete(profiles);
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
