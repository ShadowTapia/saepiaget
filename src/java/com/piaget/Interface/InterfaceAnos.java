package com.piaget.Interface;

import com.piaget.pojo.Anos;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Marcelo
 */
public interface InterfaceAnos {

    public boolean register(Session session, Anos anos) throws Exception;

    public List<Anos> getAnos(Session session) throws Exception;

    public Anos getByIdAno(Session session, Short iAno) throws Exception;

    public Anos getLastAno(Session session) throws Exception;

    public boolean updateAno(Session session, Date dateInicio, Date dateTermino, String situation, Short iYear) throws Exception;

    public boolean delAno(Session session, Anos anos) throws Exception;
}
