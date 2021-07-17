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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.allanhernandez.bean.TipoDeEmpleado;
import org.allanhernandez.db.Conexion;

import org.allanhernandez.system.Principal;

public class TipoDeEmpleadoController implements Initializable {
    private enum operaciones {NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private Principal escenarioPrincipal;
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<TipoDeEmpleado> listaTipoEmpleado;
    @FXML private TextField txtCodTipoEmpleado;
    @FXML private Label lblCodTipoEmpleado;
    @FXML private TextField txtDescripcion;
    @FXML private TableView tblTipoEmpleados;
    @FXML private TableColumn colCodTipoEmpleado;
    @FXML private TableColumn colDescripcion;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    public void cargarDatos(){
        tblTipoEmpleados.setItems(getTipoDeEmpleado());
        colCodTipoEmpleado.setCellValueFactory(new PropertyValueFactory<TipoDeEmpleado, Integer>("codigoTipoEmpleado"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<TipoDeEmpleado, String>("descripción"));
    }
    
    public ObservableList<TipoDeEmpleado> getTipoDeEmpleado(){
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
        switch(tipoDeOperacion){
            case NINGUNO:
                txtCodTipoEmpleado.setText(String.valueOf(((TipoDeEmpleado)tblTipoEmpleados.getSelectionModel().getSelectedItem()).getCodigoTipoEmpleado()));
                txtDescripcion.setText(((TipoDeEmpleado)tblTipoEmpleados.getSelectionModel().getSelectedItem()).getDescripción());
                break;
                
            case GUARDAR:
                limpiarControles();
        }
    }
    
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                activarControles();
                limpiarControles();
                btnNuevo.setVisible(true);
                btnNuevo.setText("Guardar");
                btnEliminar.setVisible(true);
                btnEliminar.setText("Cancelar");
                btnEliminar.setDisable(false);
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                btnReporte.setVisible(false);
                btnEditar.setVisible(false);
                desactivarID();
                tipoDeOperacion = operaciones.GUARDAR;
                break;
                
            case GUARDAR:
                guardar();
                desactivarControles();
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
        TipoDeEmpleado registro = new TipoDeEmpleado();
        registro.setDescripción(txtDescripcion.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarTipoEmpleado(?)}");
            procedimiento.setString(1, registro.getDescripción());
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
                btnReporte.setVisible(true);
                btnEditar.setVisible(true);
                tipoDeOperacion = operaciones.NINGUNO;
                break;
            default:
                if(tblTipoEmpleados.getSelectionModel().getSelectedItem() !=null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de querer eliminar el registro?", "Eliminar Tipo de empleado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarTipoEmpleado(?)}");
                            procedimiento.setInt(1, ((TipoDeEmpleado)tblTipoEmpleados.getSelectionModel().getSelectedItem()).getCodigoTipoEmpleado());
                            procedimiento.execute();
                            listaTipoEmpleado.remove(tblTipoEmpleados.getSelectionModel().getSelectedIndex());
                            limpiarControles();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }else{
                    JOptionPane.showConfirmDialog(null, "Debe sellecionar un elemento primero", "Advertencia", JOptionPane.CANCEL_OPTION);
                }
        }
    }
    
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if(tblTipoEmpleados.getSelectionModel().getSelectedItem() !=null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    btnNuevo.setVisible(false);
                    btnEliminar.setVisible(false);
                    activarControles();
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
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
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarTipoEmpleado(?,?)}");
            TipoDeEmpleado registro = (TipoDeEmpleado)tblTipoEmpleados.getSelectionModel().getSelectedItem();
            registro.setDescripción(txtDescripcion.getText());
            procedimiento.setInt(1, registro.getCodigoTipoEmpleado());
            procedimiento.setString(2, registro.getDescripción());
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

//    public void desactivarBotones(){
//        btnNuevo.setVisible(false);
//        btnEliminar.setVisible(false);
//        btnEditar.setVisible(false);
//        btnReporte.setVisible(false);
//    }
    
    public void activarID(){
        txtCodTipoEmpleado.setVisible(true);
        lblCodTipoEmpleado.setVisible(true);
    }

    public void desactivarID(){
        txtCodTipoEmpleado.setVisible(false);
        lblCodTipoEmpleado.setVisible(false);
    }
    
    public void desactivarControles(){
        txtCodTipoEmpleado.setEditable(false);
        txtDescripcion.setEditable(false);
    }
    
    public void activarControles(){
        txtCodTipoEmpleado.setEditable(false);
        txtDescripcion.setEditable(true);
    }
    
    public void limpiarControles(){
        txtCodTipoEmpleado.setText("");
        txtDescripcion.setText("");
    }
    
    public Principal getEscenarioPrincipal(){
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal; 
    }
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    
    public void EmpleadoVen(){
        escenarioPrincipal.EmpleadoVen();
    }
}
