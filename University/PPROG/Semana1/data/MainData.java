package data;

public class MainData {

    static void main() {

        Data data1 = new Data(2026, 2, 26);
        Data data2 = new Data(1974, 4, 25);
        Data data3 = new Data(data1.getAno(), 12, 25);

        System.out.println(data1);

        System.out.println(data2.toAnoMesDiaString());

        System.out.println(data1.isMaior(data2));

        System.out.println(data1.calcularDiferenca(data2));

        System.out.println(data1.calcularDiferenca(data3));

        System.out.println(data2.determinarDiaDaSemana());

        System.out.println(Data.isAnoBissexto(data1.getAno()));
    }
}