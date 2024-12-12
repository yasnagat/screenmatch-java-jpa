package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.Episode;
import br.com.alura.screenmatch.model.EpisodesData;
import br.com.alura.screenmatch.model.SeriesData;
import br.com.alura.screenmatch.service.APIConsumer;
import br.com.alura.screenmatch.service.DataConverter;
import br.com.alura.screenmatch.model.SeasonsData;

import java.io.DataInput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Program {
    private Scanner read = new Scanner(System.in);
    private DataConverter conversor = new DataConverter();
    private APIConsumer APIConsumer = new APIConsumer();

    private final String ADDRESS = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";

    public void menuExibit() {
        System.out.print("Insira o nome da série que você quer buscar: ");
        var serieName = read.nextLine();

        var json = APIConsumer.dataObtainer(ADDRESS + serieName.replace(" ", "+") + API_KEY);
        System.out.println(json);

        SeriesData data = conversor.dataObtainer(json, SeriesData.class);
        System.out.println(data);

        List<SeasonsData> seasons = new ArrayList<>();
        for(int i = 1; i<=data.totalSeasons(); i++) {
            json = APIConsumer.dataObtainer(ADDRESS +
                    serieName.replace(" ", "+") +
                    "&season=" +
                    i +
                    API_KEY);
            SeasonsData seasonsData = conversor.dataObtainer(json, SeasonsData.class);
            seasons.add(seasonsData);
        }
        // seasons.forEach(System.out::println);
        seasons.forEach(t -> t.episodes().forEach(e -> System.out.println(e.title())));

//        // unificar as listas com os dados de cada temporada em uma só lista usando o stream
//        List<EpisodesData> episodesData = seasons.stream()
//                // metodo para concatenar cada elemento da lista em uma lista unica
//                .flatMap(e -> e.episodes().stream())
//
//                // collect é responsavel por adicionar ou remover itens da lista
//                // se usassemos o toList direto, a List se tornaria imutavel
//                .collect(Collectors.toList());

//        // para ordenar e limitar o top 5, implementando o parametro da busca
//        episodesData.stream()
//                .filter(e -> !e.rating().equalsIgnoreCase("N/A"))
//                        .sorted(Comparator.comparing(EpisodesData::rating).reversed())
//                                .limit(5)
//                                        .forEach(System.out::println);
        // BUSCANDO TODOS OS DADOS DE UM EPISODIO
        // para lidar com cada episodio isoladamente, como elemento separado da lista total da temporada
        List<Episode> episodes = seasons.stream()
                .flatMap(e -> e.episodes().stream())
                .map(d -> new Episode(d.number(), d)
                ).collect(Collectors.toList());
        episodes.forEach(System.out::println);


//        // FILTRO POR ANO DE LANÇAMENTO
//        // cria um novo input para o usuario filtrar os episodios a partir da data de lançamento
//        System.out.println("A partir de que ano você deseja ver os episódios? ");
//        var ano = read.nextInt();
//        //depois de um nextInt devemos colocar o nextLine para absorver o enter clicado
//        read.nextLine();
//        // define a variavel de busca do ano
//        LocalDate date = LocalDate.of(ano, 1, 1);
//        // formata a data para ser exibida no padrao usado no brasil
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//


//        // PARA CONSEGUIR VISUALIZAR O DEBBUG DA APLICAÇÃO: peek();
//        episodes.stream()
//                .filter(e -> e.getLaunchDate() != null && e.getLaunchDate().isAfter(date))
//                .peek(e -> System.out.println("Busca a data do episódio: " + e))
//                .forEach(e -> System.out.println(
//                        "Season: " + e.getSeasonNumber()
//                                + " Episode's name: " + e.getTitle()
//                                + " Launch date: " + e.getLaunchDate())
//                );


//        System.out.println("Digite o trecho do título do episódio que você deseja encontrar: ");
//        var titlePart = read.nextLine();
//        // criamos um container Optional, porque a variavel pode ou não ter um valor. é melhor do que tratar como null porque evita excecoes
//        Optional<Episode> searchedEpisode = episodes.stream()
//                .filter(e -> e.getTitle().toUpperCase().contains(titlePart.toUpperCase()))
//                .findFirst();
//        if(searchedEpisode.isPresent()) {
//            System.out.println("Episódio encontrado");
//            System.out.println("Temporada: " + searchedEpisode.get().getSeasonNumber().);
//        }
//        else {
//            System.out.println("Episódio não encontrado.");
//        }


        Map<Integer, Double> ratingPerSeason = episodes.stream()
                .filter(e-> e.getRating() > 0.0)
                .collect(Collectors.groupingBy(Episode::getSeasonNumber,
                        Collectors.averagingDouble(Episode::getRating)));
        System.out.println(ratingPerSeason);

        DoubleSummaryStatistics statistics = episodes.stream()
                .filter(st -> st.getRating() > 0.0)
                .collect(Collectors.summarizingDouble(Episode::getRating));
        System.out.println("Média: " + statistics.getAverage());
        System.out.println("Melhor episódio: " + statistics.getMax());
        System.out.println("Pior episódio: " + statistics.getMin());
        System.out.println("Quantidade de episódios: " + statistics.getCount());
    }
}