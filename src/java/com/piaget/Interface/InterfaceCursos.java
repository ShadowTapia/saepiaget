package com.piaget.Interface;

import com.piaget.pojo.Cursos;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Marcelo
 */
public interface InterfaceCursos {

    public boolean register(Session session, Cursos cursos) throws Exception;

    public List<Cursos> getAllCursos(Session session, Short iano) throws Exception;

    public Cursos getByidcurso(Session session, Short Code) throws Exception;
    
    public Cursos getByicursoiano(Session session, Short code, Short iano)throws Exception;

    public boolean modOrden(Session session, Byte zOrden, Short zCurso) throws Exception;

    public boolean updateCursos(Session session, String xnomcurso, char xletra, String xprofe, Short xsede, Short xcurso) throws Exception;

    public boolean deleteCur(Session session, Cursos cursos) throws Exception;
}
