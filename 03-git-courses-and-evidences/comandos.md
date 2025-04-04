# Comandos Git

acontinuacion se observan lo comandos visto en el curso **Git Cheat Sheet – 50 Git Commands You Should Know**

## Cómo verificar la configuración de Git:
El siguiente comando devuelve una lista de información sobre la configuración de Git, incluyendo el nombre de usuario y el correo electrónico:
git config -l


## Cómo configurar tu nombre de usuario en Git:
Con el siguiente comando puedes configurar tu nombre de usuario:
git config --global user.name "Fabio"


## Cómo configurar tu correo electrónico de usuario en Git:
Este comando te permite configurar la dirección de correo electrónico que usarás en tus commits:
git config --global user.email "signups@fabiopacifici.com"

## Cómo almacenar las credenciales de inicio de sesión en Git:
Puedes almacenar las credenciales de inicio de sesión en la caché para no tener que escribirlas cada vez. Solo usa este comando:
git config --global credential.helper cache


## Cómo inicializar un repositorio Git:
Todo comienza aquí. El primer paso es inicializar un nuevo repositorio Git localmente en la raíz de tu proyecto:
git init


## Cómo agregar un archivo al área de preparación en Git:
El siguiente comando agregará un archivo al área de preparación. Solo reemplaza `filename_here` con el nombre del archivo que quieres agregar al área de preparación:
git add filename_here


## Cómo agregar todos los archivos al área de preparación en Git:
Si quieres agregar todos los archivos de tu proyecto al área de preparación, puedes usar el comodín `.` y todos los archivos serán agregados automáticamente:
git add .


## Cómo agregar solo ciertos archivos al área de preparación en Git:
Con el asterisco en el siguiente comando, puedes agregar todos los archivos que comiencen con 'fil' al área de preparación:
git add fil*


## Cómo verificar el estado de un repositorio en Git:
Este comando mostrará el estado del repositorio actual, incluyendo archivos preparados, no preparados y no rastreados:
git status


## Cómo hacer un commit con un or en Git:
Este comando abrirá un or de texto en la terminal donde podrás escribir un mensaje completo para el commit:
git commit

## Cómo hacer un commit con un mensaje en Git:
Puedes agregar un mensaje de commit sin abrir el or. Este comando solo te permite especificar un resumen corto para tu mensaje de commit:
git commit -m "tu mensaje de commit aquí"


## Cómo hacer un commit (y omitir el área de preparación) en Git:
Puedes agregar y hacer commit de los archivos rastreados con un solo comando usando las opciones `-a` y `-m`:
git commit -a -m"tu mensaje de commit aquí"


## Cómo ver el historial de commits en Git:
Este comando muestra el historial de commits para el repositorio actual:
git log


## Cómo ver el historial de commits incluyendo los cambios en Git:
Este comando muestra el historial de commits, incluyendo todos los archivos y sus cambios:
git log -p


## Cómo ver un commit específico en Git:
Este comando muestra un commit específico. Reemplaza `commit-id` con el id del commit que encuentres en el registro de commits después de la palabra `commit`:
git show commit-id

## Cómo ver estadísticas del log en Git:
Este comando hará que el log de Git muestre estadísticas sobre los cambios en cada commit, incluyendo las líneas cambiadas y los nombres de los archivos:
git log --stat

## Cómo ver los cambios antes de hacer el commit usando "diff" en Git:
Puedes pasar un archivo como parámetro para ver solo los cambios en un archivo específico. `git diff` muestra solo los cambios no preparados por defecto. Podemos llamar a `diff` con el flag `--staged` para ver los cambios preparados:
git diff git diff all_checks.py git diff --staged

## Cómo ver los cambios usando "git add -p":
Este comando abre un prompt y te pregunta si quieres preparar los cambios o no, e incluye otras opciones:
git add -p

## Cómo eliminar archivos rastreados del árbol de trabajo actual en Git:
Este comando espera un mensaje de commit para explicar por qué se eliminó el archivo:
git rm filename


## Cómo renombrar archivos en Git:
Este comando prepara los cambios y luego espera un mensaje de commit:
git mv oldfile newfile


## Cómo ignorar archivos en Git:
Crea un archivo `.gitignore` y haz commit de él.

## Cómo revertir cambios no preparados en Git:
git checkout filename


## Cómo revertir cambios preparados en Git:
Puedes usar el flag `-p` para especificar los cambios que quieres restablecer:
git reset HEAD filename git reset HEAD -p


## Cómo modificar el commit más reciente en Git:
`git commit --amend` te permite modificar y agregar cambios al commit más reciente:
git commit --amend

**¡Nota!**: Corregir un commit local con `amend` es útil y puedes empujarlo a un repositorio compartido después de corregirlo. Pero debes evitar modificar commits que ya se han hecho públicos.

## Cómo deshacer el último commit en Git:
`git revert` creará un nuevo commit que será lo opuesto de todo lo que contiene el commit dado. Podemos revertir el último commit usando el alias `head` de esta forma:
git revert HEAD

## Cómo deshacer un commit antiguo en Git:
Puedes revertir un commit antiguo usando su id de commit. Esto abrirá el or para que puedas agregar un mensaje de commit:
git revert commit_id_here


