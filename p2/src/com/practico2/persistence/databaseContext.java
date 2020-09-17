package src.com.practico2.persistence;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class databaseContext {

    private String username;

    private String host;

    private String pass;

    private String db;

    private String driver;

    private String url;

    private Connection connection;

    public databaseContext() throws IOException {

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
            connection = DriverManager.getConnection(url,username,pass);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
