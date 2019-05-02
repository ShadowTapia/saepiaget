package com.piaget.Interface;

import com.piaget.pojo.Sedes;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Marcelo
 */
public interface InterfaceSedes {

    public boolean register(Session session, Sedes sedes) throws Exception;

    public List<Sedes> getAllSedes(Session session) throws Exception;

    public Sedes getSedebycode(Session session, Short code) throws Exception;

    public boolean delSedes(Session session, Sedes sedes) throws Exception;
}
