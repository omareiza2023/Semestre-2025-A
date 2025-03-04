# Documentación del Diagrama de Procesos

## Explicación del Diagrama

El diagrama representa el flujo de interacción entre los pasajeros, la tripulación y el personal de tierra con la aplicación móvil. Los pasajeros pueden verificar el estado de su vuelo, realizar el check-in y solicitar información sobre servicios. La tripulación actualiza los horarios de vuelo y el personal de tierra gestiona las puertas de embarque. Todas estas acciones generan notificaciones automáticas que se centralizan en una base de información para mantener a los usuarios actualizados. Esta estructura mejora la comunicación, agiliza la gestión de vuelos y optimiza la experiencia del usuario al ofrecer información en tiempo real desde una plataforma centralizada.

## Diagrama

```plantuml
@startuml
left to right direction

actor Pasajero as P
actor Tripulación as T
actor "Personal de Tierra" as PT

rectangle "App Móvil" {
    P --> (Verificar Estado de Vuelo)
    P --> (Realizar Check-in)
    P --> (Solicitar Información de Servicios)
    T --> (Actualizar Horarios de Vuelo)
    PT --> (Gestionar Puertas de Embarque)

    (Verificar Estado de Vuelo) --> (Generar Notificaciones)
    (Actualizar Horarios de Vuelo) --> (Generar Notificaciones)
    (Gestionar Puertas de Embarque) --> (Generar Notificaciones)

    (Generar Notificaciones) --> (Base de Información Centralizada)
    (Realizar Check-in) --> (Base de Información Centralizada)
    (Solicitar Información de Servicios) --> (Base de Información Centralizada)
}
@enduml
