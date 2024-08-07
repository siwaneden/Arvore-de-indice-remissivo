import java.io.*;
public class Main {
    public static void main(String[] args) {
        TabelaHashEF tabelaHash = new TabelaHashEF();

        carregarPalavrasChave(tabelaHash, "exemplo_palavras-chave.txt");

        processarTexto(tabelaHash, "exemplo_texto.txt");

        gerarIndiceRemissivo(tabelaHash, "exemplo-indice.txt");
    }
    private static void carregarPalavrasChave(TabelaHashEF tabela, String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String keyword;
            while ((keyword = br.readLine()) != null) {
                keyword = keyword.trim().toLowerCase();
                if (!keyword.isEmpty()) {
                    tabela.adicionar(keyword);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo de palavras-chave: " + e.getMessage());
        }
    }
    private static void processarTexto(TabelaHashEF tabela, String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                // Modificando a expressão regular para incluir hífen em palavras compostas
                String[] words = line.split("[^\\wáéíóúÁÉÍÓÚà-èìòùÀÈÌÒÙãõÃÕâêôÂÊÔç-]+");
                for (String word : words) {
                    if (!word.isEmpty() && !word.startsWith("-") && !word.endsWith("-") && !word.matches(".*\\d.*")) {
                        word = word.toLowerCase();
                        if (tabela.contem(word)) {
                            tabela.registrarOcorrencia(word, lineNumber);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo de texto: " + e.getMessage());
        }
    }

    private static void gerarIndiceRemissivo(TabelaHashEF tabela, String filePath) {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            for (ArvoreBinariaAlfabetica arvore : tabela.tabela) {
                arvore.gerarIndiceRemissivo(writer);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Erro ao escrever no arquivo de índice remissivo: " + e.getMessage());
        }
    }
}
