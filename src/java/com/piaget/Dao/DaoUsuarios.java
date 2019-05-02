package com.piaget.Dao;

import com.piaget.Interface.InterfaceUsuarios;
import com.piaget.pojo.Usuarios;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Marcelo
 */
public class DaoUsuarios implements InterfaceUsuarios {

    @Override
    public boolean register(Session session, Usuarios usuarios) throws HibernateException {
        try {
            session.save(usuarios);
            return true;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public List<Usuarios> getUsuarios(Session session) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Usuarios getUser(Session session, String mailUser) throws HibernateException {
        try {
            String hql = "SELECT u FROM Usuarios u join fetch u.profesores p WHERE (p.emailProfe=:mailUser)";
            Query query = session.createQuery(hql);
            query.setParameter("mailUser", mailUser);
            Usuarios usuarios = (Usuarios) query.setMaxResults(1).uniqueResult();
            return usuarios;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public Usuarios getByIdUser(Session session, String IdProfe) throws HibernateException {
        try {
            String hql = "SELECT u FROM Usuarios u join fetch u.profesores p WHERE (p.idProfe=:IdProfe)";
            Query query = session.createQuery(hql);
            query.setParameter("IdProfe", IdProfe);
            Usuarios usuarios = (Usuarios) query.setMaxResults(1).uniqueResult();
            return usuarios;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public boolean update(Session session, Usuarios usuarios) throws HibernateException {
        try{
            session.update(usuarios);
            return true;
        }catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    /* Se encarga de manejar las excepciones en el Dao */
    private void manejaExcepcion(HibernateException he) throws HibernateException {
        throw new HibernateException("Ocurrio un error en la capa de acceso de datos " + he.getMessage(), he);
    }
}
