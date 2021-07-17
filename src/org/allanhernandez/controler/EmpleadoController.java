package org.allanhernandez.controler;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.allanhernandez.bean.Empleado;
import org.allanhernandez.bean.TipoDeEmpleado;
import org.allanhernandez.db.Conexion;
import org.allanhernandez.system.Principal;

public class EmpleadoController implements Initializable{
    private enum operaciones {NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private Principal escenarioPrincipal;
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Empleado> listaEmpleado;
    private ObservableList<TipoDeEmpleado> listaTipoEmpleado;
    @FXML private Label lblCodEmpleado;
    @FXML private TextField txtCodEmpleado;
    @FXML private TextField txtNumEmpleado;
    @FXML private TextField txtNombres;
    @FXML private TextField txtApellidos;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtGradoCocinero;
    @FXML private TextField txtTelefono;
    @FXML private ComboBox cmbCodTipoEmpleado;
    @FXML private TableView tblEmpleados;
    @FXML private TableColumn colCodEmpleado;
    @FXML private TableColumn colNumEmpleado;
    @FXML private TableColumn colNombres;
    @FXML private TableColumn colApellidos;
    @FXML private TableColumn colDireccion;
    @FXML private TableColumn colTelefono;
    @FXML private TableColumn colGradoCocinero;
    @FXML private TableColumn colCodTipoEmpleado;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCodTipoEmpleado.setItems(getTipoDeEmpleado());
    }
    
