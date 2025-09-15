# Taller de de modularización con virtualización e Introducción a Docker

## Descripción

Este proyecto es una base para aplicaciones Java usando Maven, lista para contenerización con Docker. Implementa una aplicación web RESTful que demuestra conceptos fundamentales de modularización, virtualización con contenedores Docker, y despliegue en infraestructura de nube usando AWS EC2.

La aplicación utiliza Spring Boot 3.3.13 con Java 17, proporcionando endpoints REST para interacción web, configuración dinámica de puertos, y capacidades de containerización para despliegue escalable en diferentes entornos.

## Estructura del proyecto (Scaffolding)

```
springdockerapp/
├── pom.xml                          # Configuración Maven y dependencias
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── co/edu/escuelaing/
│   │   │       ├── Application.java           # Clase principal Spring Boot
│   │   │       └── controller/
│   │   │           └── HelloRestController.java  # Controlador REST
│   │   └── resources/               # Recursos de la aplicación
│   └── test/
│       └── java/                    
├── target/                          # Archivos compilados Maven
│   ├── classes/                     # Clases compiladas
│   └── dependency/                  # Dependencias copiadas
├── Dockerfile                       # Configuración del contenedor
├── docker-compose.yml              # Orquestación multi-servicio
├── .dockerignore                    # Exclusiones para Docker
└── README.md                        # Documentación del proyecto
```
# AREP Spring Docker App

## Resumen del Proyecto

Este proyecto implementa una aplicación web RESTful desarrollada con Spring Boot, containerizada con Docker y desplegada en AWS EC2. La aplicación demuestra conceptos de modularización, virtualización con contenedores, y despliegue en la nube.

### Características Principales
- Aplicación Spring Boot con endpoints REST
- Containerización con Docker
- Despliegue automatizado con Docker Compose
- Configuración de puerto dinámico
- Despliegue en AWS EC2
- Gestión de dependencias con Maven

## Arquitectura

### Arquitectura General
```
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│   Cliente Web   │    │   AWS EC2        │    │  Docker Hub     │
│   (Navegador)   │◄──►│   Instance       │◄──►│  Registry       │
└─────────────────┘    └──────────────────┘    └─────────────────┘
                              │
                              ▼
                       ┌──────────────────┐
                       │ Docker Container │
                       │ Spring Boot App  │
                       │ Puerto 6000      │
                       └──────────────────┘
```

### Componentes de la Aplicación

#### 1. **Spring Boot Application**
- Framework: Spring Boot 3.3.13
- Java Version: 17
- Puerto interno: 6000 (configurable vía variable `PORT`)
- Puerto por defecto: 4567

#### 2. **Docker Container**
- Base Image: `openjdk:17`
- Working Directory: `/usrapp/bin`
- Exposed Port: 6000
- Environment: `PORT=6000`

#### 3. **AWS Infrastructure**
- Instance Type: t3.micro
- AMI: Amazon Linux 2023
- Security Group: Puerto 42000 abierto
- Public IP: Dinámico

## Diseño de Clases

### Estructura del Proyecto
```
src/main/java/co/edu/escuelaing/
├── Application.java           # Clase principal de Spring Boot
└── controller/
    └── HelloRestController.java  # Controlador REST
```


### Clases Principales

#### `Application.java`
```java
@SpringBootApplication
public class Application {
    public static void main(String[] args)
    private static int getPort()
}
```
**Responsabilidades:**
- Punto de entrada de la aplicación
- Configuración dinámica del puerto
- Inicialización del contexto Spring

#### `HelloRestController.java`
```java
@RestController
public class HelloRestController {
    @GetMapping("/")
    @GetMapping("/hello")
    @GetMapping("/greeting")
    @GetMapping("/health")
}
```
**Responsabilidades:**
- Manejo de peticiones HTTP REST
- Endpoints de la aplicación
- Respuestas en formato texto/JSON

## Endpoints Disponibles

| Endpoint | Método | Descripción | Ejemplo |
|----------|---------|-------------|---------|
| `/` | GET | Página principal | `Welcome to Spring Docker App!` |
| `/hello` | GET | Saludo básico | `Hello, Docker!` |
| `/hello?name=Juan` | GET | Saludo personalizado | `Hello, Juan!` |
| `/greeting` | GET | Endpoint de saludo | `Hello, World!` |
| `/greeting?name=Juan` | GET | Saludo con parámetro | `Hello, Juan!` |
| `/health` | GET | Health check | `OK` |

## Generación y Despliegue

### Prerrequisitos
- Java 17 o superior
- Maven 3.6+
- Docker Desktop
- AWS CLI (opcional)
- Cuenta AWS con EC2 habilitado

### 1. Compilación Local

```bash
# Clonar el repositorio
git clone https://github.com/juan-beltran0518/ArepSpringDockerApp.git
cd ArepSpringDockerApp

# Compilar y generar dependencias
mvn clean compile dependency:copy-dependencies

# Ejecutar localmente (opcional)
java -cp "target/classes:target/dependency/*" co.edu.escuelaing.Application
```

### 2. Construcción de Imagen Docker

```bash
# Construir imagen
docker build -t juanbeltra/arepfirstprkwebapprepo .

# Verificar imagen creada
docker images
```

