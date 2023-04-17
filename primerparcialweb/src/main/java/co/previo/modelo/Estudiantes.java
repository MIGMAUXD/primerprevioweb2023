package co.previo.modelo;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Estudiantes implements Serializable {
	
	private Integer id;
	
	private String documento;
	
	private String nombre;
	
	private String apellido;
	
	private String email;
	
	private String genero;
	
	private Date fechanacimiento;
	
	private String telefono;
	
	private String direccion;
	
	private Float estatura;
	
	private Float peso;

	public Estudiantes(String documento, String nombre, String apellido, String email, String genero,
			Date fechanacimiento, String telefono, String direccion, Float estatura, Float peso) {
		this.documento = documento;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.genero = genero;
		this.fechanacimiento = fechanacimiento;
		this.telefono = telefono;
		this.direccion = direccion;
		this.estatura = estatura;
		this.peso = peso;
	}
	
	

}
