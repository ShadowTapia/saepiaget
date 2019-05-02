package com.piaget.Dao;

import com.piaget.Interface.InterfaceCursos;
import com.piaget.pojo.Cursos;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Marcelo
 */
public class DaoCursos implements InterfaceCursos {

    @Override
    public boolean register(Session session, Cursos cursos) throws HibernateException {
        try {
            session.save(cursos);
            return true;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public Cursos getByidcurso(Session session, Short Code) throws HibernateException {
        try {
            return (Cursos) session.get(Cursos.class, Code);
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public Cursos getByicursoiano(Session session, Short code, Short iano) throws HibernateException {
        try {
            String hql = "SELECT c FROM Cursos c join fetch c.profesores p join fetch c.anos a WHERE (c.idcurso=:code)AND(a.idAno=:iano)";
            Query query = session.createQuery(hql);
            query.setParameter("code", code);
            query.setParameter("iano", iano);
            Cursos cursos = (Cursos) query.setMaxResults(1).uniqueResult();
            return cursos;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public List<Cursos> getAllCursos(Session session, Short iano) throws HibernateException {
        try {
            String hql = "SELECT c FROM Cursos c join fetch c.tensenanza t WHERE id_ano=:iano ORDER BY c.orden";
            Query query = session.createQuery(hql);
            query.setParameter("iano", iano);
            @SuppressWarnings("unchecked")
            ArrayList<Cursos> lista = (ArrayList<Cursos>) query.list();
            return lista;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public boolean modOrden(Session session, Byte zOrden, Short zCurso) throws HibernateException {
        try {
            String hql = "UPDATE Cursos set orden=:zOrden WHERE idcurso=:zCurso";
            Query query = session.createQuery(hql);
            query.setParameter("zOrden", zOrden);
            query.setParameter("zCurso", zCurso);
            int resul = query.executeUpdate();
            return true;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public boolean updateCursos(Session session, String xnomcurso, char xletra, String xprofe, Short xsede, Short xcurso) throws HibernateException {
        try {
            String hql;
            hql = "UPDATE Cursos set nomcurso=:xnomcurso, letra=:xletra, id_profe=:xprofe, idsede=:xsede WHERE idcurso=:xcurso";
            Query query = session.createQuery(hql);
            query.setParameter("xnomcurso", xnomcurso);
            query.setParameter("xletra", xletra);
            query.setParameter("xprofe", xprofe);
            query.setParameter("xsede", xsede);
            query.setParameter("xcurso", xcurso);
            int resul = query.executeUpdate();
            return true;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public boolean deleteCur(Session session, Cursos cursos) throws HibernateException {
        try {
            session.delete(cursos);
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
