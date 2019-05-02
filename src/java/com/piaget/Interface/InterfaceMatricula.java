package com.piaget.Interface;

import com.piaget.pojo.Matricula;
import org.hibernate.Session;

/**
 *
 * @author Marcelo
 */
public interface InterfaceMatricula {

    public boolean register(Session session, Matricula matricula) throws Exception;

    public boolean updateList(Session session, Short zLista, String zAlumno, Short zAno) throws Exception;
}
