/**
 * Representa um utente de um ginásio.
 *
 * @author francisgomesx
 */

public class Utente {

    /**
     * O total de utentes.
     */
    private int totalUtentes;

    /**
     * O nome do utente.
     */
    private String nome;

    /**
     * A idade do utente.
     */
    private int idade;

    /**
     * O peso do utente.
     */
    private double peso;

    /**
     * A altura do utente.
     */
    private double altura;

    /**
     * O género do utente.
     */
    private String genero;

    /**
     * O nome do utente por omissão.
     */
    private static final String NOME_POR_OMISSAO = "Sem nome";

    /**
     * A idade do utente por omissão.
     */
    private static final int IDADE_POR_OMISSAO = 0;

    /**
     * O género do utente por omissão.
     */
    private static final String GENERO_POR_OMISSAO = "N/A";

    /**
     * A altura do utente por omissão.
     */
    private static final double ALTURA_POR_OMISSAO = 0.0;

    /**
     * O peso do utente por omissão.
     */
    private static final double PESO_POR_OMISSAO = 0.0;

    /**
     * O limite mínino no imc para ser magro.
     */
    private static final int LIMITE_MAGRO = 18;

    /**
     * O limite máximo no imc para ser saudável.
     */
    private static final int LIMITE_SAUDAVEL = 25;

    /**
     * Constrói uma instância de Ginasio com o utente por omissão.
     */
    public Utente(String nome, double peso, double altura) {
        this.nome = NOME_POR_OMISSAO;
        genero = GENERO_POR_OMISSAO;
        idade = IDADE_POR_OMISSAO;
        this.peso = PESO_POR_OMISSAO;
        this.altura = ALTURA_POR_OMISSAO;
        totalUtentes++;
    }

    /**
     * Constrói uma instância de Ginasio recebendo o nome e idade.
     *
     * @param nome
     * @param idade
     */
    public Utente(String nome, int idade) {
        this(nome, GENERO_POR_OMISSAO, idade, PESO_POR_OMISSAO, ALTURA_POR_OMISSAO);
    }

    /**
     * Constrói uma instância de Ginasio recebendo todos os atributos.
     *
     * @param nome
     * @param genero
     * @param idade
     * @param peso
     * @param altura
     */
    public Utente(String nome, String genero, int idade, double peso, double altura) {
        this.nome = nome;
        this.idade = idade;
        this.altura = altura;
        this.genero = genero;
        this.peso = peso;
        totalUtentes++;
    }

    /**
     * Devolve o nome do utente.
     *
     * @return nome do utente
     */
    public String getNome() {return nome;}

    /**
     * Devolve a idade do utente.
     *
     * @return idade do utente
     */
    public int getIdade() {return idade;}

    /**
     * Devolve o género do utente.
     *
     * @return género do utente.
     */
    public String genero() {return genero;}


    /**
     * Devolve a altura do utente.
     *
     * @return altura do utente
     */
    public double altura() {return altura;}

    /**
     * Devolve o peso do utente.
     *
     * @return peso do utente
     */
    public double peso() {return peso;}

    /**género
     * Modifica o nome do utente.
     *
     * @param nome o nome do utente
     */
    public void setNome(String nome) {this.nome = nome;}

    /**
     * Modifica o género do utente.
     *
     * @param genero o género do utente
     */
    public void setGenero(String genero) {this.genero = genero;}

    /**
     * Modifica a idade do utente.
     *
     * @param idade a idade do utente
     */
    public void setIdade(int idade) {this.idade = idade;}

    /**
     * Modifica a altura do utente.
     *
     * @param altura a altura do utente
     */
    public void setAltura(double altura) {this.altura = altura;}

    /**
     * Modifica o peso do utente.
     *
     * @param peso o peso do utente
     */
    public void setPeso(double peso) {this.peso = peso;}

    /**
     * Devolve a descrição textual do utente no formato: nome, idade, género,
     * altura e peso.
     *
     * @return características do utente
     */
    @Override
    public String toString() {
        return nome + " tem " + idade + " anos " + "e " + " é do género " + genero +
                ", " + "a sua altura é " + altura + " e o seu peso é " + peso;
    }

    /**
     * Devolve a diferença de idades entre o utente u1 e u2 que são
     * recebidos por parâmetros.
     *
     * @param u1 o primeiro utente com o qual se compara a diferença de idades
     * @param u2 o segundo utente com o qual se compara a diferença de idades
     *double peso, double altura, String nome
     * @return diferença em número de anos entre o u1 e u2 recebidos
     * por parâmetro
     */
    public int calcularDiferencaIdade(Utente u1, Utente u2) {
        return Math.abs(u1.getIdade() - u2.getIdade());
    }

    /**
     * Devolve o IMC do utente usando os atributos do próprio objeto
     * que são o peso e altura.
     *
     * @return imc em valor double através da divisão entre o peso
     * e o quadrado da altura que são atributos de determinado utente
     */
    public double calcularIMC() {
        double imc = peso / (altura * altura);

        return Math.round(imc * 10.0) / 10.0;
    }

    /**
     * Devolve o grau de obesidade de um utente com o IMC recebido
     * por cálculo com "calcularIMC()"
     *
     * @return grau de obesidade de um utente com o IMC recebido no cálculo
     * "calcularIMC()"
     */
    public String grauObesidade() {
        double imc = calcularIMC();
        if (imc < LIMITE_MAGRO){
            return "Magro";
        } else if (imc <= LIMITE_SAUDAVEL) {
            return "Saudável";
        } else if (imc > LIMITE_SAUDAVEL) {
            return "Obeso";
        }
        return "";
    }

    /**
     * Devolve true se o grau de obesidade do utente recebido
     * por determinação com "grauObesidade()" for igual a "Saudável".
     * Se o grau de obesidade for diferente, devolve false.
     *
     * @return true se o grau de obesidade for igual a "Saudável",
     * caso contrário devolve false
     */
    public boolean isSaudavel() {
       return grauObesidade().equals("Saudável");
    }

    /**
     * Devolve um inteiro {-1,1,0} para ver quem é o utente
     * mais novo e que corresponde a
     * {mais novo, mais velho, mesma idade}, isto
     * de um utente para outro utente recebido por parâmetro.
     *
     * @param outroUtente o outro utente com a qual se compara a idade
     *
     * @return -1 se este utente for mais novo,
     *          1 se o outro utente for mais novo,
     *          0 se ambos tiverem a mesma idade
     */
    public int maisNovo(Utente outroUtente) {
        if (this.idade < outroUtente.idade) {
            return -1;
        } else if (this.idade > outroUtente.idade) {
            return 1;
        } else
            return 0;
    }

    /**
     * Devolve o total de utentes.
     *
     * @return total de utentes
     */
    public int getTotalUtentes() {return totalUtentes;}
}
