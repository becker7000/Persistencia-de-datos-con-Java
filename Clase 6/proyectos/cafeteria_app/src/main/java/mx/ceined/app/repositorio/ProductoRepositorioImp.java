package mx.ceined.app.repositorio;

import mx.ceined.app.model.Producto;
import mx.ceined.app.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositorioImp implements Repositorio<Producto> {

    // Crear un método para la conexión a la base de datos.
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
        } catch (SQLException e) {
            System.out.println("\n\t (listar) Error: "+e.getMessage());
        }
        return productos;
    }

    @Override
    public Producto porId(Integer id) {
        Producto producto = null;
        String query = "SELECT * FROM productos WHERE id=?";
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(query)){
            preparedStatement.setInt(1,id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){ // Consultas simples.
                if (resultSet.next()) {
                    producto = crearProducto(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.print("\n\t (porId) Error: "+e.getMessage());
        } // El preparedStatement y el resultSet se auto cierran con el bloque try.
        return producto;
    }

    @Override
    public void guardar(Producto producto) { // Modifica o inserta registros.
        String query;
        if(producto.getId()!=null && producto.getId()>0){ // Si el procuto ya existe
            query="UPDATE productos SET nombre=?, precio=? WHERE id=?";
        }else{
            query="INSERT INTO productos(nombre,precio,fecha) VALUES(?,?,?)";
        }

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)){
            preparedStatement.setString(1,producto.getNombre());
            preparedStatement.setDouble(2,producto.getPrecio());
            if(producto.getId()!=null && producto.getId()>0){ // Si el producto ya existe.
                preparedStatement.setInt(3,producto.getId());
            }else{
                preparedStatement.setDate(3,new Date(producto.getFechaRegistro().getTime()));
            }

            preparedStatement.executeUpdate(); // Modificaciones a la base de datos.

        } catch (SQLException e) {
            System.out.println("\n\t (guardar) Error: "+e.getMessage());
        }

    }

    @Override
    public void eliminar(Integer id) {
        String query="DELETE FROM productos WHERE id=?";
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(query)){
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate(); // Actualización a la base de datos.
        } catch (SQLException e) {
            System.out.println("\n\t (eliminar) Error: "+e.getMessage());
        }
    }

    // Método auxiliar:
    private Producto crearProducto(ResultSet resultSet) throws SQLException {
        Producto producto = new Producto();
        producto.setId(resultSet.getInt("id"));
        producto.setNombre(resultSet.getString("nombre"));
        producto.setPrecio(resultSet.getDouble("precio"));
        producto.setFechaRegistro(resultSet.getDate("fecha"));
        return producto;
    }
}
