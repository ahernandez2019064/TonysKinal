package org.allanhernandez.controler;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.allanhernandez.system.Principal;



public class DatosProgramadorController implements Initializable{
    private Principal escenarioPrincipal;
    @FXML private Button btnDatosP;
    @FXML private Button btnCorp;
    @FXML private Label lblNombre;
    @FXML private Label lblCarne;
    @FXML private Label lblColegio;
    @FXML private Label lblCarrera;
    @FXML private Label lblCorreo;
    @FXML private Label lblSeccion;
    @FXML private ImageView imgPerfil;
    @FXML private ImageView imgLogo;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    @FXML
    public void Opciones(ActionEvent event){
        if (event.getSource() == btnDatosP){
            lblColegio.setVisible(false);
            lblCarrera.setVisible(false);
            lblNombre.setVisible(true);
            imgLogo.setVisible(false);
            imgPerfil.setVisible(true);
            lblCorreo.setVisible(true);
            lblSeccion.setVisible(true);
            lblCarne.setVisible(true);
            
                        
        }else if(event.getSource()== btnCorp){
            lblSeccion.setVisible(false);
            lblCarne.setVisible(false);
            lblNombre.setVisible(false);
            imgPerfil.setVisible(false);
            lblCorreo.setVisible(false);
            lblColegio.setVisible(true);
            lblCarrera.setVisible(true);
            imgLogo.setVisible(true);
                  
        }
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
}
