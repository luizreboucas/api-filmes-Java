import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) throws Exception {
        var opcao = 0;
        while (opcao != 3) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Bem vindo ao IMDBBase, a seleção dos melhores filmes de todos os tempos");
            System.out.println("o que deseja fazer?");
            System.out.println("[1] -> pesquisar filme\n[2] -> listar os melhores 10 filmes\n[3] -> sair");
            opcao = scanner.nextInt();
            scanner.nextLine();

            String url = "https://mocki.io/v1/9a7c1ca9-29b4-4eb3-8306-1adb9d159060";
            ExtratorDeConteudo extrator = new ExtratorDeConteudoDoIMDB();


            var http = new ClienteHttp();
            String json = http.buscaDados(url);

            // exibir e manipular os dados
            List<Conteudo> conteudos = extrator.extraiConteudos(json);

            var geradora = new GeradoraDeFigurinhas();
            if(opcao ==1){
                System.out.println("Digite o filme o qual deseja pesquisar. obs: nome em inglês, Pascal Case com espacos ");
                String nomePesquisa = scanner.nextLine();
                List<Conteudo> busca = conteudos
                        .stream()
                        .filter(filme -> filme.getTitulo().equals(nomePesquisa))
                                .collect(Collectors.toList());

                if(!busca.isEmpty()){
                    var resultado = busca.get(0);
                    System.out.println("O filme " + resultado.getTitulo() + " está presente na lista dos 500 melhores filmes do mundo\no poster deste filme foi gerado e está na pasta ./saída");
                    InputStream inputStream = new URL(resultado.getUrlImagem()).openStream();
                    String nomeArquivo = "C:\\Users\\luiz.reboucas\\Documents\\api-filmes-Java\\alura-stickers\\saida/" + resultado.getTitulo() + ".png";
                    geradora.cria(inputStream, nomeArquivo);
                    break;
                }
                else{
                    System.out.println("filme não encontrado");
                }
            }
            if(opcao == 2) {
                for (int i = 0; i < 9; i++) {

                    Conteudo conteudo = conteudos.get(i);

                    InputStream inputStream = new URL(conteudo.getUrlImagem()).openStream();
                    String nomeArquivo = "C:\\Users\\luiz.reboucas\\Documents\\api-filmes-Java\\alura-stickers\\saida/" + conteudo.getTitulo() + ".png";

                    geradora.cria(inputStream, nomeArquivo);

                    System.out.println(conteudo.getTitulo());
                    System.out.println();
                }
            }
            if(opcao < 0 || opcao > 3){
                System.out.println("opção inválida!");
            }
        }
    }
}
