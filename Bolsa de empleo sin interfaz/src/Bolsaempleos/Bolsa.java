package Bolsaempleos;


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.logger.Level;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import javax.swing.*;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class Bolsa {
    public static void main(String[] args) throws Exception {
        boolean salir = false;
        int opcion;

        Logger.setGlobalLogLevel(Level.OFF);
        Scanner teclado = new Scanner(System.in);
        // Ubicación del archivo de la base de datos
        String url = "jdbc:h2:file:./banco";
        ConnectionSource conexion =
                new JdbcConnectionSource(url);
        // Obtener acceso a la lista de objetos=>Tabla (DAO)
        // Primero es la clase de la tabla, Segundo tipo de la llave
        Dao<Aspirante, String> listaAspirantes =
                DaoManager.createDao(conexion, Aspirante.class);


        // Cerrar la conexión
        conexion.close();


        while (!salir) {
            System.out.println("Menu de opciones");
            System.out.println("1. Agregar nuevo aspirante");
            System.out.println("2. Mostrar cedulas de aspirantes");
            System.out.println("3. Mostrar informacion de aspirante");
            System.out.println("4. Buscar aspirante");
            System.out.println("5. Ordenar aspirantes");
            System.out.println("6. Aspirante con mayor experiencia");
            System.out.println("7. Aspirante mas joven");
            System.out.println("8. Contratar aspirante");
            System.out.println("9. Experiencia que necesita en los aspirantes");
            System.out.println("10. Promedio de edad de los aspirantes");
            System.out.println("11. Salir");

            try {

                System.out.println("Seleccione que opcion desea: ");
                opcion = teclado.nextInt();

                switch (opcion) {
                    case 1:
                        boolean menu = false;

                        while (!menu) {


                            System.out.println("A continuación agrega la información del nuevo aspirante");
                            System.out.print("Entre el número de cédula: ");
                            teclado.nextLine(); // Consumir el carácter de nueva línea pendiente
                            String cedula = teclado.nextLine().toLowerCase();
                            System.out.print("Entre el nombre del aspirante: ");
                            String nombre = teclado.nextLine().toLowerCase();
                            System.out.print("Entre la profesión: ");
                            String profesion = teclado.nextLine().toLowerCase();
                            System.out.print("Entre la edad: ");
                            int edad = teclado.nextInt();
                            System.out.print("Entre la experiencia en años: ");
                            int experiencia = teclado.nextInt();
                            System.out.print("Entre el número de teléfono: ");
                            int telefono = teclado.nextInt();

                            Aspirante aspirantes = new Aspirante(cedula, nombre, edad, experiencia, profesion, telefono);

                            System.out.println("¿Desea agregar otro aspirante? Si / No");
                            String valor = teclado.nextLine();
                            menu = valor.equalsIgnoreCase("no");
                            String respuesta = menu ? "si" : "no";

                        }
                        break;
                    case 2:
                        boolean menu2 = false;

                        while (!menu2) {

                            System.out.println("¿Desea de nuevo ver las cedulas de los aspirantes?: Si / No");
                            String valor = teclado.nextLine();
                            menu2 = valor.equalsIgnoreCase("no");
                            String respuesta = menu2 ? "si" : "no";

                            for (Aspirante C : listaAspirantes) {
                                System.out.println("Cedula: " + C.getCedula() + " Nombre: " + C.getNombre());
                            }

                        }
                        break;
                    case 3:
                        boolean menu3 = false;
                        while (!menu3) {

                            System.out.println("¿Desea buscar otra cedula?: Si / No");
                            String valor = teclado.nextLine();
                            menu3 = valor.equalsIgnoreCase("no");
                            String respuesta = menu3 ? "si" : "no";
                            System.out.println(menu3);

                            System.out.print("Ingrese el numero de cedula del Aspirante a buscar: ");
                            teclado.nextLine();
                            String cedulaBusquedad = teclado.nextLine();


                            Aspirante aspirante = listaAspirantes.queryForId(cedulaBusquedad);
                            if (aspirante == null) {
                                System.out.println("No existe un aspirante con esa cedula : " + cedulaBusquedad);
                            } else {
                                System.out.println("Cedula: " + aspirante.getCedula());
                                System.out.println("Nombre: " + aspirante.getNombre());
                                System.out.println("Profesion: " + aspirante.getProfesion());
                                System.out.println("Edad: " + aspirante.getEdad());
                                System.out.println("Experiencia: " + aspirante.getExperiencia());
                                System.out.println("Telefono: " + aspirante.getTelefono());
                            }
                            conexion.close();


                        }
                        break;
                    case 4:
                        boolean menu4 = false;
                        while (!menu4) {


                            System.out.println("¿Desea de nuevo buscar algun otro aspirante?: Si / No");
                            String valor = teclado.nextLine();
                            menu4 = valor.equalsIgnoreCase("no");
                            String respuesta = menu4 ? "si" : "no";
                            System.out.println(menu4);

                            System.out.print("Ingrese el nombre del Aspirante a buscar: ");
                            teclado.nextLine(); // Consumir el carácter de nueva línea pendiente
                            String nombreBusqueda = teclado.nextLine().toLowerCase();

                            List<Aspirante> buscarNombre = listaAspirantes.queryForEq("nombre", nombreBusqueda);

                            if (buscarNombre.size() == 0) {
                                System.out.println("No se encontraron resultados.");
                            } else {
                                System.out.println("Se encontraron " + buscarNombre.size() + " resultados:");
                                for (Aspirante nomB : buscarNombre) {
                                    System.out.println("Cedula: " + nomB.getCedula() + " Nombre: " + nomB.getNombre() + " Edad: " + nomB.getEdad() + " Profesion: " + nomB.getProfesion() + " Experiencia: " + nomB.getExperiencia() + " Telefono: " + nomB.getTelefono());
                                }
                            }


                        }
                        break;
                    case 5:
                        boolean datosordenar = false;
                        boolean menu5 = false;
                        while (!menu5) {

                            boolean Decendente = true;
                            int opcionord;

                            System.out.println("¿Desea que los datos se ordenen Decendente?: Si / No");
                            String valor = teclado.nextLine();
                            datosordenar = valor.equalsIgnoreCase("no");
                            String respuesta = datosordenar ? "si" : "no";
                            System.out.println(datosordenar);


                            while (!datosordenar) {


                                System.out.println("Elija con que dato desea ordenar los aspirantes");
                                System.out.println("1. Edades");
                                System.out.println("2. Experiencia");
                                System.out.println("3. Profesion");
                                System.out.println("4. Salir");


                                System.out.println("Seleccione que opcion desea: ");
                                opcionord = teclado.nextInt();

                                switch (opcionord) {
                                    case 1:
                                        boolean ord1 = false;
                                        int ordenamiento1;
                                        while (!ord1) {

                                            System.out.println("Elija con que criterio desea ordenar los aspirantes");
                                            System.out.println("1. Igual a");
                                            System.out.println("2. Distinto a ");
                                            System.out.println("3. Mayor que");
                                            System.out.println("4. Mayor o igual que");
                                            System.out.println("5. Menor que");
                                            System.out.println("6. Menor o igual que");
                                            System.out.println("7. Entre dos valores");
                                            System.out.println("8. Salir");

                                            System.out.println("Seleccione que opcion desea: ");
                                            ordenamiento1 = teclado.nextInt();


                                            switch (ordenamiento1) {
                                                case 1:
                                                    String ordEdadIgual = "";

                                                    System.out.println("Ingrese la edad que desea de los aspirantes: ");
                                                    String busqAñosI = teclado.nextLine();

                                                    QueryBuilder<Aspirante, String> queryBuilder = listaAspirantes.queryBuilder();

                                                    queryBuilder.where().eq("edad", busqAñosI);

                                                    queryBuilder.orderBy("edad", Decendente);

                                                    List<Aspirante> order = queryBuilder.query();

                                                    for (Aspirante edM : order) {
                                                        ordEdadIgual += "Cedula: " + edM.getCedula() + " Nombre: " + edM.getNombre() + " Edad: " + edM.getEdad() + " Experiencia: " + edM.getExperiencia() + " Profesion: " + edM.getProfesion() + " Telefono: " + edM.getTelefono() + "\n";
                                                    }
                                                    System.out.println("Ordenamiento realizado");
                                                    System.out.println(ordEdadIgual);

                                                    break;
                                                case 2:
                                                    String ordEdadDistinto = "";

                                                    System.out.println("Ingrese la edad que no quiere que tengan los aspirantes: ");
                                                    String busqAñosD = teclado.nextLine();

                                                    QueryBuilder<Aspirante, String> busqAñosDistinto = listaAspirantes.queryBuilder();

                                                    busqAñosDistinto.where().ne("edad", busqAñosD);

                                                    busqAñosDistinto.orderBy("edad", Decendente);

                                                    List<Aspirante> orderD = busqAñosDistinto.query();

                                                    for (Aspirante edM : orderD) {
                                                        ordEdadDistinto += "Cedula: " + edM.getCedula() + " Nombre: " + edM.getNombre() + " Edad: " + edM.getEdad() + " Experiencia: " + edM.getExperiencia() + " Profesion: " + edM.getProfesion() + " Telefono: " + edM.getTelefono() + "\n";
                                                    }
                                                    JOptionPane.showMessageDialog(null, ordEdadDistinto, "Ordenamiento realizado", JOptionPane.PLAIN_MESSAGE);


                                                    break;
                                                case 3:
                                                    String ordEdadMayor = "";

                                                    System.out.println("Ingrese la edad mayor que desea de los aspirantes: ");
                                                    String busqAñosM = teclado.nextLine();

                                                    QueryBuilder<Aspirante, String> busqAñosMayor = listaAspirantes.queryBuilder();

                                                    busqAñosMayor.where().gt("edad", busqAñosM);

                                                    busqAñosMayor.orderBy("edad", Decendente);

                                                    List<Aspirante> orderM = busqAñosMayor.query();

                                                    for (Aspirante edM : orderM) {
                                                        ordEdadMayor += "Cedula: " + edM.getCedula() + " Nombre: " + edM.getNombre() + " Edad: " + edM.getEdad() + " Experiencia: " + edM.getExperiencia() + " Profesion: " + edM.getProfesion() + " Telefono: " + edM.getTelefono() + "\n";
                                                    }
                                                    JOptionPane.showMessageDialog(null, ordEdadMayor, "Ordenamiento realizado", JOptionPane.PLAIN_MESSAGE);

                                                    break;
                                                case 4:
                                                    String ordEdadMayoroIgual = "";

                                                    System.out.println("Ingrese la edad mayor o igual que desea de los aspirantes: ");
                                                    String busqAñosMoI = teclado.nextLine();

                                                    QueryBuilder<Aspirante, String> busqAñosMayoroIgual = listaAspirantes.queryBuilder();

                                                    busqAñosMayoroIgual.where().ge("edad", busqAñosMoI);

                                                    busqAñosMayoroIgual.orderBy("edad", Decendente);

                                                    List<Aspirante> orderMoI = busqAñosMayoroIgual.query();

                                                    for (Aspirante edM : orderMoI) {
                                                        ordEdadMayoroIgual += "Cedula: " + edM.getCedula() + " Nombre: " + edM.getNombre() + " Edad: " + edM.getEdad() + " Experiencia: " + edM.getExperiencia() + " Profesion: " + edM.getProfesion() + " Telefono: " + edM.getTelefono() + "\n";
                                                    }
                                                    JOptionPane.showMessageDialog(null, ordEdadMayoroIgual, "Ordenamiento realizado", JOptionPane.PLAIN_MESSAGE);

                                                    break;
                                                case 5:
                                                    String ordEdadMenor = "";

                                                    System.out.println("Ingrese la edad menor que desea de los aspirantes: ");
                                                    String busqAñosMen = teclado.nextLine();

                                                    QueryBuilder<Aspirante, String> busqAñosMenor = listaAspirantes.queryBuilder();

                                                    busqAñosMenor.where().lt("edad", busqAñosMen);

                                                    busqAñosMenor.orderBy("edad", Decendente);

                                                    List<Aspirante> orderMen = busqAñosMenor.query();

                                                    for (Aspirante edM : orderMen) {
                                                        ordEdadMenor += "Cedula: " + edM.getCedula() + " Nombre: " + edM.getNombre() + " Edad: " + edM.getEdad() + " Experiencia: " + edM.getExperiencia() + " Profesion: " + edM.getProfesion() + " Telefono: " + edM.getTelefono() + "\n";
                                                    }
                                                    JOptionPane.showMessageDialog(null, ordEdadMenor, "Ordenamiento realizado", JOptionPane.PLAIN_MESSAGE);

                                                    break;
                                                case 6:
                                                    String ordEdadMenoroIgual = "";

                                                    System.out.println("Ingrese la edad menor o igual que desea de los aspirantes: ");
                                                    String busqAñosMenoI = teclado.nextLine();

                                                    QueryBuilder<Aspirante, String> busqAñosMenoroIgual = listaAspirantes.queryBuilder();

                                                    busqAñosMenoroIgual.where().le("edad", busqAñosMenoI);

                                                    busqAñosMenoroIgual.orderBy("edad", Decendente);

                                                    List<Aspirante> orderMenoI = busqAñosMenoroIgual.query();

                                                    for (Aspirante edM : orderMenoI) {
                                                        ordEdadMenoroIgual += "Cedula: " + edM.getCedula() + " Nombre: " + edM.getNombre() + " Edad: " + edM.getEdad() + " Experiencia: " + edM.getExperiencia() + " Profesion: " + edM.getProfesion() + " Telefono: " + edM.getTelefono() + "\n";
                                                    }
                                                    JOptionPane.showMessageDialog(null, ordEdadMenoroIgual, "Ordenamiento realizado", JOptionPane.PLAIN_MESSAGE);

                                                    break;
                                                case 7:
                                                    String ordEdadEntre = "";

                                                    System.out.println("Ingrese la edad mínima de los aspirantes: ");
                                                    String busqAñosEntMin = teclado.nextLine();
                                                    teclado.nextLine();
                                                    System.out.println("Ingrese la edad máxima de los aspirantes: ");
                                                    String busqAñosEntMax = teclado.nextLine();

                                                    QueryBuilder<Aspirante, String> busqAñosEntre = listaAspirantes.queryBuilder();

                                                    busqAñosEntre.where().between("edad", busqAñosEntMin, busqAñosEntMax);

                                                    busqAñosEntre.orderBy("edad", Decendente);

                                                    List<Aspirante> orderent = busqAñosEntre.query();

                                                    for (Aspirante edM : orderent) {
                                                        ordEdadEntre += "Cedula: " + edM.getCedula() + " Nombre: " + edM.getNombre() + " Edad: " + edM.getEdad() + " Experiencia: " + edM.getExperiencia() + " Profesion: " + edM.getProfesion() + " Telefono: " + edM.getTelefono() + "\n";
                                                    }
                                                    JOptionPane.showMessageDialog(null, ordEdadEntre, "Ordenamiento realizado", JOptionPane.PLAIN_MESSAGE);

                                                    break;
                                                case 8:
                                                    ord1 = true;
                                                    System.out.println("Ah vuelto al menu anterior");
                                                    break;
                                                default:
                                                    System.out.println("Las opciones son entre el 1 al 8");
                                                    break;
                                            }
                                        }


                                        break;
                                    case 2:
                                        boolean ord2 = false;
                                        int ordenamiento2;
                                        while (!ord2) {

                                            System.out.println("Elija con que criterio desea ordenar los aspirantes");
                                            System.out.println("1. Igual a");
                                            System.out.println("2. Distinto a ");
                                            System.out.println("3. Mayor que");
                                            System.out.println("4. Mayor o igual que");
                                            System.out.println("5. Menor que");
                                            System.out.println("6. Menor o igual que");
                                            System.out.println("7. Entre dos valores");
                                            System.out.println("8. Salir");

                                            System.out.println("Seleccione que opcion desea: ");
                                            ordenamiento2 = teclado.nextInt();

                                            switch (ordenamiento2) {
                                                case 1:
                                                    String ordEdadIgual = "";

                                                    String busqAñosI = JOptionPane.showInputDialog("Ingrese la experiencia  que desea de los aspirantes: ");

                                                    QueryBuilder<Aspirante, String> queryBuilder = listaAspirantes.queryBuilder();

                                                    queryBuilder.where().eq("experiencia", busqAñosI);

                                                    queryBuilder.orderBy("experiencia", Decendente);

                                                    List<Aspirante> order = queryBuilder.query();

                                                    for (Aspirante edM : order) {
                                                        ordEdadIgual += "Cedula: " + edM.getCedula() + " Nombre: " + edM.getNombre() + " Edad: " + edM.getEdad() + " Experiencia: " + edM.getExperiencia() + " Profesion: " + edM.getProfesion() + " Telefono: " + edM.getTelefono() + "\n";
                                                    }
                                                    JOptionPane.showMessageDialog(null, ordEdadIgual, "Ordenamiento realizado", JOptionPane.PLAIN_MESSAGE);


                                                    break;
                                                case 2:
                                                    String ordEdadDistinto = "";

                                                    String busqAñosD = JOptionPane.showInputDialog("Ingrese la experiencia  que no quiere que tengan los aspirantes: ");

                                                    QueryBuilder<Aspirante, String> busqAñosDistinto = listaAspirantes.queryBuilder();

                                                    busqAñosDistinto.where().ne("experiencia", busqAñosD);

                                                    busqAñosDistinto.orderBy("experiencia", Decendente);

                                                    List<Aspirante> orderD = busqAñosDistinto.query();

                                                    for (Aspirante edM : orderD) {
                                                        ordEdadDistinto += "Cedula: " + edM.getCedula() + " Nombre: " + edM.getNombre() + " Edad: " + edM.getEdad() + " Experiencia: " + edM.getExperiencia() + " Profesion: " + edM.getProfesion() + " Telefono: " + edM.getTelefono() + "\n";
                                                    }
                                                    JOptionPane.showMessageDialog(null, ordEdadDistinto, "Ordenamiento realizado", JOptionPane.PLAIN_MESSAGE);


                                                    break;
                                                case 3:
                                                    String ordEdadMayor = "";

                                                    String busqAñosM = JOptionPane.showInputDialog("Ingrese la experiencia mayor que desea de los aspirantes: ");

                                                    QueryBuilder<Aspirante, String> busqAñosMayor = listaAspirantes.queryBuilder();

                                                    busqAñosMayor.where().gt("experiencia", busqAñosM);

                                                    busqAñosMayor.orderBy("experiencia", Decendente);

                                                    List<Aspirante> orderM = busqAñosMayor.query();

                                                    for (Aspirante edM : orderM) {
                                                        ordEdadMayor += "Cedula: " + edM.getCedula() + " Nombre: " + edM.getNombre() + " Edad: " + edM.getEdad() + " Experiencia: " + edM.getExperiencia() + " Profesion: " + edM.getProfesion() + " Telefono: " + edM.getTelefono() + "\n";
                                                    }
                                                    JOptionPane.showMessageDialog(null, ordEdadMayor, "Ordenamiento realizado", JOptionPane.PLAIN_MESSAGE);

                                                    break;
                                                case 4:
                                                    String ordEdadMayoroIgual = "";

                                                    String busqAñosMoI = JOptionPane.showInputDialog("Ingrese la experiencia mayor o igual que desea de los aspirantes: ");

                                                    QueryBuilder<Aspirante, String> busqAñosMayoroIgual = listaAspirantes.queryBuilder();

                                                    busqAñosMayoroIgual.where().ge("experiencia", busqAñosMoI);

                                                    busqAñosMayoroIgual.orderBy("experiencia", Decendente);

                                                    List<Aspirante> orderMoI = busqAñosMayoroIgual.query();

                                                    for (Aspirante edM : orderMoI) {
                                                        ordEdadMayoroIgual += "Cedula: " + edM.getCedula() + " Nombre: " + edM.getNombre() + " Edad: " + edM.getEdad() + " Experiencia: " + edM.getExperiencia() + " Profesion: " + edM.getProfesion() + " Telefono: " + edM.getTelefono() + "\n";
                                                    }
                                                    JOptionPane.showMessageDialog(null, ordEdadMayoroIgual, "Ordenamiento realizado", JOptionPane.PLAIN_MESSAGE);

                                                    break;
                                                case 5:
                                                    String ordEdadMenor = "";

                                                    String busqAñosMen = JOptionPane.showInputDialog("Ingrese la experiencia menor que desea de los aspirantes: ");

                                                    QueryBuilder<Aspirante, String> busqAñosMenor = listaAspirantes.queryBuilder();

                                                    busqAñosMenor.where().lt("experiencia", busqAñosMen);

                                                    busqAñosMenor.orderBy("experiencia", Decendente);

                                                    List<Aspirante> orderMen = busqAñosMenor.query();

                                                    for (Aspirante edM : orderMen) {
                                                        ordEdadMenor += "Cedula: " + edM.getCedula() + " Nombre: " + edM.getNombre() + " Edad: " + edM.getEdad() + " Experiencia: " + edM.getExperiencia() + " Profesion: " + edM.getProfesion() + " Telefono: " + edM.getTelefono() + "\n";
                                                    }
                                                    JOptionPane.showMessageDialog(null, ordEdadMenor, "Ordenamiento realizado", JOptionPane.PLAIN_MESSAGE);

                                                    break;
                                                case 6:
                                                    String ordEdadMenoroIgual = "";

                                                    String busqAñosMenoI = JOptionPane.showInputDialog("Ingrese la experiencia menor o igual que desea de los aspirantes: ");

                                                    QueryBuilder<Aspirante, String> busqAñosMenoroIgual = listaAspirantes.queryBuilder();

                                                    busqAñosMenoroIgual.where().le("experiencia", busqAñosMenoI);

                                                    busqAñosMenoroIgual.orderBy("experiencia", Decendente);

                                                    List<Aspirante> orderMenoI = busqAñosMenoroIgual.query();

                                                    for (Aspirante edM : orderMenoI) {
                                                        ordEdadMenoroIgual += "Cedula: " + edM.getCedula() + " Nombre: " + edM.getNombre() + " Edad: " + edM.getEdad() + " Experiencia: " + edM.getExperiencia() + " Profesion: " + edM.getProfesion() + " Telefono: " + edM.getTelefono() + "\n";
                                                    }
                                                    JOptionPane.showMessageDialog(null, ordEdadMenoroIgual, "Ordenamiento realizado", JOptionPane.PLAIN_MESSAGE);

                                                    break;
                                                case 7:
                                                    String ordEdadEntre = "";

                                                    String busqAñosEntMin = JOptionPane.showInputDialog("Ingrese la experiencia mínima de los aspirantes: ");
                                                    String busqAñosEntMax = JOptionPane.showInputDialog("Ingrese la experiencia máxima de los aspirantes: ");

                                                    QueryBuilder<Aspirante, String> busqAñosEntre = listaAspirantes.queryBuilder();

                                                    busqAñosEntre.where().between("experiencia", busqAñosEntMin, busqAñosEntMax);

                                                    busqAñosEntre.orderBy("experiencia", Decendente);

                                                    List<Aspirante> orderent = busqAñosEntre.query();

                                                    for (Aspirante edM : orderent) {
                                                        ordEdadEntre += "Cedula: " + edM.getCedula() + " Nombre: " + edM.getNombre() + " Edad: " + edM.getEdad() + " Experiencia: " + edM.getExperiencia() + " Profesion: " + edM.getProfesion() + " Telefono: " + edM.getTelefono() + "\n";
                                                    }
                                                    JOptionPane.showMessageDialog(null, ordEdadEntre, "Ordenamiento realizado", JOptionPane.PLAIN_MESSAGE);

                                                    break;
                                                case 8:
                                                    ord2 = true;
                                                    System.out.println("Ah vuelto al menu anterior");
                                                    break;
                                                default:
                                                    System.out.println("Las opciones son entre el 1 al 8");
                                                    break;
                                            }
                                        }
                                        break;
                                    case 3:
                                        boolean ord3 = false;
                                        int ordenamiento3;
                                        while (!ord3) {

                                            System.out.println("Elija con que criterio desea ordenar los aspirantes");
                                            System.out.println("1. Igual a");
                                            System.out.println("2. Distinto a ");
                                            System.out.println("3. Salir");

                                            System.out.println("Seleccione que opcion desea: ");
                                            ordenamiento3 = teclado.nextInt();

                                            switch (ordenamiento3) {
                                                case 1:
                                                    String ordEdadIgual = "";

                                                    String busqAñosI = JOptionPane.showInputDialog("Ingrese la profesion  que desea de los aspirantes: ");

                                                    QueryBuilder<Aspirante, String> queryBuilder = listaAspirantes.queryBuilder();

                                                    queryBuilder.where().eq("profesion", busqAñosI);

                                                    queryBuilder.orderBy("profesion", Decendente);

                                                    List<Aspirante> order = queryBuilder.query();

                                                    for (Aspirante edM : order) {
                                                        ordEdadIgual += "Cedula: " + edM.getCedula() + " Nombre: " + edM.getNombre() + " Edad: " + edM.getEdad() + " Experiencia: " + edM.getExperiencia() + " Profesion: " + edM.getProfesion() + " Telefono: " + edM.getTelefono() + "\n";
                                                    }
                                                    JOptionPane.showMessageDialog(null, ordEdadIgual, "Ordenamiento realizado", JOptionPane.PLAIN_MESSAGE);


                                                    break;
                                                case 2:
                                                    String ordEdadDistinto = "";

                                                    String busqAñosD = JOptionPane.showInputDialog("Ingrese la profesion  que no quiere que tengan los aspirantes: ");

                                                    QueryBuilder<Aspirante, String> busqAñosDistinto = listaAspirantes.queryBuilder();

                                                    busqAñosDistinto.where().ne("profesion", busqAñosD);

                                                    busqAñosDistinto.orderBy("profesion", Decendente);

                                                    List<Aspirante> orderD = busqAñosDistinto.query();

                                                    for (Aspirante edM : orderD) {
                                                        ordEdadDistinto += "Cedula: " + edM.getCedula() + " Nombre: " + edM.getNombre() + " Edad: " + edM.getEdad() + " Experiencia: " + edM.getExperiencia() + " Profesion: " + edM.getProfesion() + " Telefono: " + edM.getTelefono() + "\n";
                                                    }
                                                    JOptionPane.showMessageDialog(null, ordEdadDistinto, "Ordenamiento realizado", JOptionPane.PLAIN_MESSAGE);


                                                    break;
                                                case 3:
                                                    ord3 = true;
                                                    System.out.println("Ah vuelto al menu anterior");
                                                    break;
                                                default:
                                                    System.out.println("Las opciones son entre el 1 al 3");
                                                    break;
                                            }
                                        }
                                        break;
                                    case 4:
                                        datosordenar = true;
                                        System.out.println("Regreso al menu");
                                        break;
                                    default:
                                        System.out.println("Las opciones son entre el 1 al 4");
                                        break;
                                }
                            }
                            break;
                        }
                        break;
                    case 6:
                        boolean menu6 = false;
                        while (!menu6) {

                            System.out.println("¿Desea ver de nuevo el aspirante con mayor experiencia?: Si / No");
                            String valor = teclado.nextLine();
                            menu6 = valor.equalsIgnoreCase("no");
                            String respuesta = menu6 ? "si" : "no";
                            System.out.println(menu6);

                            int mayor = 0;

                            for (Aspirante ex : listaAspirantes) {
                                if (mayor < ex.getExperiencia()) {
                                    mayor = ex.getExperiencia();
                                }
                            }

                            System.out.println("Los datos del aspirante con mayor experiencia es: ");

                            for (Aspirante datos : listaAspirantes) {
                                if (datos.getExperiencia() == mayor) {
                                    System.out.println("Cedula: " + datos.getCedula());
                                    System.out.println("Nombre: " + datos.getNombre());
                                    System.out.println("Profesion: " + datos.getProfesion());
                                    System.out.println("Edad: " + datos.getEdad());
                                    System.out.println("Experiencia: " + datos.getExperiencia());
                                    System.out.println("Telefono: " + datos.getTelefono());
                                    break;
                                }
                            }


                        }
                        break;
                    case 7:
                        boolean menu7 = false;
                        while (!menu7) {
                            int joven = 999;

                            System.out.println("¿Desea ver de nuevo el aspirante mas joven?: Si / No");
                            String valor = teclado.nextLine();
                            menu7 = valor.equalsIgnoreCase("no");
                            String respuesta = menu7 ? "si" : "no";

                            for (Aspirante jov : listaAspirantes) {
                                if (joven > jov.getEdad()) {
                                    joven = jov.getEdad();
                                }
                            }

                            System.out.println("Los datos del aspirante mas joven es: ");

                            for (Aspirante datosjoven : listaAspirantes) {
                                if (datosjoven.getEdad() == joven) {
                                    System.out.println("Cedula: " + datosjoven.getCedula());
                                    System.out.println("Nombre: " + datosjoven.getNombre());
                                    System.out.println("Profesion: " + datosjoven.getProfesion());
                                    System.out.println("Edad: " + datosjoven.getEdad());
                                    System.out.println("Experiencia: " + datosjoven.getExperiencia());
                                    System.out.println("Telefono: " + datosjoven.getTelefono());
                                    break;
                                }
                            }


                        }
                        break;
                    case 8:
                        boolean menu8 = false;
                        while (!menu8) {

                            System.out.println("¿Desea contratar otro aspirante?: Si / No");
                            String valor = teclado.nextLine();
                            menu8 = valor.equalsIgnoreCase("no");
                            String respuesta = menu8 ? "si" : "no";
                            System.out.println(menu8);

                            System.out.println("Coloque la Cedula del aspirante a contratar");
                            teclado.nextLine();
                            String cedulaEliminar = teclado.nextLine();

                            Dao<Aspirante, String> asEl = DaoManager.createDao(conexion, Aspirante.class);

                            Aspirante aspiranteAEliminar = asEl.queryForId(cedulaEliminar);

                            // Verificar que el objeto existe en la base de datos
                            if (aspiranteAEliminar == null) {
                                System.out.println("No existe un aspirante con esa cédula");
                            } else {
                                // Eliminar el objeto utilizando el método delete del DAO
                                asEl.delete(aspiranteAEliminar);
                                System.out.println("Aspirante contratado de la base de datos");
                            }
                            conexion.close();

                        }
                        break;
                    case 9:
                        boolean menu9 = false;
                        while (!menu9) {

                            System.out.println("¿Desea colocar otro minimo requerido de experiencia?: Si / No");
                            String valor = teclado.nextLine();
                            menu9 = valor.equalsIgnoreCase("no");
                            String respuesta = menu9 ? "si" : "no";
                            System.out.println(menu9);

                            System.out.println("Coloque la cantidad de años de experiencia como minimo que desea de los aspirantes: ");
                            teclado.nextLine();
                            int minEx = teclado.nextInt();

                            Dao<Aspirante, String> minExper = DaoManager.createDao(conexion, Aspirante.class);

                            for (Aspirante minimos : listaAspirantes) {
                                if (minimos.getExperiencia() < minEx) {

                                    Aspirante aspiranteMinimo = minExper.queryForId(minimos.getCedula());

                                    // Verificar que el objeto existe en la base de datos
                                    if (aspiranteMinimo == null) {
                                        System.out.println("No existe un aspirante con esa cédula");
                                    } else {
                                        // Eliminar el objeto utilizando el método delete del DAO
                                        minExper.delete(aspiranteMinimo);
                                        System.out.println("Aspirante eliminado de la base de datos");
                                    }
                                }
                            }
                            conexion.close();


                        }
                        break;
                    case 10:
                        boolean menu10 = false;
                        while (!menu10) {

                            System.out.println("¿Desea ver de nuevo el promedio de edades?: Si / No");
                            String valor = teclado.nextLine();
                            menu10 = valor.equalsIgnoreCase("no");
                            String respuesta = menu10 ? "si" : "no";
                            System.out.println(menu10);

                            Dao<Aspirante, String> Lista = DaoManager.createDao(conexion, Aspirante.class);

                            List<Aspirante> aspirantess = Lista.queryForAll();
                            int count = 0;
                            for (Aspirante aEdad : aspirantess) {
                                count += aEdad.getEdad();
                            }
                            int PromEdades = count / aspirantess.size();
                            System.out.println("El promedio de edades de los aspirantes es: " + PromEdades);
                            conexion.close();


                        }
                        break;
                    case 11:
                        System.out.println("Ah salido del programa");
                        salir = true;
                        break;
                    default:
                        System.out.println("Las opciones son entre el 1 al 11");
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes introducir un numero");
                teclado.next();
            }

        }


    }
}

