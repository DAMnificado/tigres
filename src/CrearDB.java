import java.sql.SQLException;
import java.sql.Statement;

public class CrearDB {

public static void CreacionDB(Statement statement){
    try{

        String NombreDB = "Biblioteca";

        statement.execute("Drop database if exists "+ NombreDB+";");
        statement.execute("Create database if not exists "+NombreDB+";");
        statement.execute("Use database "+ NombreDB +";");

        statement.execute("Create table Autores(\n" +
                "DNI varchar(9) primary key,\n" +
                "Nombre varchar(30),\n" +
                "Nacionalidad varchar(30)\n" +
                ");");

        statement.execute("Create table Libros(\n" +
                "idLibro int(5) primary key auto_increment,\n" +
                "Titulo varchar(30),\n" +
                "Precio float,\n" +
                "Autor varchar(9),\n" +
                "foreign key (Autor) references Autores (DNI)\n" +
                ");");

    }catch (SQLException e){
        System.out.println(e);
    }
    }


}
