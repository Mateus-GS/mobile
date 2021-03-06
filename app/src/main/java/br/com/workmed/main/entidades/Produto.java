package br.com.workmed.main.entidades;

import java.io.Serializable;
import java.util.Objects;


public class Produto implements Serializable {

    private String id;
    private String descricao;
    private String quantidadeEmEstoque;

    public Produto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getQuantidadeEmEstoque() {
        return quantidadeEmEstoque;
    }

    public void setQuantidadeEmEstoque(String quantidadeEmEstoque) {
        this.quantidadeEmEstoque = quantidadeEmEstoque;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id='" + id + '\'' +
                ", descricao='" + descricao + '\'' +
                ", quantidadeEmEstoque=" + quantidadeEmEstoque +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(id, produto.id) &&
                Objects.equals(descricao, produto.descricao) &&
                Objects.equals(quantidadeEmEstoque, produto.quantidadeEmEstoque);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descricao, quantidadeEmEstoque);
    }
}
