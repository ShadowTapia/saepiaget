package com.piaget.Dao;

import com.piaget.Interface.InterfaceAnos;
import com.piaget.pojo.Anos;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;

/**
 *
 * @author Marcelo
 */
public class DaoAnos implements InterfaceAnos {

    @Override
    public boolean register(Session session, Anos anos) throws HibernateException {
        try {
            session.save(anos);
            return true;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public List<Anos> getAnos(Session session) throws HibernateException {
        try {
            Criteria criteria = session.createCriteria(Anos.class);
            criteria.setProjection(Projections.projectionList()
                    //Definimos los campos a desplegar
                    .add(Projections.property("idAno"), "idAno")
                    .add(Projections.property("ano"), "ano")
                    .add(Projections.property("finicio"), "finicio")
                    .add(Projections.property("ftermino"), "ftermino")
                    .add(Projections.property("situacion"), "situacion")
                    .add(Projections.property("regimen"), "regimen")
            )
                    //Asignamos el orden en q se desplegaran los registros
                    .addOrder(Order.desc("ano"))
                    .setResultTransformer(Transformers.aliasToBean(Anos.class));
            @SuppressWarnings("unchecked")
            ArrayList<Anos> lista = (ArrayList<Anos>) criteria.list();
            return lista;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public Anos getLastAno(Session session) throws HibernateException {
        try {
            String hql = "SELECT a FROM Anos a ORDER BY a.idAno DESC";
            Query query = session.createQuery(hql);
            Anos anos = (Anos) query.uniqueResult();
            return anos;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public Anos getByIdAno(Session session, Short iAno) throws HibernateException {
        try {
            String hql = "SELECT a FROM Anos a WHERE (a.idAno=:iAno)";
            Query query = session.createQuery(hql);
            query.setParameter("iAno", iAno);
            Anos anos = (Anos) query.uniqueResult();
            return anos;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public boolean updateAno(Session session, Date dateInicio, Date dateTermino, String situation, Short iYear) throws HibernateException {
        try {
            String hql = "UPDATE Anos set finicio=:dateInicio, ftermino=:dateTermino, situacion=:situation WHERE idAno=:iYear";
            Query query = session.createQuery(hql);
            query.setParameter("dateInicio", dateInicio);
            query.setParameter("dateTermino", dateTermino);
            query.setParameter("situation", situation);
            query.setParameter("iYear", iYear);
            int resul = query.executeUpdate();
            return true;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        }
    }

    @Override
    public boolean delAno(Session session, Anos anos) throws HibernateException {
        try {
            session.delete(anos);
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
