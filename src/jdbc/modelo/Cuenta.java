package jdbc.modelo;

public class Cuenta {
	private String correo;
	private String contrasenia;

	public Cuenta(String correo, String contrasenia) {
		this.correo = correo;
		this.contrasenia = contrasenia;
	}

	public String getCorreo() {
		return correo;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public boolean equals(Cuenta cuenta) {
		return cuenta.getContrasenia().equals(this.getContrasenia()) && cuenta.getCorreo().equals(this.getCorreo());
	}
}
