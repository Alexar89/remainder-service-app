
# Descripción de Remainder Service App

Esta aplicación se ha construido con Java Versión 11 y Java Spring Framework 2.7.x. Se trata de un servicio REST que recibe peticiones GET y POST en una arquitectura de capas, sin embargo puede evolucionar para ser incluido como un microservicio, se usa esta arquitectura por simplicidad del ejercicio. Se tiene un Endpoint de /login/ingresa que se usa para autenticar al usuario antes de usar los demás endpoints del API mediante un Token JWT y un filtro JWT que mapea todas las peticiones de los endpoints para validar que se tiene un token autorizado. El usuario y clave puede ser cualquiera ya que para efectos de simplificar la ejcución local se ha definido que se puede recibir cualquier usuario y clave sin necesidad de conectarse a una base de datos.

Los endpoints reciben tres valores: x, y , n y ejecutan una logica interna para retornar un valor k entero tal que k esta en el rango de [0,n] y k Modulo x = y.

La complejidad del algorithmo se estima en O(1) ya que se usa el enfoque de Naive(https://cs.stackexchange.com/questions/33914/what-is-a-naive-method) donde se considera los siguiente:

/*  Posibles valores de K * /
Calculamos K1 como K = N – N % X + Y
Calculamos K2 como K = N – N%X + Y – X.
Si K1 esta dentro del rango [0, N], retorna K1.
En otro caso, si K2 Esta en el rango de [0, N], retorna K2.
<table>
<td>
**Los casos de entrada son:
</td>
<td>
7 5 12345,
5 0 4,
10 5 15,
17 8 54321,
499999993 9 1000000000,
10 5 187,
2 0 999999999
</td>

<td>
**Su salida:
</td>
<td>
12339,
0,
15,
54306,
999999995,
185,
999999998,
</td>
</table>
Un mayor detalle del problema se encuentar en: https://codeforces.com/problemset/problem/1374/A

# Pasos para ejecutar el proyecto "remainder-service-app" en Eclipse IDE o en Spring Tools STS

Clona o descarga el proyecto desde github. Con git clone puedes copiarlo a una carpeta local en tu equipo.

** Comando para clonar desde git: git clone https://github.com/Alexar89/remainder-service-app

Deberia generarse esta estructura de archivos:

![imagen](https://github.com/Alexar89/remainder-service-app/assets/11586423/a951d26b-a17b-4ec7-8e70-e91dab9502ff)

## 1. Importar el proyecto Maven

- Abre Eclipse IDE o Spring STS IDE.
- Ve al menú "File" y selecciona "Import".
- En la ventana de importación, expande la carpeta "Maven" y elige "Existing Maven Projects".
- Haz clic en "Next".
- En el campo "Root Directory", selecciona la ubicación donde has clonado el proyecto "remainder-service-app" desde el repositorio de GitHub.
- Asegúrate de que el archivo "pom.xml" esté seleccionado y haz clic en "Finish".
- Eclipse importará automáticamente el proyecto Maven y descargará todas las dependencias necesarias.

El proyecto deberia verse asi:

![imagen](https://github.com/Alexar89/remainder-service-app/assets/11586423/9e9860aa-42d5-4cb0-8773-f1fca53eb7b6)


## 2. Ejecutar como aplicación de Spring Boot

- En la vista "Project Explorer" de Eclipse, busca el proyecto "remainder-service-app".
- Haz clic derecho en el proyecto sobre la clase principal "RemainderServiceApplication.java" y ve a "Run As" -> "Spring Boot App".
  
  ![imagen](https://github.com/Alexar89/remainder-service-app/assets/11586423/ac4d0ed4-834a-4764-9171-d7c703eecaaf)

- Eclipse ejecutará la aplicación de Spring Boot y mostrará los registros en la consola.
  
  ![imagen](https://github.com/Alexar89/remainder-service-app/assets/11586423/e756aeaf-6cce-4a01-9099-fe0089479b0c)

- Una vez que la aplicación se haya iniciado correctamente, estará lista para recibir peticiones.

## 3. Uso del API RemainderAPI_v1

El API "RemainderAPI_v1" es la interfaz para interactuar con la aplicación "remainder-service-app". A continuación, se muestra cómo puedes utilizarlo:

Abre una herramienta de prueba de API, como Postman e importa el Collection JSON que esta en el proyecto: remainder-service-app/RemainderAPI_v1.postman_collection.json.

![imagen](https://github.com/Alexar89/remainder-service-app/assets/11586423/dc08c875-6591-4b6e-b826-8123ba4a8324)

Despues del import debes ver el collection como sigue:

![imagen](https://github.com/Alexar89/remainder-service-app/assets/11586423/7d9af20d-f142-414b-a014-23e853ab4ca9)


** Realiza una solicitud HTTP a la URL base del API.

Para realizar una solicitud primero debes usar el Endpoint de Login para obtener un Token.
	 `http://localhost:8089/login/ingresa`.
  
![imagen](https://github.com/Alexar89/remainder-service-app/assets/11586423/214a05ff-0b4c-4b65-bcb3-d350edec7624)


Ahora Copia el token en el header Authorization como se muestra en esta imagen:


![imagen](https://github.com/Alexar89/remainder-service-app/assets/11586423/944d17e2-6238-4b7c-9441-297e5e4a7942)

![imagen](https://github.com/Alexar89/remainder-service-app/assets/11586423/a769e46e-8121-4191-ac7c-9e7b4912bf5f)


Luego haz las peticions al GET Remainder y POST Remainder, aqui un ejemplo del GET y POST:

**Ejemplo GET:

![imagen](https://github.com/Alexar89/remainder-service-app/assets/11586423/949a6006-9cef-4615-a78e-8d230c919916)


**Ejemplo POST:

![imagen](https://github.com/Alexar89/remainder-service-app/assets/11586423/ddf8bc81-2449-4d7c-b942-b3a7015630a8)


Para Ejecutar los Test puedes hacerlo como se muestra a continuación:

 Paso 1:
 
 ![imagen](https://github.com/Alexar89/remainder-service-app/assets/11586423/6441c527-018a-46b6-99a5-eb307d9a22ea)

Paso 2: 

Observar la ejecución correcta de los test:

![imagen](https://github.com/Alexar89/remainder-service-app/assets/11586423/2576a4e7-5a3f-4ad0-8b43-694fe6a1f62c)


Recuerda que mientras la aplicación de Spring Boot esté en ejecución, podrás realizar solicitudes al API y probar su funcionalidad.

¡Listo! Ahora puedes importar el proyecto "remainder-service-app" en Eclipse, ejecutarlo como una aplicación de Spring Boot y utilizar el API "RemainderAPI_v1".


