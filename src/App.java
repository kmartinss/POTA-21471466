import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.text.Normalizer;

//INTEGRANTES:
//Henrique Albuquerque Ferreira | RA: 21461619
//José Antonio Lucena Marques | RA: 21454701
//Karine Martins dos Santos | RA: 21471466
//Matheus Soares Georgetti | RA: 21224791

public class App {
    private static int contComparacao;
    private static int cont = 0;
    private static ArrayList<String> list = new ArrayList<String>();
    private static ArrayList<String> nome = new ArrayList<String>();
    private static ArrayList<String> sexo = new ArrayList<String>();
    private static ArrayList<String> endereco = new ArrayList<String>();
    private static ArrayList<String> cidade = new ArrayList<String>();
    private static ArrayList<String> email = new ArrayList<String>();
    private static ArrayList<String> telefone = new ArrayList<String>();
    private static ArrayList<String> idade = new ArrayList<String>();

    public static void main(String[] args) throws Exception {
        try {
            int cont_2 = 0;
            Scanner read = lerArquivo();

            extracaoDadosParaListas(cont_2);
            read.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        int contExec = 0;
        Scanner input = new Scanner(System.in);

        while (contExec < 1) {
            System.out.println("\n\nDigite um nome \n\nou \n\n[exit] para sair\n\n");
            String res = input.nextLine();

            if (res.equals("exit"))
                contExec++;

            else {
                System.out.println("Buscando...\n");
                Thread.sleep(1000);
                int pos = buscaBinaria(res);
                if (pos > 0) {
                    imprimirCliente(pos);
                } else {
                    System.out.println("Não localizado. Tente novamente.");
                }
            }
        }
        input.close();
    }

    private static Scanner lerArquivo() throws FileNotFoundException {
        File file = new File("arquivoDados.csv");
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            String linha = sc.nextLine();
            list.add(linha);
            cont++;
        }
        return sc;
    }

    private static void extracaoDadosParaListas(int cont_2) {
        while (cont_2 < cont) {
            String campoCliente = list.get(cont_2);
            String[] campo = campoCliente.split(",");
            int campos = 0;

            while (campos <= 6) {
                nome.add(campo[0]);
                sexo.add(campo[1]);
                endereco.add(campo[2]);
                cidade.add(campo[3]);
                email.add(campo[4]);
                telefone.add(campo[5]);
                idade.add(campo[6]);
                campos++;
            }
            cont_2++;
        }
    }

    private static int buscaBinaria(String nomeBuscado) {
        int inicio, meio, fim;

        inicio = 0;
        fim = nome.size() - 1;
        contComparacao = 0;

        while (inicio <= fim) {
            meio = (inicio + fim) / 2;
            contComparacao++;

            String nomeList = nome.get(meio);
            String comparacao = tratarString(nomeBuscado, nomeList);

            if (comparacao.equals("igual")) {
                return meio;
            }

            contComparacao++;

            if (comparacao.equals("menor"))
                fim = meio - 1;
            else
                inicio = meio + 1;
        }
        return -1;
    }

    // Essa função é utilizada no tratamento de nomes com acento
    private static String tratarString(String x, String y) {
        String xSemAcento = Normalizer.normalize(x, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        String ySemAcento = Normalizer.normalize(y, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");

        for (int i = 0; i < x.length(); i++) {

            int posicaoA = (int) xSemAcento.toLowerCase().charAt(i);
            int posicaob = (int) ySemAcento.toLowerCase().charAt(i);

            if (posicaoA != posicaob) {
                if (posicaoA > posicaob)
                    return "maior";
                return "menor";
            }
        }
        return "igual";
    }

    private static void imprimirCliente(int pos) {
        System.out.println("(1) Usuário encontrado: \n");
        System.out.println("=======================================================");
        System.out.println("NOME: " + nome.get(pos) + "\n" + "SEXO: " + sexo.get(pos) + "\n" + "ENDEREÇO: "
                + endereco.get(pos) + "\n" + "CIDADE: " + cidade.get(pos) + "\n" + "EMAIL: "
                + email.get(pos) + "\n" + "TEL: " + telefone.get(pos) + "\n" + "IDADE: " + idade.get(pos)
                + "\n" + "Número de comparações: " + contComparacao);
        System.out.println("=======================================================");
    }
}