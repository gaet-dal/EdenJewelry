package main.java.gestioneProdotti.prodotto;

public class ProdottoBean{
    private String nome;
    private float prezzo;
    private int quantità;
    private String categoria;
    private String immagine;

    public ProdottoBean(){
        nome = null;
        prezzo = 0.0f;
        quantità = 0;
        categoria = null;
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

    public int getQuantità() {
        return quantità;
    }

    public void setQuantità(int quantità) {
        this.quantità = quantità;
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
        ProdottoBean p = (ProdottoBean) o;

        if ((this.nome).equals(p.getNome()))
            return true;

        return false;
    }
}