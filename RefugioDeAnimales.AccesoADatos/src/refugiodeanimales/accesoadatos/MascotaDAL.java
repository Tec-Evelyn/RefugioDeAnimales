package refugiodeanimales.accesoadatos;

import java.util.*;
import java.sql.*;
import refugiodeanimales.entidadesdenegocio.*;

public class MascotaDAL {
    
    static String obtenerCampos() {
        return "m.Id, m.IdTipo, m.IdGenero, m.Raza, m.Nombre, m.ImagenURL, m.Estatus";
    }
    
    private static String obtenerSelect(Mascota pMascota) {
        String sql;
        sql = "SELECT ";
        if (pMascota.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {            
            sql += "TOP " + pMascota.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + " FROM Mascota m");
        return sql;
    }
    
    private static String agregarOrderBy(Mascota pMascota) {
        String sql = " ORDER BY m.Id DESC";
        if (pMascota.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.MYSQL) {
            sql += " LIMIT " + pMascota.getTop_aux() + " ";
        }
        return sql;
    }
    
    public static int crear(Mascota pMascota) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { 
            sql = "INSERT INTO Mascota(IdTipo, IdGenero, Raza, Nombre, ImagenURL, Estatus) VALUES(?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pMascota.getIdTipo());
                ps.setInt(2, pMascota.getIdGenero());
                ps.setString(3, pMascota.getRaza());
                ps.setString(4, pMascota.getNombre());
                ps.setString(5, pMascota.getImagenurl());
                ps.setByte(6, pMascota.getEstatus());
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
    
    public static int modificar(Mascota pMascota) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
            sql = "UPDATE Mascota SET IdTipo=?, IdGenero=?, Raza=?, Nombre=?, ImagenURL=?, Estatus=? WHERE Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pMascota.getIdTipo());
                ps.setInt(2, pMascota.getIdGenero());
                ps.setString(3, pMascota.getRaza());
                ps.setString(4, pMascota.getNombre());
                ps.setString(5, pMascota.getImagenurl());
                ps.setByte(6, pMascota.getEstatus());
                ps.setInt(7, pMascota.getId());
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
    
    public static int eliminar(Mascota pMascota) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
            sql = "DELETE FROM Mascota WHERE Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pMascota.getId());
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
    
    static int asignarDatosResultSet(Mascota pMascota, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        pMascota.setId(pResultSet.getInt(pIndex));
         pIndex++;
        pMascota.setIdTipo(pResultSet.getInt(pIndex));
         pIndex++;
        pMascota.setIdGenero(pResultSet.getInt(pIndex));
          pIndex++;
        pMascota.setRaza(pResultSet.getString(pIndex));
        pIndex++;
        pMascota.setNombre(pResultSet.getString(pIndex));
        pIndex++;
        pMascota.setImagenurl(pResultSet.getString(pIndex));
        pIndex++;
        pMascota.setEstatus(pResultSet.getByte(pIndex));    
        return pIndex;
    }
    
   private static void obtenerDatos(PreparedStatement pPS, ArrayList<Mascota> pMascotas) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) { 
            while (resultSet.next()) {
                Mascota mascota = new Mascota();
                asignarDatosResultSet(mascota, resultSet, 0);
                pMascotas.add(mascota);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    private static void obtenerDatosIncluirRelaciones(PreparedStatement pPS, ArrayList<Mascota> pMascotas) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) {
            HashMap<Integer, Tipo> tipoMap = new HashMap(); 
            HashMap<Integer, Genero> generoMap = new HashMap(); 
            while (resultSet.next()) {
                Mascota mascota = new Mascota();
                int index = asignarDatosResultSet(mascota, resultSet, 0);
                if (tipoMap.containsKey(mascota.getIdTipo()) == false) {
                    Tipo tipo = new Tipo();
                    TipoDAL.asignarDatosResultSet(tipo, resultSet, index);
                    tipoMap.put(tipo.getId(), tipo); 
                    mascota.setTipo(tipo); 
                } else {
                    mascota.setTipo(tipoMap.get(mascota.getIdTipo())); 
                }
                
                if (generoMap.containsKey(mascota.getIdGenero()) == false) {
                    Genero genero = new Genero();
                    GeneroDAL.asignarDatosResultSet(genero, resultSet, index+2);
                    generoMap.put(genero.getId(), genero); 
                    mascota.setGenero(genero); 
                } else {
                    mascota.setGenero(generoMap.get(mascota.getIdGenero())); 
                } 
                
                pMascotas.add(mascota); 
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex; 
        }
    }
    
    public static Mascota obtenerPorId(Mascota pMascota) throws Exception {
        Mascota mascota = new Mascota();
        ArrayList<Mascota> mascotas = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pMascota);
            sql += " WHERE m.Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pMascota.getId());
                obtenerDatos(ps, mascotas);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        if (!mascotas.isEmpty()) {
            mascota = mascotas.get(0);
        }
        return mascota;
    }
    
    public static ArrayList<Mascota> obtenerTodos() throws Exception {
        ArrayList<Mascota> mascotas = new ArrayList<>();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(new Mascota()); 
            sql += agregarOrderBy(new Mascota());
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                obtenerDatos(ps, mascotas);
                ps.close();
            } catch (SQLException ex) {
                throw ex; 
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        return mascotas;
    }
    
    static void querySelect(Mascota pMascota, ComunDB.utilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement();
        if (pMascota.getId() > 0) {
            pUtilQuery.AgregarNumWhere(" m.Id=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pMascota.getId());
            }
        }
         if (pMascota.getIdTipo() > 0) {
            pUtilQuery.AgregarNumWhere(" m.IdTipo=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pMascota.getIdTipo());
            }
        }
         if (pMascota.getIdGenero() > 0) {
            pUtilQuery.AgregarNumWhere(" m.IdGenero=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pMascota.getIdGenero());
            }
        }
         if (pMascota.getRaza() != null && pMascota.getRaza().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" m.Raza LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pMascota.getRaza() + "%");
            }
        }
        if (pMascota.getNombre() != null && pMascota.getNombre().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" m.Nombre LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pMascota.getNombre() + "%");
            }
        }
         if (pMascota.getImagenurl() != null && pMascota.getImagenurl().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" m.ImagenURL LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pMascota.getImagenurl() + "%");
            }
        }
        if (pMascota.getEstatus() > 0) {
            pUtilQuery.AgregarNumWhere(" m.Estatus=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pMascota.getEstatus());
            }
        }        
    }
    
    public static ArrayList<Mascota> buscar(Mascota pMascota) throws Exception {
        ArrayList<Mascota> mascotas = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pMascota);
            ComunDB comundb = new ComunDB();
            ComunDB.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0);
            querySelect(pMascota, utilQuery);
            sql = utilQuery.getSQL();
            sql += agregarOrderBy(pMascota);
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pMascota, utilQuery);
                obtenerDatos(ps, mascotas);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } 
        catch (SQLException ex) {
            throw ex;
        }        
        return mascotas;
    }
    
    public static ArrayList<Mascota> buscarIncluirRelaciones(Mascota pMascota) throws Exception {
        ArrayList<Mascota> mascotas = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = "SELECT ";
            if (pMascota.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {
                sql += "TOP " + pMascota.getTop_aux() + " "; 
            }
            sql += obtenerCampos();
            sql += ", ";
            sql += TipoDAL.obtenerCampos();
            sql += ", ";
            sql += GeneroDAL.obtenerCampos();
            sql += " FROM Mascota m";
            sql += " INNER JOIN Tipo t on (m.IdTipo = t.Id)";
            sql += " INNER JOIN Genero g on (m.IdGenero = g.Id)";
            ComunDB comundb = new ComunDB();
            ComunDB.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0);
            querySelect(pMascota, utilQuery);
            sql = utilQuery.getSQL();
            sql += agregarOrderBy(pMascota);
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pMascota, utilQuery);
                obtenerDatosIncluirRelaciones(ps, mascotas);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return mascotas;
    }
}
