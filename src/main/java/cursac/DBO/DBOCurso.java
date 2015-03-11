/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cursac.DBO;

/**
 *
 * @author Chaz
 */
public class DBOCurso {
    private int codigo;
    private String nombre;
    private int creditos;
    private int obligatorio;

    public DBOCurso(int codigo, String nombre, int creditos, int obligatorio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.creditos = creditos;
        this.obligatorio = obligatorio;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public int getObligatorio() {
        return obligatorio;
    }

    public void setObligatorio(int obligatorio) {
        this.obligatorio = obligatorio;
    }
    
    
}
