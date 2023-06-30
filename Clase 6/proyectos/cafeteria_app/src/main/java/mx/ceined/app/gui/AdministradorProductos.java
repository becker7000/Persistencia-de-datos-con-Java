package mx.ceined.app.gui;

import mx.ceined.app.model.Producto;
import mx.ceined.app.repositorio.ProductoRepositorioImp;
import mx.ceined.app.repositorio.Repositorio;
import mx.ceined.app.util.ConexionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class AdministradorProductos extends JFrame{
    private JPanel panel1;
    private JPanel panelGeneral;
    private JPanel panelProducto;
    private JTextField idTextField;
    private JTextField nombreTextField;
    private JTextField precioTextField;
    private JButton crearButton;
    private JButton modificarButton;
    private JButton eliminarButton;
    private JTextField buscarPorIdTF;
    private JButton buscarButton;
    private JButton mostrarTodosButton;
    private JTable tablaProductos;
    private JPanel panelTabla;
    private DefaultTableModel defaultTableModel;
    private Connection connection;
    private Repositorio<Producto> productoRepositorio;

    public AdministradorProductos() throws HeadlessException {

        setTitle("Sistema de control de productos.");
        setSize(new Dimension(880,400));
        setVisible(true);
        setLocationRelativeTo(null);
        getContentPane().add(panelGeneral);
        iniciarComponentes();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void iniciarComponentes() {
        configurarTabla();
        configurarBotones();
    }

    private void configurarTabla() {
        defaultTableModel = new DefaultTableModel();
        String[] cabeceras = {"Id","Nombre","Precio","Fecha de registro"};
        defaultTableModel.setColumnIdentifiers(cabeceras);
        tablaProductos.setModel(defaultTableModel);

        // Centramos el texto de las columnas de tabla:
        DefaultTableCellRenderer centradorTabla = new DefaultTableCellRenderer();
        centradorTabla.setHorizontalAlignment(JLabel.CENTER);
        for(int i=0; i<tablaProductos.getColumnCount();i++){
            tablaProductos.getColumnModel().getColumn(i).setCellRenderer(centradorTabla);
        }
        mostrarProductos();
    }

    private void mostrarProductos() {
        try{
            connection = ConexionBD.getInstance();
            productoRepositorio = new ProductoRepositorioImp();
            actualizarTabla();
        } catch (SQLException e) {
            System.out.println("\n\t (mostrarProductos) Error: "+e.getMessage());
        }
    }

    private void actualizarTabla() {
        limpiarTabla();
        productoRepositorio.listar().forEach(this::crearFila);
    }

    private void crearFila(Producto producto){
        defaultTableModel.addRow(new Object[]{
                producto.getId(),
                producto.getNombre(),
                producto.getPrecio(),
                producto.getFechaRegistro()
        });
    }

    private void limpiarTabla() {
        int numeroFilas = defaultTableModel.getRowCount();
        for(int i=0; i<numeroFilas;i++){
            defaultTableModel.removeRow(0);
        }
    }

    private void configurarBotones() {

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarTabla();
                int id = Integer.parseInt(buscarPorIdTF.getText());
                Producto producto = productoRepositorio.porId(id);
                crearFila(producto);
                // TODO: generar un mensaje en caso de que no exista.
                limpiarEntradas();
            }
        });

        mostrarTodosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarTabla();
            }
        });

        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Producto producto = new Producto();
                producto.setNombre(nombreTextField.getText());
                producto.setPrecio(Double.parseDouble(precioTextField.getText()));
                producto.setFechaRegistro(new Date());
                productoRepositorio.guardar(producto);
                actualizarTabla();
                limpiarEntradas();
            }
        });

        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Producto producto = new Producto();
                producto.setId(Integer.parseInt(idTextField.getText()));
                producto.setNombre(nombreTextField.getText());
                producto.setPrecio(Double.parseDouble(precioTextField.getText()));
                productoRepositorio.guardar(producto);
                actualizarTabla();
                limpiarEntradas();
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idTextField.getText());
                productoRepositorio.eliminar(id);
                actualizarTabla();
                limpiarEntradas();
            }
        });

    }

    private void limpiarEntradas(){
        idTextField.setText("");
        nombreTextField.setText("");
        precioTextField.setText("");
        buscarPorIdTF.setText("");
    }

}
