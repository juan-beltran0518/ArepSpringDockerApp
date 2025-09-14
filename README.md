# Spring Boot + Maven Scaffolding

Este proyecto es una base para aplicaciones Java usando Spring Boot y Maven, lista para contenerización con Docker.

## Estructura del proyecto

```
springdockerapp/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── co/edu/escuelaing/Main.java
│   │   └── resources/
│   └── test/
│       └── java/
├── target/ (ignorado por git)
```

## ¿Cómo ejecutar localmente?

1. Compila el proyecto:
   ```sh
   mvn clean package
   ```
2. Ejecuta la aplicación:
   ```sh
   mvn spring-boot:run
   ```

## ¿Cómo contribuir?

- Trabaja en ramas distintas a `main`.
- Haz Pull Requests claros y descriptivos.
- Sigue la estructura y convenciones del proyecto.

## Contenerización (Docker)

Próximamente: se agregará un `Dockerfile` para facilitar el despliegue en contenedores.

---

> Proyecto para el taller de modularización y virtualización (AREM, Escuela de Ingeniería).
