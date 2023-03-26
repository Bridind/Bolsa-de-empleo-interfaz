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

            String opcionStr = JOptionPane.showInputDialog(null,
                    "Elija la opción que desee:\n" +
                            "1. Agregar nuevo aspirante \n" +
                            "2. Mostrar cedulas de los aspirantes \n" +
                            "3. Mostrar informacion de aspirante apartir de su cedula \n" +
                            "4. Buscar aspirante por nombre \n" +
                            "5. Ordenar aspirantes por \n" +
                            "6. Aspirante con mayor experiencia \n" +
                            "7. Aspirante mas joven \n" +
                            "8. Contratar aspirante \n" +
                            "9. Experiencia que necesita en los aspirantes \n" +
                            "10. Promedio de edad de los aspirantes \n" +
                            "11. Salir",
                    "Menú de opciones",
                    JOptionPane.QUESTION_MESSAGE);

            opcion = Integer.parseInt(opcionStr);

            try {

                switch (opcion) {
                    case 1:

                        boolean menu = false;

                        while (!menu) {

                            JOptionPane.showMessageDialog(null, "A continuación agrega la información del nuevo aspirante", "Informacion", JOptionPane.INFORMATION_MESSAGE);
                            String cedula = JOptionPane.showInputDialog("Coloque el numero de cedula").toLowerCase();
                            String nombre = JOptionPane.showInputDialog("Coloque el nombre del aspirante").toLowerCase();
                            String profesion = JOptionPane.showInputDialog("cual es su profesion").toLowerCase();
                            int edad = Integer.parseInt(JOptionPane.showInputDialog("cuantos años tiene"));
                            int experiencia = Integer.parseInt(JOptionPane.showInputDialog("cuantos años de experiencia tiene"));
                            int telefono = Integer.parseInt(JOptionPane.showInputDialog("cual es su numero de telefono"));
                            Aspirante aspirantes = new Aspirante(cedula, nombre, edad, experiencia, profesion, telefono);
                            listaAspirantes.create(aspirantes);
                            conexion.close();

                            int confirmar = JOptionPane.showConfirmDialog(null, "¿Desea agregar otro aspirante?");
                            if (JOptionPane.OK_OPTION == confirmar) {
                                menu = false;
                            } else {
                                menu = true;
                            }
                        }
                        break;
                    case 2:

                        String Info = "";




                            for (Aspirante C : listaAspirantes) {

                                Info += "Cedula: " + C.getCedula() + " Nombre: " + C.getNombre() + " Edad: " + C.getEdad() + "\n";

                            }

                            JOptionPane.showMessageDialog(null, Info, "Cedulas de aspirantes", JOptionPane.PLAIN_MESSAGE);





                        break;
                    case 3:

                        boolean menu3 = false;
                        while (!menu3) {

                            String cedulaBusquedad = JOptionPane.showInputDialog("Ingrese el numero de cedula del Aspirante a buscar");

                            Aspirante aspirante = listaAspirantes.queryForId(cedulaBusquedad);
                            if (aspirante == null) {
                                JOptionPane.showMessageDialog(null, "No existe un aspirante con esa cedula : " + cedulaBusquedad, "No encontrado", JOptionPane.WARNING_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, " Cedula: " + aspirante.getCedula() + "\n Nombre: " + aspirante.getNombre() + "\n Profesion: " + aspirante.getProfesion() + "\n Edad: " + aspirante.getEdad() + "\n Experiencia: " + aspirante.getExperiencia() + "\n Telefono: " + aspirante.getTelefono(), "informacion de la cedula buscada", JOptionPane.PLAIN_MESSAGE);
                            }
                            conexion.close();
                            int confirmar = JOptionPane.showConfirmDialog(null, "¿Desea buscar otra cedula?");
                            if (JOptionPane.OK_OPTION == confirmar) {
                                menu3 = false;
                            } else {
                                menu3 = true;
                            }
                        }
                        break;
                    case 4:


                        boolean menu4 = false;
                        while (!menu4) {

                            String ordEdadIgual = "";

                            String busqNom = JOptionPane.showInputDialog("Ingrese la profesion  que desea de los aspirantes: ").toLowerCase();

                            QueryBuilder<Aspirante, String> queryBuilder = listaAspirantes.queryBuilder();

                            queryBuilder.where().eq("nombre", busqNom);

                            queryBuilder.orderByRaw("LOWER(nombre) ASC");

                            List<Aspirante> order = queryBuilder.query();

                            for (Aspirante edM : order) {
                                ordEdadIgual += "Cedula: " + edM.getCedula() + " Nombre: " + edM.getNombre() + " Edad: " + edM.getEdad() + " Experiencia: " + edM.getExperiencia() + " Profesion: " + edM.getProfesion() + " Telefono: " + edM.getTelefono() + "\n";
                            }
                            JOptionPane.showMessageDialog(null, ordEdadIgual, "Ordenamiento realizado", JOptionPane.PLAIN_MESSAGE);


                            int confirmar = JOptionPane.showConfirmDialog(null, "¿Desea de nuevo buscar algun otro aspirante?");
                            if (JOptionPane.OK_OPTION == confirmar) {
                                menu4 = false;
                            } else {
                                menu4 = true;
                            }
                        }

                        break;

                    case 5:

                        boolean datosordenar = false;
                        boolean menu5 = false;
                        while (!menu5) {

                            boolean Decendente = true;
                            int opcionord;

                            int confirmar = JOptionPane.showConfirmDialog(null, "¿Desea que los datos se ordenen Decendente?");
                            if (JOptionPane.OK_OPTION == confirmar) {
                                Decendente = false;
                            } else {
                                Decendente = true;
                            }

                            while (!datosordenar) {


                                String datosord = JOptionPane.showInputDialog(null,
                                        "Elija con que dato desea ordenar los aspirantes:\n" +
                                                "1. Edades \n" +
                                                "2. Experiencia \n" +
                                                "3. Profesion \n" +
                                                "4. Salir",
                                        "Ordenar listas",
                                        JOptionPane.QUESTION_MESSAGE);
                                opcionord = Integer.parseInt(datosord);


                                switch (opcionord) {
                                    case 1:
                                        boolean ord1 = false;
                                        int ordenamiento1;
                                        while (!ord1) {
                                            String ordenamientoDatos1 = JOptionPane.showInputDialog(null,
                                                    "Elija con que criterio desea ordenar los aspirantes:\n" +
                                                            "1. Igual a \n" +
                                                            "2. Distinto a \n" +
                                                            "3. Mayor que \n" +
                                                            "4. Mayor o igual que \n" +
                                                            "5. Menor que  \n" +
                                                            "6. Menor o igual que \n" +
                                                            "7. Entre dos valores \n" +
                                                            "8. Salir",
                                                    "Ordenar listas",
                                                    JOptionPane.QUESTION_MESSAGE);
                                            ordenamiento1 = Integer.parseInt(ordenamientoDatos1);
                                            switch (ordenamiento1) {
                                                case 1:
                                                    String ordEdadIgual = "";

                                                    String busqAñosI = JOptionPane.showInputDialog("Ingrese la edad  que desea de los aspirantes: ");

                                                    QueryBuilder<Aspirante, String> queryBuilder = listaAspirantes.queryBuilder();

                                                    queryBuilder.where().eq("edad", busqAñosI);

                                                    queryBuilder.orderBy("edad", Decendente);

                                                    List<Aspirante> order = queryBuilder.query();

                                                    for (Aspirante edM : order) {
                                                        ordEdadIgual += "Cedula: " + edM.getCedula() + " Nombre: " + edM.getNombre() + " Edad: " + edM.getEdad() + " Experiencia: " + edM.getExperiencia() + " Profesion: " + edM.getProfesion() + " Telefono: " + edM.getTelefono() + "\n";
                                                    }
                                                    JOptionPane.showMessageDialog(null, ordEdadIgual, "Ordenamiento realizado", JOptionPane.PLAIN_MESSAGE);


                                                    break;
                                                case 2:
                                                    String ordEdadDistinto = "";

                                                    String busqAñosD = JOptionPane.showInputDialog("Ingrese la edad que no quiere que tengan los aspirantes: ");

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

                                                    String busqAñosM = JOptionPane.showInputDialog("Ingrese la edad mayor que desea de los aspirantes: ");

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

                                                    String busqAñosMoI = JOptionPane.showInputDialog("Ingrese la edad mayor o igual que desea de los aspirantes: ");

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

                                                    String busqAñosMen = JOptionPane.showInputDialog("Ingrese la edad menor que desea de los aspirantes: ");

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

                                                    String busqAñosMenoI = JOptionPane.showInputDialog("Ingrese la edad menor o igual que desea de los aspirantes: ");

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

                                                    String busqAñosEntMin = JOptionPane.showInputDialog("Ingrese la edad mínima de los aspirantes: ");
                                                    String busqAñosEntMax = JOptionPane.showInputDialog("Ingrese la edad máxima de los aspirantes: ");

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
                                                    JOptionPane.showMessageDialog(null, "Ah vuelto al menu anterior", "Regreso al menu", JOptionPane.ERROR_MESSAGE);
                                                    break;
                                                default:
                                                    JOptionPane.showMessageDialog(null, "Las opciones son entre el 1 al 8", "Error", JOptionPane.ERROR_MESSAGE);
                                                    break;
                                            }
                                        }


                                        break;
                                    case 2:
                                        boolean ord2 = false;
                                        int ordenamiento2;
                                        while (!ord2) {
                                            String ordenamientoDatos2 = JOptionPane.showInputDialog(null,
                                                    "Elija con que criterio desea ordenar los aspirantes:\n" +
                                                            "1. Igual a \n" +
                                                            "2. Distinto a \n" +
                                                            "3. Mayor que \n" +
                                                            "4. Mayor o igual que \n" +
                                                            "5. Menor que  \n" +
                                                            "6. Menor o igual que \n" +
                                                            "7. Entre dos valores \n" +
                                                            "8. Salir",
                                                    "Ordenar listas",
                                                    JOptionPane.QUESTION_MESSAGE);
                                            ordenamiento2 = Integer.parseInt(ordenamientoDatos2);
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
                                                    JOptionPane.showMessageDialog(null, "Ah vuelto al menu anterior", "Regreso al menu", JOptionPane.ERROR_MESSAGE);
                                                    break;
                                                default:
                                                    JOptionPane.showMessageDialog(null, "Las opciones son entre el 1 al 8", "Error", JOptionPane.ERROR_MESSAGE);
                                                    break;
                                            }
                                        }
                                        break;
                                    case 3:
                                        boolean ord3 = false;
                                        int ordenamiento3;
                                        while (!ord3) {
                                            String ordenamientoDatos3 = JOptionPane.showInputDialog(null,
                                                    "Elija con que criterio desea ordenar los aspirantes:\n" +
                                                            "1. Igual a \n" +
                                                            "2. Distinto a \n" +
                                                            "3. Salir",
                                                    "Ordenar listas",
                                                    JOptionPane.QUESTION_MESSAGE);
                                            ordenamiento3 = Integer.parseInt(ordenamientoDatos3);
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
                                                    JOptionPane.showMessageDialog(null, "Ah vuelto al menu anterior", "Regreso al menu", JOptionPane.ERROR_MESSAGE);
                                                    break;
                                                default:
                                                    JOptionPane.showMessageDialog(null, "Las opciones son entre el 1 al 3", "Error", JOptionPane.ERROR_MESSAGE);
                                                    break;
                                            }
                                        }
                                        break;
                                    case 4:
                                        datosordenar = true;
                                        JOptionPane.showMessageDialog(null, "Ah vuelto al menu anterior", "Regreso al menu", JOptionPane.ERROR_MESSAGE);
                                        break;
                                    default:
                                        JOptionPane.showMessageDialog(null, "Las opciones son entre el 1 al 4", "Error", JOptionPane.ERROR_MESSAGE);
                                        break;
                                }
                            }
                            break;
                        }
                        break;
                    case 6:

                            int mayor = 0;

                            for (Aspirante ex : listaAspirantes) {
                                if (mayor < ex.getExperiencia()) {
                                    mayor = ex.getExperiencia();
                                }
                            }

                            JOptionPane.showMessageDialog(null, "Los datos del aspirante con mayor experiencia es", "Aspirante con mayor experiencia", JOptionPane.INFORMATION_MESSAGE);

                            for (Aspirante datos : listaAspirantes) {
                                if (datos.getExperiencia() == mayor) {
                                    JOptionPane.showMessageDialog(null, " Cedula: " + datos.getCedula() + "\n Nombre: " + datos.getNombre() + "\n Profesion: " + datos.getProfesion() + "\n Edad: " + datos.getEdad() + "\n Experiencia: " + datos.getExperiencia() + "\n Telefono: " + datos.getTelefono(), "informacion del aspirante con mayor experiencia", JOptionPane.PLAIN_MESSAGE);
                                    break;
                                }
                            }


                        break;
                    case 7:


                            int joven = 999;

                            for (Aspirante jov : listaAspirantes) {
                                if (joven > jov.getEdad()) {
                                    joven = jov.getEdad();
                                }
                            }

                            JOptionPane.showMessageDialog(null, "Los datos del aspirante mas joven es", "Aspirante mas joven", JOptionPane.INFORMATION_MESSAGE);

                            for (Aspirante datosjoven : listaAspirantes) {
                                if (datosjoven.getEdad() == joven) {
                                    JOptionPane.showMessageDialog(null, " Cedula: " + datosjoven.getCedula() + "\n Nombre: " + datosjoven.getNombre() + "\n Profesion: " + datosjoven.getProfesion() + "\n Edad: " + datosjoven.getEdad() + "\n Experiencia: " + datosjoven.getExperiencia() + "\n Telefono: " + datosjoven.getTelefono(), "informacion del aspirante mas joven", JOptionPane.PLAIN_MESSAGE);
                                    break;
                                }
                            }


                        break;
                    case 8:
                        boolean menu8 = false;
                        while (!menu8) {

                            String cedulaEliminar = JOptionPane.showInputDialog("Coloque la Cedula del aspirante a contratar:");

                            Dao<Aspirante, String> asEl = DaoManager.createDao(conexion, Aspirante.class);

                            Aspirante aspiranteAEliminar = asEl.queryForId(cedulaEliminar);

                            // Verificar que el objeto existe en la base de datos
                            if (aspiranteAEliminar == null) {
                                JOptionPane.showMessageDialog(null, "No existe un aspirante con esta cédula: : " + cedulaEliminar, "No encontrado", JOptionPane.WARNING_MESSAGE);
                            } else {
                                // Eliminar el objeto utilizando el método delete del DAO
                                asEl.delete(aspiranteAEliminar);
                                JOptionPane.showMessageDialog(null, "Aspirante contratado y retirado de la base de datos");
                            }
                            conexion.close();
                            int confirmar = JOptionPane.showConfirmDialog(null, "¿Desea contratar otro aspirante?");
                            if (JOptionPane.OK_OPTION == confirmar) {
                                menu8 = false;
                            } else {
                                menu8 = true;
                            }
                        }
                        break;
                    case 9:
                        boolean menu9 = false;
                        while (!menu9) {

                            int minEx = Integer.parseInt(JOptionPane.showInputDialog("Coloque la cantidad de años de experiencia como minimo que desea de los aspirantes"));

                            Dao<Aspirante, String> minExper = DaoManager.createDao(conexion, Aspirante.class);

                            for (Aspirante minimos : listaAspirantes) {
                                if (minimos.getExperiencia() < minEx) {

                                    Aspirante aspiranteMinimo = minExper.queryForId(minimos.getCedula());

                                    // Verificar que el objeto existe en la base de datos
                                    if (aspiranteMinimo == null) {
                                        JOptionPane.showMessageDialog(null, "No existen aspirantes con el minimo de experiencia que indico" + minEx, "No hay aspirantes con esa experiencia", JOptionPane.WARNING_MESSAGE);
                                    } else {
                                        // Eliminar el objeto utilizando el método delete del DAO
                                        minExper.delete(aspiranteMinimo);
                                        JOptionPane.showMessageDialog(null, "Guardado aspirantes que si cumplen con el minimo de experiencia");
                                        break;
                                    }
                                }
                            }
                            conexion.close();
                            int confirmar = JOptionPane.showConfirmDialog(null, "¿Desea colocar otro minimo requerido de experiencia?");
                            if (JOptionPane.OK_OPTION == confirmar) {
                                menu9 = false;
                            } else {
                                menu9 = true;
                            }
                        }
                        break;
                    case 10:

                            Dao<Aspirante, String> Lista = DaoManager.createDao(conexion, Aspirante.class);

                            List<Aspirante> aspirantess = Lista.queryForAll();
                            int count = 0;
                            for (Aspirante aEdad : aspirantess) {
                                count += aEdad.getEdad();
                            }
                            int PromEdades = count / aspirantess.size();
                            JOptionPane.showMessageDialog(null, "El promedio de edades de los aspirantes es " + PromEdades, "Promedio de edad de los aspirantes", JOptionPane.WARNING_MESSAGE);
                            conexion.close();


                        break;
                    case 11:
                        salir = true;
                        JOptionPane.showMessageDialog(null, "Ah salido del programa", "Salida", JOptionPane.ERROR_MESSAGE);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Las opciones son entre el 1 al 11", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (InputMismatchException e) {
                JOptionPane.showMessageDialog(null, "Debes introducir un numero", "Solo numeros", JOptionPane.INFORMATION_MESSAGE);
                teclado.next();
            }

        }


    }
}

