# Api-Spring-Mastering

#------------ ORDER API ------------------

#API REST creada con Spring Boot implementando el patrón MVC 

Funcionalidades – Un usuario puede realizar una orden de múltiples productos, quedando registrada la fecha de la orden, éstos pertenecerán al mismo, 
el usuario tendrá un detalle de los productos que ordenó y el precio total que debe abonar.
Por otra parte solo los usuarios ADMIN podrán crear – eliminar – actualizar la lista de los productos,
mientras que los clientes solo podrán obtener una lista de detalles de los mismos y ordenarlos.
Los usuarios deben registrarse e iniciar sesión para poder realizar cualquier tipo de petición a la API,
en casos de error, recibirán un mensaje muy claro del motivo por el que se produjo. 

--Qué utilicé para crear esta API? 

SPRING DATA con MySQL / DevTool /Lombok /CRUD de servicios REST / Validador /
DTO /Convertidores Entidad - DTO / Paginación / Excepciones personalizadas, específicas y globales/
Estandarización de respuesta a los clientes / JPA / Hibernate / Relaciones bidireccionales/
Spring SECURITY – JWT , Login y Signup/ Validación de TOKEN / Encriptación  contronseña / Swagger UI / POSTMAN

![orderApiCap1](https://user-images.githubusercontent.com/86859904/160519473-3875059a-bf0d-4161-aa7c-a96379c116f0.PNG)
![orderApiCap2](https://user-images.githubusercontent.com/86859904/160519493-7f60a8db-546a-40e8-8867-856c3d1b7377.PNG)
