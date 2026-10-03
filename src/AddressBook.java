import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class AdressBook {
    private final Map<String,String> contactos = new HashMap<>();
    private final Path archivo = Paths.get("contactos.csv");

    public void load() throws IOException{
        if(!Files.exists(archivo)){
            return;
        }

        contactos.clear();
        for (String linea: Files.readAllLines(
                acrhivo,StandardCharsets.UTF_8)){
            String[] datos = linea.split(",",2);

            if (datos.length == 2) {
                String numero = datos[0].trim();
                String nombre = datos[1].trim();

                if (!numero.isEmpty() && !nombre.isEmpty()) {
                    contactos.put(numero,nombre);
                }
            }
        }
    }

    public void save() throws IOException {
        StringBuilder contenido = new StringBuilder();

        for (Map.Entry<String, String> contacto : contactos.entrySet()) {
            contenido.append(contacto.getKey())
                    .append(",")
                    .append(contacto.getValue())
                    .append(System.lineSeparator());
        }

        Files.write(
                archivo,
                contenido.toString().getBytes(StandardCharsets.UTF_8)
        );
    }

    // Mostrar todos los contactos.
    public void list() {
        System.out.println("\nContactos:");

        if (contactos.isEmpty()) {
            System.out.println("La agenda está vacía.");
            return;
        }

        for (Map.Entry<String, String> contacto : contactos.entrySet()) {
            System.out.println(
                    contacto.getKey() + " : " + contacto.getValue()
            );
        }
    }

    // Crear un contacto y guardar el cambio.
    public void create(String numero, String nombre) throws IOException {
        numero = numero.trim();
        nombre = nombre.trim();

        if (numero.isEmpty() || nombre.isEmpty()) {
            System.out.println("El número y el nombre son obligatorios.");
            return;
        }

        if (!numero.matches("[0-9]+")) {
            System.out.println("El número debe contener solamente dígitos.");
            return;
        }

        if (nombre.contains(",")) {
            System.out.println("Escribe el nombre sin comas.");
            return;
        }

        if (contactos.containsKey(numero)) {
            System.out.println("Ese número ya está registrado.");
            return;
        }

        contactos.put(numero, nombre);
        save();
        System.out.println("Contacto creado y guardado correctamente.");
    }

    // Eliminar un contacto mediante su número.
    public void delete(String numero) throws IOException {
        numero = numero.trim();

        if (!contactos.containsKey(numero)) {
            System.out.println("No existe un contacto con ese número.");
            return;
        }

        contactos.remove(numero);
        save();
        System.out.println("Contacto eliminado correctamente.");
    }
}
