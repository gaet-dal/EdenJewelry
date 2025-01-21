package main.java.gestioneAccount.utente;

public class UtenteBean {

    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String tipo;

    public UtenteBean() {
        nome = null;
        cognome = null;
        email = null;
        password = null;
        String tipo = null;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}