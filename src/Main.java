import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// Clase abstracta Cuenta
abstract class Cuenta {
    protected double saldo;
    protected List<String> historialTransacciones;

    public Cuenta() {
        this.saldo = 0;
        this.historialTransacciones = new ArrayList<>();
    }

    public double getSaldo() {
        return saldo;
    }

    public List<String> getHistorial() {
        return historialTransacciones;
    }

    public void agregarTransaccion(String transaccion) {
        historialTransacciones.add(transaccion);
    }

    public void depositar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a depositar debe ser positivo.");
        }
        saldo += monto;
        agregarTransaccion("Depósito: $" + monto);
    }

    public boolean retirar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a retirar debe ser positivo.");
        }
        if (monto <= saldo) {
            saldo -= monto;
            agregarTransaccion("Retiro: $" + monto);
            return true;
        }
        return false;
    }

    public abstract boolean transferir(Cuenta cuentaDestino, double monto, String cedulaDestino);
}

// Clase concreta para Cuenta Bancaria
class CuentaBancaria extends Cuenta {
    private double tasaInteresAnual;
    private String tipoCuenta;

    public CuentaBancaria(String tipoCuenta) {
        super();
        this.tasaInteresAnual = 0.05; // 5% de interés anual
        this.tipoCuenta = tipoCuenta;
    }

    public double getSaldoConInteres(int años) {
        return saldo * Math.pow(1 + tasaInteresAnual, años);
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    @Override
    public boolean transferir(Cuenta cuentaDestino, double monto, String cedulaDestino) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a transferir debe ser positivo.");
        }
        if (monto <= saldo) {
            saldo -= monto;
            cuentaDestino.depositar(monto);
            agregarTransaccion("Transferencia de $" + monto + " a cuenta " + cedulaDestino);
            cuentaDestino.agregarTransaccion("Transferencia recibida de $" + monto + " desde cuenta " + cedulaDestino);
            return true;
        }
        return false;
    }
}

// Clase abstracta Persona
abstract class Persona {
    protected String nombreCompleto;
    protected String cedula;
    protected int edad;

    public Persona(String nombreCompleto, String cedula, int edad) {
        this.nombreCompleto = nombreCompleto;
        this.cedula = cedula;
        this.edad = edad;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getCedula() {
        return cedula;
    }

    public int getEdad() {
        return edad;
    }

    public abstract String obtenerPerfil();
}

// Clase Usuario que hereda de Persona
class Usuario extends Persona {
    private String contrasena;
    private String ciudad;
    private String provincia;
    private String correoElectronico;
    private String telefono;
    private CuentaBancaria cuentaBancaria;

    public Usuario(String nombreCompleto, String cedula, String contrasena, int edad, String ciudad, String provincia, String correoElectronico, String telefono, String tipoCuenta) {
        super(nombreCompleto, cedula, edad);
        this.contrasena = contrasena;
        this.ciudad = ciudad;
        this.provincia = provincia;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
        this.cuentaBancaria = new CuentaBancaria(tipoCuenta);
    }

    public boolean iniciarSesion(String cedula, String contrasena) {
        return this.cedula.equals(cedula) && this.contrasena.equals(contrasena);
    }

    public CuentaBancaria getCuentaBancaria() {
        return cuentaBancaria;
    }

    @Override
    public String obtenerPerfil() {
        return "Nombre: " + nombreCompleto + "\nCédula: " + cedula + "\nEdad: " + edad + "\nCiudad: " + ciudad + "\nProvincia: " + provincia +
                "\nCorreo: " + correoElectronico + "\nTeléfono: " + telefono + "\nTipo de cuenta: " + cuentaBancaria.getTipoCuenta();
    }

    @Override
    public String toString() {
        return "Usuario: " + nombreCompleto + " - Cédula: " + cedula;
    }
}

// Clase principal del proyecto
class ProyectoFinal {
    private List<Usuario> usuarios;

    public ProyectoFinal() {
        usuarios = new ArrayList<>();
        usuarios.add(new Usuario("Juan Pérez", "1234567890", "p", 30, "Ciudad Prueba", "Provincia Prueba", "correo@prueba.com", "987654321", "Ahorros"));
    }

    public static void main(String[] args) {
        ProyectoFinal proyecto = new ProyectoFinal();
        proyecto.mostrarVentanaPrincipal();
    }

