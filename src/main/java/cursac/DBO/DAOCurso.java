/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cursac.DBO;

import cursac.cursosusac.ConexionMySQL;
import java.sql.SQLException;
import java.util.HashMap;
import javax.sql.RowSet;

/**
 *
 * @author Chaz
 */
public class DAOCurso {

    ConexionMySQL conn = new ConexionMySQL();

    public HashMap<String, DBOCurso> obtenerCursosPost(DBOCurso cursoPadre) {
        HashMap<String, DBOCurso> cursos;
        cursos = convertirDBO(conn.query("SELECT C.codigo, C.nombre, C.creditos, C.obligatorio FROM CURSO C "
                + "JOIN CURSO_PRE_POST P ON C.codigo = P.hijo WHERE P.padre = ?", 
                new Object[]{cursoPadre.getCodigo()}, new Object[]{1}));
        return cursos;
    }
    
    public HashMap<String, DBOCurso> obtenerCursos() {
        HashMap<String, DBOCurso> cursos;
        cursos = convertirDBO(conn.query("SELECT codigo, nombre, creditos, obligatorio FROM curso", 
                new Object[0], new Object[0]));
        return cursos;
    }
    
    public DBOCurso obtenerCurso(int codigo) {
        HashMap<String, DBOCurso> cursos;
        cursos = convertirDBO(conn.query("SELECT codigo, nombre, creditos, obligatorio FROM curso "
                + "WHERE codigo = ?", 
                new Object[]{codigo}, new Object[]{codigo}));
        return cursos.values().iterator().next();
    }
    
    private HashMap<String, DBOCurso> convertirDBO(RowSet setCurso) {
        HashMap<String, DBOCurso> cursos = new HashMap<String, DBOCurso>();
        try {
            while (setCurso.next()) {
                DBOCurso actual = new DBOCurso(setCurso.getInt("codigo"), setCurso.getString("nombre"), 
                        setCurso.getInt("creditos"), setCurso.getInt("obligatorio"));
                cursos.put(String.valueOf(actual.getCodigo()),actual);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage() + " | " + ex.getSQLState());
        }

        return cursos;
    }

   
}
