package refugiodeanimales.accesoadatos;

import java.util.*;
import java.sql.*;
import refugiodeanimales.entidadesdenegocio.*;

public class TipoDAL {
    static String obtenerCampos() {
        return "t.Id, t.Nombre";
    }
    
    private static String obtenerSelect(Tipo pTipo) {
        String sql;
        sql = "SELECT ";
        if (pTipo.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {            
            sql += "TOP " + pTipo.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + " FROM Tipo t");
        return sql;
    }
    
    private static String agregarOrderBy(Tipo pTipo) {
        String sql = " ORDER BY t.Id DESC";
        if (pTipo.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.MYSQL) {
            sql += " LIMIT " + pTipo.getTop_aux() + " ";
        }
        return sql;
    }
    
    public static int crear(Tipo pTipo) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { 
            sql = "INSERT INTO Tipo(Nombre) VALUES(?)";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setString(1, pTipo.getNombre());
//                ps.setByte(2, pTipo.getEstatus());
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
    
    public static int modificar(Tipo pTipo) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
            sql = "UPDATE Tipo SET Nombre=? WHERE Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setString(1, pTipo.getNombre());
//                ps.setByte(2, pTipo.getEstatus());
                ps.setInt(3, pTipo.getId());
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
    
    public static int eliminar(Tipo pTipo) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
            sql = "DELETE FROM Tipo WHERE Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pTipo.getId());
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
    
    static int asignarDatosResultSet(Tipo pTipo, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        pTipo.setId(pResultSet.getInt(pIndex));
        pIndex++;
        pTipo.setNombre(pResultSet.getString(pIndex));
        return pIndex;
//        pTipo.setEstatus(pResultSet.getByte(pIndex)); 
//        return pIndex;
    }
    
    private static void obtenerDatos(PreparedStatement pPS, ArrayList<Tipo> pTipos) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) {
            while (resultSet.next()) {
                Tipo tipo = new Tipo(); 
                asignarDatosResultSet(tipo, resultSet, 0);
                pTipos.add(tipo);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    public static Tipo obtenerPorId(Tipo pTipo) throws Exception {
        Tipo tipo = new Tipo();
        ArrayList<Tipo> tipos = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) { 
            String sql = obtenerSelect(pTipo);
            sql += " WHERE t.Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pTipo.getId());
                obtenerDatos(ps, tipos);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        
        if (tipos.size() > 0) {
            tipo = tipos.get(0);
        }
        
        return tipo;
    }
    
    public static ArrayList<Tipo> obtenerTodos() throws Exception {
        ArrayList<Tipo> tipos = new ArrayList<>();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(new Tipo());
            sql += agregarOrderBy(new Tipo());
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                obtenerDatos(ps, tipos);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } 
        catch (SQLException ex) {
            throw ex;
        }
        
        return tipos;
    }
    
    static void querySelect(Tipo pTipo, ComunDB.utilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement();
        if (pTipo.getId() > 0) {
            pUtilQuery.AgregarNumWhere(" t.Id=? ");
            if (statement != null) { 
                statement.setInt(pUtilQuery.getNumWhere(), pTipo.getId()); 
            }
        }

        if (pTipo.getNombre() != null && pTipo.getNombre().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" t.Nombre LIKE ? "); 
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pTipo.getNombre() + "%"); 
            }
        }
        
//        if (pTipo.getEstatus() > 0) {
//            pUtilQuery.AgregarNumWhere(" t.Estatus=? ");
//            if (statement != null) {
//                statement.setInt(pUtilQuery.getNumWhere(), pTipo.getEstatus());
//            }
//        }
    }
    
    public static ArrayList<Tipo> buscar(Tipo pTipo) throws Exception {
        ArrayList<Tipo> tipos = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pTipo);
            ComunDB comundb = new ComunDB();
            ComunDB.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0); 
            querySelect(pTipo, utilQuery);
            sql = utilQuery.getSQL(); 
            sql += agregarOrderBy(pTipo);
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0); 
                querySelect(pTipo, utilQuery);
                obtenerDatos(ps, tipos);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        return tipos;
    }
}
