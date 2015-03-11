/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cursac.cursosusac;

import com.sun.rowset.CachedRowSetImpl;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author Chaz
 */
public class ConexionMySQL {

    public CachedRowSet query(String query, Object[] datos, Object[] tipos) {
        String dbUrl = "jdbc:mysql://localhost/cursac";
        String dbClass = "com.mysql.jdbc.Driver";
        String username = "root";
        String password = "admin123";
        try {

            Class.forName(dbClass);
            Connection connection = DriverManager.getConnection(dbUrl,
                    username, password);
            
            PreparedStatement ps = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            for (int i = 0; i < datos.length; i++) {
                String clase = tipos[i].getClass().getSimpleName();
                if (datos[i] == null) {
                    int sqlType = java.sql.Types.INTEGER;
                    if (clase.equalsIgnoreCase("String")) {
                        sqlType = java.sql.Types.VARCHAR;
                    } else if (clase.equalsIgnoreCase("Integer")) {
                        sqlType = java.sql.Types.INTEGER;
                    } else if (clase.equalsIgnoreCase("Doble")) {
                        sqlType = java.sql.Types.NUMERIC;
                    } else if (clase.equalsIgnoreCase("Boolean")) {
                        sqlType = java.sql.Types.BOOLEAN;
                    }

                    ps.setNull(i + 1, sqlType);
                } else {
                    if (clase.equalsIgnoreCase("String")) {
                        ps.setString(i + 1, datos[i].toString());
                    } else if (clase.equalsIgnoreCase("Integer")) {
                        ps.setInt(i + 1, Integer.parseInt(datos[i].toString()));
                    } else if (clase.equalsIgnoreCase("Double")) {
                        ps.setDouble(i + 1, Double.parseDouble(datos[i].toString()));
                    } else if (clase.equalsIgnoreCase("Boolean")) {
                        ps.setBoolean(i + 1, Boolean.parseBoolean(datos[i].toString()));
                    }else{
                        ps.setString(i + 1, datos[i].toString());
                    }
                }
            }

            ResultSet rs = ps.executeQuery();

            CachedRowSet crs = new CachedRowSetImpl();
            crs.populate(rs);

            rs.close();
            ps.close();
            connection.close();

            return crs;

            
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
       return null;
    }
}
