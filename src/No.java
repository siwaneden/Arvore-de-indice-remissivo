public class No {
    String valor;
    ListaEstatica ocorrencias;
    No esquerda, direita;
    int altura;

    public No(String valor) {
        this.valor = valor;
        this.ocorrencias = new ListaEstatica();
        this.esquerda = null;
        this.direita = null;
        this.altura = 1;
    }
    // Método modificado para registrar uma ocorrência de uma palavra
    public void registrarOcorrencia(int lineNumber) {
        if (!this.ocorrencias.contem(lineNumber)) {
            this.ocorrencias.adicionarOrd(lineNumber);
        }
    }
    // Método para atualizar a altura do nó
    public void atualizarAltura() {
        altura = 1 + Math.max(altura(esquerda), altura(direita));
    }

    // Método privado para obter a altura de um nó
    private int altura(No node) {
        return node == null ? 0 : node.altura;
    }
}
