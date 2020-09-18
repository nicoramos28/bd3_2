package src.com.practico2.persistence;

import src.com.practico2.api.Examen;
import src.com.practico2.api.Resultado;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class DatabaseContext {

    private String username;

    private String host;

    private String pass;

    private String db;

    private String driver;

    private String url;

    private Connection connection;

    public DatabaseContext() throws IOException {

        Properties properties = new Properties();

        String nomArch = "p2/resources/conf/application.properties";
        properties.load (new FileInputStream(nomArch));
        this.username = properties.getProperty("username");
        this.host = properties.getProperty("host");
        this.pass = properties.getProperty("password");
        this.db = properties.getProperty("practico3.db-name");
        this.driver = "com.mysql.jdbc.Driver";
        this.url = "jdbc:mysql://"+ host + "/" + db;
        this.connection = createDBConnection();
    }


    private Connection createDBConnection(){
        try {
            Class.forName(driver);
            this.connection = DriverManager.getConnection(url,username,pass);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return this.connection;
    }

    public void closeDBConnection(){
        try {
            this.connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    /* devuelve un listado de todos los exámenes registrados en la BD */ /* de cada examen se tiene código, asignatura y período */
    public List<Examen> listarExamenes(Connection connection){
        String query = "Select * from Examenes";
        List<Examen> listaExamenes = new ArrayList<Examen>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String codigo = resultSet.getString("codigo");
                String materia = resultSet.getString("materia");
                String periodo = resultSet.getString("periodo");
                Examen examen = new Examen(codigo,materia,periodo);
                listaExamenes.add(examen);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return listaExamenes;
    }

    /* ingresa el resultado de un examen en la BD, los datos del */ /* resultado vienen almacenados en el objeto resu */
    public void ingresarResultado (Connection con, Resultado resultado){
        String query = "Update Resultados set calificacion = ? where cedula = ? and codigo = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,resultado.getCalificacion());
            preparedStatement.setInt(2,resultado.getCedula());
            preparedStatement.setString(3,resultado.getCodigo());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
