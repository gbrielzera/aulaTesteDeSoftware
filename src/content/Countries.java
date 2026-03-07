package content;

public class Countries {
    private int id;
    private String nome;
    private String continente;
    private String direcaoDaMao;

    public Countries() {
        this.id = id;
        this.nome = nome;
        this.continente = continente;
        this.direcaoDaMao = direcaoDaMao;
    }

    // Getters

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
    
    public String getContinente() {
        return continente;
    }

    public String getDirecaoDaMao() {
        return direcaoDaMao;
    }
    
    // Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setContinente(String continente) {
        this.continente = continente;
    }

    public void setDirecaoDaMao(String direcaoDaMao) {
        this.direcaoDaMao = direcaoDaMao;
    }

    @Override
    public String toString() { 
    return "Countries{" +
            "id=" + id +
            ", name='" + nome + '\'' +
            ", continent='" + continente + '\'' +
            ", trafficHand='" + direcaoDaMao + '\'' +
            '}';
}






}