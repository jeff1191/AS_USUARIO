#Desarrollo de un sistema informático para ayudar a adultos con síndrome de Asperger

Este proyecto presenta un sistema informático para ayudar a personas Asperger, que tienen problemas para recordar actividades y objetos básicos, y a sus profesores especialistas. Se ha decidido llamarlo AS (Asperger). Se compone de dos aplicaciones Android: la aplicación tutor y la aplicación usuario. La primera es para que los profesionales, desde su propia tablet, puedan
centralizar y gestionar toda la información de sus alumnos creando tareas, retos y eventos específicos para cada uno. La segunda es para los móviles de las personas Asperger, se encarga de recordarles los sucesos que su tutor les ha asignado y después les pregunta si los realizaron correctamente. Ambas interfaces siguen los principios de claridad y sencillez, además la aplicación usuario es totalmente personalizable para que una persona Asperger de cualquier edad pueda interactuar y motivarse con ella.

La información es almacenada localmente en cada dispositivo. Ambas aplicaciones se comunican para sincronizar los datos mediante sockets usando la tecnología WIFI. Esto permite tener un seguimiento del progreso de cada alumno desde la aplicación tutor.

![Alt text](https://s11.postimg.org/w1rdtpeib/esquema_general_intro.png?raw=true "Esquema general")

Para desarrollar las aplicaciones tutor y usuario se ha decidido utilizar la arquitectura multicapa ya que nos proporciona integración y reusabilidad, encapsulación, distribución, escalabilidad, manejabilidad, mejora el rendimiento y mejora la fiabilidad.

![Alt text](https://s11.postimg.org/pt6204gxf/arq_multicapa.png?raw=true )

En el sistema que hemos desarrollado, la arquitectura multicapa utiliza patrones de Ingeniería de Software para conseguir la separación de las capas de presentación, negocio e integración. Los patrones empleados son: singleton, dispatcher, command, factoría abstracta, servicio de aplicación, DAO y data transfer object (DTO).
A grandes rasgos sería un esquema como el siguiente: 

![Alt text](https://s11.postimg.org/sjfdxbdmb/arq_multi_AS.png?raw=true )

# AS_USUARIO
Esta aplicación tiene como objetivo ayudar a que las personas Asperger, que hagan un correcto uso de ella, recuerden sus actividades cotidianas con la intención de que adquieran hábitos por sí mismas. Su funcionalidad está destinada a que un usuario realice determinadas tareas, que se le avisan mediante una notificación tal y después de un breve periodo de tiempo debe contestar una pregunta indicando si ha realizado o no dicha tarea, esta pregunta también es realizada mediante una notificación.

Como es una aplicación destinada a un usuario su modelo de dominio no tiene relaciones entre sus clases partipantes, siendo de la siguiente manera: 

![Alt text](https://s11.postimg.org/lu8uhasab/as_usuario_bdd.png?raw=true "BDD Usuario")
