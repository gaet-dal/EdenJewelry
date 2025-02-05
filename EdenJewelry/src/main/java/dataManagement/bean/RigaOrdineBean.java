package main.java.dataManagement.bean;

public class RigaOrdineBean {
    private int id;
    private int numeroOrdine;
    private String nomeProdotto;
    private int quantità;
    private float prezzoUnitario;

    public RigaOrdineBean() {
        id = 0;
        numeroOrdine = 0;
        nomeProdotto = null;
        quantità = 0;
        prezzoUnitario = 0.0f;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumeroOrdine() {
        return numeroOrdine;
    }

    public void setNumeroOrdine(int numeroOrdine) {
        this.numeroOrdine = numeroOrdine;
    }

    public String getNomeProdotto() {
        return nomeProdotto;
    }

    public void setNomeProdotto(String nomeProdotto) {
        this.nomeProdotto = nomeProdotto;
    }

    public float getPrezzoUnitario() {
        return prezzoUnitario;
    }

    public void setPrezzoUnitario(float prezzoUnitario) {
        this.prezzoUnitario = prezzoUnitario;
    }

    public int getQuantità() {
        return quantità;
    }

    public void setQuantità(int quantità) {
        this.quantità = quantità;
    }
}
