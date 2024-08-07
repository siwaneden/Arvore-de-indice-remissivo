import java.text.Normalizer;
public class TabelaHashEF {
    public ArvoreBinariaAlfabetica[] tabela;

    public TabelaHashEF() {
        tabela = new ArvoreBinariaAlfabetica[26];
        for (int i = 0; i < tabela.length; i++) {
            tabela[i] = new ArvoreBinariaAlfabetica();
        }
    }
    private int mod(String value) {   //calcula o indice da tabela
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Valor não pode ser nulo ou vazio");
        }
        String normalizedValue = Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .replaceAll("\\d", "");
        char firstChar = Character.toLowerCase(normalizedValue.charAt(0));
        if (firstChar < 'a' || firstChar > 'z') {
            throw new IllegalArgumentException("String deve começar com uma letra");
        }

        return firstChar - 'a';
    }
    public void adicionar(String valor) {
        int indice = mod(valor);
        tabela[indice].inserir(valor);
    }
    public void registrarOcorrencia(String palavra, int lineNumber) {
        int indice = mod(palavra);
        tabela[indice].registrarOcorrencia(palavra, lineNumber);
    }
    public boolean contem(String valor) {
        int indice = mod(valor);
        return tabela[indice].contem(valor);
    }
}
