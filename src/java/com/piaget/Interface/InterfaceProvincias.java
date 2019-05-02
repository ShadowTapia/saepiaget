package com.piaget.Interface;

import com.piaget.pojo.Provincias;
import com.piaget.pojo.Regiones;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Marcelo
 */
public interface InterfaceProvincias {

    public boolean register(Session session, Provincias provincias) throws Exception;

    public List<Provincias> getProvinciasByRegion(Session session, Regiones regiones) throws Exception;

    public Provincias getProvinciaById(Session session, Short iProvi) throws Exception;

    public boolean delProvincia(Session session, Provincias provincias) throws Exception;
}
