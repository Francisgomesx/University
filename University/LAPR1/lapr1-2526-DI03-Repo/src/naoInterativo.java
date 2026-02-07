import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;

import java.io.IOException;

/**
 * Classe dedicada ao MODO NÃO-INTERATIVO.
 * NOTA:
 * Esta classe "reaproveita o código já feito" chamando métodos estáticos existentes na classe Main:
 * - estabilizar, verificarEstabilidade, algortimoBurningDhar, gerarMatrizEstavel, verificarNumeroDeMatrizesEstaveis,
 *   gerarLaplaciana, intLaplacianaParaDoubleLaplaciana, calcularDeterminante,
 *   calcularVetoresProprios, calcularValoresProprios, normalizarColunasParaMenos1Mais1,
 *   somarEstabilizacao, estabilizarComSnapshots, selecionarAte20Equiespacados, matrizesIguais, verficarMatrizQuadrada,
 *   verificarElementoNeutro, etc..
 */
public class naoInterativo {

    //======================== M A I N ========================//

    public static void main(String[] args) throws IOException {
        if (args == null || args.length == 0) {
            throw new IllegalArgumentException("Modo não-interativo: tens de indicar argumentos. Ex: -f 1 -a Input/A.csv -o Output/out.txt");
        }
        runNonInteractive(args);
    }
    //======================== M O D O  N Ã O  I N T E R A T I V O ========================//

    public static void runNonInteractive(String[] args) throws IOException {

        // obrigatórios no modo não-interativo
        String fStr = requireArgValue(args, "-f", "Modo não-interativo: falta -f (funcionalidade).");
        String outPath = requireArgValue(args, "-o", "Modo não-interativo: falta -o (ficheiro de output TXT).");

        int f = parsePositiveInt(fStr, "Valor inválido em -f: " + fStr);

        ensureParentDir(outPath);

        PrintWriter out = new PrintWriter(new File(outPath));

        dispatchNonInteractive(args, f, out);

        out.flush();
        out.close();
    }

