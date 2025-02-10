Descripción

BANCO X es una aplicación de escritorio desarrollada en Java utilizando Swing para la interfaz gráfica. 
Permite la gestión de cuentas bancarias, transacciones, transferencias y operaciones bancarias de los usuarios.

Características

Registro e inicio de sesión de usuarios.
Gestión de cuentas bancarias (depósitos, retiros, transferencias).
Cálculo de saldo con interés.
Historial de transacciones.
Interfaz gráfica interactiva.

Requisitos del sistema

Java 8 o superior.
Entorno de desarrollo compatible (Eclipse, IntelliJ IDEA o NetBeans).
Instalación y ejecución
Clona o descarga este repositorio.
Asegúrate de tener Java instalado en tu sistema.

Compila el código fuente y ejecuta la clase ProyectoFinal.java.

javac ProyectoFinal.java
java ProyectoFinal

Estructura del código

Cuenta.java: Clase abstracta base para cuentas bancarias.

CuentaBancaria.java: Implementación de una cuenta bancaria con saldo e interés.

Persona.java: Clase abstracta base para usuarios del banco.

Usuario.java: Implementación de un usuario con información personal y cuenta bancaria.

ProyectoFinal.java: Clase principal que maneja la interfaz gráfica y la lógica del sistema.

Uso

Al ejecutar el programa, aparecerá una ventana con opciones de Iniciar Sesión o Registrar Usuario.

Si eres nuevo, puedes registrarte proporcionando datos como nombre, cédula, edad, ciudad, provincia, correo y tipo de cuenta.

Una vez dentro, podrás acceder a diferentes funciones como consultar saldo, ver historial de transacciones, calcular saldo con interés, realizar depósitos y retiros, transferir dinero y cerrar sesión.

Contribuciones

Si deseas contribuir al proyecto, puedes hacer un fork, realizar mejoras y enviar un pull request.
