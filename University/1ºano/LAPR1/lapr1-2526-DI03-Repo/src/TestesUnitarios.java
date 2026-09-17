import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.EigenDecomposition;

import java.io.IOException;

public class TestesUnitarios {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        int total = 0;
        int passou = 0;
        boolean ok;

        ok = test_copiarMatriz_deepCopy();
        if (ok) passou++; else System.out.println("Falhou: test_copiarMatriz_deepCopy");
        total++;

        ok = test_estabilizarUmSweep_semToppling();
        if (ok) passou++; else System.out.println("Falhou: test_estabilizarUmSweep_semToppling");
        total++;

        ok = test_estabilizarUmSweep_topplingCentro_3x3();
        if (ok) passou++; else System.out.println("Falhou: test_estabilizarUmSweep_topplingCentro_3x3");
        total++;

        ok = test_estabilizarComSnapshots_centroZC_gera2();
        if (ok) passou++; else System.out.println("Falhou: test_estabilizarComSnapshots_centroZC_gera2");
        total++;

        ok = test_selecionarAte20Equiespacados_total45();
        if (ok) passou++; else System.out.println("Falhou: test_selecionarAte20Equiespacados_total45");
        total++;

        ok = test_tokenValido();
        if (ok) passou++; else System.out.println("Falhou: test_tokenValido");
        total++;

        ok = test_converterParaInt();
        if (ok) passou++; else System.out.println("Falhou: test_converterParaInt");
        total++;

        ok = test_gerarLaplaciana_2x2();
        if (ok) passou++; else System.out.println("Falhou: test_gerarLaplaciana_2x2");
        total++;

        ok = test_verificarDimensao_limites();
        if (ok) passou++; else System.out.println("Falhou: test_verificarDimensao_limites");
        total++;

        ok = test_calcularVizinhosNaoQueimados();
        if (ok) passou++; else System.out.println("Falhou: test_calcularVizinhosNaoQueimados");
        total++;

        ok = test_verficarMatrizQuadrada();
        if (ok) passou++; else System.out.println("Falhou: test_verficarMatrizQuadrada");
        total++;

        ok = test_verficarEstabilidade();
        if (ok) passou++; else System.out.println("Falhou: test_verficarEstabilidade");
        total++;

        ok = test_matrizesIguais();
        if (ok) passou++; else System.out.println("Falhou: test_matrizesIguais");
        total++;

        ok = test_algoritmoBurningDhar_1x1();
        if (ok) passou++; else System.out.println("Falhou: test_algoritmoBurningDhar_1x1");
        total++;

        ok = test_estabilizar_topplingCentro_3x3();
        if (ok) passou++; else System.out.println("Falhou: test_estabilizar_topplingCentro_3x3");
        total++;

        ok = test_somarEstabilizacao_somaJaEstavel();
        if (ok) passou++; else System.out.println("Falhou: test_somarEstabilizacao_somaJaEstavel");
        total++;

        ok = test_intLaplacianaParaDouble();
        if (ok) passou++; else System.out.println("Falhou: test_intLaplacianaParaDouble");
        total++;

        ok = test_calcularDeterminante_2x2();
        if (ok) passou++; else System.out.println("Falhou: test_calcularDeterminante_2x2");
        total++;

        ok = test_imprimirArrayDouble();
        if (ok) passou++; else System.out.println("Falhou: test_imprimirArrayDouble");
        total++;

        ok = test_imprimirMatrizDouble();
        if (ok) passou++; else System.out.println("Falhou: test_imprimirMatrizDouble");
        total++;

        ok = test_calcularValoresProprios_identidade2();
        if (ok) passou++; else System.out.println("Falhou: test_calcularValoresProprios_identidade2");
        total++;

        ok = test_calcularVetoresProprios_identidade2();
        if (ok) passou++; else System.out.println("Falhou: test_calcularVetoresProprios_identidade2");
        total++;