    private static void dispatchNonInteractive(String[] args, int f, PrintWriter out) throws IOException {

        // NOTA: -d é usado nas funcionalidades 6/7/9/10
        //       -a, -b, -e são usados conforme o caso

        switch (f) {

            //======================== Funcionalidade 1 ========================//
            case 1 -> {
                String a = requireArgValue(args, "-a", "Funcionalidade 1 requer -a A.csv");
                int[][] A = carregarFicheiroCSVFromPath(a);

                out.println("=== Funcionalidade 1: Carregar matriz e imprimir ===");
                out.println("Ficheiro: " + a);
                out.println("Matriz carregada:\n");
                imprimirMatrizToWriter(A, out);
            }

            //======================== Funcionalidade 2 ========================//
            case 2 -> {
                String a = requireArgValue(args, "-a", "Funcionalidade 2 requer -a A.csv");
                int[][] A = carregarFicheiroCSVFromPath(a);

                out.println("=== Funcionalidade 2: Verificar estabilidade e estabilizar ===");
                out.println("Ficheiro: " + a);
                out.println("Matriz carregada:\n");
                imprimirMatrizToWriter(A, out);

                if (Main.verificarEstabilidade(A)) {
                    out.println("A matriz já está estável.");
                } else {
                    out.println("A matriz não está estável. A estabilizar...");
                    Main.estabilizar(A);
                    out.println("Matriz estabilizada:\n");
                    imprimirMatrizToWriter(A, out);
                }

                // opcional -csv (se não vier, gera automático)
                String outCsv = getArgValue(args, "-csv");
                if (outCsv == null) outCsv = buildOutputCsvName(a, "estabilizada");
                ensureParentDir(outCsv);

                escreverMatrizCsv(A, outCsv);
                out.println("Matriz estabilizada escrita em: " + outCsv);
            }

            //======================== Funcionalidade 3 ========================//
            case 3 -> {
                String a = requireArgValue(args, "-a", "Funcionalidade 3 requer -a A.csv");
                String b = requireArgValue(args, "-b", "Funcionalidade 3 requer -b B.csv");
                int[][] A = carregarFicheiroCSVFromPath(a);
                int[][] B = carregarFicheiroCSVFromPath(b);

                out.println("=== Funcionalidade 3: Adicionar tarefas (A ⊕ B) ===");
                out.println("A: " + a);
                out.println("B: " + b);

                // A deve ser estável (pedido do cliente)
                if (!Main.verificarEstabilidade(A)) {
                    out.println("Aviso: A não estava estável. A estabilizar A...");
                    Main.estabilizar(A);
                }

                if (!Main.verficarMatrizQuadrada(A) || !Main.verficarMatrizQuadrada(B) || A.length != B.length) {
                    out.println("Erro: A e B têm de ser quadradas e com a mesma dimensão.");
                    return;
                }

                int[][] somaEstab = Main.somarEstabilizacao(A, B);
                int[][][] snaps = Main.estabilizarComSnapshots(somaEstab);
                int[][][] selecionados = Main.selecionarAte20Equiespacados(snaps);

                out.println("Total snapshots gerados: " + snaps.length);
                out.println("Snapshots selecionados (máx 20): " + selecionados.length);

                // nomes base para Output
                String baseA = fileBaseNameNoExt(a);
                String baseB = fileBaseNameNoExt(b);
                String prefix = "Output/" + baseA + "_" + baseB;
                ensureParentDir(prefix + "_snapshot_00.jpg");

                // Reaproveita a tua classe existente
                HeatmapImageWriter writer = new HeatmapImageWriter();
                for (int i = 0; i < selecionados.length; i++) {
                    String nome = prefix + "_snapshot_" + twoDigits(i) + ".jpg";
                    writer.writeArrayAsImage(selecionados[i], nome);
                }

                out.println("Snapshots JPG gerados com prefixo: " + prefix);
            }

            //======================== Funcionalidade 4 ========================//
            case 4 -> {
                String a = requireArgValue(args, "-a", "Funcionalidade 4 requer -a A.csv");
                int[][] A = carregarFicheiroCSVFromPath(a);

                out.println("=== Funcionalidade 4: Testar matriz recorrente (Burning de Dhar) ===");
                out.println("Ficheiro: " + a);

                if (!Main.verificarEstabilidade(A)) {
                    out.println("Erro: a matriz não é estável.");
                    return;
                }

                boolean recorrente = Main.algortimoBurningDhar(A);
                out.println(recorrente
                        ? "Pelo Algoritmo de Burning de Dhar a matriz é recorrente."
                        : "Pelo Algoritmo de Burning de Dhar a matriz não é recorrente.");
            }

            //======================== Funcionalidade 5 ========================//
            case 5 -> {
                String e = requireArgValue(args, "-a", "Funcionalidade 5 requer -a E.csv (matriz E)");
                int[][] E = carregarFicheiroCSVFromPath(e);

                out.println("=== Funcionalidade 5: Verificar elemento neutro ===");
                out.println("Ficheiro: " + e);

                if (!Main.verificarEstabilidade(E)) {
                    out.println("Erro: E não é estável.");
                    return;
                }

                out.println(Main.verificarElementoNeutro(E)
                        ? "A matriz inserida é elemento neutro."
                        : "A matriz inserida não é elemento neutro.");
            }

            //======================== Funcionalidade 6 ========================//
            case 6 -> {
                int d = requireArgInt(args, "-d", "Funcionalidade 6 requer -d N (dimensão).");

                out.println("=== Funcionalidade 6: Nº recorrentes (sem Laplaciano) ===");
                out.println("Dimensão: " + d);

                int total = Main.verificarNumeroDeMatrizesEstaveis(d);
                if (total == -1) {
                    out.println("Erro: dimensão demasiado grande (overflow/limites).");
                    return;
                }

                long start = System.currentTimeMillis();

                int contador = 0;
                for (int ordem = 0; ordem < total; ordem++) {
                    int[][] M = Main.gerarMatrizEstavel(ordem, d);
                    if (Main.algortimoBurningDhar(M)) contador++;
                }

                long end = System.currentTimeMillis();

                out.println("Número de matrizes recorrentes: " + contador);
                out.println("Tempo de execução (Func 6): " + (end - start) + " ms");
            }

            //======================== Funcionalidade 7 ========================//
            case 7 -> {
                int d = requireArgInt(args, "-d", "Funcionalidade 7 requer -d N (dimensão).");

                out.println("=== Funcionalidade 7: Nº recorrentes (com Laplaciano) ===");
                out.println("Dimensão: " + d);

                long start = System.currentTimeMillis();

                int[][] lap = Main.gerarLaplaciana(d);
                double[][] lapD = Main.intLaplacianaParaDoubleLaplaciana(lap);
                double det = Main.calcularDeterminante(lapD);

                long end = System.currentTimeMillis();

                out.printf("Número de matrizes recorrentes: %.0f%n", det);
                out.println("Tempo de execução (Func 7): " + (end - start) + " ms");
            }

            //======================== Funcionalidade 8 ========================//
            case 8 -> {
                String a = requireArgValue(args, "-a", "Funcionalidade 8 requer -a A.csv");
                String e = requireArgValue(args, "-e", "Funcionalidade 8 requer -e E.csv (elemento neutro)");
                int[][] A = carregarFicheiroCSVFromPath(a);
                int[][] E = carregarFicheiroCSVFromPath(e);

                out.println("=== Funcionalidade 8: Inversa estabilizada ===");
                out.println("A: " + a);
                out.println("E: " + e);

                inversaEstabilizadaNonInteractive(A, E, out);
            }

            //======================== Funcionalidade 9 ========================//
            case 9 -> {
                int d = requireArgInt(args, "-d", "Funcionalidade 9 requer -d N (dimensão).");

                out.println("=== Funcionalidade 9: Autovalores e Autovetores (normalizados [-1,1]) ===");
                out.println("Dimensão: " + d);

                long start = System.currentTimeMillis();

                int[][] lap = Main.gerarLaplaciana(d);
                double[][] lapD = Main.intLaplacianaParaDoubleLaplaciana(lap);
                double[][] vecs = Main.calcularVetoresProprios(lapD);
                double[] vals = Main.calcularValoresProprios(lapD);

                // normalização pedida pelo cliente
                Main.normalizarColunasParaMenos1Mais1(vecs);

                long end = System.currentTimeMillis();

                out.println("Autovetores (colunas) normalizados [-1,1]:");
                imprimirMatrizDoubleToWriter(vecs, out);
                out.println("Autovalores:");
                imprimirArrayDoubleToWriter(vals, out);

                out.println("Tempo de execução (Func 9): " + (end - start) + " ms");
            }

            //======================== Funcionalidade 10 ========================//
            case 10 -> {
                int d = requireArgInt(args, "-d", "Funcionalidade 10 requer -d N (dimensão).");

                out.println("=== Funcionalidade 10: Autovalores e Autovetores (Fórmula Fechada) ===");
                out.println("Dimensão: " + d);

                long start = System.currentTimeMillis();

                for (int k = 1; k <= d; k++) {
                    for (int l = 1; l <= d; l++) {
                        double lambda =
                                4 - 2 * Math.cos(k * Math.PI / (d + 1))
                                        - 2 * Math.cos(l * Math.PI / (d + 1));

                        out.printf("λ(%d,%d) = %.3f%n", k, l, lambda);

                        double[][] v = new double[d][d];
                        for (int i = 1; i <= d; i++) {
                            for (int j = 1; j <= d; j++) {
                                v[i - 1][j - 1] =
                                        Math.sin(k * i * Math.PI / (d + 1)) *
                                                Math.sin(l * j * Math.PI / (d + 1));
                            }
                        }

                        // normaliza toda a matriz para [-1,1] (boa prática e consistente com o pedido)
                        normalizarMatrizParaMenos1Mais1(v);
                        imprimirMatrizDoubleToWriter(v, out);
                    }
                }

                long end = System.currentTimeMillis();
                out.println("Tempo de execução (Func 10): " + (end - start) + " ms");
            }

            default -> out.println("Erro: funcionalidade inválida: " + f);
        }
    }