    public void mostrarVentanaPrincipal() {
        JFrame ventanaPrincipal = new JFrame("BANCO X - Software Hub Solutions");
        ventanaPrincipal.setSize(400, 500);
        ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaPrincipal.setLayout(new BorderLayout());

        JLabel titulo = new JLabel("BANCO X", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        ventanaPrincipal.add(titulo, BorderLayout.NORTH);

        JButton btnIngresar = new JButton("Iniciar Sesión");
        JButton btnRegistrar = new JButton("No tienes sesión? Crea tu cuenta");

        JPanel panelBotones = new JPanel(new GridLayout(2, 1, 20, 20));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        panelBotones.add(btnIngresar);
        panelBotones.add(btnRegistrar);
        ventanaPrincipal.add(panelBotones, BorderLayout.CENTER);

        btnIngresar.addActionListener(e -> {
            ventanaPrincipal.dispose();
            iniciarSesion();
        });

        btnRegistrar.addActionListener(e -> {
            ventanaPrincipal.dispose();
            registrarUsuario();
        });

        ventanaPrincipal.setLocationRelativeTo(null);
        ventanaPrincipal.setVisible(true);
    }

    public void iniciarSesion() {
        int intentos = 3;

        while (intentos > 0) {
            String cedula = JOptionPane.showInputDialog(null, "Ingrese su cédula (solo números, mínimo 10 dígitos):", "Inicio de Sesión", JOptionPane.PLAIN_MESSAGE);
            if (cedula == null) {
                mostrarVentanaPrincipal();
                return;
            }

            if (!cedula.matches("\\d{10,}")) {
                JOptionPane.showMessageDialog(null, "La cédula debe tener al menos 10 dígitos.", "Error", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            String contrasena = JOptionPane.showInputDialog(null, "Ingrese su contraseña:", "Inicio de Sesión", JOptionPane.PLAIN_MESSAGE);
            if (contrasena == null) {
                mostrarVentanaPrincipal();
                return;
            }

            for (Usuario usuario : usuarios) {
                if (usuario.iniciarSesion(cedula, contrasena)) {
                    mostrarMenu(usuario);
                    return;
                }
            }

            intentos--;
            if (intentos == 0) {
                JOptionPane.showMessageDialog(null, "Has agotado los intentos. El programa se cerrará.", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
    }

    public void registrarUsuario() {
        String nombre = obtenerInputValido("Ingrese su nombre completo:", "El nombre solo puede contener letras y espacios.", "[a-zA-Z\\s]+");
        if (nombre == null) {
            mostrarVentanaPrincipal();
            return;
        }

        String cedula = obtenerInputValido("Ingrese su cédula (solo números, mínimo 10 dígitos):", "La cédula debe tener al menos 10 dígitos.", "\\d{10,}");
        if (cedula == null) {
            mostrarVentanaPrincipal();
            return;
        }

        // Verificar si la cédula ya está registrada
        for (Usuario usuario : usuarios) {
            if (usuario.getCedula().equals(cedula)) {
                JOptionPane.showMessageDialog(null, "La cédula ingresada ya está registrada.", "Error", JOptionPane.ERROR_MESSAGE);
                mostrarVentanaPrincipal();
                return;
            }
        }

        String contrasena = JOptionPane.showInputDialog(null, "Ingrese su contraseña:", "Registrar Usuario", JOptionPane.PLAIN_MESSAGE);
        if (contrasena == null) {
            mostrarVentanaPrincipal();
            return;
        }

        int edad = Integer.parseInt(obtenerInputValido("Ingrese su edad (18-100):", "La edad debe estar entre 18 y 100.", "\\d+"));
        if (edad < 18 || edad > 100) {
            JOptionPane.showMessageDialog(null, "La edad debe estar entre 18 y 100 años.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String ciudad = obtenerInputValido("Ingrese su ciudad:", "La ciudad solo puede contener letras y espacios.", "[a-zA-Z\\s]+");
        if (ciudad == null) {
            mostrarVentanaPrincipal();
            return;
        }

        String provincia = obtenerInputValido("Ingrese su provincia:", "La provincia solo puede contener letras y espacios.", "[a-zA-Z\\s]+");
        if (provincia == null) {
            mostrarVentanaPrincipal();
            return;
        }

        String correo = obtenerInputValido("Ingrese su correo electrónico:", "El correo no tiene un formato válido.", "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");
        if (correo == null) {
            mostrarVentanaPrincipal();
            return;
        }

        String telefono = obtenerInputValido("Ingrese su teléfono (mínimo 9 dígitos):", "El teléfono debe tener al menos 9 dígitos.", "\\d{9,}");
        if (telefono == null) {
            mostrarVentanaPrincipal();
            return;
        }

        String tipoCuenta = (String) JOptionPane.showInputDialog(null, "Seleccione el tipo de cuenta:", "Registro", JOptionPane.PLAIN_MESSAGE, null,
                new String[]{"Ahorros", "Corriente"}, "Ahorros");

        if (tipoCuenta != null) {
            usuarios.add(new Usuario(nombre, cedula, contrasena, edad, ciudad, provincia, correo, telefono, tipoCuenta));
            JOptionPane.showMessageDialog(null, "Usuario registrado exitosamente.", "Registro", JOptionPane.INFORMATION_MESSAGE);
            mostrarVentanaPrincipal();
        }
    }


        public String obtenerInputValido(String mensaje, String errorMensaje, String regex) {
        String input;
        while (true) {
            input = JOptionPane.showInputDialog(null, mensaje, "Registro", JOptionPane.PLAIN_MESSAGE);
            if (input == null) return null;
            if (input.matches(regex)) return input;
            JOptionPane.showMessageDialog(null, errorMensaje, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void mostrarMenu(Usuario usuario) {
        JFrame ventanaMenu = new JFrame("Menú");
        ventanaMenu.setSize(400, 400);
        ventanaMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaMenu.setLayout(new BorderLayout()); // Cambiar a BorderLayout

        JLabel titulo = new JLabel("Menú Principal", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        ventanaMenu.add(titulo, BorderLayout.NORTH);

        JPanel panelOpciones = new JPanel();
        panelOpciones.setLayout(new GridLayout(0, 1, 10, 10)); // Distribución vertical y centrada
        panelOpciones.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        String[] opciones = {"Consultar saldo", "Ver historial de transacciones", "Ver saldo con interés", "Realizar operación", "Transferir dinero", "Ver perfil", "Cerrar sesión"};

        for (String opcion : opciones) {
            JButton boton = new JButton(opcion);
            boton.addActionListener(e -> {
                switch (opcion) {
                    case "Consultar saldo":
                        JOptionPane.showMessageDialog(null, "Saldo: $" + usuario.getCuentaBancaria().getSaldo());
                        break;
                    case "Ver historial de transacciones":
                        String historial = String.join("\n", usuario.getCuentaBancaria().getHistorial());
                        JOptionPane.showMessageDialog(null, historial.isEmpty() ? "No hay transacciones" : historial);
                        break;
                    case "Ver saldo con interés":
                        String inputAños = JOptionPane.showInputDialog(null, "Ingrese el número de años:");
                        try {
                            int años = Integer.parseInt(inputAños);
                            JOptionPane.showMessageDialog(null, "Saldo con interés: $" + usuario.getCuentaBancaria().getSaldoConInteres(años));
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Por favor ingrese un número válido.");
                        }
                        break;
                    case "Realizar operación":
                        realizarOperacion(usuario);
                        break;
                    case "Transferir dinero":
                        transferirDinero(usuario);
                        break;
                    case "Ver perfil":
                        JOptionPane.showMessageDialog(null, usuario.obtenerPerfil());
                        break;
                    case "Cerrar sesión":
                        JOptionPane.showMessageDialog(null, "Sesión cerrada.");
                        ventanaMenu.dispose();
                        mostrarVentanaPrincipal();
                        break;
                }
            });
            panelOpciones.add(boton);
        }

        ventanaMenu.add(panelOpciones, BorderLayout.CENTER);
        ventanaMenu.setLocationRelativeTo(null);
        ventanaMenu.setVisible(true);
    }


    public void realizarOperacion(Usuario usuario) {
        String[] opciones = {"Depositar", "Retirar"};
        String opcion = (String) JOptionPane.showInputDialog(null, "Seleccione una operación:", "Operación", JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);

        if (opcion == null) return;

        String montoInput = JOptionPane.showInputDialog(null, "Ingrese monto:");
        try {
            double monto = Double.parseDouble(montoInput);
            if (opcion.equals("Depositar")) {
                usuario.getCuentaBancaria().depositar(monto);
                JOptionPane.showMessageDialog(null, "Depósito realizado con éxito.");
            } else if (opcion.equals("Retirar")) {
                if (usuario.getCuentaBancaria().retirar(monto)) {
                    JOptionPane.showMessageDialog(null, "Retiro realizado con éxito.");
                } else {
                    JOptionPane.showMessageDialog(null, "Fondos insuficientes.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Monto inválido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void transferirDinero(Usuario usuario) {
        String cedulaDestino = JOptionPane.showInputDialog(null, "Ingrese la cédula del destinatario:", "Transferencia", JOptionPane.PLAIN_MESSAGE);
        if (cedulaDestino == null) return;

        Usuario destinatario = null;
        for (Usuario u : usuarios) {
            if (u.getCedula().equals(cedulaDestino)) {
                destinatario = u;
                break;
            }
        }

        if (destinatario == null) {
            JOptionPane.showMessageDialog(null, "No se encontró usuario con esa cédula.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String montoInput = JOptionPane.showInputDialog(null, "Ingrese el monto a transferir:");
        try {
            double monto = Double.parseDouble(montoInput);
            if (usuario.getCuentaBancaria().transferir(destinatario.getCuentaBancaria(), monto, usuario.getCedula())) {
                JOptionPane.showMessageDialog(null, "Transferencia realizada con éxito.");
            } else {
                JOptionPane.showMessageDialog(null, "Fondos insuficientes.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Monto inválido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}