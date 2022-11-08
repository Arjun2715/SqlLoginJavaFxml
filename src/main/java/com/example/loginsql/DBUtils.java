package com.example.loginsql;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.*;

public class DBUtils {
    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username) {
        Parent root = null;


        if (username != null) {
            try {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                LoggedInController loggedInController  =loader.getController();
                loggedInController.setUserInfo(username);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            try{
                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root,700,500));
        stage.show();
    }

    public static void signUpUser(ActionEvent event, String username, String password){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckExist  =null;
        ResultSet resultSet = null;

        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaappdb","root","@Multani2715");
            psCheckExist = connection.prepareStatement("SELECT * FROM table1 WHERE username  = ?");
            psCheckExist.setString(1,username);

            resultSet = psCheckExist.executeQuery();

            if(resultSet.isBeforeFirst()){
                System.out.println("User already exists");
                Alert alert  = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("you cannot use this username.");
                alert.show();

            }else{
                psInsert =  connection.prepareStatement("INSERT INTO table1 (username,password) values(? ? );");
                psInsert.setString(1,username);
                psInsert.setString(2,password);
                psInsert.executeUpdate();

                changeScene(event,"logged-in.fxml","Welcome!",username);

            }


        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(resultSet!=null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(psCheckExist !=null){
                try {
                    psCheckExist.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(psInsert !=null){
                try {
                    psInsert.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection !=null){
                try {
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
    public static void logInUser(ActionEvent event,String username,String password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaappdb","root","@Multani2715");
            preparedStatement = connection.prepareStatement("Select usrpass from users WHERE username = ?");
            preparedStatement.setString(1,username);
            resultSet  = preparedStatement.executeQuery();
            if(resultSet.isBeforeFirst()){
                System.out.println("User not found in the Database");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect!");
                alert.show();

            }else{
                while(resultSet.next()){
                    String retrivePassword = resultSet.getString("usrpass") ;
                    if(retrivePassword.equals(password)){
                        changeScene(event,"logged-in.fxml", "Welcome!",username);

                    }else{
                        System.out.println("password didn't matched");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("The Provided credentials are incorrect!");
                        alert.show();
                    }
                }
            }

        }catch (SQLException e ){
            e.printStackTrace();
        }finally {
            if(resultSet!=null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(preparedStatement !=null){
                try {
                    preparedStatement.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection !=null){
                try {
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
