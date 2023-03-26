package Bolsaempleos;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class tablasBolsaAspirantes {
    public static void main(String[] args) throws Exception {
        // Ubicación del archivo de la base de datos
        String url = "jdbc:h2:file:./bolsaempleo";
        // Conexión con el driver de la base de datos
        ConnectionSource conexion =
                new JdbcConnectionSource(url);
        // Creación de la base de datos y la tabla
        TableUtils.createTable(conexion, Aspirante.class);
        System.out.println("Tabla creada satisfactoriamente!");
        // Cerrar la conexión
        conexion.close();
    }
}
