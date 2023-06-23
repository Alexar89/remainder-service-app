# Pasos para ejecutar el proyecto "remainder-service-app" en Eclipse IDE o en Spring Tools STS

## 1. Importar el proyecto Maven

- Abre Eclipse IDE o Spring STS IDE.
- Ve al menú "File" y selecciona "Import".
- En la ventana de importación, expande la carpeta "Maven" y elige "Existing Maven Projects".
- Haz clic en "Next".
- En el campo "Root Directory", selecciona la ubicación donde has clonado el proyecto "remainder-service-app" desde el repositorio de GitHub.
- Asegúrate de que el archivo "pom.xml" esté seleccionado y haz clic en "Finish".
- Eclipse importará automáticamente el proyecto Maven y descargará todas las dependencias necesarias.

## 2. Ejecutar como aplicación de Spring Boot

- En la vista "Project Explorer" de Eclipse, busca el proyecto "remainder-service-app".
- Haz clic derecho en el proyecto y ve a "Run As" -> "Spring Boot App".
- Eclipse ejecutará la aplicación de Spring Boot y mostrará los registros en la consola.
- Una vez que la aplicación se haya iniciado correctamente, estará lista para recibir peticiones.

## 3. Uso del API RemainderAPI_v1

El API "RemainderAPI_v1" es la interfaz para interactuar con la aplicación "remainder-service-app". A continuación, se muestra cómo puedes utilizarlo:

- Abre una herramienta de prueba de API, como Postman e importa el Collection JSON que esta en el proyecto: remainder-service-app/RemainderAPI_v1.postman_collection.json.
- Realiza una solicitud HTTP a la URL base del API.

** Para realizar una solicitud primero debes usar el Endpoint de Loging para obtener un Token.
	 `http://localhost:8089/login/ingresa`.
** Copia el token en el header Authorization como se muestra en esta imagen:

Luego haz las peticions al GET Remainder y POST Remainder usando los parametros encesarios, aqui un ejemplo del GET y POST:


Recuerda que mientras la aplicación de Spring Boot esté en ejecución, podrás realizar solicitudes al API y probar su funcionalidad.

¡Listo! Ahora puedes importar el proyecto "remainder-service-app" en Eclipse, ejecutarlo como una aplicación de Spring Boot y utilizar el API "RemainderAPI_v1".


