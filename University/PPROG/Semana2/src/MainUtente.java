public class MainUtente {

    static void main() {

        Utente u1 = new Utente("Francisco", "Masculino", 18, 63.2, 1.70);
        Utente u2 = new Utente("Manuel", "Masculino",19, 100, 1.78);
        Utente u3 = new Utente("Maria", "Feminino",17, 55, 1.65);
        Utente u4 = new Utente("Carolina", "Feminino",17, 50, 1.60);

        Utente[] utentes = new Utente[4];
        utentes[0] = u1;
        utentes[1] = u2;
        utentes[2] = u3;
        utentes[3] = u4;


        for (int i = 0; i < utentes.length; i++) {
            System.out.println(utentes[i]);
        }

        for (int i = 0; i < utentes.length; i++) {
            System.out.println(utentes[i].getNome());
            System.out.println(utentes[i].calcularIMC());
            System.out.println(utentes[i].grauObesidade());
        }

        for (int i = 0; i < utentes.length; i++) {
            utentes[i].calcularIMC();
            utentes[i].grauObesidade();

            if (!utentes[i].isSaudavel()) {
                System.out.println(utentes[i].getNome() + " não é saudável,"
                + " é " + utentes[i].grauObesidade().toLowerCase());
            }
        }

        u2.setIdade(u3.getIdade());

        int resultado = u1.maisNovo(u2);

        if (resultado == -1) {
            System.out.println("O " + u1.getNome() + " é mais novo do que " + u2.getNome());
        } else if (resultado == 1) {
            System.out.println("O " + u2.getNome() + " é mais novo do que " + u1.getNome());
        } else if (resultado == 0) {
            System.out.println("Ambos os utentes " + u1.getNome() + " e " + u2.getNome() + "tem a mesma idade.");
        }
    }
}
