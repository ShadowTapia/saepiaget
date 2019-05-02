package com.piaget.Dao;

import com.piaget.Interface.InterfaceMatricula;
import com.piaget.pojo.Matricula;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Marcelo
 */
public class DaoMatricula implements InterfaceMatricula {

    @Override
    public boolean register(Session session, Matricula matricula) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateList(Session session, Short zLista, String zAlumno, Short zAno) throws HibernateException {
        try {
            String hql = "UPDATE Matricula set idlista=:zLista WHERE idalumno=:zAlumno AND id_ano=:zAno";
            Query query = session.createQuery(hql);
            query.setParameter("zLista", zLista);
            query.setParameter("zAlumno", zAlumno);
            query.setParameter("zAno", zAno);
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
