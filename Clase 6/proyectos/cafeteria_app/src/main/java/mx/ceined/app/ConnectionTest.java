package mx.ceined.app;

import mx.ceined.app.model.Producto;
import mx.ceined.app.repositorio.ProductoRepositorioImp;
import mx.ceined.app.repositorio.Repositorio;
import mx.ceined.app.util.ConexionBD;

import java.sql.*;
import java.util.Date;
import java.util.Scanner;

public class ConnectionTest {

    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);
        int opcion = 0;

        try(Connection connection = ConexionBD.getInstance()) { // Controlamos la exception tipo SQLException:

            Repositorio<Producto> productoRepositorio = new ProductoRepositorioImp();

            while(opcion!=6){

                do{ // Filtro para opciones del 1 al 6.
                    System.out.print("\n\t +---------------------------+");
                    System.out.print("\n\t | Menú:                     |");
                    System.out.print("\n\t | 1. Ver productos          |");
                    System.out.print("\n\t | 2. Buscar por id          |");
                    System.out.print("\n\t | 3. Crear producto         |");
                    System.out.print("\n\t | 4. Modificar producto     |");
                    System.out.print("\n\t | 5. Eliminar producto      |");
                    System.out.print("\n\t | 6. Salir                  |");
                    System.out.print("\n\t +---------------------------+");
                    System.out.print("\n\t | Opción: ");
                    opcion = Integer.parseInt(entrada.nextLine());
                }while(opcion<1 || opcion>6);

                switch (opcion){
                    case 1 -> {
                        // Probando el método listar()
                        System.out.print("\n\t +----------------------------+");
                        System.out.print("\n\t | Listando productos         |");
                        productoRepositorio.listar().forEach(Producto::mostrar); // :: Operador de resolución del alcance
                    }
                    case 2 -> {
                        // Probando el método porId(Integer)
                        System.out.print("\n\t +----------------------------+");
                        System.out.print("\n\t | Buscando por id            |");
                        System.out.print("\n\t | id: ");
                        try{
                            productoRepositorio.porId(Integer.parseInt(entrada.nextLine())).mostrar();
                            System.out.print("\n\t | Producto encontrado        |");
                            System.out.print("\n\t +----------------------------+");
                        }catch (NullPointerException exception){
                            System.out.print("\n\t | (Caso 2) Error: "+exception.getMessage());
                            System.out.print("\n\t | Producto no encontrado     |");
                            System.out.print("\n\t +----------------------------+");
                        }
                    }
                    case 3 -> {
                        // Probando el método guardar() con INSERT
                        System.out.print("\n\t +----------------------------+");
                        System.out.print("\n\t | Creando producto           |");
                        Producto producto = new Producto();
                        System.out.print("\n\t | Nombre: ");
                        producto.setNombre(entrada.nextLine());
                        System.out.print("\n\t | Precio: $ ");
                        producto.setPrecio(Double.parseDouble(entrada.nextLine()));
                        producto.setFechaRegistro(new Date());
                        productoRepositorio.guardar(producto);
                        System.out.print("\n\t | Producto guardado          |");
                        System.out.print("\n\t +----------------------------+");
                    }
                    case 4 -> {
                        // Probando el método guardar() con UPDATE
                        System.out.print("\n\t +----------------------------+");
                        System.out.print("\n\t | Modificando producto       |");
                        Producto producto = new Producto();
                        System.out.print("\n\t | Id: ");
                        producto.setId(Integer.parseInt(entrada.nextLine()));
                        System.out.print("\n\t | Nombre: ");
                        producto.setNombre(entrada.nextLine());
                        System.out.print("\n\t | Precio: $ ");
                        producto.setPrecio(Double.parseDouble(entrada.nextLine()));
                        productoRepositorio.guardar(producto);
                        System.out.print("\n\t | Producto actualizado       |");
                        System.out.print("\n\t +----------------------------+");
                    }
                    case 5 -> {
                        // Probando el método eliminar()
                        System.out.print("\n\t +----------------------------+");
                        System.out.print("\n\t | Eliminando producto        |");
                        System.out.print("\n\t | Id: ");
                        productoRepositorio.eliminar(Integer.parseInt(entrada.nextLine()));
                        System.out.print("\n\t | Producto eliminado         |");
                        System.out.print("\n\t +----------------------------+");
                    }
                    case 6 -> {
                        System.out.print("\n\t +----------------------------+");
                        System.out.print("\n\t | Saliendo, vuela pronto...  |");
                        System.out.print("\n\t +----------------------------+");
                    }
                }

                System.out.print("\n\t Da [ENTER] para continuar.");
                entrada.nextLine(); // Generar una pausa hasta dar enter

            }

        } catch (SQLException e) {
            System.out.print("\n\t (Connection Test) Error: "+e.getMessage());
        }

    }

}
