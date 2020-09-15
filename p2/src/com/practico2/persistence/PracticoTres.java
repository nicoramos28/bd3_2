package src.com.practico2.persistence;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class PracticoTres {

    public static void main(String[] args) throws IOException, SQLException {

        Properties properties = new Properties();
        String nomArch = "p2/resources/conf/application.properties";
        properties.load (new FileInputStream(nomArch));
        String username = properties.getProperty("username");
        String host = properties.getProperty("host");
        String pass = properties.getProperty("password");
        String db = properties.getProperty("db-name");
        String driver = "com.mysql.jdbc.Driver";

        Connection connection = null;
        try {
            Class.forName(driver);
            String url = "jdbc:mysql://"+ host + "/" + db;
            connection = DriverManager.getConnection(url,username, pass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        /*** Práctico 3 ***/

        boolean hasEnded = false;
        while (!hasEnded){
            System.out.println("\n########################################");
            System.out.println("##                                    ##");
            System.out.println("## 1 - Crear la base                  ##");
            System.out.println("##                                    ##");
            System.out.println("##                                    ##");
            System.out.println("##                                    ##");
            System.out.println("##                                    ##");
            System.out.println("## 9 - Salir.                         ##");
            System.out.println("##                                    ##");
            System.out.println("########################################");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String input = reader.readLine();

            switch (input){
                case "9" :
                    hasEnded = true;
                    System.out.println("Hasta la proxima!");
                    try{
                        connection.close();
                    }catch (SQLException e){
                        System.out.println("Error al cerrar la conexion " + e);
                    }
                    break;
                case "1" :
                    String query = "CREATE DATABASE practico3";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    int result = preparedStatement.executeUpdate();
                    System.out.println("Cant de lineas afectadas : " + result);
                    preparedStatement.close();
            }
        }

    }


}
