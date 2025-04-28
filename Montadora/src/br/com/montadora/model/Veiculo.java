package br.com.montadora.model;

public class Veiculo {
    // --- NOVO ---
    private int id; // Para armazenar o ID vindo do banco de dados
    // --- FIM NOVO ---

    private String nomeCarro;
    private String montadora;

    // --- NOVO: Getters e Setters para ID ---
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    // --- FIM NOVO ---

     public String getNomeCarro() {
        return nomeCarro;
    }
    public void setNomeCarro(String nomeCarro) {
        this.nomeCarro = nomeCarro;
    }
    public String getMontadora() {
        return montadora;
    }
    public void setMontadora(String montadora) {
        this.montadora = montadora;
    }

}