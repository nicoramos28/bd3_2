package src.com.practico2.persistence;

import src.com.practico2.api.Examen;
import src.com.practico2.api.Resultado;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.List;
import java.util.Properties;

public class PracticoTresConsola {

    public static void main(String[] args) throws IOException, SQLException {

        Properties properties = new Properties();
        String nomArch = "p2/resources/conf/application.properties";
        properties.load (new FileInputStream(nomArch));
        String username = properties.getProperty("username");
        String host = properties.getProperty("host");
        String pass = properties.getProperty("password");
        String db = properties.getProperty("practico3.db-name");
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
            System.out.println("## 2 - Carga de datos primaria        ##");
            System.out.println("## 3 - Prueba metodos                 ##");
            System.out.println("##                                    ##");
            System.out.println("## 8 - Salir y eliminar datos         ##");
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
                    String createTable1 = "Create table practico3.Examenes (codigo VARCHAR(45), materia VARCHAR(45), periodo VARCHAR(45))";
                    String createTable2 = "Create table practico3.Resultados (cedula INT, codigo VARCHAR(45), calificacion INT)";

                    PreparedStatement preparedInsert1 = connection.prepareStatement(createTable1);
                    PreparedStatement preparedInsert2 = connection.prepareStatement(createTable2);

                    int insert1 = preparedInsert1.executeUpdate();
                    int insert2 = preparedInsert2.executeUpdate();

                    preparedInsert1.close();
                    preparedInsert2.close();
                    break;
                case "2":
                    String insertExamen = "Insert into Examenes values (?, ?, ?)";
                    String insertResultados = "Insert into Resultados values (1111, 'MD2019Dic', 3)," +
                            "  (2222, 'BD22019Dic', 9)," +
                            "  (3333, 'MD2020Feb', 2)," +
                            "  (3333, 'P22020Feb', 10)," +
                            "  (4444, 'MD2019Dic', 0)," +
                            "  (4444, 'BD22019Dic', 0)," +
                            "  (4444, 'MD2020Feb', 6)";

                    PreparedStatement examen = connection.prepareStatement(insertExamen);
                    PreparedStatement resultados = connection.prepareStatement(insertResultados);

                    examen.setString(1, "MD2019Dic");
                    examen.setString(2, "Matemática discreta");
                    examen.setString(3, "Diciembre 2019");
                    examen.executeUpdate();

                    examen.setString(1, "P12019Dic");
                    examen.setString(2, "Programación 1");
                    examen.setString(3, "Diciembre 2019");
                    examen.executeUpdate();

                    examen.setString(1, "BD22019Dic");
                    examen.setString(2, "Bases de datos 2");
                    examen.setString(3, "Diciembre 2019");
                    examen.executeUpdate();

                    examen.setString(1, "MD2020Feb");
                    examen.setString(2, "Matemática discreta");
                    examen.setString(3, "Febrero 2020");
                    examen.executeUpdate();

                    examen.setString(1, "P22020Feb");
                    examen.setString(2, "Programación 2");
                    examen.setString(3, "Febrero 2020");

                    examen.executeUpdate();
                    resultados.executeUpdate();

                    examen.close();
                    resultados.close();
                    break;
                case "3":
                    DatabaseContext databaseContext = new DatabaseContext();
                    Resultado res = new Resultado(1111,"MD2019Dic",10);
                    databaseContext.ingresarResultado(databaseContext.getConnection(), res);
                    break;
            }
        }
    }
}
