package com.piaget.Dao;

import com.piaget.Interface.InterfaceSedes;
import com.piaget.pojo.Sedes;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Marcelo
 */
public class DaoSedes implements InterfaceSedes {

    @Override
    public boolean register(Session session, Sedes sedes) throws HibernateException {
        try {
            session.save(sedes);
            return true;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public List<Sedes> getAllSedes(Session session) throws HibernateException {
        try {
            String hql = "SELECT s FROM Sedes s join fetch s.comunas c ORDER BY s.namesede";
            Query query = session.createQuery(hql);
            @SuppressWarnings("unchecked")
            ArrayList<Sedes> lista = (ArrayList<Sedes>) query.list();
            return lista;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }
    
    @Override
    public Sedes getSedebycode(Session session, Short code) throws HibernateException {
        try{
          return (Sedes)session.get(Sedes.class, code);
        }catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public boolean delSedes(Session session, Sedes sedes) throws HibernateException {
        try {
            session.delete(sedes);
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
