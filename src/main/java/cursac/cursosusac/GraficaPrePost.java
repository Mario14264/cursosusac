/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cursac.cursosusac;

import cursac.DBO.DAOCurso;
import cursac.DBO.DBOCurso;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Chaz
 */
public class GraficaPrePost {

    DBOCurso curso;

    public GraficaPrePost(DBOCurso curso) {
        this.curso = curso;
    }

    public void escribirArchivo() {
        FileWriter fichero = null;
        PrintWriter pw = null;
        DAOCurso connCurso = null;
        try {
            connCurso = new DAOCurso();
            Collection<DBOCurso> cursosPost = connCurso.obtenerCursosPost(curso).values();

            fichero = new FileWriter("c:\\grafica.txt");
            pw = new PrintWriter(fichero);
            pw.println("diagraph G{");
            pw.println("nodesep=.05;");
            pw.println("rankdir=LR;");
            pw.println("node [shape=box];");
            pw.println("node0 [label=\"" + curso.getNombre() + "\n Créditos: " + curso.getCreditos()
                    + "\n Categoria: " + getCategoria(curso.getObligatorio()) + "\" ,height=2.5];");
            int i = 1;
            for (DBOCurso cursoP : cursosPost) {
                String nombreNodo = "node" + i;

                pw.println(nombreNodo + " [label=\"" + cursoP.getNombre() + "\n Créditos: " + cursoP.getCreditos()
                        + "\n Categoria: " + getCategoria(cursoP.getObligatorio()) + "\"];");

                pw.println("node0 -> " + nombreNodo + ";");
                i++;
                int j = 1;
                Collection<DBOCurso> cursosPostPost = connCurso.obtenerCursosPost(cursoP).values();
                for (DBOCurso cursoPP : cursosPostPost) {
                    String nombreNodoPost = "node" + i + "P" + j;
                    pw.println(nombreNodoPost + " [label=\"" + cursoPP.getNombre() + "\n Créditos: " + cursoPP.getCreditos()
                            + "\n Categoria: " + getCategoria(cursoPP.getObligatorio()) + "\"];");
                    pw.println(nombreNodo + " -> " + nombreNodoPost + ";");
                    j++;
                }

            }
            pw.println("}");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("");
        }
    }

    public ImageIcon generarGrafica() {
        escribirArchivo();
        try {
            String dotPath = "C:\\Program Files (x86)\\Graphviz 2.28\\bin\\dot.exe";

            String fileInputPath = "c:\\grafica.txt";
            String fileOutputPath = "c:\\grafica.jpg";

            String tParam = "-Tjpg";
            String tOParam = "-o";

            String[] cmd = new String[5];
            cmd[0] = dotPath;
            cmd[1] = tParam;
            cmd[2] = fileInputPath;
            cmd[3] = tOParam;
            cmd[4] = fileOutputPath;

            Runtime rt = Runtime.getRuntime();

            rt.exec(cmd);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            System.out.println("");
        }
        
        return new ImageIcon("c:\\grafica.jpg");
    }

    public String getCategoria(int obligatorio) {
        if (obligatorio == 1) {
            return "Obligatorio";
        }
        return "No obligatorio";
    }
}
