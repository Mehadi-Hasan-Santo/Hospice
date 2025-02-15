package com.aem.hospice.LoginRegister;

import com.aem.hospice.Employee.EmployeepageController;
import com.aem.hospice.Patient.PatientpageController;
import com.aem.hospice.Classes.DBLogInManagerMySQL;
import com.aem.hospice.PopUp.AlertBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;

public class LoginController {
    @FXML
    TextField uid;
    @FXML
    PasswordField password;
    private static Stage stage;

    String uid1, pass1;
    public void login(ActionEvent actionEvent){
        try{
            FXMLLoader loader=new FXMLLoader(getClass().getResource("LoginController.fxml"));
            Parent root=loader.load();
            stage=(Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Hospice Log In");
            Scene scene=new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void Register(ActionEvent actionEvent) throws IOException{
        try{
            Register register=new Register();
            register.register(actionEvent);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public  void LoginButton(ActionEvent actionEvent) throws Exception {
        uid1=uid.getText();
        pass1 = password.getText();

        if(!uid1.isEmpty() && DBLogInManagerMySQL.LogInValidate(uid1,pass1))
        {
            if(uid1.charAt(0)=='1') //Patient
            {
               PatientpageController pp = new PatientpageController(uid1);
               pp.patientpagestart(actionEvent);
            }
            else if(uid1.charAt(0) == '3')//Employee
            {
                EmployeepageController ep = new EmployeepageController(uid1);
                ep.employee(actionEvent);
            }
            if(uid1.equals("00000")){
                EmployeepageController ep = new EmployeepageController(uid1);
                ep.employee(actionEvent);

            }
        }
        else
            AlertBox.display("Wrong ID or Password","Try Again");



    }

    public void resetpass(ActionEvent actionEvent) {
    }

    public void recoveruid(ActionEvent actionEvent) {
    }
}
