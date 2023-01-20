package jdbc.modelo;

import java.sql.Date;

public class Reserva {
	
	private Date fechaE;
	private Date fechaS;
	private int valor;
	private String formaPago;
	private Integer id;
	
	
	public Reserva(Date fechaE, Date fechaS, int valor, String formaPago) {		
		this.fechaE = fechaE;
		this.fechaS = fechaS;
		this.valor = valor;
		this.formaPago = formaPago;
	}
		
	public Reserva(Integer id, Date fechaE, Date fechaS, int valor, String formaPago) {
		this.id = id;
		this.fechaE = fechaE;
		this.fechaS = fechaS;
		this.valor = valor;
		this.formaPago = formaPago;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public Date getfechaE() {
		return fechaE;
	}

	public Date getfechaS() {
		return fechaS;
	}

	public int getvalor() {
		return valor;
	}

	public String getformaPago() {
		return formaPago;
	}

	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("La reserva generada fue: %d, %s, %s, %s, %s", this.id, this.fechaE, this.fechaS, this.valor, this.formaPago);
	}
	
	
	
}

