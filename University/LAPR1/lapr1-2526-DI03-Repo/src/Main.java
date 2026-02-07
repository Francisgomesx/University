import java.io.*;
import java.util.Scanner;

import org.apache.commons.math3.linear.RealMatrix;            //Biblioteca Apash Commons
import org.apache.commons.math3.linear.MatrixUtils;           //Biblioteca Apash Commons
import org.apache.commons.math3.linear.LUDecomposition;       //Biblioteca Apash Commons
import org.apache.commons.math3.linear.EigenDecomposition;    //Biblioteca Apash Commons


public class Main {
    static Scanner input = new Scanner(System.in);
    static final int ZC = 4;
    static final int VIZINHOS = -1;
    static final int LIMITE_DIMENSAO_MAXIMO = 1000;
    static final int LIMITE_DIMENSAO_MINIMO = 1;


    public static void main(String[] args) throws IOException {
        if (args != null && args.length > 0) {
            naoInterativo.runNonInteractive(args);
            return;
        }
        Scanner input = new Scanner(System.in);
        int opcao;
        boolean voltarMenu = true;

        System.out.println("Made by: Bitmasters");
        System.out.println();

        do {

            System.out.println("""
                       ======================================
                       ███╗   ███╗███████╗███╗   ██╗██╗   ██╗
                       ████╗ ████║██╔════╝████╗  ██║██║   ██║
                       ██╔████╔██║█████╗  ██╔██╗ ██║██║   ██║
                       ██║╚██╔╝██║██╔══╝  ██║╚██╗██║██║   ██║
                       ██║ ╚═╝ ██║███████╗██║ ╚████║╚██████╔╝
                       ╚═╝     ╚═╝╚══════╝╚═╝  ╚═══╝ ╚═════╝
                       ======================================
                    """);

            System.out.println("1. Carregar matriz e imprimi-la");
            System.out.println("2. Verificar estabilidade e estabilizar");
            System.out.println("3. Adicionar tarefas (A ⊕ B)");
            System.out.println("4. Testar matriz recorrente");
            System.out.println("5. Verificar elemento neutro");
            System.out.println("6. Nº recorrentes (sem Laplaciano)");
            System.out.println("7. Nº recorrentes (com Laplaciano reduzido)");
            System.out.println("8. Inversa estabilizada");
            System.out.println("9. Autovalores e autovetores");
            System.out.println("10. Autovalores e autovetores (com Fórmula Fechada)");
            System.out.println("0. Sair");
            System.out.println();

            System.out.print("Escolha uma opção: ");

            if (!input.hasNextInt()) {
                input.nextLine();
                System.out.println("Opção inválida! Introduza um número entre 0 e 10.");
                continue;
            }

            opcao = input.nextInt();

            switch (opcao) {
                case 1 -> carregarFicheiroEImprimir();
                case 2 -> estabilizarEEscrever();
                case 3 -> adicionarTarefas();
                case 4 -> burningDhar();
                case 5 -> elementoNeutro();
                case 6 -> contarRecorrentesSemLapl();
                case 7 -> contarRecorrentesComLapl();
                case 8 -> inversaEstabilizada();
                case 9 -> autovaloresLaplaciana();
                case 10 -> autovaloresEAutovetoresFormulaFechada();

                case 0 -> {
                    System.out.println("A sair...");
                    voltarMenu = false;
                    continue;
                }

                default -> {
                    System.out.println("Opção inválida! Introduza um número entre 0 e 10.");
                    continue;
                }
            }

            input.nextLine();
            System.out.print("\nPretende voltar ao menu? [S/n]: ");
            String resposta = input.nextLine().trim();

            //noinspection StatementWithEmptyBody
            if (resposta.isEmpty()
                    || resposta.equalsIgnoreCase("s")
                    || resposta.equalsIgnoreCase("sim")
                    || resposta.equals("Sim")
                    || resposta.equalsIgnoreCase("S")) {
            } else {
                voltarMenu = false;
                System.out.println("\nPrograma terminado.");
            }


        } while (voltarMenu);

        input.close();
    }


    //======================== R E U T I L I Z Á V E I S ========================//


    //======================================================= Auxiliares para Snapshots (Funcionalidade 3)
    /**
     * copia uma matriz de inteiros para uma nova matriz
     *
     * @param m matriz original
     * @return cópia da matriz
     */
    public static int[][] copiarMatriz(int[][] m) {
        int[][] c = new int[m.length][m[0].length];
        for (int i = 0; i < m.length; i++) {
            System.arraycopy(m[i], 0, c[i], 0, m[0].length);
        }
        return c;
    }

    /**
     * estabiliza uma matriz guardando snapshots intermédios
     *
     * @param matriz matriz inicial
     * @return array com snapshots da estabilização
     */
    public static int[][][] estabilizarComSnapshots(int[][] matriz) {
        int[][] temp = copiarMatriz(matriz);
        int total = 1;

        while (estabilizarUmSweep(temp)) {
            total++;
        }

        int[][][] snaps = new int[total][][];
        snaps[0] = copiarMatriz(matriz);

        int idx = 1;
        while (estabilizarUmSweep(matriz)) {
            snaps[idx] = copiarMatriz(matriz);
            idx++;
        }

        return snaps;
    }

    /**
     * Executa UM SWEEP de estabilização (testa todos os vértices UMA VEZ)
     *
     * @param matriz a ser estabilizada (MODIFICADA IN-PLACE)
     * @return true se houve pelo menos um toppling, false caso contrário
     */

