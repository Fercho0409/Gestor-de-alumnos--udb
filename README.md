
# Gestor de Alumnos (UDB Virtual) – Java (Compatibilidad JDK 8+)

Aplicación de consola para gestionar alumnos con **persistencia** y **listado ordenado por carnet** usando `TreeMap`.

## Requisitos
- **JDK 8 o superior** (recomendado JDK 17)
- (Opcional) Maven 3.8+
- IntelliJ IDEA

## Ejecutar en IntelliJ IDEA
1. **File → Open** y selecciona la carpeta `udbvirtual-gestor-alumnos-fix`.
2. Asegúrate de que **Project SDK** apunte a tu JDK (8, 11 o 17).  
   *(File → Project Structure → Project → Project SDK)*
3. Abre `src/main/java/udbvirtual/Main.java` y ejecuta **Run 'Main.main()'**.

## Ejecutar con Maven (opcional)
```bash
mvn -q -DskipTests exec:java
```

## Cambios de compatibilidad
- `new Scanner(System.in, "UTF-8")` (antes usaba `StandardCharsets.UTF_8`).
- Reemplazado `Files.writeString(...)` por `BufferedWriter` para funcionar en JDK 8.
