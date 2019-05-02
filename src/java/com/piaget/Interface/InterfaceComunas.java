package com.piaget.Interface;

import com.piaget.pojo.Comunas;
import com.piaget.pojo.Provincias;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Marcelo
 */
public interface InterfaceComunas {

    public boolean register(Session session, Comunas comunas) throws Exception;

    public List<Comunas> getAllComunas(Session session) throws Exception;
    
    public List<Comunas>getComunasByProvincias(Session session, Provincias provincias)throws Exception;

    public Comunas getByIdComuna(Session session, Short iComuna) throws Exception;

    public boolean delComunas(Session session, Comunas comunas) throws Exception;
}
