package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.SeasonsData;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.model.SeriesData;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.service.APIConsumer;
import br.com.alura.screenmatch.service.DataConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Program {
    private final Scanner read = new Scanner(System.in);
    private final DataConverter conversor = new DataConverter();
    private final APIConsumer APIConsumer = new APIConsumer();

    private final String ADDRESS = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";
    private final List<SeriesData> seriesData = new ArrayList<>();


    private final SerieRepository repository;

    public Program(SerieRepository repository) {
        this.repository = repository;
    }

    public void menuExibit() {
        var option = -1;
        while (option != 0) {
            var menu = """ 
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar séries buscadas
                    0 - Sair""";
            System.out.println(menu);
            option = read.nextInt();
            read.nextLine();

            switch (option) {
                case 1:
                    searchSerie();
                    break;
                case 2:
                    searchEpisode();
                    break;
                case 3:
                    listSeries();
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
        private void searchSerie() {
            SeriesData data = getSeriesData();
            // seriesData.add(data);

            // chama o repositorio para passar a guardar os dados requeridos no banco de dados em vez de uma lista
            // não é possível instanciar um repositorio de interface que herda de uma outra interface.
            // injeção de dependência:

            Serie serie = new Serie(data);
            repository.save(serie);
            System.out.println(data);
        }

        // metodo de consulta à API e converte os dados JSONq
        private SeriesData getSeriesData() {
            System.out.print("Insira o nome que você quer buscar: ");
            var name = read.nextLine();
            var json = APIConsumer.dataObtainer(ADDRESS + name.replace(" ", "-") + API_KEY);
            SeriesData data = conversor.dataObtainer(json, SeriesData.class);
            return data;
        }

        // busca pelo episodio da serie, semelhante ao metodo anterior, porém cria uma lista para guardar os dados
        // por temporada
        private void searchEpisode() {
            SeriesData seriesData = getSeriesData();
            List<SeasonsData> season = new ArrayList<>();

            for (int i = 0; i <= seriesData.totalSeasons(); i++) {
                var json = APIConsumer.dataObtainer(ADDRESS + seriesData.title().replace(" ", "-") + "&season=" + i + API_KEY);
                SeasonsData seasonData = conversor.dataObtainer(json, SeasonsData.class);
                season.add(seasonData);
            }
            season.forEach(System.out::println);
        }

        private void listSeries() {
            List<Serie> series = new ArrayList<>();

            series = seriesData.stream()
                    .map(Serie::new)
                    .toList();
            series.stream()
                    .sorted(Comparator.comparing(Serie::getGenre))
                    .forEach(System.out::println);
        }

    }