### 3. Ejecución Local con Docker

```bash
# Ejecutar contenedor individual
docker run -d -p 4567:6000 --name springapp juanbeltra/arepfirstprkwebapprepo

# O usar Docker Compose
docker-compose up -d
```

### 4. Despliegue en AWS EC2

#### Paso 1: Crear Instancia EC2
```bash
# Configuración recomendada:
# - AMI: Amazon Linux 2023
# - Instance Type: t3.micro
# - Key Pair: arep.pem
# - Security Group: Puerto 42000 abierto
```

#### Paso 2: Conectar y Configurar
```bash
# Conectar por SSH
ssh -i arep.pem ec2-user@[IP-PUBLICA]

# Instalar Docker
sudo yum update -y
sudo yum install docker -y
sudo service docker start
sudo usermod -a -G docker ec2-user

# Salir y reconectar
exit
ssh -i arep.pem ec2-user@[IP-PUBLICA]
```

#### Paso 3: Desplegar Aplicación
```bash
# Clonar y construir
git clone https://github.com/juan-beltran0518/ArepSpringDockerApp.git
cd ArepSpringDockerApp
sudo yum install java-17-amazon-corretto-devel maven -y
mvn clean compile dependency:copy-dependencies

# Construir y ejecutar
docker build -t juanbeltra/arepfirstprkwebapprepo .
docker run -d -p 42000:6000 --name firstdockerimageaws juanbeltra/arepfirstprkwebapprepo
```

## Configuración de Docker

### Dockerfile
```dockerfile
FROM openjdk:17
WORKDIR /usrapp/bin
ENV PORT=6000
COPY target/classes /usrapp/bin/classes
COPY target/dependency /usrapp/bin/dependency
CMD ["java","-cp","./classes:./dependency/*","co.edu.escuelaing.Application"]
```

### docker-compose.yml
```yaml
services:
  web:
    build:
      context: .
      dockerfile: Dockerfile
    container_name: web
    ports:
      - "8087:6000"
  db:
    image: mongo:5.0
    container_name: db
    volumes:
      - mongodb:/data/db
      - mongodb_config:/data/configdb
    ports:
      - 27017:27017
    command: mongod

volumes:
  mongodb:
  mongodb_config:
```

## Resultados del Despliegue

### 1. Aplicación Local
[INSERTAR_IMAGEN_EJECUCION_LOCAL]

**URL Local:** `http://localhost:4567/hello`

### 2. Docker Local
[INSERTAR_IMAGEN_DOCKER_LOCAL]

**Comandos utilizados:**
```bash
docker run -d -p 34000:6000 --name firstdockercontainer dockersparkprimer
docker run -d -p 34001:6000 --name firstdockercontainer2 dockersparkprimer
docker run -d -p 34002:6000 --name firstdockercontainer3 dockersparkprimer
```

### 3. Docker Compose
[INSERTAR_IMAGEN_DOCKER_COMPOSE]

**Servicios levantados:**
- Web Service (Puerto 8087)
- MongoDB Service (Puerto 27017)

### 4. Despliegue AWS EC2
[INSERTAR_IMAGEN_AWS_CONSOLE]

**Configuración de la instancia:**

[INSERTAR_IMAGEN_EC2_DETAILS]

### 5. Security Groups
[INSERTAR_IMAGEN_SECURITY_GROUPS]

**Reglas configuradas:**
- Inbound: Puerto 22 (SSH), Puerto 42000 (App)
- Outbound: Puerto 80, 443, 42000

### 6. Aplicación Funcionando en AWS
[INSERTAR_IMAGEN_APP_AWS]

**URL Pública:** `http://ec2-54-234-78-39.compute-1.amazonaws.com:42000/hello`

### 7. Pruebas de Endpoints
[INSERTAR_IMAGEN_ENDPOINTS_TESTING]

**Endpoints probados:**
- `/` - Página principal
- `/hello` - Saludo básico
- `/greeting?name=Usuario` - Saludo personalizado
- `/health` - Health check

## Comandos de Administración

### Docker
```bash
# Ver contenedores activos
docker ps

# Ver logs de la aplicación
docker logs firstdockerimageaws

# Parar contenedor
docker stop firstdockerimageaws

# Reiniciar contenedor
docker restart firstdockerimageaws
```

### AWS EC2
```bash
# Parar instancia
aws ec2 stop-instances --instance-ids i-0040110ac21f818c0

# Iniciar instancia
aws ec2 start-instances --instance-ids i-0040110ac21f818c0

# Ver estado
aws ec2 describe-instances --instance-ids i-0040110ac21f818c0
```

## Tecnologías Utilizadas

- **Backend:** Spring Boot 3.3.13
- **Language:** Java 17
- **Build Tool:** Maven 3.9+
- **Containerization:** Docker
- **Orchestration:** Docker Compose
- **Cloud Platform:** AWS EC2
- **Base Image:** OpenJDK 17
- **Database:** MongoDB 5.0 (para Docker Compose)

## Autor

**Juan Beltran**
- GitHub: [@juan-beltran0518](https://github.com/juan-beltran0518)
- Repository: [ArepSpringDockerApp](https://github.com/juan-beltran0518/ArepSpringDockerApp)


