package com.piaget.Interface;

import com.piaget.pojo.Profiles;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Marcelo
 */
public interface InterfaceProfiles {

    public boolean register(Session session, Profiles profiles) throws Exception;

    public List<Profiles> getPerfiles(Session session) throws Exception;

    public Profiles getPerfilByCod(Session session, Short idPerfil) throws Exception;

    public boolean delete(Session session, Profiles profiles) throws Exception;

}
