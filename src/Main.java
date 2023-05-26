import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    static Statement statement=null;
    public static void main(String[] args) {

    }

    public static void conectarDB(){


        Connection conexion = null;
        int op = 0;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = "jdbc:mariadb://localhost:3307/?user=root&password=abc123.";

        try {
            conexion = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("No hay ningún driver que reconozca la URL especificada");
        } catch (Exception e) {
            System.out.println("Se ha producido algún otro error");
        }
        try {
            statement = conexion.createStatement();
        } catch (SQLException e) {
            System.out.println("Error");
        }catch (NullPointerException e){
            throw new RuntimeException();
        }

        CrearDB.CreacionDB(statement);


    }
    public static void HP(Statement statement){
        System.out.println("");
    }
}