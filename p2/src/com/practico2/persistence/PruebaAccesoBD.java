package src.com.practico2.persistence;


import java.sql.*;

public class PruebaAccesoBD {

    public static void main(String[] args) {

        /* 1. cargo dinamicamente el driver de MySQL */
        String driver = "com.mysql.jdbc.Driver";

        try {
            Class.forName(driver);

            /* 2. una vez cargado el driver, me conecto con la base de datos */
            String url = "jdbc:mysql://localhost:3306/Prueba";
            Connection con = DriverManager.getConnection(url, "admin", "astropay");

            /* 3. creo un PreparedStatement para insertar una persona en base de datos */
            String insert = "INSERT into Personas values (?)";
            PreparedStatement pstmt = con.prepareStatement(insert);
            pstmt.setString(1, "Juan");

            /* 4. ejecuto la sentencia de insercion y cierro el PreparedStatement */
            int cant = pstmt.executeUpdate();
            pstmt.close();
            System.out.println("Resultado del Insert :" + insert + "\n");
            System.out.println("Cantidad de filas afectadas "+cant+"\n");

            /* 5. creo un Statement para listar todas las personas de la base de datos */
            Statement stmt = con.createStatement();
            String query = "Select * from Personas";

            /* 6. ejecuto la consulta, listo las personas y cierro el ResultSet y el Statement */
            ResultSet result = stmt.executeQuery(query);
            System.out.println("Resultado de query : " + query);
            while (result.next()){
                System.out.println("\nPersona nombre :" + result.getString("nombre").trim());
            }
            result.close();
            stmt.close();

            /* 7. por ultimo, cierro la conexion con la base de datos */
            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
