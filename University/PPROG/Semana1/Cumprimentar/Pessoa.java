/**
 * Representar uma pessoa através do nome e idade.
 *
 * @author francisgomesx
 */
public class Pessoa {

    /**
     * O nome da pessoa.
     * A idade da pessoa.
     */
    private String nome;
    private int idade;

    /**
     * Constrói uma instância da pessoa recebendo o nome e idade.
     *
     * @param nome o nome da pessoa.
     * @param idade a idade da pessoa.
     */

    public Pessoa(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }

    /**
     * Constrói uma instância da pessoa sem valores.
     */

    public Pessoa() {

    }

    /**
     * Devolve o nome da pessoa.
     *
     * @return nome da pessoa.
     */

    public String getNome() { return nome; }

    /**
     * Devolve a idade da pessoa.
     *
     * @return idade da pessoa.
     */

    public int getIdade() { return idade; }

    /**
     * Modifica o nome da pessoa
     *
     * @param nome o novo nome da pessoa
     */

    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Modifica a idade da pessoa
     *
     * @param idade a nova idade da pessoa
     */

    public void setIdade(int idade) {
        this.idade = idade;
    }

    /**
     * Devolve uma string legível para utilizar contendo o nome e idade da pessoa.
     *
     * @return características da pessoa
     */
    @Override
    public String toString() { return nome + " tem " + idade + " anos."; }
}