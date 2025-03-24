# Requisitos Previos

Antes de instalar Ionic, es necesario contar con Node.js y npm instalados en el sistema.

## 1. Verificar si Node.js y npm están instalados

Ejecuta el siguiente comando en la terminal o línea de comandos:

```bash
node -v
npm -v
```

Si estos comandos devuelven una versión, significa que ya están instalados. De lo contrario, procede con la instalación.

## 2. Instalar Node.js

Descarga e instala Node.js desde su sitio oficial: [https://nodejs.org](https://nodejs.org)

Asegúrate de descargar la versión LTS (Long-Term Support) para mayor estabilidad.
Después de la instalación, verifica nuevamente con los comandos anteriores que Node.js y npm se han instalado correctamente.

# Instalación de Ionic Globalmente

Una vez que Node.js y npm estén instalados, procede a instalar Ionic globalmente ejecutando el siguiente comando:

```bash
npm install -g @ionic/cli
```

Este comando instalará el CLI de Ionic a nivel global, permitiéndote acceder a él desde cualquier directorio.

## Verificación de la Instalación

Para comprobar que la instalación se realizó correctamente, ejecuta:

```bash
ionic -v
```

Si Ionic está instalado correctamente, verás la versión instalada en la terminal.

# Creación de un Nuevo Proyecto Ionic

Para asegurarte de que Ionic está funcionando correctamente, puedes crear un nuevo proyecto con el siguiente comando:

```bash
ionic start myApp blank
```

Este comando creará una nueva aplicación Ionic llamada "myApp" con una plantilla en blanco.
Luego, accede al directorio del proyecto:

```bash
cd myApp
```

Y ejecuta la aplicación en un servidor local:

```bash
ionic serve
```

Si todo funciona correctamente, se abrirá una ventana en el navegador mostrando la aplicación.

![ENTORNO](/session-7/IMG/EVIDECNCIA.png)