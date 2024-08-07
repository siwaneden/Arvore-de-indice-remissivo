public class ListaEstatica {
    private Object[] vetor;
    private int contador;
    public ListaEstatica() {
        vetor = new Object[10];
        contador = 0;
    }
    public void adicionarOrd(int elemento) {
        int i = 0;
        while (i < contador && (int) vetor[i] < elemento) {
            i++;
        }
        adicionar(i, elemento);
    }
    // Método para adicionar um elemento em uma posição específica
    public void adicionar(int posicao, int elemento) {
        if (contador == vetor.length) {
            expandirVetor();
        }
        if (posicao < contador) {
            System.arraycopy(vetor, posicao, vetor, posicao + 1, contador - posicao);
            vetor[posicao] = elemento;
        } else {
            vetor[contador] = elemento;
        }
        contador++;
    }
    // Método privado para expandir o tamanho do vetor quando necessário
    private void expandirVetor() {
        Object[] vetorNovo = new Object[vetor.length * 2];
        System.arraycopy(vetor, 0, vetorNovo, 0, vetor.length);
        vetor = vetorNovo;
    }
    public Object buscar(int posicao) {
        return posicao >= 0 && posicao < contador ? vetor[posicao] : null;
    }
    public int tamanho() {
        return contador;
    }
    public boolean contem(int elemento) {
        for (int i = 0; i < contador; i++) {
            if ((int) vetor[i] == elemento) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("[ ");
        for (int i = 0; i < contador; i++) {
            s.append(vetor[i]).append(" ");
        }
        return s.append("]").toString();
    }
}
