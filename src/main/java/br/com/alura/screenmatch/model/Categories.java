package br.com.alura.screenmatch.model;

public enum Categories {
    ACAO("Action"),
    ROMANCE("Romance"),
    COMEDIA("Comedy"),
    DRAMA("Drama"),
    SUSPENSE("Suspense"),
    CRIME("Crime");

    // criando correspondencias entre os idiomas
    private final String categoriaOmdb;

    // criando o metodo para relacionar as correspondencias
    Categories(String categoriaOmdb) {
        this.categoriaOmdb = categoriaOmdb;
    }

    // percorre a lista das constantes e identifica a correspondencia com os generos definidos
    // quando encontra, faz a transformacao
    public static Categories fromString(String text) {
        for (Categories categorie : Categories.values()) {
            if (categorie.categoriaOmdb.equalsIgnoreCase(text)) {
                return categorie;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }


}

