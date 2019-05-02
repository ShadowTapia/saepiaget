package com.piaget.Interface;

import com.piaget.pojo.Alumnos;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Marcelo
 */
public interface InterfaceAlumnos {

    public boolean register(Session session, Alumnos alumnos) throws Exception;

    public Alumnos getBymatricula(Session session, Short iano, int runx) throws Exception;

    public Alumnos getByFoundMat(Session session, Short iano, Short nmat) throws Exception;

    public List<Alumnos> getAlumnosbycurso(Session session, Short iano, Short icurso) throws Exception;
}
