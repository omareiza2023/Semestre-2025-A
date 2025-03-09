# 01-HU Entendimiento del Negocio para la App de Barbería

## Explicación General
El modelo de negocio de una barbería se centra en ofrecer servicios como cortes de cabello, afeitados y otros tratamientos de cuidado personal. La barbería cuenta con empleados (barberos) que brindan estos servicios y clientes que los solicitan. La aplicación permite gestionar reservas, cancelar citas, y mantener un registro de la información de clientes, empleados y servicios. Esto facilita la organización y eficiencia en la operación diaria de la barbería.

## Comportamiento a Nivel de Base de Datos
A continuación se presenta el modelo de base de datos que soporta la gestión de la barbería:

![Modelo de Base de Datos](/recursos-adicionales/mode_DB%20(1).png)

## Comportamiento a Nivel de Modelo Relacional
El modelo relacional define las entidades y relaciones clave para la gestión de la barbería:

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

![Modelo Relacional](/recursos-adicionales/MER.png)

## Comportamiento a Nivel de Casos de Uso
Los casos de uso describen las interacciones principales dentro de la aplicación:

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

![Casos de Uso](/recursos-adicionales/casos_de_uso.png)