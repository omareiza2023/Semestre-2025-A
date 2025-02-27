

## Título:  
**Comprensión del negocio y estructura de datos para la gestión de una barbería**  

## Descripción  
Como analista de negocios, quiero comprender el funcionamiento de una barbería para diseñar una base de datos eficiente que permita gestionar clientes, citas, barberos, servicios y pagos dentro de la aplicación.  

## Criterios de Aceptación  

### Modelos de Datos  
Se debe diseñar un modelo relacional que incluya las siguientes entidades:  

- **Usuarios** (clientes y barberos)  
- **Citas** (fecha, hora, cliente, barbero, servicio)  
- **Servicios** (nombre, duración, precio)  
- **Pagos** (monto, método de pago, estado)  

Las relaciones deben ser claras:  

- Un cliente puede agendar múltiples citas.  
- Un barbero atiende múltiples citas.  
- Un servicio puede ser solicitado en múltiples citas.  
- Un pago está asociado a una cita.  

### Casos de Uso  

#### Cliente  
- Registrarse en la aplicación.  
- Ver disponibilidad de barberos y horarios.  
- Agendar una cita.  
- Cancelar o reprogramar una cita.  
- Pagar por un servicio.  
- Ver historial de citas y pagos.  

#### Barbero  
- Gestionar su disponibilidad de horarios.  
- Confirmar o cancelar citas.  
- Registrar los servicios realizados.  
- Ver historial de clientes y citas.  

#### Administrador (Opcional)  
- Gestionar barberos y clientes.  
- Configurar servicios y precios.  
- Generar reportes de ingresos y citas.  

### Comportamiento a Nivel de Base de Datos  
- Se debe garantizar la integridad referencial entre entidades.  
- Se usarán índices para optimizar búsquedas de citas y clientes.  
- Los pagos deben manejarse con una lógica de estados (**pendiente, completado, cancelado**).  
- Se deben registrar logs de cambios en citas y pagos para auditoría.  

### Notas Adicionales  
Se debe evaluar la posibilidad de integración con pasarelas de pago y notificaciones push para recordatorios de citas.  
