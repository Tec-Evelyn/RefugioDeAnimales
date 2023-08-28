package refugiodeanimales.accesoadatos;

import java.util.*;
import java.sql.*;
import refugiodeanimales.entidadesdenegocio.*;

public class GeneroDAL { 

    static String obtenerCampos() {
        return "g.Id, g.Nombre ";
    }
    private static String obtenerSelect(Genero pGenero) {
        String sql;
        sql = "SELECT "; 
        if (pGenero.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {            
            sql += "TOP " + pGenero.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + " FROM Genero g");
        return sql;   
    }
     private static String agregarOrderBy(Genero pGenero) {
        String sql = " ORDER BY g.Id DESC";
        if (pGenero.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.MYSQL) {
            sql += " LIMIT " + pGenero.getTop_aux() + " ";
        }
        return sql;
    }
      public static int crear(Genero pGenero) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { 
            sql = "INSERT INTO Genero(Nombre) VALUES(?)";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setString(1, pGenero.getNombre());
                result = ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) { 
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return result;
    }
        public static int modificar(Genero pGenero) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
            sql = "UPDATE Genero SET Nombre= ? WHERE Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setString(1, pGenero.getNombre());
                ps.setInt(2, pGenero.getId());  
                result = ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return result;
    }
        public static int eliminar(Genero pGenero) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
            sql = "DELETE FROM Genero WHERE Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pGenero.getId());
                result = ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return result;
    }
         static int asignarDatosResultSet(Genero pGenero, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        pGenero.setId(pResultSet.getInt(pIndex));
        pIndex++;
        pGenero.setNombre(pResultSet.getString(pIndex));
        pIndex++;
        return pIndex;
    }
         private static void obtenerDatos(PreparedStatement pPS, ArrayList<Genero> pGeneros) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) {
            while (resultSet.next()) {
                Genero genero = new Genero(); 
                asignarDatosResultSet(genero, resultSet, 0);
                pGeneros.add(genero);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }

        public static Genero obtenerPorId(Genero pGenero) throws Exception {
        Genero genero = new Genero();
        ArrayList<Genero> generos = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) { 
            String sql = obtenerSelect(pGenero);
            sql += " WHERE g.Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pGenero.getId());
                obtenerDatos(ps, generos);
                ps.close();
            } catch (SQLException ex) { 
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        if (generos.size() > 0) {
            genero = generos.get(0); 
        }
        return genero;
    }
        public static ArrayList<Genero> obtenerTodos() throws Exception {
        ArrayList<Genero> generos = new ArrayList<>();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(new Genero());
            sql += agregarOrderBy(new Genero());
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                obtenerDatos(ps, generos);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } 
        catch (SQLException ex) {
            throw ex;
        }
        return generos;
    }
      static void querySelect(Genero pGenero, ComunDB.utilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement();
        if (pGenero.getId() > 0) {
            pUtilQuery.AgregarNumWhere(" g.Id=? ");
            if (statement != null) { 
                statement.setInt(pUtilQuery.getNumWhere(), pGenero.getId()); 
            }
        }

        if (pGenero.getNombre() != null && pGenero.getNombre().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" g.Nombre LIKE ? "); 
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pGenero.getNombre() + "%"); 
            }
        }              
    } 
      public static ArrayList<Genero> buscar(Genero pGenero) throws Exception {
        ArrayList<Genero> generos = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pGenero);
            ComunDB comundb = new ComunDB();
            ComunDB.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0); 
            querySelect(pGenero, utilQuery);
            sql = utilQuery.getSQL(); 
            sql += agregarOrderBy(pGenero);
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0); 
                querySelect(pGenero, utilQuery);
                obtenerDatos(ps, generos);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        return generos; 
    }

}
