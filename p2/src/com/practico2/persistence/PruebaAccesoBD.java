package src.com.practico2.persistence;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class PruebaAccesoBD {


    public static void main(String[] args) throws IOException {

        /* 1. cargo dinamicamente el driver de MySQL y sus properties */
        Properties properties = new Properties();
        String nomArch = "p2/resources/conf/application.properties";
        properties.load (new FileInputStream(nomArch));

        String username = properties.getProperty("username");
        String host = properties.getProperty("host");
        String pass = properties.getProperty("password");
        String db = properties.getProperty("db-name");
        String driver = "com.mysql.jdbc.Driver";

        /*** Ejercicio 1 ***/
//        try {
//            Class.forName(driver);
//
//            /* 2. una vez cargado el driver, me conecto con la base de datos */
//            String url = "jdbc:mysql://"+ host + "/" + db;
//            Connection con = DriverManager.getConnection(url, username, pass);
//
//            /* 3. creo un PreparedStatement para insertar una persona en base de datos */
//            String insert = "INSERT into Personas values (?)";
//            PreparedStatement pstmt = con.prepareStatement(insert);
//            pstmt.setString(1, "Juan");
//
//            /* 4. ejecuto la sentencia de insercion y cierro el PreparedStatement */
//            int cant = pstmt.executeUpdate();
//            pstmt.close();
//            System.out.println("Resultado del Insert :" + insert + "\n");
//            System.out.println("Cantidad de filas afectadas "+cant+"\n");
//
//            /* 5. creo un Statement para listar todas las personas de la base de datos */
//            Statement stmt = con.createStatement();
//            String query = "Select * from Personas";
//
//            /* 6. ejecuto la consulta, listo las personas y cierro el ResultSet y el Statement */
//            ResultSet result = stmt.executeQuery(query);
//            System.out.println("Resultado de query : " + query);
//            while (result.next()){
//                System.out.println("\nPersona nombre :" + result.getString("nombre").trim());
//            }
//            result.close();
//            stmt.close();
//
//            /* 7. por ultimo, cierro la conexion con la base de datos */
//            con.close();
//
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        }

        /*** Ejercicio 2 ***/
//
//        try {
//            Class.forName(driver);
//            String url = "jdbc:mysql://"+ host + "/" + db;
//            Connection connection = DriverManager.getConnection(url, username, pass);
//
//            String sql1 = "Create table Personas (cedula int, nombre varchar(45), apellido varchar(45), primary key (cedula))";
//            String sql2 = "Create table Maestras (cedula int, grupo varchar(45), primary key (cedula), FOREIGN KEY (cedula) REFERENCES Personas(cedula))";
//            String sql3 = "Create table Alumnos (cedula int, cedulaMaestra int, primary key (cedula), FOREIGN KEY (cedula) REFERENCES Personas(cedula), FOREIGN KEY (cedulaMaestra) references Maestras(cedula))";
//
//            Statement stmt1 = connection.createStatement();
//            Statement stmt2 = connection.createStatement();
//            Statement stmt3 = connection.createStatement();
//
//            int result1 = stmt1.executeUpdate(sql1);
//            int result2 = stmt2.executeUpdate(sql2);
//            int result3 = stmt3.executeUpdate(sql3);
//
//            System.out.println("RESULT QUERY 1 : " + result1);
//            System.out.println("RESULT QUERY 1 : " + result2);
//            System.out.println("RESULT QUERY 1 : " + result3);
//
//            stmt1.close();
//            stmt2.close();
//            stmt3.close();
//            connection.close();
//
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        }

        /*** Ejercicio 3 ***/

        boolean hasEnded = false;
        Connection connection = null;
        int rows = 0;
        try {
            Class.forName(driver);
            String url = "jdbc:mysql://"+ host + "/" + db;
            connection = DriverManager.getConnection(url, username, pass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        
        while (!hasEnded){
            System.out.println("\n########################################");
            System.out.println("## 1 - Ingresar comando manualmente.  ##");
            System.out.println("## 2 - Maestra con mas alumnos.       ##");
            System.out.println("## 3 - Maestra con mas alumnos-detalle##");
            System.out.println("## 4 - Salir.                         ##");
            System.out.println("########################################");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String input = reader.readLine();
            switch (input){
                case "4" :
                    hasEnded = true;
                    System.out.println("Hasta la proxima!");
                    try {
                        connection.close();
                    } catch (SQLException throwables) {
                        System.out.println("Error cerrando la conexion.");
                    }
                    break;
                case "1" :
                    try {
                        System.out.println("\n\nIngrese comando : ");

                        BufferedReader readerQuery = new BufferedReader(new InputStreamReader(System.in));
                        String command = reader.readLine();
                        PreparedStatement preparedStatement = connection.prepareStatement(command);
                        rows = preparedStatement.executeUpdate();

                        System.out.println("Cantidad de filas afectadas :" + rows);

                        preparedStatement.close();
                    } catch (SQLException throwables) {
                        System.out.println("El comando ingresado es incorrecto.");
                    }
                    break;
                case "2" :
                    try {
                        Statement statement = connection.createStatement();
                        String query = "Select p.nombre, p.apellido from Personas p, Maestras m where p.cedula = m.cedula and m.cedula =" +
                                "(Select cedulaMaestra from Alumnos group by cedulaMaestra order by count(*) desc  limit 1);";

                        ResultSet result = statement.executeQuery(query);
                        while (result.next()){
                            String nombre = result.getString("nombre");
                            String apellido = result.getString("apellido");
                            System.out.println ("\nLa maestra con mas alumnos es : " + nombre + " " + apellido);
                        }
                        result.close();
                        statement.close();
                    } catch (SQLException throwables) {
                        System.out.println("No se pudo calcular el total de alumnos por Maestra.");
                    }
                    break;
                case "3" :
                    int cantidadMayor = 0;
                    int cedulaMayor = 0;
                    try {
                        //Cedulas de maestras
                        Statement statement1 = connection.createStatement();
                        String query1 = "Select cedula from Maestras";
                        ResultSet result1 = statement1.executeQuery(query1);

                        //Cantidad de alumnos por maestra
                        String cantAlmunos = "Select count(*) as cantidad from Alumnos where cedulaMaestra = ?";
                        PreparedStatement preparedStmAlumnos = connection.prepareStatement(cantAlmunos);
                        System.out.println("Datos de las Maestras del sistema :\n");
                        System.out.println("---------------------------");
                        while (result1.next()){
                            preparedStmAlumnos.setInt(1, result1.getInt("cedula"));
                            ResultSet cantidadActual = preparedStmAlumnos.executeQuery();

                            System.out.println("Cedula :" + result1.getInt("cedula"));
                            while(cantidadActual.next()){
                                System.out.println("Cantidad de alumnos : " + cantidadActual.getInt("cantidad"));
                                System.out.println("---------------------------\n");
                                if(cantidadActual.getInt("cantidad") > cantidadMayor){
                                    cantidadMayor = cantidadActual.getInt("cantidad");
                                    cedulaMayor = result1.getInt("cedula");
                                }
                            }
                        }

                        //Datos personales de maestra con más alumnos
                        String datosMaestra = "Select nombre, apellido from Personas where cedula = ?";
                        PreparedStatement preparedStatement1 = connection.prepareStatement(datosMaestra);
                        preparedStatement1.setInt(1, cedulaMayor);
                        ResultSet nombreMaestra = preparedStatement1.executeQuery();

                        while(nombreMaestra.next()){
                            System.out.println("La maestra con mayor cantidad de alumnos es " + nombreMaestra.getString("nombre")
                                    + " " + nombreMaestra.getString("apellido"));
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    break;
                default :
                    System.out.println("La opcion ingresada es incorrecta, por favor, intente nuevamente.");
                    break;
            }
        }



        /* Connection to re-use
        try {
            Class.forName(driver);
            String url = "jdbc:mysql://"+ host + "/" + db;
            Connection connection = DriverManager.getConnection(url, username, pass);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
         */
    }
}