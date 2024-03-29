# MeliChallengePedro
Meli Challenge implementation by Pedro Llanes

Notas y consideraciones:
- En el challenge indicaban que la app debería contar con 3 pantallas: búsqueda, listado de productos y detalles 
de producto. Yo sólo utilicé dos fragmentos, ya que el componente de buscar y el listado de productos los ubiqué
en el mismo fragmento.

- En el listado de productos, a la hora de hacer el fetch, se debería implementar un paginado. 
Para esto se podría haber realizado una implementación de Scroll Infinito en el RecyvlerView 
que lista los productos. Por cuestiones de tiempo no pude implementarlo.

- Se utiliza retrofit para las consultas a la API

- Se implementa inyección con Dagger

- Se manejan las excepciones no tratadas con un DefaultUncaughtExceptionHandler

- Se utiliza org.slf4j.Logger para loggear. Aquí sería interesante utilizar algún servicio de analytics y de
registro de crashes, como por ejemplo Firebase (Analytics y Crashlytics)

- Se utiliza Robolectric para pruebas unitarias básicas (Por problemas de tiempo, no fue posible implementar la 
mayoría de las pruebas unitarias y de integración que podrían ser relevantes. Sólo se implementaron unas pocas 
pruebas básicas.
Entiendo que sería importante implementar pruebas unitarias a los servicios de retrofit, al mecanismo de navegación
entre fragmentos, al ViewModel que comparten los fragmentos, etc.
Una prueba interesante sería, por ejemplo, utilizar un mock de la clase de la API (u de cualquier otra implicada), y 
verificar que se esté invocando una sola vez cuando el usuario ejecuta una búsqueda desde el fragmento de buscar, 
para así agrantizar que no se están consumiendo recursos de más por error.
