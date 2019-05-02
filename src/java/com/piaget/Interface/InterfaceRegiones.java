package com.piaget.Interface;

import com.piaget.pojo.Regiones;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Marcelo
 */
public interface InterfaceRegiones {

    public boolean register(Session session, Regiones regiones) throws Exception;

    public List<Regiones> getAllRegion(Session session) throws Exception;

    public Regiones getRegionByCode(Session session, Short iCodRegion) throws Exception;

    public boolean delRegiones(Session session, Regiones regiones) throws Exception;
}
