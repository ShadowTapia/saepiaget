package com.piaget.Dao;

import com.piaget.Interface.InterfaceAlumnos;
import com.piaget.pojo.Alumnos;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Marcelo
 */
public class DaoAlumnos implements InterfaceAlumnos {

    @Override
    public boolean register(Session session, Alumnos alumnos) throws HibernateException {
        try {
            session.save(alumnos);
            return true;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public Alumnos getByFoundMat(Session session, Short iano, Short nmat) throws HibernateException {
        try {
            String hql = "SELECT a FROM Alumnos a join fetch a.anos y WHERE ((y.idAno=:iano)AND(a.nummatricula=:nmat))";
            Query query = session.createQuery(hql);
            query.setParameter("iano", iano);
            query.setParameter("nmat", nmat);
            Alumnos alumnos = (Alumnos) query.setMaxResults(1).uniqueResult();
            return alumnos;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public Alumnos getBymatricula(Session session, Short iano, int runx) throws HibernateException {
        try {
            String hql = "SELECT a FROM Alumnos a join fetch a.matricula m join fetch m.anos y WHERE ((y.idAno=:iano)AND(a.runalumno=:runx))";
            Query query = session.createQuery(hql);
            //seteamos los parametros de la consulta
            query.setParameter("iano", iano);
            query.setParameter("runx", runx);            
            Alumnos alumnos = (Alumnos) query.setMaxResults(1).uniqueResult();
            return alumnos;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public List<Alumnos> getAlumnosbycurso(Session session, Short iano, Short icurso) throws HibernateException {
        try {
            String hql = "SELECT a FROM Alumnos a join fetch a.matricula m join fetch m.anos y join fetch m.cursos c WHERE (y.idAno=:iano)AND(c.idcurso=:icurso) Order by m.idlista";
            Query query = session.createQuery(hql);
            query.setParameter("iano", iano);
            query.setParameter("icurso", icurso);
            ArrayList<Alumnos> lista = (ArrayList<Alumnos>) query.list();
            return lista;
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
