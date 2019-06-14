# interview-app
This app fetches movies info from a public repo (themoviedb) and displays its content.
If you tap on any row, you'll be able to see movie details and watch its trailer (if available).
The app works online/offline. In order to work offline, you must have loaded data at least once.


- Please note that v3 version of "themoviedb" API has been used (instead of v4) because there are almost none API doc for v4.
- Please note that we are only loading one single page (1st page) for every movie list. I think this is enough for this interview app purposes.
- Youtube library was included to show videos.
- No Unit testing was performed. If it's really necessary for this interview just let me know.
- No logging has been performed in this app.
- I haven't taken care of http failure cases. I don't think it's necessary for this interview. Let me know if this isn't enough.


# Las capas de la aplicación (por ejemplo capa de persistencia, vistas, red, negocio, etc) y qué clases pertenecen a cual
Las capas arquitectonicas de la aplicacion son las siguientes:
 - capa de visualizacion / UI
 - capa de modelo
 - capa de vista-modelo

 El patron elegido fue MVVM. Retrofit es la libreria elegida para los llamados HTTP.

 Capa de vista:
   - com.rappi.test.rappitestapp.ui
      capa de visualizacion
      activities / fragments / adapters

 Capa de modelo:
   - com.rappi.test.rappitestapp.model
      modelo de la app
      beans pojos

 Capa de vista-modelo:
   - com.rappi.test.rappitestapp.viewmodel
      logica de fetching de datos (modelo) y notificacion a la vista
      MoviesViewModel / SearchViewModel


# En qué consiste el principio de responsabilidad única? Cuál es su propósito?
Se refiere a que cada clase o modulo de una app/proyecto/programa sea responsable de una unica funcionalidad del sistema. Dicha responsabilidad debe estar bien encapsulada.

# Qué características tiene, según su opinión, un “buen” código o código limpio?
- su testeabilidad debe ser straight forward. es decir, se deberia poder testear unitariamente cada clase/modulo
- las capas arquitectonicas deben estar bien definidas e interactuar entre ellas
- no debe haber multiples funcionalidades/responsabilidades en una misma clase/modulo
- mas alla de contar con javadoc y comentarios, el codigo por si mismo deberia ser legible
- no deberia haber funciones/metodos/clases extremadamente largos
- una buena arquitectura asegura una buena escalabilidad
- el sistema debe ser configurable (por ej. diferentes configuraciones de archivos properties segun el tipo de build)
- se debe tener en cuenta la performance y el uso de memoria, especialmente en apps mobile
- en apps android, los layouts deben ser lo menos anidados en lo posible






