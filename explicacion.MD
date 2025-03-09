# 01-HU Entendimiento del Negocio para la App de Barbería

## Descripción
Como analista de negocios, quiero comprender el funcionamiento de una barbería para diseñar una base de datos eficiente que permita gestionar clientes, citas, barberos, servicios y pagos dentro de la aplicación.

## Comportamiento a Nivel de Casos de Uso
1. **Gestión de Clientes**
   - Crear, editar y eliminar clientes.
   - Ver historial de citas y pagos.
   
2. **Gestión de Citas**
   - Reservar citas para clientes con barberos disponibles.
   - Modificar o cancelar citas según disponibilidad.
   
3. **Gestión de Barberos**
   - Asignación de citas a barberos.
   - Configuración de horarios de trabajo y disponibilidad.
   
4. **Gestión de Servicios**
   - Definir los servicios ofrecidos (ej. corte de cabello, afeitado, etc.).
   - Asignar precios y tiempos a cada servicio.

5. **Gestión de Pagos**
   - Realizar pagos por los servicios prestados.
   - Registro de pagos para cada cliente y cita.

## Comportamiento a Nivel de Modelo Relacional
- **Entidades Principales:**
   - **Cliente**: Nombre, teléfono, historial de citas y pagos.
   - **Cita**: Fecha, hora, servicio asignado, cliente, barbero.
   - **Barbero**: Nombre, especialidades, horarios disponibles.
   - **Servicio**: Descripción, precio, duración.
   - **Pago**: Monto, método de pago, fecha.
   
- **Relaciones:**
   - Un cliente puede tener múltiples citas.
   - Un barbero puede atender múltiples citas.
   - Un servicio puede ser parte de múltiples citas.
   - Un pago está asociado a una cita específica.

## Prioridad y Estimación
- **Prioridad**: Alta (Es fundamental para estructurar correctamente la aplicación desde el inicio).
- **Estimación**: 3 puntos en Scrum (Es un esfuerzo moderado, manejable en un sprint).

## Anexo de Imágenes
A continuación se incluyen las imágenes que establecen la lógica del negocio:

![Modelo Relacional](/recursos-adicionales/MER.png)
![Casos de Uso](/recursos-adicionales/casos_de_uso.png)
![Modelo de Base de Datos](/recursos-adicionales/mode_DB%20(1).png)