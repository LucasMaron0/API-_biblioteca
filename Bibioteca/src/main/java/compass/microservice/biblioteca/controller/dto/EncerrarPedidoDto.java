package compass.microservice.biblioteca.controller.dto;

public class EncerrarPedidoDto {
	
	
	private Long  idUser;
	private Long  idRegistro;
	private String status;
	

	
	public EncerrarPedidoDto() {
		super();
	}
	
	
	
	
	public EncerrarPedidoDto(Long idUser, Long idRegistro, String status) {
		super();
		this.idUser = idUser;
		this.idRegistro = idRegistro;
		this.status = status;
	}




	public Long getIdUser() {
		return idUser;
	}
	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}
	public Long getIdRegistro() {
		return idRegistro;
	}
	public void setIdRegistro(Long idRegistro) {
		this.idRegistro = idRegistro;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	

}
