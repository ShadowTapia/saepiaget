package com.piaget.Interface;

import com.piaget.pojo.Planes;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Marcelo
 */
public interface InterfacePlanes {

    public boolean register(Session session, Planes planes) throws Exception;

    public List<Planes> getAllPlanes(Session session) throws Exception;

    public Planes getByPlanes(Session session, int iddec) throws Exception;
}
