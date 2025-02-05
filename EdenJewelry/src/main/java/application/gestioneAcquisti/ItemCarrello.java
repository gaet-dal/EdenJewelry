package main.java.application.gestioneAcquisti;

import java.util.Objects;

public class ItemCarrello {
    private String nome;
    private int quantità;

    public ItemCarrello(String nome, int quantità) {
        this.nome = nome;
        this.quantità = quantità;
    }

    public void setQuantità(int quantità) {
        this.quantità = quantità;
    }

    public int getQuantità() {
        return quantità;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;

        if(o.getClass() != this.getClass())
            return false;

        ItemCarrello it = (ItemCarrello) o;

        return nome.equals(it.getNome());
    }
}
