import java.io.PrintWriter;
import java.text.Normalizer;
public class ArvoreBinariaAlfabetica {
    private No raiz;
    public ArvoreBinariaAlfabetica() {
        raiz = null;
    }
    public void inserir(String valor) {
        raiz = inserirRecursivamente(raiz, valor);
    }
    private String normalizar(String valor) {
        return Normalizer.normalize(valor, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase();
    }
    private No inserirRecursivamente(No raiz, String valor) {
        String valorNormalizado = normalizar(valor);
        String valorRaizNormalizado = raiz != null ? normalizar(raiz.valor) : null;

        if (raiz == null) {
            return new No(valor);
        }

        if (valorNormalizado.compareTo(valorRaizNormalizado) < 0) {
            raiz.esquerda = inserirRecursivamente(raiz.esquerda, valor);
        } else if (valorNormalizado.compareTo(valorRaizNormalizado) > 0) {
            raiz.direita = inserirRecursivamente(raiz.direita, valor);
        }

        raiz.atualizarAltura();
        return balancear(raiz);
    }

    private No balancear(No raiz) {
        int balanceamento = fator(raiz);

        if (balanceamento > 1) {
            if (fator(raiz.esquerda) < 0) {
                raiz.esquerda = rotacaoEsquerda(raiz.esquerda);
            }
            return rotacaoDireita(raiz);
        }
        if (balanceamento < -1) {
            if (fator(raiz.direita) > 0) {
                raiz.direita = rotacaoDireita(raiz.direita);
            }
            return rotacaoEsquerda(raiz);
        }
        return raiz;
    }

    private int fator(No no) {
        return no == null ? 0 : altura(no.esquerda) - altura(no.direita);
    }

    private int altura(No no) {
        return no == null ? 0 : no.altura;
    }

    private No rotacaoDireita(No no) {
        No esq = no.esquerda;
        no.esquerda = esq.direita;
        esq.direita = no;
        no.atualizarAltura();
        esq.atualizarAltura();
        return esq;
    }

    private No rotacaoEsquerda(No no) {
        No dir = no.direita;
        no.direita = dir.esquerda;
        dir.esquerda = no;
        no.atualizarAltura();
        dir.atualizarAltura();
        return dir;
    }

    public void registrarOcorrencia(String palavra, int lineNumber) {
        No node = buscar(raiz, palavra);
        if (node != null) {
            node.registrarOcorrencia(lineNumber);
        }
    }

    private No buscar(No raiz, String valor) {
        if (raiz == null) {
            return null;
        }

        String valorNormalizado = normalizar(valor);
        String valorRaizNormalizado = normalizar(raiz.valor);

        if (valorNormalizado.equals(valorRaizNormalizado)) {
            return raiz;
        }

        if (valorNormalizado.compareTo(valorRaizNormalizado) < 0) {
            return buscar(raiz.esquerda, valor);
        } else {
            return buscar(raiz.direita, valor);
        }
    }
    public void gerarIndiceRemissivo(PrintWriter writer) {
        gerarIndiceRecursivo(raiz, writer);
    }

    private void gerarIndiceRecursivo(No no, PrintWriter writer) {
        if (no == null) {
            return;
        }
        gerarIndiceRecursivo(no.esquerda, writer);
        if(no.ocorrencias.tamanho()!=0) {     // se nao tiver ocorrencia nao imprime
            writer.print(no.valor + " ");
            imprimirOcorrencias(no, writer);
            writer.println();
        }
        gerarIndiceRecursivo(no.direita, writer);
    }

    private void imprimirOcorrencias(No no, PrintWriter writer) {
        for (int i = 0; i < no.ocorrencias.tamanho(); i++) {
            writer.print(no.ocorrencias.buscar(i) + " ");
        }
    }
    public boolean contem(String valor) {
        return buscar(raiz, valor) != null;

    }
}
