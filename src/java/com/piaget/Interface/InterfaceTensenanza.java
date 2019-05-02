package com.piaget.Interface;

import com.piaget.pojo.Planes;
import com.piaget.pojo.Tensenanza;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Marcelo
 */
public interface InterfaceTensenanza {

    public boolean register(Session session, Tensenanza tense) throws Exception;

    public List<Tensenanza> getAllEnse(Session session) throws Exception;

    public List<Tensenanza> getByCodPlan(Session session, Planes iPlan) throws Exception;

    public Tensenanza getEnsebycode(Session session, String code) throws Exception;

    public boolean delEnse(Session session, Tensenanza tense) throws Exception;
}
