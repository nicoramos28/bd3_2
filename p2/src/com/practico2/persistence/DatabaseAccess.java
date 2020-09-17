package src.com.practico2.persistence;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseAccess {

    DatabaseContext databaseContext = new DatabaseContext();

    public DatabaseAccess() throws IOException {
    }

    /* crea el texto de la consulta que obtiene un listado de todos los exámenes registrados en la BD */
    public String listarExamenes(){
        String query = "Select * from Examenes";
        String queryResult = "";
        try {
            PreparedStatement preparedStatement = databaseContext.getConnection().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                queryResult += "Codigo ".concat(resultSet.getString("codigo").concat(" - "));
                queryResult += "Materia ".concat(resultSet.getString("materia").concat(" - "));
                queryResult += "Pateria ".concat(resultSet.getString("periodo").concat("\n"));
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return queryResult;
    }

    /* crea el texto de la consulta que inserta un resultado de examen */
    public Boolean insertarResultado(int codigo, String ced, int resultado){
        String query = "update Resultados set calificacion = ? where cedula = ? and codigo = ?";
        try {
            PreparedStatement preparedStatement = databaseContext.getConnection().prepareStatement(query);
            preparedStatement.setInt(1,resultado);
            preparedStatement.setInt(2,codigo);
            preparedStatement.setString(3,ced);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