    public void cargarDatos(){
        tblEmpleados.setItems(getEmpleado());
        colCodEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("codigoEmpleado"));
        colNumEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("numeroEmpleado"));
        colNombres.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombresEmpleado"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<Empleado, String>("apellidosEmpleado"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Empleado, String>("direccionEmpleado"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Empleado, String>("telefonoContacto"));
        colGradoCocinero.setCellValueFactory(new PropertyValueFactory<Empleado, String>("gradoCocinero"));
        colCodTipoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("codigoTipoEmpleado"));
    }
    
    public ObservableList<Empleado> getEmpleado(){
        ArrayList<Empleado> lista = new ArrayList<Empleado>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmpleados}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Empleado( resultado.getInt("codigoEmpleado"),
                                        resultado.getInt("numeroEmpleado"),
                                        resultado.getString("apellidosEmpleado"),
                                        resultado.getString("nombresEmpleado"),
                                        resultado.getString("direccionEmpleado"),
                                        resultado.getString("telefonoContacto"),
                                        resultado.getString("gradoCocinero"),
                                        resultado.getInt("codigoTipoEmpleado")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaEmpleado = FXCollections.observableArrayList(lista);
    }
    
    public ObservableList<TipoDeEmpleado> getTipoDeEmpleado(){
        toString();
        ArrayList<TipoDeEmpleado> lista = new ArrayList<TipoDeEmpleado>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTipoEmpleados}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new TipoDeEmpleado(  resultado.getInt("codigoTipoEmpleado"),
                                        resultado.getString("descripción")));   
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaTipoEmpleado = FXCollections.observableArrayList(lista);
    }
    
    public void seleccionarElemento(){
        txtCodEmpleado.setText(String.valueOf(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
        txtNumEmpleado.setText(String.valueOf(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getNumeroEmpleado()));
        txtNombres.setText(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getNombresEmpleado());
        txtApellidos.setText(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getApellidosEmpleado());
        txtDireccion.setText(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getDireccionEmpleado());
        txtTelefono.setText(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getTelefonoContacto());
        txtGradoCocinero.setText(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getGradoCocinero());
        cmbCodTipoEmpleado.getSelectionModel().select(buscarTipoEmpleado(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoTipoEmpleado()));
    }
    
    public TipoDeEmpleado buscarTipoEmpleado(int codigoTipoEmpleado){
        TipoDeEmpleado resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarTipoEmpleado(?)}");
            procedimiento.setInt(1, codigoTipoEmpleado);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new TipoDeEmpleado( registro.getInt("codigoTipoEmpleado"),
                                                registro.getString("descripción"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return resultado;
    }
    
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                activarControles();
                limpiarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnNuevo.setVisible(true);
                btnEliminar.setVisible(true);
                btnEditar.setVisible(false);
                btnEditar.setDisable(true);
                btnReporte.setVisible(false);
                btnReporte.setDisable(false);
                desactivarID();
                tipoDeOperacion = operaciones.GUARDAR;
                break;
            case GUARDAR: 
                guardar();
                //desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEliminar.setDisable(true);
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                activarID();
                tipoDeOperacion = operaciones.NINGUNO;
                activarBotones();
                cargarDatos();
                break;
        }
    }
    
    public void guardar(){
        Empleado registro = new Empleado();
        registro.setNumeroEmpleado(Integer.valueOf(txtNumEmpleado.getText()));
        registro.setNombresEmpleado(txtNombres.getText());
        registro.setApellidosEmpleado(txtApellidos.getText());
        registro.setDireccionEmpleado(txtDireccion.getText());
        registro.setTelefonoContacto(txtTelefono.getText());
        registro.setGradoCocinero(txtGradoCocinero.getText());
        registro.setCodigoTipoEmpleado(((TipoDeEmpleado)cmbCodTipoEmpleado.getSelectionModel().getSelectedItem()).getCodigoTipoEmpleado());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarEmpleado(?,?,?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getNumeroEmpleado());
            procedimiento.setString(2, registro.getApellidosEmpleado());
            procedimiento.setString(3, registro.getNombresEmpleado());
            procedimiento.setString(4, registro.getDireccionEmpleado());
            procedimiento.setString(5, registro.getTelefonoContacto());
            procedimiento.setString(6, registro.getGradoCocinero());
            procedimiento.setInt(7, registro.getCodigoTipoEmpleado());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    
    public void eliminar(){
        switch(tipoDeOperacion){
            case GUARDAR:
                activarControles();
                activarID();
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnNuevo.setDisable(false);
                btnEliminar.setText("Eliminar");
                btnEliminar.setDisable(false);
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                btnEditar.setVisible(true);
                btnReporte.setVisible(true);
                tipoDeOperacion =operaciones.NINGUNO;
                break;
            default:
                if(tblEmpleados.getSelectionModel().getSelectedItem() !=null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de querer eliminar el registro?", "Eliminar Empleado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarEmpleado(?)}");
                            procedimiento.setInt(1, ((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
                            procedimiento.execute();
                            listaEmpleado.remove(tblEmpleados.getSelectionModel().getSelectedIndex());
                            limpiarControles();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }else{
                    JOptionPane.showConfirmDialog(null, "Debe seleccionar un elemento primero", "Advertencia", JOptionPane.CANCEL_OPTION);
                }
        }     
    }
    
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if(tblEmpleados.getSelectionModel().getSelectedItem() !=null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnEliminar.setDisable(true);
                    btnNuevo.setDisable(true);
                    activarControles();
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento primero");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarEmpleado(?,?,?,?,?,?,?,?)}");
            Empleado registro = (Empleado)tblEmpleados.getSelectionModel().getSelectedItem();
            registro.setNumeroEmpleado(Integer.valueOf(txtNumEmpleado.getText()));
            registro.setNombresEmpleado(txtNombres.getText());
            registro.setApellidosEmpleado(txtApellidos.getText());
            registro.setDireccionEmpleado(txtDireccion.getText());
            registro.setTelefonoContacto(txtTelefono.getText());
            registro.setGradoCocinero(txtGradoCocinero.getText());
            registro.setCodigoTipoEmpleado(((TipoDeEmpleado)cmbCodTipoEmpleado.getSelectionModel().getSelectedItem()).getCodigoTipoEmpleado());
            procedimiento.setInt(1,registro.getCodigoEmpleado());
            procedimiento.setInt(2, registro.getNumeroEmpleado());
            procedimiento.setString(3, registro.getApellidosEmpleado());
            procedimiento.setString(4, registro.getNombresEmpleado());
            procedimiento.setString(5, registro.getDireccionEmpleado());
            procedimiento.setString(6, registro.getTelefonoContacto());
            procedimiento.setString(7, registro.getGradoCocinero());
            procedimiento.setInt(8, registro.getCodigoTipoEmpleado());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void reporte(){
        switch(tipoDeOperacion){
            case ACTUALIZAR:
                limpiarControles();
                desactivarControles();
                activarBotones();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                tipoDeOperacion = operaciones.NINGUNO;

        }
    }
    
    public void activarID(){
        txtCodEmpleado.setVisible(true);
        lblCodEmpleado.setVisible(true);
    }

    public void desactivarID(){
        txtCodEmpleado.setVisible(false);
        lblCodEmpleado.setVisible(false);
    }
    
    public void activarBotones(){
        btnNuevo.setVisible(true);
        btnEliminar.setVisible(true);
        btnEditar.setVisible(true);
        btnReporte.setVisible(true);

        btnNuevo.setDisable(false);
        btnEliminar.setDisable(false);
        btnEditar.setDisable(false);
        btnReporte.setDisable(false);
    }
    
    public void desactivarControles(){
        txtCodEmpleado.setEditable(false);
        txtNumEmpleado.setEditable(false);
        txtNombres.setEditable(false);
        txtApellidos.setEditable(false);
        txtDireccion.setEditable(false);
        txtGradoCocinero.setEditable(false);
        txtTelefono.setEditable(false);
        cmbCodTipoEmpleado.setDisable(false);
    }
    
    public void activarControles(){
        txtCodEmpleado.setEditable(false);
        txtNumEmpleado.setEditable(true);
        txtNombres.setEditable(true);
        txtApellidos.setEditable(true);
        txtDireccion.setEditable(true);
        txtGradoCocinero.setEditable(true);
        txtTelefono.setEditable(true);
        cmbCodTipoEmpleado.setDisable(false);
    }
    
    public void limpiarControles(){
        txtCodEmpleado.setText("");
        txtNumEmpleado.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        txtDireccion.setText("");
        txtGradoCocinero.setText("");
        txtTelefono.setText("");
    }
    
    public Principal getEscenarioPrincpal(){
        return escenarioPrincipal;
    }
    
    public void setEscenarioPrincipal(Principal escenarioPrincipal ){
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void TipoDeEmpleadoVen(){
        escenarioPrincipal.TipoDeEmpleadoVen();
    }
            
}
