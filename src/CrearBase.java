import java.sql.SQLException;
import java.sql.Statement;

public class CrearBase {

public static void CrearB(Statement sentencia){
    try{

        String NombreDB = "Biblioteca";

        sentencia.execute("Drop database if exists "+ NombreDB+";");
        sentencia.execute("Create database if not exists "+NombreDB+";");
        sentencia.execute("Use database "+ NombreDB +";");

        sentencia.execute("Create table Autores(\n" +
                "DNI varchar(9) primary key,\n" +
                "Nombre varchar(30),\n" +
                "Nacionalidad varchar(30)\n" +
                ");");

        sentencia.execute("Create table Libros(\n" +
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
