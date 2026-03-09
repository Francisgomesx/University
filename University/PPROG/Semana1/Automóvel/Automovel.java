/**
 * Representa um automóvel.
 *
 * @author francisgomesx
 */

public class Automovel {

    /**
     * O total de automóveis.
     */
    private static int totalAutomoveis;

    /**
     * A matrícula do automóvel.
     */
    private String matricula;

    /**
     * A marca do automóvel.
     */
    private String marca;

    /**
     * A cilindrada do automóvel.
     */
    private int cilindrada;

    /**
     * A matrícula por omissão.
     */
    private static final String MATRICULA_POR_OMISSAO = "sem matrícula";

    /**
     * A marca por omissão.
     */
    private static final String MARCA_POR_OMISSAO = "sem marca";

    /**
     * A cilindrada por omissão.
     */
    private static final int CILINDRADA_POR_OMISSAO = 0;


    public Automovel(String matricula, String marca, int cilindrada) {
        this.matricula = matricula;
        this.marca = marca;
        this.cilindrada = cilindrada;
        totalAutomoveis++;
    }

    /**
     * Devolve a matrícula do automóvel.
     *
     * @return a matrícula do automóvel.
     */
    public String getMatricula() {return matricula;}

    /**
     * Devlve a marca do automóvel.
     *
     * @return a marca do automóvel.
     */
    public String getMarca() {return marca;}

    /**
     * Devolve a cilidrada do automóvel.
     *
     * @return a cilidrada do automóvel.
     */
    public int getCilindrada() {return cilindrada;}


    /**
     * Modifica a matrícula do automóvel.
     *
     * @param matricula a matrícula do automóvel.
     */
    public void setMatricula(String matricula) {this.matricula = matricula;}


    /**
     * Modifica a marca do automóvel.
     *
     * @param marca a marca do automóvel.
     */
    public void setMarca(String marca) {this.marca = marca;}


    /**
     * Modifica a cilindrada do automóvel.
     *
     * @param cilindrada a cilindrada do automóvel.
     */
    public void setCilindrada(int cilindrada) {this.cilindrada = cilindrada;}


    /**
     * Devolve a descrição textual do automóvel no formato:
     * matrícula, marca e cilindrada.
     *
     * @return características do automóvel.
     */
    @Override
    public String toString() {
        return "Automóvel com matrícula " + matricula + " é um " + marca + " e tem cilindrada de " + cilindrada + " cc.";
    }

    /**
     * Devolve a diferença de cilindrada entre o automóvel a1 e a2 que são
     * recebidos por parâmetros.
     *
     * @param a1 o primeiro automóvel com a qual se compara a diferença de cilindrada.
     * @param a2 o segundo automóvel com a qual se compara a diferençca de cilindrada.
     *
     * @return diferença de cilindrada em número inteiro entre o a1 e a2 recebidos
     * parâmetro.
     */
    public int diferencaCilindrada(Automovel a1, Automovel a2) {return Math.abs(a1.getCilindrada() - a2.getCilindrada());}


    /**
     * Devolve true se a cilindrada do a1 for maior que a do a2
     * ou false se a cilindrada do a1 for menor que a do a2.
     *
     * @param a1 o primeiro automóvel com a qual se compara a superioridade de cilindrada.
     * @param a2 o segundo automóvel com a qual se compara a superioridade de cilindrada.
     *
     * @return true se a cilindrada do a1 for maior do que a do a2,
     * caso contrário devolve falso.
     */
    public boolean cilindradaSuperior(Automovel a1, Automovel a2) {
        if (a1.getCilindrada() > a2.getCilindrada()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Devolve true se a cilindrada do a1 for maior do que a do valor
     * dado por parâmetro.
     *
     * @param a1 o automóvel com a qual se compara a superioridade de cilindrada.
     * @param valor o valor com a qual se compara a superioridade de cilindrada.
     *
     * @return true se o a1 for maior do que o valor dado,
     * caso contrário devolve falso.
     */
    public boolean cilindradaSuperiorAValor(Automovel a1, int valor) {
        if (a1.getCilindrada() > valor) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Devolve o total de automóveis.
     *
     * @return o total de automóveis.
     */
    public static int getTotalAutomoveis() {return totalAutomoveis;}
}
