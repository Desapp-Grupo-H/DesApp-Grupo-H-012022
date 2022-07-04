package ar.edu.unq.desapp.grupoh.backenddesappapi.service.user;

public class TokenDTO {

    public String token;

    public TokenDTO() {}

    public TokenDTO(String token) {
        this.token = token;
    }

    public void setToken(String token){
        this.token = token;
    }

    public String getToken(){
        return this.token;
    }
}
