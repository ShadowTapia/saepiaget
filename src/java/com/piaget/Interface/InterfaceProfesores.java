package com.piaget.Interface;

import com.piaget.pojo.Profesores;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Marcelo
 */
public interface InterfaceProfesores {

    public boolean register(Session session, Profesores profesores) throws Exception;

    public List<Profesores> getAllProfes(Session session) throws Exception;

    public List<Profesores> getOprofes(Session session) throws Exception;

    public Profesores getProfesById(Session session, String idProfe) throws Exception;

    public Profesores getProfesByRun(Session session, String runProfe) throws Exception;

    public Profesores getProfesByMail(Session session, String mailProfe) throws Exception;

    public boolean updateProfe(Session session, String iProfe, String iNom, String iPater, String iMater, String iDire, int iFono, String iMail) throws Exception;
}
