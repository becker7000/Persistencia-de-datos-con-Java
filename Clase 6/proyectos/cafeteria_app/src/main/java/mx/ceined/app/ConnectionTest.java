package mx.ceined.app;

import java.sql.*;
import java.util.Scanner;

public class ConnectionTest {

    public static void main(String[] args) {

        String url="jdbc:mysql://localhost:3306/cafeteria?serverTimezone=UTC";
        String username="root";
        String password="toor";

        Scanner entrada = new Scanner(System.in);
        System.out.print("\n\t +---------------------------------+");
        System.out.print("\n\t | 1. Mostrar todos los productos. |");
        System.out.print("\n\t | 2. Buscar por id.               |");
        System.out.print("\n\t +---------------------------------+");
        System.out.print("\n\t Opción: ");
        int opcion = Integer.parseInt(entrada.nextLine());

        try { // Controlamos la exception tipo SQLException:

            Connection connection = DriverManager.getConnection(url,username,password);

            Statement statement=null;
            ResultSet resultSet=null;
            PreparedStatement preparedStatement=null;

            switch (opcion){
                case 1 -> {
                    statement = connection.createStatement();
                    resultSet = statement.executeQuery("SELECT * FROM productos");
                }
                case 2 -> {
                    System.out.print("\n\t Escribe el id: ");
                    int id = Integer.parseInt(entrada.nextLine());
                    String query = "SELECT * FROM productos WHERE id=?";
                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1,id); // Traer el producto con id=3
                    resultSet = preparedStatement.executeQuery();
                }
            }

            System.out.print("\n\t Lista de cafés: ");
            System.out.print("\n\t +------------------------------+");
            while(resultSet.next()){ // Se recorre registro a registro.
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                double precio = resultSet.getDouble("precio");
                System.out.printf("\n\t | %d %s $%.2f",id,nombre,precio);
            }
            System.out.print("\n\t +------------------------------+");

            // Buenas practicas:
            resultSet.close();
            //statement.close();
            //preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
