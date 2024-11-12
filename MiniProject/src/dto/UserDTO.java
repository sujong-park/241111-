package dto;


public class UserDTO {
	private String id;
	private String password;

	public UserDTO(String id, String password) {
		this.id = id;
		this.password = password;
	}
 
	public String getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}
}

