public class MainAutomovel {

    static void main() {

        Automovel a1 = new Automovel("11-11-AA", "Toyota", 1400);

        System.out.println(a1.toString());
        System.out.println(a1.getMatricula());
        System.out.println(Automovel.getTotalAutomoveis());

        Automovel a2 = new Automovel("22-22-BB", "Audi", 0);
        
        System.out.println(a2.toString());
        a2.setCilindrada(1800);
        System.out.println(a2.toString());
        System.out.println(Automovel.getTotalAutomoveis());
        System.out.println(a1.diferencaCilindrada(a1, a2));

        if (a1.cilindradaSuperior(a1, a2)) {
            System.out.println(a1.getMatricula());
        } else {
            System.out.println(a2.getMatricula());
        }

        if (a1.cilindradaSuperiorAValor(a1, 2000)) {
            System.out.println("O automóvel " + a1 + " tem a cilindrada maior que o valor dado.");
        } else {
            System.out.println("O automóvel " + a1 + " tem a cilindrada menor que o valor dado.");
        }
    }
}