    public static boolean estabilizarUmSweep(int[][] matriz) {
        int dimensao = matriz.length;
        boolean houveColapso = false;

        for (int linha = 0; linha < dimensao; linha++) {
            for (int coluna = 0; coluna < dimensao; coluna++) {
                if (matriz[linha][coluna] >= ZC) {
                    matriz[linha][coluna] -= ZC;
                    if (linha > 0) {
                        matriz[linha - 1][coluna]++;
                    }
                    if (linha < dimensao - 1) {
                        matriz[linha + 1][coluna]++;
                    }
                    if (coluna > 0) {
                        matriz[linha][coluna - 1]++;
                    }
                    if (coluna < dimensao - 1) {
                        matriz[linha][coluna + 1]++;
                    }
                    houveColapso = true;
                }
            }
        }

        return houveColapso;
    }


    /**
     * seleciona até 20 snapshots igualmente espaçados
     *
     * @param snapshots array de snapshots
     * @return snapshots selecionados
     */
    public static int[][][] selecionarAte20Equiespacados(int[][][] snapshots) {
        int total = snapshots.length;
        int max = 20;

        if (total <= max) return snapshots;

        int[][][] out = new int[max][][];
        int step = total / max;

        int idx = 0;
        for (int i = 0; i < total && idx < max; i += step) {
            out[idx] = snapshots[i];
            idx++;
        }

        out[max - 1] = snapshots[total - 1];
        return out;
    }

    /**
     * normaliza as colunas de uma matriz para o intervalo de [-1,1]
     *
     * @param M matriz de doubles
     */
    public static void normalizarColunasParaMenos1Mais1(double[][] M) {
        if (M == null || M.length == 0 || M[0].length == 0) return;

        int linhas = M.length;
        int colunas = M[0].length;

        for (int c = 0; c < colunas; c++) {
            double maxAbs = 0.0;

            for (int r = 0; r < linhas; r++) {
                double a = Math.abs(M[r][c]);
                if (a > maxAbs) maxAbs = a;
            }

            if (maxAbs == 0.0) continue;

            for (int r = 0; r < linhas; r++) {
                M[r][c] /= maxAbs;
            }
        }
    }

    public static int[][] somarMatrizes(int[][] A, int[][] B) {
        int dimensao = A.length;
        int[][] soma = new int[dimensao][dimensao];

        for (int i = 0; i < dimensao; i++) {
            for (int j = 0; j < dimensao; j++) {
                soma[i][j] = A[i][j] + B[i][j];
            }
        }

        return soma;
    }


