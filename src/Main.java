import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class Main {

    //importante dejar claro que la tabla de datos tiene una variable que se llama
    //autor en la que se registra el DNI como clave foránea que viene de la tabla autores
    //por eso en borrar se compara Autor ='" + DNIAutorABorrar +"'"

    static Statement sentencia =null;

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

    conectar();

    }

    public static void conectar(){


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
            sentencia = conexion.createStatement();
        } catch (SQLException e) {
            System.out.println("Error");
        }catch (NullPointerException e){
            throw new RuntimeException();
        }

        CrearBase.CrearB(sentencia);
// esto es un filtro para comprobar que el DNI que acaban
// de introducir por teclado no está en ningún autor de nuestra base de datos
// lo que significa que el autor que quieren añadir ya está registrado


    }

    public static void addAutor() {
        sc = new Scanner(System.in);

        try {
            System.out.println("Vamos a añadir el autor");
            System.out.println("Dime el DNI del nuevo autor");
            String nuevoDNI = sc.next();

            String consultaDNI = "Select * from Autores where DNI ='" + nuevoDNI + "'";
            ResultSet resultadoConsultaDNI = sentencia.executeQuery(consultaDNI);

            // el if es un filtro para comprobar si el DNI que acaban
            // de introducir por teclado ya está el algún autor de nuestra base de datos
            // si fuera asi el autor que quieren añadir ya estaría registrado y saltaría un error
            if (resultadoConsultaDNI.next()) {
                System.out.println("Este autor ya está registrado");
            } else {
                System.out.println("Necesito el nombre del autor a añadir");
                String nuevoNombre = sc.next();
                System.out.println("Para finalizar dime su nacionalidad por favor");
                String nuevaNacionalidad = sc.next();

                String adAutor = "Insert into Autores (DNI, Nombre, Nacionalidad) Values('" + nuevoDNI + "', '" + nuevoNombre + "', '" + nuevaNacionalidad + "')";
                sentencia.execute(adAutor);
                System.out.println("Se ha añadido este autor a nuestra base de datos. Gracias!");
            }
        } catch (SQLException e) {
            System.out.println("Error al añadir" + e.getMessage());
        }
    }

    public static void addLibro(){
          sc = new Scanner(System.in);

          try {

              System.out.println("Vamos a añadir un libro");
              System.out.println("Dime el DNI de quien lo escribió por favor");
              String DNIautorNuevoLibro = sc.nextLine();

              // lo escribo asi porque si no no me queda claro Nuria
              String consultaLibroPorDNIautorNuevoLibro = "Select * from Autores where DNI ='" + DNIautorNuevoLibro + "'";
              ResultSet resultado = sentencia.executeQuery(consultaLibroPorDNIautorNuevoLibro);

              if (resultado.next()) {

                  sc = new Scanner(System.in);
                  System.out.println("Dime el ID del libro a añadir");
                  int IDNuevoLibro = sc.nextInt();

                  System.out.println("Dime su titulo");
                  String tituloNuevoLibro = sc.next();

                  sc = new Scanner(System.in);
                  System.out.println("su precio");
                  float precioNuevoLibro = sc.nextInt();

                  sc = new Scanner(System.in);
                  System.out.println("y por último el nombre del autor");
                  String autorNuevoLibro = sc.next();

                  String adLibro = "Insert Into Libros (idLibro Titulo," +
                          " Precio, Autor) Values ('" + IDNuevoLibro + "', " + tituloNuevoLibro + ", '"
                          + precioNuevoLibro + ",'" + autorNuevoLibro + "')";
                  sentencia.execute(adLibro);
                  System.out.println("Libro añadido, gracias");


              } else {
                  System.out.println("El libro que quieres añadir no coincide" +
                          " con ningún DNI relacionado a ningún autor que esté en nuestra Base de datos");


              }
          }
          catch (SQLException e) {
              System.out.println("Error al añadir" + e.getMessage());
          }
      }

    public static void borrarLibro(){
        sc = new Scanner(System.in);
        try {
            System.out.println("Vamos a borrar un libro de nuestra base de datos");
            System.out.println("Dime el ID del libro a borrar");
            sc = new Scanner(System.in);
            int IDLibroABorrar = sc.nextInt();

            String consultaPorIDLibroABorrar = "Select * from Libros where idLibro ='" + IDLibroABorrar + "'";
            ResultSet resultadoConsultaPorIDLibroABorrar = sentencia.executeQuery(consultaPorIDLibroABorrar);

            if (resultadoConsultaPorIDLibroABorrar.next()){

                String borrarLibro = "Delete from Libros where idLibro ='" + IDLibroABorrar +"'";
                sentencia.execute(borrarLibro);
                System.out.println("Libro borrado, gracias");

            }else System.out.println("El ID no coincide, error");

        }catch (SQLException e){
            System.out.println("Error al borrar" + e.getMessage());
        }

    }

    public static void borrarAutor(){
        sc = new Scanner(System.in);
        try {
            System.out.println("Vamos a eliminar un autor de nuestra base de datos");
            System.out.println("Dime el DNI del autor a borrar");
            sc = new Scanner(System.in);
            String DNIAutorABorrar = sc.next();

            String consultaPorDNIAutorABorrar = "Select * from Autores where DNI ='"+DNIAutorABorrar+"'";
            ResultSet resultadoConsultaPorDNIAutorABorrar = sentencia.executeQuery(consultaPorDNIAutorABorrar);

            if (resultadoConsultaPorDNIAutorABorrar.next()){

                //borramos el autor
                String borrarAutor = "Delete from Autores where DNI ='" + DNIAutorABorrar +"'";
                sentencia.execute(borrarAutor);

                //borramos los libros que escribió el autor que acabamos de borrar
                String borrarLibrosQueEscribioElAutorEliminado = "Delete from Libros where Autor ='" + DNIAutorABorrar +"'";
                sentencia.execute(borrarLibrosQueEscribioElAutorEliminado);

                System.out.println("Autor eliminado con todos sus libros, gracias");

            }else System.out.println("El DNI no coincide, error");

        }catch (SQLException e){
            System.out.println("Error al borrar" + e.getMessage());
        }
    }

    public static void modLibro (){

        sc = new Scanner(System.in);
        try {
            System.out.println("Vamos a modificar un libro de nuestra base de datos");
            System.out.println("Dime cual es el título actual");
            sc = new Scanner(System.in);
            String TituloLibroActual = sc.next();

            String consultaPorTituloLibroMod = "Select * from Libros where Titulo ='"+TituloLibroActual+"'";
            ResultSet resultadoconsultaPorTituloLibroMod = sentencia.executeQuery(consultaPorTituloLibroMod);

            if (resultadoconsultaPorTituloLibroMod.next()){

                sc = new Scanner(System.in);
                System.out.println("Dime el nuevo ID del libro");
                int IDLibroModificado = sc.nextInt();

                System.out.println("Dime su titulo");
                String tituloLibroModificado = sc.next();

                sc = new Scanner(System.in);
                System.out.println("su precio");
                float precioLibroModificado = sc.nextInt();

                sc = new Scanner(System.in);
                System.out.println("y por último el nombre del autor que lo escribió");
                String autorLibroModificado = sc.next();

                String MODLibro = "Update Libros Set Titulo = '" + tituloLibroModificado + "', Precio = "
                        + precioLibroModificado +"', idLibro = " + IDLibroModificado
                        +"', Autor = " + autorLibroModificado + " WHERE Titulo = '" + TituloLibroActual + "'";
                sentencia.execute(MODLibro);

                System.out.println("Libro modificado, gracias");


            }else System.out.println("El título coincide con ningún libro que tengamos, error");

        }catch (SQLException e){
            System.out.println("Error al borrar" + e.getMessage());
        }
    }

    public static void modAutor(){
        sc = new Scanner(System.in);
        try {
            System.out.println("Vamos a modificar un autor de nuestra base de datos");
            System.out.println("Dime cual el DNI de dicho autor");
            sc = new Scanner(System.in);
            String DNIAutorActual = sc.next();

            String consultaPorDNIAutorAMod = "Select * from Libros where Titulo ='"+DNIAutorActual+"'";
            ResultSet resultadoconsultaPorDNIAutorAMod = sentencia.executeQuery(consultaPorDNIAutorAMod);

            if (resultadoconsultaPorDNIAutorAMod.next()){

                sc = new Scanner(System.in);
                System.out.println("Dime el nuevo DNi del autor");
                int DNIAutorModificado = sc.nextInt();

                System.out.println("Dime su nombre");
                String NombreAutorModificado = sc.next();

                System.out.println("y su nacionalidad, por favor");
                String NacionalidadAutorModificada = sc.next();

                String MODAutor = "Update Autores Set DNI = '" + DNIAutorModificado + "', Nombre = "
                        + NombreAutorModificado +"', Nacionalidad = " + NacionalidadAutorModificada
                        + " WHERE DNI = '" + DNIAutorActual + "'";
                sentencia.execute(MODAutor);

                System.out.println("Autor modificado, gracias");


            }else System.out.println("El DNI del autor no coincide con ninguno que tengamos, error");

        }catch (SQLException e){
            System.out.println("Error al borrar" + e.getMessage());
        }












    }



    }






