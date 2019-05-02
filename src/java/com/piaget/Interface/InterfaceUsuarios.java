package com.piaget.Interface;

import com.piaget.pojo.Usuarios;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Marcelo
 */
public interface InterfaceUsuarios {

    public boolean register(Session session, Usuarios usuarios) throws Exception;

    public List<Usuarios> getUsuarios(Session session) throws Exception;

    public Usuarios getUser(Session session, String mailUser) throws Exception;

    public Usuarios getByIdUser(Session session, String IdProfe) throws Exception;

    public boolean update(Session session, Usuarios usuarios) throws Exception;
}
