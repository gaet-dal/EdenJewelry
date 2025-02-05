package main.java.dataManagement.bean;

public class ProdottoBean{
    private String nome;
    private float prezzo;
    private int quantita;
    private String categoria;
    private String immagine;

    public ProdottoBean(){
        nome = null;
        prezzo = 0.0f;
        quantita = 0;
        categoria = null;
        immagine = null;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null)
            return false;

        if(!(o instanceof ProdottoBean))
            return false;

        ProdottoBean p = (ProdottoBean) o;

        return (this.nome).equals(p.getNome());
    }
}