    //======================== R E U T I L I Z Á V E I S ========================//

    //======================== Argumentos ========================//

    private static String getArgValue(String[] args, String flag) {
        for (int i = 0; i < args.length; i++) {
            if (args[i] != null && args[i].equals(flag)) {
                if (i + 1 < args.length) return args[i + 1];
                return null;
            }
        }
        return null;
    }

    private static String requireArgValue(String[] args, String flag, String msgErro) {
        String v = getArgValue(args, flag);
        if (v == null || v.trim().isEmpty()) throw new IllegalArgumentException(msgErro);
        return v.trim();
    }

    private static int requireArgInt(String[] args, String flag, String msgErro) {
        String v = requireArgValue(args, flag, msgErro);
        return parsePositiveInt(v, msgErro + " (valor inválido: " + v + ")");
    }

    // parse manual (sem Integer.parseInt, logo sem try/catch)
    private static int parsePositiveInt(String s, String msgErro) {
        if (s == null || s.isEmpty()) throw new IllegalArgumentException(msgErro);

        int valor = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c < '0' || c > '9') throw new IllegalArgumentException(msgErro);
            valor = valor * 10 + (c - '0');
        }
        return valor;
    }

    //======================== Ficheiros/Pastas ========================//

    private static void ensureParentDir(String path) {
        File f = new File(path);
        File parent = f.getParentFile();
        if (parent != null && !parent.exists()) parent.mkdirs();
    }

    private static String buildOutputCsvName(String inputPath, String suffix) {
        String name = new File(inputPath).getName();
        if (name.length() >= 4 && name.substring(name.length() - 4).equalsIgnoreCase(".csv")) {
            name = name.substring(0, name.length() - 4);
        }
        return "Output/" + name + "_" + suffix + ".csv";
    }

    private static String fileBaseNameNoExt(String path) {
        String name = new File(path).getName();
        if (name.length() >= 4 && name.substring(name.length() - 4).equalsIgnoreCase(".csv")) {
            return name.substring(0, name.length() - 4);
        }
        return name;
    }

    private static String twoDigits(int i) {
        if (i < 10) return "0" + i;
        return "" + i;
    }

    //======================== CSV (sem prompts) ========================//

    public static int[][] carregarFicheiroCSVFromPath(String caminhoCsv) throws FileNotFoundException {
        File file = new File(caminhoCsv);

        Scanner leitor = new Scanner(file);

        int numLinhas = 0;
        while (leitor.hasNextLine()) {
            leitor.nextLine();
            numLinhas++;
        }
        leitor.close();

        if (numLinhas <= 0) return null;

        leitor = new Scanner(file);

        String primeiraLinha = leitor.nextLine();
        String[] tokensPrimeira = primeiraLinha.split(",");
        int numColunas = tokensPrimeira.length;

        if (numColunas == 0 || numColunas != numLinhas) {
            leitor.close();
            return null;
        }

        int[][] matriz = new int[numLinhas][numColunas];

        for (int j = 0; j < numColunas; j++) {
            String token = tokensPrimeira[j].trim();
            if (!tokenValido(token)) {
                leitor.close();
                return null;
            }
            matriz[0][j] = converterParaInt(token);
        }

        int linhaAtual = 1;
        while (leitor.hasNextLine() && linhaAtual < numLinhas) {
            String linha = leitor.nextLine();
            String[] tokens = linha.split(",");

            if (tokens.length != numColunas) {
                leitor.close();
                return null;
            }

            for (int j = 0; j < numColunas; j++) {
                String token = tokens[j].trim();
                if (!tokenValido(token)) {
                    leitor.close();
                    return null;
                }
                matriz[linhaAtual][j] = converterParaInt(token);
            }

            linhaAtual++;
        }

        leitor.close();

        if (linhaAtual != numLinhas) return null;

        return matriz;
    }

    private static boolean tokenValido(String token) {
        if (token == null || token.isEmpty()) return false;
        for (int k = 0; k < token.length(); k++) {
            char c = token.charAt(k);
            if (c < '0' || c > '9') return false;
        }
        return true;
    }

    private static int converterParaInt(String token) {
        int valor = 0;
        for (int k = 0; k < token.length(); k++) {
            int digito = token.charAt(k) - '0';
            valor = valor * 10 + digito;
        }
        return valor;
    }

    public static void escreverMatrizCsv(int[][] matriz, String caminhoOut) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new File(caminhoOut));
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                writer.print(matriz[i][j]);
                if (j < matriz[i].length - 1) writer.print(",");
            }
            writer.println();
        }
        writer.flush();
        writer.close();
    }

    //======================== Impressão para ficheiro ========================//

    private static void imprimirMatrizToWriter(int[][] matriz, PrintWriter out) {
        if (matriz == null) {
            out.println("(matriz nula)");
            return;
        }
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                out.printf("%4d ", matriz[i][j]);
            }
            out.println();
        }
        out.println();
    }

    private static void imprimirMatrizDoubleToWriter(double[][] matriz, PrintWriter out) {
        if (matriz == null) {
            out.println("(matriz nula)");
            return;
        }
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                out.printf("%8.2f ", matriz[i][j]);
            }
            out.println();
        }
        out.println();
    }

    private static void imprimirArrayDoubleToWriter(double[] array, PrintWriter out) {
        if (array == null) {
            out.println("(array nulo)");
            return;
        }
        for (int i = 0; i < array.length; i++) {
            out.printf("%.3f ", array[i]);
        }
        out.println();
    }

    //======================== Normalização [-1,1] para matriz 2D ========================//

    private static void normalizarMatrizParaMenos1Mais1(double[][] M) {
        double maxAbs = 0.0;
        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M[i].length; j++) {
                double a = Math.abs(M[i][j]);
                if (a > maxAbs) maxAbs = a;
            }
        }
        if (maxAbs == 0.0) return;

        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M[i].length; j++) {
                M[i][j] /= maxAbs;
            }
        }
    }

    //======================== Funcionalidade 8 (não-interativo) ========================//

    private static void inversaEstabilizadaNonInteractive(int[][] A, int[][] E, PrintWriter out) {

        if (A == null || E == null) {
            out.println("Erro: A e/ou E não carregadas.");
            return;
        }

        if (!Main.verficarMatrizQuadrada(A) || !Main.verficarMatrizQuadrada(E)) {
            out.println("Erro: A e/ou E não são matrizes quadradas.");
            return;
        }

        if (A.length != E.length) {
            out.println("Erro: A e E têm dimensões diferentes.");
            return;
        }

        if (!Main.verificarEstabilidade(A)) {
            out.println("Erro: A não é estável.");
            return;
        }

        if (!Main.verificarEstabilidade(E)) {
            out.println("Erro: E não é estável.");
            return;
        }

        if (!Main.algortimoBurningDhar(A)) {
            out.println("Erro: A não é recorrente.");
            return;
        }

        if (!Main.algortimoBurningDhar(E)) {
            out.println("Erro: E não é recorrente.");
            return;
        }

        int dimensao = A.length;
        int total = Main.verificarNumeroDeMatrizesEstaveis(dimensao);
        if (total == -1) {
            out.println("Erro: dimensão demasiado grande para pesquisa exaustiva.");
            return;
        }

        for (int ordem = 0; ordem < total; ordem++) {
            int[][] B = Main.gerarMatrizEstavel(ordem, dimensao);
            if (!Main.algortimoBurningDhar(B)) continue;

            int[][] somaEstab = Main.somarEstabilizacao(A, B);
            if (Main.matrizesIguais(somaEstab, E)) {
                out.println("A inversa estabilizada de A é:");
                imprimirMatrizToWriter(B, out);
                return;
            }
        }

        out.println("Não foi encontrada inversa estabilizada para A.");
    }
}