## Cómo crear una nueva rama en Git:
Por defecto, tienes una rama, la rama principal. Con este comando, puedes crear una nueva rama. Git no cambiará a ella automáticamente, tendrás que hacerlo manualmente con el siguiente comando:
git branch branch_name

## Cómo cambiar a una nueva rama en Git:
Cuando quieras usar una rama diferente o una recién creada, puedes usar este comando:
git checkout branch_name

## Cómo listar las ramas en Git:
Puedes ver todas las ramas creadas usando el comando `git branch`. Mostrará una lista de todas las ramas y marcará la rama actual con un asterisco y la destacará en verde:
git branch

## Cómo crear una rama en Git y cambiar a ella inmediatamente:
En un solo comando, puedes crear y cambiar a una nueva rama de inmediato:
git checkout -b branch_name

## Cómo eliminar una rama en Git:
Cuando hayas terminado de trabajar con una rama y la hayas fusionado, puedes eliminarla usando el siguiente comando:
git branch -d branch_name

## Cómo fusionar dos ramas en Git:
Para fusionar el historial de la rama en la que te encuentras con la `branch_name`, necesitarás usar el siguiente comando:
git merge branch_name


## Cómo mostrar el log de commits como un gráfico en Git:
Podemos usar `--graph` para que el log de commits se muestre como un gráfico. También `--oneline` limitará los mensajes de commit a una sola línea:
git log --graph --oneline

## Cómo mostrar el log de commits como un gráfico de todas las ramas en Git:
Hace lo mismo que el comando anterior, pero para todas las ramas:
git log --graph --oneline --all

## Cómo abortar una fusión conflictiva en Git:
Si quieres desechar una fusión y empezar de nuevo, puedes ejecutar el siguiente comando:
git merge --abort

## Cómo agregar un repositorio remoto en Git:
Este comando agrega un repositorio remoto a tu repositorio local (solo reemplaza `https://repo_here` con la URL de tu repositorio remoto):
git add remote https://repo_here

## Cómo ver las URLs remotas en Git:
Puedes ver todos los repositorios remotos para tu repositorio local con este comando:
git remote -v

## Cómo obtener más información sobre un repositorio remoto en Git:
Solo reemplaza `origin` con el nombre del remoto obtenido ejecutando el comando `git remote -v`:
git remote show origin

## Cómo subir cambios a un repositorio remoto en Git:
Cuando todo tu trabajo esté listo para guardarse en un repositorio remoto, puedes subir todos los cambios usando el siguiente comando:
git push

## Cómo obtener cambios de un repositorio remoto en Git:
Si otros miembros del equipo están trabajando en tu repositorio, puedes obtener los últimos cambios realizados en el repositorio remoto con el siguiente comando:
git pull


## Cómo verificar las ramas remotas que Git está rastreando:
Este comando muestra el nombre de todas las ramas remotas que Git está rastreando para el repositorio actual:
git branch -r

## Cómo obtener cambios del repositorio remoto en Git:
Este comando descargará los cambios de un repositorio remoto pero no realizará una fusión en tu rama local (como lo hace `git pull`):
git fetch

## Cómo verificar el registro de commits de un repositorio remoto en Git:
Commit tras commit, Git acumula un registro. Puedes ver el log del repositorio remoto usando este comando:
git log origin/main

## Cómo fusionar un repositorio remoto con tu repositorio local en Git:
Si el repositorio remoto tiene cambios que quieres fusionar con el tuyo, este comando lo hará:
git merge origin/main

## Cómo obtener los contenidos de las ramas remotas en Git sin fusionar automáticamente:
Esto te permite actualizar el remoto sin fusionar ningún contenido en las ramas locales. Puedes ejecutar `git merge` o `git checkout` para realizar la fusión:
git remote update


## Cómo subir una nueva rama a un repositorio remoto en Git:
Si quieres subir una rama a un repositorio remoto, puedes usar el siguiente comando. Solo recuerda agregar `-u` para crear la rama upstream:
git push -u origin branch_name

## Cómo eliminar una rama remota en Git:
Si ya no necesitas una rama remota, puedes eliminarla usando el siguiente comando:
git push --delete origin branch_name_here

## Cómo usar Git rebase:
Puedes transferir trabajo completo de una rama a otra usando `git rebase`:
git rebase branch_name_here

**Advertencia sobre Git Rebase**: Git Rebase puede volverse realmente desordenado si no lo haces correctamente. Antes de usar este comando, te sugiero que releas la documentación oficial aquí.

## Cómo ejecutar rebase de manera interactiva en Git:
Puedes ejecutar `git rebase` de manera interactiva usando el flag `-i`. Esto abrirá el or y presentará un conjunto de comandos que puedes usar:
git rebase -i master

Comandos para Rebase Interactivo:
- `p, pick` = usar commit
- `r, reword` = usar commit, pero ar el mensaje de commit
- `e, ` = usar commit, pero detenerse para modificar
- `s, squash` = usar commit, pero combinar con el commit anterior
- `f, fixup` = como "squash", pero descartar el mensaje de este commit
- `x, exec` = ejecutar comando (el resto de la línea) usando shell
- `d, drop` = eliminar commit

## Cómo forzar un push en Git:
Este comando forzará un push. Esto generalmente está bien para ramas de pull request porque nadie más debería habe
 git push force