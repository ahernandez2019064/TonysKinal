package org.allanhernandez.system;

import java.io.InputStream;
import javafx.scene.image.Image;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.allanhernandez.controler.DatosProgramadorController;
import org.allanhernandez.controler.EmpleadoController;
import org.allanhernandez.controler.EmpresaController;
import org.allanhernandez.controler.MenuPrincipalController;
import org.allanhernandez.controler.PresupuestoController;
import org.allanhernandez.controler.TipoDeEmpleadoController;

public class Principal extends Application {
    private final String Paquete_Vista = "/org/allanhernandez/view/";
    private Stage escenarioPrincipal;
    private Scene escena;
    
    @Override
    public void start(Stage escenarioPrincipal) throws Exception{
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Tony's Kinal App");
        escenarioPrincipal.getIcons().add(new Image("/org/allanhernandez/image/logollama.png"));
        //Parent root = FXMLLoader.load(getClass().getResource("/org/allanhernandez/view/MenuPrincipalView.fxml"));
        //Scene escena = new Scene(root);
        //escenarioPrincipal.setScene(escena);
        menuPrincipal();
        escenarioPrincipal.show();
        
    }
    
    public void menuPrincipal(){
        try{
            MenuPrincipalController menuPrincipal = (MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml", 1222, 802);
            menuPrincipal.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void DatosProgramador(){
        try{
           DatosProgramadorController DatosProgramador = (DatosProgramadorController) cambiarEscena ("DatosView.fxml", 408, 409);
           DatosProgramador.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void EmpresaC(){
        try{
            EmpresaController EmpresaC = (EmpresaController) cambiarEscena ("EmpresasView.fxml",794, 446);
            EmpresaC.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void VenPresupuesto(){
        try{
            PresupuestoController VenPresupuesto = (PresupuestoController) cambiarEscena ("PresupuestoView.fxml", 961, 535);
            VenPresupuesto.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void TipoDeEmpleadoVen(){
        try{
            TipoDeEmpleadoController TipoDeEmpleadoVen = (TipoDeEmpleadoController) cambiarEscena ("TipoDeEmpleadoView.fxml", 861, 460);
            TipoDeEmpleadoVen.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void EmpleadoVen(){
        try{
            EmpleadoController EmpleadoVen = (EmpleadoController) cambiarEscena ("EmpleadosView.fxml", 1125, 631);
            EmpleadoVen.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    
    
    public Initializable cambiarEscena(String fxml, int ancho, int alto) throws Exception{
        
        Initializable resultado = null;
        FXMLLoader cargadorFXML = new FXMLLoader();
        InputStream archivo = Principal.class.getResourceAsStream(Paquete_Vista+fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Principal.class.getResource(Paquete_Vista + fxml));
        escena = new Scene((AnchorPane)cargadorFXML.load(archivo));
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable)cargadorFXML.getController();
        
        return resultado ;
    } 
        
    
    
}
