package mx.ceined.app.repositorio;

import mx.ceined.app.models.Producto;
import mx.ceined.app.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositorioImp implements Repositorio<Producto> {

    // Delegamos al exception.
    private Connection getConnection() throws SQLException {
        return ConexionBD.getInstance();
    }

    @Override
    public List<Producto> listar() {
        List<Producto> productos = new ArrayList<>();
        try(Statement statement = getConnection().createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM productos");
            while(resultSet.next()){
                Producto producto = crearProducto(resultSet);
                productos.add(producto);
            }
        } // Es importante no cerrar la conexión ni el statement.
        catch (SQLException e) {
            System.out.println("\n\t (Listar) Error al obtener la conexión: "+e.getMessage());
        }
        return productos;
    }

    @Override
    public Producto porId(Integer id) {
        Producto producto = null;
        String query = "SELECT * FROM productos WHERE id=?";
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(query)){
            preparedStatement.setInt(1,id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) { // Ya que va a devolver uno solo
                    producto = crearProducto(resultSet);
                }
                // resultSet.close();
            } // Consulta de datos.
        } catch (SQLException e) {
            System.out.println("\n\t (porId) Error: "+e.getMessage());
        }
        return producto;
    }

    @Override
    public void guardar(Producto producto) {
        String query;
        if (producto.getId()!= null && producto.getId()>0) {
            query = "UPDATE productos SET nombre=?, precio=? WHERE id=?";
        } else {
            query = "INSERT INTO productos(nombre,precio,fecha) VALUES(?,?,?)";
        }
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setString(1,producto.getNombre());
            preparedStatement.setDouble(2,producto.getPrecio());
            if (producto.getId()!= null && producto.getId()>0) {
                preparedStatement.setInt(3,producto.getId());
            } else {
                preparedStatement.setDate(3,new Date(producto.getFechaRegistro().getTime()));
            }
            preparedStatement.executeUpdate(); // Actualización a la base de datos.
        } catch (SQLException e) {
            System.out.print("\n\t (Guardar) Error: "+e.getMessage());
        }
    }

    @Override
    public void eliminar(Integer id) {
        String query = "DELETE FROM productos WHERE id=?";
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(query)){
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate(); // Actualización a la base de datos.
        } catch (SQLException e) {
            System.out.println("\n\t (Eliminar) Error: "+e.getMessage());
        }
    }

    private static Producto crearProducto(ResultSet resultSet) throws SQLException {
        Producto producto = new Producto();
        producto.setId(resultSet.getInt("id"));
        producto.setNombre(resultSet.getString("nombre"));
        producto.setPrecio(resultSet.getDouble("precio"));
        // La fecha en la BD es de java.sql y en java es java.util pero
        // un java.sql también es un java.util la coersión es implicita
        // al revés debe ser explicita.
        producto.setFechaRegistro(resultSet.getDate("fecha"));
        return producto;
    }

}