    //===============================================================Imprimir Matriz
    /**
     * imprime uma matriz de inteiros no terminal
     *
     * @param matriz matriz a imprimir
     */
    public static void imprimirMatriz(int[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.printf("%4d ", matriz[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    //==================================================CarregarFicheiroCSV

    /**
     * Lê uma matriz de inteiros a partir de um ficheiro CSV.
     * Valida automaticamente:
     * - Existência do ficheiro
     * - Matriz quadrada (n x n)
     * - Valores válidos (apenas dígitos positivos)
     * USA TRY-WITH-RESOURCES para evitar resource leaks.
     **/

    public static int[][] carregarFicheiroCSV() {
        while (true) {
            try {
                System.out.print("Insira o nome do ficheiro CSV com a matriz: ");
                String nomeFicheiro = input.nextLine();
                String caminho = "Input/" + nomeFicheiro + ".csv";

                int numLinhas;
                try (Scanner leitor = new Scanner(new File(caminho))) {
                    System.out.println("\nO ficheiro existe e vai ser validado...");
                    numLinhas = 0;
                    while (leitor.hasNextLine()) {
                        leitor.nextLine();
                        numLinhas++;
                    }
                }

                if (numLinhas <= 0) {
                    System.out.println("Erro: ficheiro vazio. Tente outro ficheiro.\n");
                    continue;
                }

                try (Scanner leitor = new Scanner(new File(caminho))) {

                    String primeiraLinha = leitor.nextLine();
                    String[] tokensPrimeira = primeiraLinha.split(",");
                    int numColunas = tokensPrimeira.length;

                    if (numColunas == 0 || numColunas != numLinhas) {
                        System.out.println("Erro: matriz não é quadrada n x n. Tente outro ficheiro.\n");
                        continue;
                    }

                    int[][] matriz = new int[numLinhas][numColunas];
                    boolean matrizValida = true;

                    for (int j = 0; j < numColunas && matrizValida; j++) {
                        String token = tokensPrimeira[j].trim();
                        if (!tokenValido(token)) {
                            System.out.println("Erro na linha 1, coluna " + (j + 1) +
                                    ". Tente outro ficheiro.\n");
                            matrizValida = false;
                        } else {
                            matriz[0][j] = converterParaInt(token);
                        }
                    }

                    if (!matrizValida) {
                        continue;
                    }

                    int linhaAtual = 1;

                    while (leitor.hasNextLine() && linhaAtual < numLinhas && matrizValida) {
                        String linha = leitor.nextLine();
                        String[] tokens = linha.split(",");

                        if (tokens.length != numColunas) {
                            System.out.println("Erro: linha " + (linhaAtual + 1) +
                                    " tem número de colunas diferente. Tente outro ficheiro.\n");
                            matrizValida = false;
                            continue;
                        }

                        for (int j = 0; j < numColunas && matrizValida; j++) {
                            String token = tokens[j].trim();
                            if (!tokenValido(token)) {
                                System.out.println("Erro na linha " + (linhaAtual + 1) +
                                        ", coluna " + (j + 1) + ". Tente outro ficheiro.\n");
                                matrizValida = false;
                            } else {
                                matriz[linhaAtual][j] = converterParaInt(token);
                            }
                        }

                        linhaAtual++;
                    }

                    if (!matrizValida) {
                        continue;
                    }

                    if (linhaAtual != numLinhas) {
                        System.out.println("Erro: número de linhas incoerente. Tente outro ficheiro.\n");
                        continue;
                    }

                    System.out.println("Matriz válida!\n");
                    return matriz;

                }

            } catch (FileNotFoundException e) {
                System.out.println("Erro: ficheiro não encontrado. Tente outro nome.\n");
            }
        }
    }

    /**
     * verifica se um token do CSV é válido (funciona apenas com dígitos)
     *
     * @param token string a validar
     * @return true se for válido
     */
    public static boolean tokenValido(String token) {
        if (token.isEmpty()) {
            return false;
        }
        for (int k = 0; k < token.length(); k++) {
            char c = token.charAt(k);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    /**
     * converte uma string para um inteiro
     *
     * @param token string numérica
     * @return valor inteiro
     */
    public static int converterParaInt(String token) {
        int valor = 0;
        for (int k = 0; k < token.length(); k++) {
            int digito = token.charAt(k) - '0';
            valor = valor * 10 + digito;
        }
        return valor;
    }

    //==================================================Estabilização de Matrizes
    /**
     * estabiliza uma matriz com o toppling
     *
     * @param M matriz a estabilizar
     * @return matriz estabilizada
     */
    public static int[][] estabilizar(int[][] M) {
        int n = M.length;

        boolean houveColapso = true;
        while (houveColapso) {
            houveColapso = false;
            for (int linha = 0; linha < n; linha++) {
                for (int coluna = 0; coluna < n; coluna++) {
                    while (M[linha][coluna] >= ZC) {
                        M[linha][coluna] -= ZC;
                        if (linha > 0) M[linha - 1][coluna]++;
                        if (linha < n - 1) M[linha + 1][coluna]++;
                        if (coluna > 0) M[linha][coluna - 1]++;
                        if (coluna < n - 1) M[linha][coluna + 1]++;
                        houveColapso = true;
                    }
                }
            }
        }
        return M;
    }

    //==================================================Gerar Laplaciana
    /**
     * gera a matriz Laplaciana de uma grelha (n x n)
     *
     * @param dimensao dimensão da grelha
     * @return matriz Laplaciana
     */
    public static int[][] gerarLaplaciana(int dimensao) {

        int tamanho = dimensao * dimensao;
        int[][] laplaciana = new int[tamanho][tamanho];

        for (int linha = 0; linha < dimensao; linha++) {
            for (int coluna = 0; coluna < dimensao; coluna++) {
                int posicao = linha * dimensao + coluna;

                laplaciana[posicao][posicao] = ZC;

                if (linha > 0) laplaciana[posicao][(linha - 1) * dimensao + coluna] = VIZINHOS;
                if (linha < dimensao - 1) laplaciana[posicao][(linha + 1) * dimensao + coluna] = VIZINHOS;
                if (coluna > 0) laplaciana[posicao][linha * dimensao + (coluna - 1)] = VIZINHOS;
                if (coluna < dimensao - 1) laplaciana[posicao][linha * dimensao + (coluna + 1)] = VIZINHOS;
            }
        }
        return laplaciana;
    }


    //==================================================Verificar a Dimensão de uma Matriz
    /**
     * verifica se a dimensão da matriz é válida
     *
     * @param dimensao valor da dimensão
     * @return true se for válida
     */
    public static boolean verificarDimensao(int dimensao) {
        return LIMITE_DIMENSAO_MAXIMO > dimensao && dimensao > LIMITE_DIMENSAO_MINIMO;
    }


    //==================================================Ler Dimensao de uma Matriz
    /**
     * lê a dimensão da matriz inserida pelo utilizador
     *
     * @return dimensão válida
     */
    public static int lerDimensao() {
        int dimensao = 0;
        do {
            System.out.println("Introduza a dimensão da matriz (n): ");

            if (input.hasNextInt()) {
                dimensao = input.nextInt();

                if (!verificarDimensao(dimensao)) {
                    System.out.println("Dimensão inválida. Tente novamente.");
                }
            } else {
                System.out.println("valor inválido");
                input.next(); // descarta a entrada inválida
            }

        } while (!verificarDimensao(dimensao));

        return dimensao;
    }


    // ===============================================================================Algoritmo de Burning de Dhar
    /**
     * aplica o algoritmo de Burning de Dhar para testar a recorrência da matriz
     *
     * @param matriz matriz estável
     * @return true se for recorrente
     */
    public static boolean algoritmoBurningDhar(int[][] matriz) {
        int dimensao = matriz.length;
        boolean[][] queimados = new boolean[dimensao][dimensao];
        boolean queimaDeCelula;

        do {
            queimaDeCelula = false;
            for (int i = 0; i < dimensao; i++) {
                for (int j = 0; j < dimensao; j++) {
                    if (!queimados[i][j]) {
                        int vizinhosNaoQueimados = calcularVizinhosNaoQueimados(queimados, i, j);
                        if (matriz[i][j] >= vizinhosNaoQueimados) {
                            queimados[i][j] = true;
                            queimaDeCelula = true;
                        }
                    }
                }
            }
        } while (queimaDeCelula);

        for (int i = 0; i < dimensao; i++) {
            for (int j = 0; j < dimensao; j++) {
                if (!queimados[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    // ===============================================================================Cálculo de Vizinhos Não Queimados
    /**
     * calcula o número de vizinhos não queimados
     *
     * @param queimados matriz de células queimadas
     * @param linha posição da linha
     * @param coluna posição da coluna
     * @return número de vizinhos não queimados
     */
    public static int calcularVizinhosNaoQueimados(boolean[][] queimados, int linha, int coluna) {
        int contador = 0;
        int dimensao = queimados.length;

        if (linha > 0 && !queimados[linha - 1][coluna]) {
            contador++;
        }
        if (linha < dimensao - 1 && !queimados[linha + 1][coluna]) {
            contador++;
        }
        if (coluna > 0 && !queimados[linha][coluna - 1]) {
            contador++;
        }
        if (coluna < dimensao - 1 && !queimados[linha][coluna + 1]) {
            contador++;
        }

        return contador;
    }

    //==================================================Verificar se é Elemento Neutro
    /**
     * verifica se a matriz é elemento neutro
     *
     * @param matrizNeutra matriz candidata
     * @return true se for elemento neutro
     */
    public static boolean verificarElementoNeutro(int[][] matrizNeutra) {

        if (!algoritmoBurningDhar(matrizNeutra)) {
            return false;
        }

        int dimensao = matrizNeutra.length;
        int total = verificarNumeroDeMatrizesEstaveis(dimensao);

        for (int ordem = 0; ordem < total; ordem++) {
            int[][] matrizA = gerarMatrizEstavel(ordem, dimensao);

            if (algoritmoBurningDhar(matrizA)) {

                int[][] neutra_A = somarEstabilizacao(matrizNeutra, matrizA);
                int[][] A_neutra = somarEstabilizacao(matrizA, matrizNeutra);

                if (!matrizesIguais(neutra_A, matrizA) || !matrizesIguais(A_neutra, matrizA)) {
                    return false;
                }
            }
        }
        return true;
    }


    //==================================================Verificar se a Matriz é Quadrada
    /**
     * verifica se a matriz é quadrada
     *
     * @param matriz matriz a verificar
     * @return true se for quadrada
     */
    public static boolean verficarMatrizQuadrada(int[][] matriz) {
        int linhas = matriz.length;
        for (int i = 0; i < linhas; i++) {
            if (matriz[i].length != linhas) {
                return false;
            }
        }
        return true;
    }

    //==================================================Verificar a Matriz Quadrada de forma completa
    /**
     * lê e valida uma matriz quadrada de um ficheiro
     * pede ao utilizador um novo ficheiro se a matriz não for quadrada
     *
     * @return matriz válida
     */
    public static int[][] verficarMatrizQuadradaDoFicheiro() {
        int[][] matriz;
        boolean matrizValida;

        do {
            matriz = carregarFicheiroCSV();

            if (!verficarMatrizQuadrada(matriz)) {
                System.out.println("A matriz não é quadrada. Ficheiro inválido.");
                matrizValida = false;
            } else {
                matrizValida = true;
            }

        } while (!matrizValida);

        return matriz;
    }

    //==================================================Verificar se a Matriz é Estável
    /**
     * verifica se uma matriz é estável
     *
     * @param matriz matriz a verificar
     * @return true se for estável
     */
    public static boolean verificarEstabilidade(int[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (matriz[i][j] >= ZC) {
                    return false;
                }
            }
        }
        return true;
    }

    //==================================================Verificação completa para elemento neutro
    /**
     * compara duas matrizes para ver se são iguais
     *
     * @param matrizA primeira matriz
     * @param matrizB segunda matriz
     * @return true se forem iguais
     */
    public static boolean matrizesIguais(int[][] matrizA, int[][] matrizB) {

        for (int i = 0; i < matrizA.length; i++) {
            for (int j = 0; j < matrizA.length; j++) {
                if (matrizA[i][j] != matrizB[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    //==================================================Soma de matrizes estabilizadas
    /**
     * soma duas matrizes e estabilização do resultado
     *
     * @param matrizA primeira matriz
     * @param matrizB segunda matriz
     * @return matriz estabilizada
     */
    public static int[][] somarEstabilizacao(int[][] matrizA, int[][] matrizB) {
        int n = matrizA.length;
        int[][] soma = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                soma[i][j] = matrizA[i][j] + matrizB[i][j];
            }
        }

        return estabilizar(soma);
    }


    //======================== F U N C I O N A L I D A D E S ============================//


    //========================Funcionalidade 1============================//
    /**
     * carrega uma matriz de um ficheiro e imprime-a
     *
     */
    public static void carregarFicheiroEImprimir() {
        boolean continuar = true;

        while (continuar) {
            int[][] matriz = carregarFicheiroCSV();
            System.out.println("=== A matriz carregada ===\n");
            imprimirMatriz(matriz);

            System.out.println("\n1. Carregar outra matriz");
            System.out.println("2. Voltar ao menu principal");
            System.out.print("Escolha: ");

            int opcao = -1;
            if (input.hasNextInt()) {
                opcao = input.nextInt();
                input.nextLine();
            } else {
                input.nextLine();
            }

            if (opcao == 2) {
                continuar = false;
            }

            System.out.println();
        }
    }

    //========================Funcionalidade 2============================//
    /**
     * estabiliza uma matriz e escreve o resultado num ficheiro
     *
     */
    public static void estabilizarEEscrever() {
        int[][] matriz = carregarFicheiroCSV();

        System.out.println("A matriz carregada é:\n");
        imprimirMatriz(matriz);

        if (verificarEstabilidade(matriz)) {
            System.out.println("A matriz já está estável.");
        } else {
            System.out.println("A matriz não está estável. A estabilizar...");
            estabilizar(matriz);
            System.out.println("Matriz estabilizada.");
            imprimirMatriz(matriz);
        }

        String caminhoOut = "Output/matrizEstabilizada.csv";
        escreverMatrizCSV(matriz, caminhoOut);
    }
    /**
     * escreve a matriz de inteiros num ficheiro CSV
     *
     * @param matriz matriz a escrever no ficheiro
     * @param caminho caminho do ficheiro CSV de saída
     */
    public static void escreverMatrizCSV(int[][] matriz, String caminho) {
        try (FileWriter fw = new FileWriter(caminho)) {
            for (int i = 0; i < matriz.length; i++) {
                for (int j = 0; j < matriz[i].length; j++) {
                    fw.write(String.valueOf(matriz[i][j]));
                    if (j < matriz[i].length - 1) {
                        fw.write(",");
                    }
                }
                fw.write("\n");
            }
            System.out.println("Matriz escrita em: " + caminho);
        } catch (IOException e) {
            System.out.println("Erro ao escrever ficheiro: " + e.getMessage());
        }
    }

    //========================Funcionalidade 3============================//
    /**
     * soma duas matrizes (A ⊕ B) com snapshots intermédios
     */
    public static void adicionarTarefas() {
        int[][] A = null;
        boolean matrizAValida = false;

        while (!matrizAValida) {
            System.out.println("\nMatriz A");
            A = carregarFicheiroCSV();

            if (!verficarMatrizQuadrada(A)) {
                System.out.println("Erro: A matriz A não é quadrada (n x n). Tente novamente.");
            } else {
                matrizAValida = true;;
            }
        }

        int[][] B = null;
        boolean matrizBValida = false;

        while (!matrizBValida) {
            System.out.println("\nMatriz B");
            B = carregarFicheiroCSV();

            if (!verficarMatrizQuadrada(B)) {
                System.out.println("Erro: A matriz B não é quadrada (n x n). Tente novamente.");
            } else {
                matrizBValida = true;
            }
        }

        while (A.length != B.length) {
            System.out.println("\nErro: As matrizes A e B têm dimensões diferentes!");
            System.out.println("  - Dimensão de A: " + A.length + "x" + A.length);
            System.out.println("  - Dimensão de B: " + B.length + "x" + B.length);
            System.out.println("Não é possível somar matrizes com dimensões diferentes.\n");

            System.out.println("Qual matriz deseja modificar?");
            System.out.println("1. Modificar matriz A");
            System.out.println("2. Modificar matriz B");
            System.out.println("0. Cancelar operação");
            System.out.print("Escolha uma opção: ");

            if (!input.hasNextInt()) {
                input.next();
                System.out.println("Opção inválida! Tente novamente.");
                continue;
            }

            int opcao = input.nextInt();
            input.nextLine();

            switch (opcao) {
                case 1 -> {
                    matrizAValida = false;
                    while (!matrizAValida) {
                        System.out.println("\nMatriz A");
                        A = carregarFicheiroCSV();

                        if (!verficarMatrizQuadrada(A)) {
                            System.out.println("Erro: A matriz A não é quadrada (n x n). Tente novamente.");
                        } else {
                            matrizAValida = true;
                            System.out.println("✓ Nova matriz A é quadrada!");
                        }
                    }
                }
                case 2 -> {
                    matrizBValida = false;
                    while (!matrizBValida) {
                        System.out.println("\nMatriz B");
                        B = carregarFicheiroCSV();

                        if (!verficarMatrizQuadrada(B)) {
                            System.out.println("Erro: A matriz B não é quadrada (n x n). Tente novamente.");
                        } else {
                            matrizBValida = true;
                            System.out.println("✓ Nova matriz B é quadrada!");
                        }
                    }
                }
                case 0 -> {
                    System.out.println("Operação cancelada pelo utilizador.");
                    return;
                }
                default -> System.out.println("Opção inválida! Escolha 1, 2 ou 0.");
            }
        }

        System.out.println("\n ✓ Matrizes A e B têm dimensões compatíveis (" + A.length + "x" + A.length + ")!\n");

        System.out.println("Matriz A\n");
        imprimirMatriz(A);

        System.out.println("Matriz B\n");
        imprimirMatriz(B);

        System.out.println("\n" + "=".repeat(50));
        System.out.println("Matriz A selecionada):");
        imprimirMatriz(A);

        System.out.println("Matriz B selecionada):");
        imprimirMatriz(B);

        int[][] soma = somarMatrizes(A, B);

        System.out.println("Matriz A + B (antes de estabilizar):");
        imprimirMatriz(soma);

        System.out.println("\n A estabilizar soma...");
        int[][][] snapshots = estabilizarComSnapshots(soma);

        int[][] resultadoFinal = snapshots[snapshots.length - 1];
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Resultado final A ⊕ B = (A + B)°:");
        imprimirMatriz(resultadoFinal);

        int[][][] selecionados = selecionarAte20Equiespacados(snapshots);

        System.out.println("\nTotal de snapshots gerados: " + snapshots.length);
        System.out.println("Snapshots selecionados (máx 20): " + selecionados.length);

        HeatmapImageWriter writer = new HeatmapImageWriter();

        for (int i = 0; i < selecionados.length; i++) {
            String nomeFicheiro = String.format("Output/snapshot_%02d.jpg", i);
            try {
                writer.writeArrayAsImage(selecionados[i], nomeFicheiro);
                System.out.println("  ✓ Snapshot " + i + " escrito: " + nomeFicheiro);
            } catch (IOException e) {
                System.out.println("  ✗ Erro ao escrever " + nomeFicheiro + ": " + e.getMessage());
            }
        }
    }

    //========================Funcionalidade 4============================//
    /**
     * testa se uma matriz é recorrente com o algortitmo de Burning de Dhar
     *
     */
    public static void burningDhar() {
        int[][] matriz = verficarMatrizQuadradaDoFicheiro();

        System.out.println("Matriz lida do ficheiro:");
        imprimirMatriz(matriz);
        System.out.println();

        if (!verificarEstabilidade(matriz)) {
            System.out.println("A matriz não é estável.");
            return;
        }

        boolean recorrente = algoritmoBurningDhar(matriz);

        if (recorrente) {
            System.out.println("Pelo Algoritmo de Burning de Dhar a matriz é recorrente.");
        } else {
            System.out.println("Pelo Algoritmo de Burning de Dhar a matriz não é recorrente.");
        }
    }
    //========================Funcionalidade 5============================//
    /**
     * verifica se uma matriz é elemento neutro
     *
     */
    public static void elementoNeutro() {
        int[][] matrizNeutra = verficarMatrizQuadradaDoFicheiro();

        System.out.println("Matriz lida do ficheiro:");
        imprimirMatriz(matrizNeutra);
        System.out.println();

        if (!verificarEstabilidade(matrizNeutra)) {
            System.out.println("A matriz não é estável.");
            return;
        }

        if (!algoritmoBurningDhar(matrizNeutra)) {
            System.out.println("A matriz não é recorrente, logo não pode ser elemento neutro");
            return;
        }

        if (verificarElementoNeutro(matrizNeutra)) {
            System.out.println("A matriz inserida é elemento neutro.");
        } else {
            System.out.println("A matriz inserida não é elemento neutro.");
        }
    }
    //========================Funcionalidade 6============================//
    /**
     * faz a contagem de matrizes recorrentes sem usar a Laplaciana
     */
    public static void contarRecorrentesSemLapl() {
        int dimensao;
        int numeroDeMatrizesEstaveis;
        do {
            dimensao = lerDimensao();
            numeroDeMatrizesEstaveis = verificarNumeroDeMatrizesEstaveis(dimensao); // verifica o OverFlow
        } while (numeroDeMatrizesEstaveis == -1);

        long start = System.currentTimeMillis();

        int contadorDeRecorrentes = 0;
        for (int ordem = 0; ordem < numeroDeMatrizesEstaveis; ordem++) {
            if (algoritmoBurningDhar(gerarMatrizEstavel(ordem, dimensao))) contadorDeRecorrentes++;
        }

        long end = System.currentTimeMillis();
        long elapsed = end - start;

        System.out.println("Número de matrizes Recorrentes com dimensão " + dimensao + " : " + contadorDeRecorrentes);
        System.out.println("\nTempo de execução (Nº recorrentes sem Laplaciano): " + elapsed + " ms");
    }

    /**
     * verifica se o número de matrizes estáveis é computável
     *
     * @param dimensao dimensão da matriz
     * @return número de matrizes estáveis
     */
    public static int calcularTotalDeMatrizesEstaveis(int dimensao) {
        long total = (long) Math.pow(ZC, dimensao * dimensao);

        if (total > Integer.MAX_VALUE) {
            return -1;
        }

        return (int) total;
    }

    /**
     * calcula o número total de matrizes estáveis para uma dada dimensão
     *
     * @param dimensao dimensão da matriz
     * @return número total de matrizes estáveis ou -1 em caso de overflow
     */
    public static int verificarNumeroDeMatrizesEstaveis(int dimensao) {

        if (calcularTotalDeMatrizesEstaveis(dimensao) == -1) {
            System.out.println("Número de matrizes recorrentes ultrapassa os limites da computação.");
        }
        return calcularTotalDeMatrizesEstaveis(dimensao);
    }

    /**
     * gera uma matriz estável a partir de uma ordem
     *
     * @param ordem índice da matriz
     * @param dimensao dimensão da matriz
     * @return matriz estável
     */
    public static int[][] gerarMatrizEstavel(int ordem, int dimensao) {
        int[][] matriz = new int[dimensao][dimensao];

        for (int i = 0; i < dimensao; i++) {
            for (int j = 0; j < dimensao; j++) {
                matriz[i][j] = ordem % 4;
                ordem /= 4;
            }
        }
        return matriz;
    }


    //========================Funcionalidade 7============================//
    /**
     * faz a contagem de matrizes recorrentes usando Laplaciana reduzida
     */
    public static void contarRecorrentesComLapl() {
        int dimensao = lerDimensao();
        gerarLaplaciana(dimensao);
        System.out.println("Matriz Laplaciana Reduzida:");
        imprimirMatriz(gerarLaplaciana(dimensao));

        long start = System.currentTimeMillis();

        double[][] doubleLaplaciana = intLaplacianaParaDoubleLaplaciana(gerarLaplaciana(dimensao));
        double determinante = calcularDeterminante(doubleLaplaciana);

        long end = System.currentTimeMillis();
        long elapsed = end - start;

        System.out.printf("Número de matrizes recorrentes: %.0f", determinante);
        System.out.println("\nTempo de execução (Nº recorrentes com Laplaciano reduzido): " + elapsed + " ms");
    }

    /**
     * converte a matriz Laplaciana de inteiros para double
     *
     * @param matriz matriz Laplaciana em inteiros
     * @return matriz Laplaciana em double
     */
    public static double[][] intLaplacianaParaDoubleLaplaciana(int[][] matriz) {
        int linhas = matriz.length;
        int colunas = matriz[0].length;
        double[][] laplacianaDouble = new double[linhas][colunas];
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                laplacianaDouble[i][j] = matriz[i][j];
            }
        }
        return laplacianaDouble;
    }

    /**
     * calcula o determinante da matriz
     *
     * @param laplacianaDouble matriz Laplaciana
     * @return determinante
     */
    public static double calcularDeterminante(double[][] laplacianaDouble) {
        RealMatrix matriz = MatrixUtils.createRealMatrix(laplacianaDouble);
        LUDecomposition lu = new LUDecomposition(matriz);
        return lu.getDeterminant();
    }

    //========================Funcionalidade 8============================//
    /**
     * calcula a inversa estabilizada da matriz
     *
     */
    public static void inversaEstabilizada() {
        System.out.println("Introduza a matriz A (CSV):");
        int[][] A = carregarFicheiroCSV();

        System.out.println("Introduza a matriz E (elemento neutro) (CSV):");
        int[][] E = carregarFicheiroCSV();

        if (!verficarMatrizQuadrada(A) || !verficarMatrizQuadrada(E)) {
            System.out.println("Erro: A e/ou E não são matrizes quadradas.");
            return;
        }

        if (A.length != E.length) {
            System.out.println("Erro: A e E têm dimensões diferentes.");
            return;
        }

        if (!verificarElementoNeutro(E)) {
            System.out.println("Erro: E não é elemento neutro.");
            return;
        } else {
            System.out.println("E é elemento neutro.");
        }

        if (!verificarEstabilidade(A)) {
            System.out.println("Erro: A não é estável. Quer estabilizar?");
            if (confirmarSimNao()) {
                estabilizar(A);
                if (verificarEstabilidade(A)) {
                    System.out.println("\nMatriz A estabilizada.");
                    imprimirMatriz(A);
                    return;
                }
            } else {
                System.out.println("\nOperação cancelada pelo utilizador.");
                return;
            }
        } else {
            System.out.println("A é estável.");
        }

        if (!algoritmoBurningDhar(A)) {
            System.out.println("Erro: a matriz A não é recorrente.");
            return;
        }
        if (!algoritmoBurningDhar(E)) {
            System.out.println("Erro: a matriz E não é recorrente (logo não pode ser elemento neutro em R).");
            return;
        }

        int dimensao = A.length;
        int total = verificarNumeroDeMatrizesEstaveis(dimensao);
        if (total == -1) {
            System.out.println("\nDimensão demasiado grande para pesquisa exaustiva da inversa estabilizada.");
            return;
        }

        for (int ordem = 0; ordem < total; ordem++) {
            int[][] B = gerarMatrizEstavel(ordem, dimensao);

            if (!algoritmoBurningDhar(B)) continue;

            int[][] somaEstabilizada = somarEstabilizacao(A, B);
            if (matrizesIguais(somaEstabilizada, E)) {
                System.out.println("\nA inversa estabilizada de A (relativamente a ⊕) é:");
                imprimirMatriz(B);
                return;
            }
        }

        System.out.println("\nNão foi encontrada inversa estabilizada para A (no universo pesquisado).");
    }

    /**
     * pede confirmação ao utilizador para passar a estabilzação da matriz
     *
     * @return true se o utilizador confirmar
     */
    public static boolean confirmarSimNao() {
        String resposta;
        do {
            System.out.print("Confirma? (S/n): ");
            resposta = input.nextLine().trim().toLowerCase();
            if (resposta.isEmpty()) {
                resposta = "sim";
            }
        } while (!resposta.equals("sim") && !resposta.equals("não") && !resposta.equals("s") && !resposta.equals("n"));
        return resposta.equals("sim") || resposta.equals("s");
    }

    //========================Funcionalidade 9============================//
    /**
     * calcula os autovalores e os autovetores da Laplaciana
     */
    public static void autovaloresLaplaciana() {
        int n = lerDimensao();

        long start = System.currentTimeMillis();

        int[][] laplaciana = gerarLaplaciana(n);
        double[][] laplacianaDouble = intLaplacianaParaDoubleLaplaciana(laplaciana);
        double[][] vetoresProprios = calcularVetoresProprios(laplacianaDouble);
        double[] valoresProprios = calcularValoresProprios(laplacianaDouble);
        normalizarColunasParaMenos1Mais1(vetoresProprios);
        double[][][] autovetoresMatrizes = converterAutovetoresParaMatrizes(vetoresProprios, n);

        long end = System.currentTimeMillis();
        long elapsed = end - start;

        System.out.println("Autovetores normalizados em forma de matrizes " + n + "x" + n + ":");
        for (int k = 0; k < autovetoresMatrizes.length; k++) {
            System.out.println("Autovetor " + k + ":");
            imprimirMatrizDouble(autovetoresMatrizes[k]);
        }

        System.out.println("Autovalores:");
        imprimirArrayDouble(valoresProprios);

        System.out.println("\nTempo de execução (autovalores/autovetores Laplaciana): " + elapsed + " ms");
    }

    /**
     * imprime um array de valores reais no terminal
     *
     * @param array array de doubles a imprimir
     */
    public static void imprimirArrayDouble(double[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.printf("%.3f ", array[i]);
        }
        System.out.println();
    }

    /**
     * imprime uma matriz de valores reais no terminal
     *
     * @param matriz matriz de doubles a imprimir
     */
    public static void imprimirMatrizDouble(double[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.printf("%8.2f ", matriz[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * calcula os autovalores reais da matriz Laplaciana
     *
     * @param laplaciana matriz Laplaciana em double
     * @return array com os autovalores reais
     */
    public static double[] calcularValoresProprios(double[][] laplaciana) {
        validarMatriz(laplaciana);

        RealMatrix matriz = MatrixUtils.createRealMatrix(laplaciana);
        EigenDecomposition eigen = new EigenDecomposition(matriz);

        return eigen.getRealEigenvalues();
    }

    /**
     * calcula os autovetores da matriz Laplaciana
     *
     * @param laplaciana matriz Laplaciana em double
     * @return matriz cujas colunas são os autovetores
     */
    public static double[][] calcularVetoresProprios(double[][] laplaciana) {
        validarMatriz(laplaciana);

        RealMatrix matriz = MatrixUtils.createRealMatrix(laplaciana);
        EigenDecomposition eigen = new EigenDecomposition(matriz);

        int n = laplaciana.length;
        double[][] vetores = new double[n][n];

        for (int i = 0; i < n; i++) {
            double[] v = eigen.getEigenvector(i).toArray();
            for (int j = 0; j < n; j++) {
                vetores[j][i] = v[j];
            }
        }

        return vetores;
    }

    /**
     * converte autovetores em forma vetorial para matrizes (n x n)
     *
     * @param vetoresProprios matriz de autovetores
     * @param n dimensão da matriz original
     * @return autovetores representados como matrizes (n x n)
     */
    public static double[][][] converterAutovetoresParaMatrizes(double[][] vetoresProprios, int n) {
        int m = vetoresProprios.length;
        int k = vetoresProprios[0].length;

        if (m != n * n) {
            throw new IllegalArgumentException("Dimensão incompatível: m = " + m + " != n^2 = " + (n * n));
        }

        double[][][] resultado = new double[k][n][n];

        for (int eigenIndex = 0; eigenIndex < k; eigenIndex++) {
            int idx = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    resultado[eigenIndex][i][j] = vetoresProprios[idx][eigenIndex];
                    idx++;
                }
            }
        }

        return resultado;
    }

    /**
     * verifica se uma matriz de double é quadrada e válida
     *
     * @param matriz matriz a validar
     */
    public static void validarMatriz(double[][] matriz) {
        if (matriz == null) {
            System.out.println("A matriz não pode ser nula.");
            return;
        }
        if (matriz.length == 0) {
            System.out.println("A matriz não pode estar vazia.");
            return;
        }

        int n = matriz.length;
        for (int i = 0; i < n; i++) {
            if (matriz[i] == null) {
                System.out.println("A linha " + i + " da matriz não pode ser nula.");
                return;
            }
            if (matriz[i].length != n) {
                System.out.println("A matriz deve ser quadrada. Linha " + i + " tem " + matriz[i].length + " elementos, mas esperavam-se " + n + ".");
                return;
            }
        }
    }

    //======================== Funcionalidade 10 ============================//
    /**
     * calcula os autovalores e os autovetores usando fórmula fechada
     */
    public static void autovaloresEAutovetoresFormulaFechada() {
        int dimensao = lerDimensao();

        long start = System.currentTimeMillis();
        for (int k = 1; k <= dimensao; k++) {
            for (int l = 1; l <= dimensao; l++) {

                double valorProprio = 4 - 2 * Math.cos(k * Math.PI / (dimensao + 1)) - 2 * Math.cos(l * Math.PI / (dimensao + 1));

                System.out.print("λ(" + k + "," + l + ") = ");
                System.out.printf("%.3f%n", valorProprio);

                double[][] vetor = new double[dimensao][dimensao];

                for (int i = 1; i <= dimensao; i++) {
                    for (int j = 1; j <= dimensao; j++) {
                        vetor[i - 1][j - 1] = Math.sin(k * i * Math.PI / (dimensao + 1)) * Math.sin(l * j * Math.PI / (dimensao + 1));
                    }
                }

                imprimirMatrizDouble(vetor);
                System.out.println();

            }
        }

        long end = System.currentTimeMillis();
        long elapsed = end - start;

        System.out.println("\nTempo de execução (autovalores/autovetores fórmula fechada): " + elapsed + " ms");
    }
}
