package mx.ceined.app.gui;

import mx.ceined.app.models.Producto;
import mx.ceined.app.repositorio.ProductoRepositorioImp;
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

public class AdministradorProductos extends JFrame {
    private JPanel panelGeneral;
    private JTextField idTextField;
    private JTextField nombreTextField;
    private JTextField precioTextField;
    private JTextField buscarPorIdTF;
    private JButton buscarButton;
    private JButton crearButton;
    private JButton actualizarButton;
    private JButton eliminarButton;
    private JTable tablaProductos;
    private JPanel panelTabla;
    private JPanel panelProducto;
    private JButton mostrarTodosButton;
    private DefaultTableModel defaultTableModel;
    private Connection connection;
    private ProductoRepositorioImp productoRepositorioImp;


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
        String[] cabeceras = new String[]{
                "Id","Nombre","Precio","Fecha de registro"
        };
        defaultTableModel.setColumnIdentifiers(cabeceras);
        tablaProductos.setModel(defaultTableModel);

        // Centrar texto de las columnas de una tabla.
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tablaProductos.getColumnCount(); i++) {
            tablaProductos.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        mostrarProductos();
    }

    private void mostrarProductos() {
        try{
            connection = ConexionBD.getInstance();
            productoRepositorioImp = new ProductoRepositorioImp();
            actualizarTabla();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al listar los productos","Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void limpiarTabla(){
        int numeroDeFilas = defaultTableModel.getRowCount(); // Contar el número de filas.
        for(int i=0;i<numeroDeFilas;i++){
            defaultTableModel.removeRow(0);
        }
    }

    private void configurarBotones() {

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarTabla();
                int id = Integer.parseInt(buscarPorIdTF.getText());
                Producto producto = productoRepositorioImp.porId(id);
                crearFila(producto);
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
                productoRepositorioImp.guardar(producto);
                actualizarTabla();
                limpiarEntradas();
            }
        });

        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Producto producto = new Producto();
                producto.setId(Integer.parseInt(idTextField.getText()));
                producto.setNombre(nombreTextField.getText());
                producto.setPrecio(Double.parseDouble(precioTextField.getText()));
                productoRepositorioImp.guardar(producto);
                actualizarTabla();
                limpiarEntradas();
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idTextField.getText());
                productoRepositorioImp.eliminar(id);
                actualizarTabla();
                limpiarEntradas();
            }
        });

    }

    private void actualizarTabla() {
        limpiarTabla();
        productoRepositorioImp.listar().forEach(this::crearFila);
    }

    private void crearFila(Producto producto){
        defaultTableModel.addRow(new Object[]{
                producto.getId(),
                producto.getNombre(),
                producto.getPrecio(),
                producto.getFechaRegistro()
        });
    }

    private void limpiarEntradas(){
        idTextField.setText("");
        nombreTextField.setText("");
        precioTextField.setText("");
        buscarPorIdTF.setText("");
    }

}