        System.out.println("=================================");
        System.out.println("TOTAL TESTES: " + total);
        System.out.println("PASSARAM:     " + passou);
        System.out.println("FALHARAM:     " + (total - passou));
        System.out.println("=================================");
    }

    // Cria uma matriz vazia n x m
    private static int[][] novaMatriz(int n, int m) {
        return new int[n][m];
    }

    // Cria uma cópia profunda de uma matriz
    private static int[][] copiarManual(int[][] m) {
        int[][] c = new int[m.length][m[0].length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                c[i][j] = m[i][j];
        return c;
    }

    // Compara duas matrizes elemento a elemento
    private static boolean matrizIgual(int[][] a, int[][] b) {
        if (a == null || b == null) return a == b;
        if (a.length != b.length) return false;

        for (int i = 0; i < a.length; i++) {
            if (a[i].length != b[i].length) return false;
            for (int j = 0; j < a[i].length; j++)
                if (a[i][j] != b[i][j]) return false;
        }
        return true;
    }

    // Valor absoluto simples
    private static double abs(double x) {
        return x < 0 ? -x : x;
    }

    // Comparação aproximada entre doubles
    private static boolean quaseIgual(double a, double b, double eps) {
        return abs(a - b) <= eps;
    }

    // Testa se copiarMatriz cria uma cópia profunda
    public static boolean test_copiarMatriz_deepCopy() {
        int[][] original = {{1, 2}, {3, 4}};
        int[][] copia = Main.copiarMatriz(original);

        if (!matrizIgual(original, copia)) return false;

        original[0][0] = 9;
        if (copia[0][0] != 1) return false;

        copia[1][1] = 7;
        return original[1][1] == 4;
    }

    // Testa estabilizarUmSweep quando não há colapsos
    public static boolean test_estabilizarUmSweep_semToppling() {
        int[][] m = {{0,1,2},{3,0,1},{2,1,0}};
        int[][] antes = copiarManual(m);

        boolean houve = Main.estabilizarUmSweep(m);
        return !houve && matrizIgual(m, antes);
    }

    // Testa um único colapso no centro da matriz
    public static boolean test_estabilizarUmSweep_topplingCentro_3x3() {
        int[][] m = novaMatriz(3,3);
        m[1][1] = Main.ZC;

        boolean houve = Main.estabilizarUmSweep(m);
        if (!houve) return false;

        int[][] esperado = novaMatriz(3,3);
        esperado[0][1] = 1;
        esperado[2][1] = 1;
        esperado[1][0] = 1;
        esperado[1][2] = 1;

        return matrizIgual(m, esperado);
    }

    // Testa se estabilizarComSnapshots devolve exatamente 2 estados (inicial + final)
    public static boolean test_estabilizarComSnapshots_centroZC_gera2() {
        int[][] m = novaMatriz(3,3);
        m[1][1] = Main.ZC;

        int[][] inicial = copiarManual(m);

        int[][][] snaps = Main.estabilizarComSnapshots(m);

        if (snaps == null || snaps.length != 2) return false;
        if (!matrizIgual(snaps[0], inicial)) return false;

        int[][] finalEsperado = novaMatriz(3,3);
        finalEsperado[0][1] = 1;
        finalEsperado[2][1] = 1;
        finalEsperado[1][0] = 1;
        finalEsperado[1][2] = 1;

        if (!matrizIgual(snaps[1], finalEsperado)) return false;

        return !Main.estabilizarUmSweep(copiarManual(snaps[1]));
    }

    // Testa seleção equiespaçada de 20 snapshots quando existem 45
    public static boolean test_selecionarAte20Equiespacados_total45() {
        int[][][] snaps = new int[45][][];

        for (int i = 0; i < 45; i++)
            snaps[i] = new int[][]{{i}};

        int[][][] out = Main.selecionarAte20Equiespacados(snaps);

        return out.length == 20 &&
                out[0][0][0] == 0 &&
                out[1][0][0] == 2 &&
                out[2][0][0] == 4 &&
                out[19][0][0] == 44;
    }

    // Verifica se o token contém apenas dígitos
    public static boolean test_tokenValido() {
        return Main.tokenValido("0")
                && Main.tokenValido("123")
                && !Main.tokenValido("")
                && !Main.tokenValido(" 1")
                && !Main.tokenValido("1 ")
                && !Main.tokenValido("1a")
                && !Main.tokenValido("-1")
                && Main.tokenValido("001");
    }

    // Testa conversão manual de string numérica para inteiro
    public static boolean test_converterParaInt() {
        return Main.converterParaInt("0") == 0
                && Main.converterParaInt("7") == 7
                && Main.converterParaInt("10") == 10
                && Main.converterParaInt("0012") == 12
                && Main.converterParaInt("999") == 999;
    }

    // Testa geração da matriz Laplaciana 2x2
    public static boolean test_gerarLaplaciana_2x2() {
        int[][] L = Main.gerarLaplaciana(2);
        if (L == null || L.length != 4 || L[0].length != 4) return false;

        return L[0][0] == Main.ZC &&
                L[1][1] == Main.ZC &&
                L[2][2] == Main.ZC &&
                L[3][3] == Main.ZC &&
                L[0][1] == Main.VIZINHOS &&
                L[0][2] == Main.VIZINHOS &&
                L[0][3] == 0;
    }

    // Verifica limites permitidos para dimensão
    public static boolean test_verificarDimensao_limites() {
        int min = Main.LIMITE_DIMENSAO_MINIMO;
        int max = Main.LIMITE_DIMENSAO_MAXIMO;

        if (Main.verificarDimensao(min)) return false;
        if (Main.verificarDimensao(max)) return false;

        return (min + 1 < max) && Main.verificarDimensao(min + 1);
    }

    // Testa contagem de vizinhos não queimados
    public static boolean test_calcularVizinhosNaoQueimados() {
        boolean[][] q = new boolean[3][3];

        if (Main.calcularVizinhosNaoQueimados(q,1,1) != 4) return false;
        if (Main.calcularVizinhosNaoQueimados(q,0,0) != 2) return false;

        q[0][1] = true;
        return Main.calcularVizinhosNaoQueimados(q,1,1) == 3;
    }

    // Testa verificação de matriz quadrada
    public static boolean test_verficarMatrizQuadrada() {
        int[][] a = {{1,2},{3,4}};
        int[][] b = {{1,2,3},{4,5}};
        return Main.verficarMatrizQuadrada(a) && !Main.verficarMatrizQuadrada(b);
    }

    // Testa verificação de estabilidade
    public static boolean test_verficarEstabilidade() {
        int[][] ok = {{0,1},{2,Main.ZC-1}};
        int[][] bad = {{0,Main.ZC},{1,2}};
        return Main.verificarEstabilidade(ok) && !Main.verificarEstabilidade(bad);
    }

    // Testa comparação de igualdade entre matrizes
    public static boolean test_matrizesIguais() {
        int[][] a = {{1,2},{3,4}};
        int[][] b = {{1,2},{3,4}};
        int[][] c = {{1,2},{3,5}};
        return Main.matrizesIguais(a,b) && !Main.matrizesIguais(a,c);
    }

    // Testa o algoritmo de Burning para o caso 1x1
    public static boolean test_algoritmoBurningDhar_1x1() {
        int[][] a = {{0}};
        int[][] b = {{3}};
        return Main.algoritmoBurningDhar(a) && Main.algoritmoBurningDhar(b);
    }


    // Testa estabilização após colapso central
    public static boolean test_estabilizar_topplingCentro_3x3() {
        int[][] m = novaMatriz(3,3);
        m[1][1] = Main.ZC;

        int[][] r = Main.estabilizar(m);

        int[][] e = novaMatriz(3,3);
        e[0][1]=1; e[2][1]=1; e[1][0]=1; e[1][2]=1;

        return matrizIgual(r, e);
    }

    // Testa soma seguida de estabilização quando a soma já é estável
    public static boolean test_somarEstabilizacao_somaJaEstavel() {
        if (Main.ZC <= 1) return true;

        int[][] a = {{0,1},{1,0}};
        int[][] b = {{1,0},{0,1}};
        int[][] e = {{1,1},{1,1}};

        return matrizIgual(Main.somarEstabilizacao(a,b), e);
    }

    // Testa conversão int[][] → double[][]
    public static boolean test_intLaplacianaParaDouble() {
        int[][] L = {{1,2},{3,4}};
        double[][] d = Main.intLaplacianaParaDoubleLaplaciana(L);

        return quaseIgual(d[0][0],1,0)
                && quaseIgual(d[0][1],2,0)
                && quaseIgual(d[1][0],3,0)
                && quaseIgual(d[1][1],4,0);
    }

    // Testa determinante de matriz 2x2
    public static boolean test_calcularDeterminante_2x2() {
        double[][] m = {{1,2},{3,4}};
        return quaseIgual(Main.calcularDeterminante(m), -2.0, 1e-9);
    }

    // Testa impressão de double[]
    public static boolean test_imprimirArrayDouble() {
        double[] d = {1.9, 2.1, -3.7, 0};
        Main.imprimirArrayDouble(d);
        return true;
    }

    // Testa impressão de double[][]
    public static boolean test_imprimirMatrizDouble() {
        double[][] d = {{1.9, -2.2}, {3.0, 4.9}};
        Main.imprimirMatrizDouble(d);
        return true;
    }

    // Testa cálculo dos autovalores da identidade 2x2
    public static boolean test_calcularValoresProprios_identidade2() {
        double[][] I = {{1,0},{0,1}};
        double[] v = Main.calcularValoresProprios(I);
        return quaseIgual(v[0],1,1e-9) && quaseIgual(v[1],1,1e-9);
    }

    // Testa autovetores da identidade: normalizados e ortogonais
    public static boolean test_calcularVetoresProprios_identidade2() {
        double[][] I = {{1,0},{0,1}};
        double[][] V = Main.calcularVetoresProprios(I);

        double v00 = V[0][0], v10 = V[1][0];
        double v01 = V[0][1], v11 = V[1][1];

        double n0 = v00*v00 + v10*v10;
        double n1 = v01*v01 + v11*v11;
        double dot = v00*v01 + v10*v11;

        return quaseIgual(n0,1,1e-6)
                && quaseIgual(n1,1,1e-6)
                && quaseIgual(dot,0,1e-6);
    }
}
