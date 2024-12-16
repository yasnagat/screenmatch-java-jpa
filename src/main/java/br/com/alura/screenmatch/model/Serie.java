package br.com.alura.screenmatch.model;

import br.com.alura.screenmatch.service.translator.MyMemoryRequest;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
// anotacao para criar correspondencia com o bd pelo JPA
@Entity
// anotacao para alterar o nome no banco de dados
@Table(name = "series")

public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String title;
    private Integer totalSeasons;
    private Double rate;

    @Enumerated(EnumType.STRING)
    private Categories genre;

    private String actors;
    private String poster;
    private String plot;

    public Serie() {

    }

    @Transient // ignora para não persistir no banco
    private List<Episode> episodes = new ArrayList<>();


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTotalSeasons() {
        return totalSeasons;
    }

    public void setTotalSeasons(Integer totalSeasons) {
        this.totalSeasons = totalSeasons;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Categories getGenre() {
        return genre;
    }

    public void setGenre(Categories genre) {
        this.genre = genre;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) { this.plot = plot;
    }

    @Override
    public String toString() {
        return "Serie{" +
                "titulo='" + title + '\'' +
                ", totalTemporadas=" + totalSeasons +
                ", avaliacao=" + rate +
                ", genero=" + genre +
                ", atores='" + actors + '\'' +
                ", poster='" + poster + '\'' +
                ", sinopse='" + plot + '\'' +
                '}';
    }

    // declarando o construtor da classe para transformar os dados
    public Serie(SeriesData seriesData){
        this.title = seriesData.title();
        this.totalSeasons = seriesData.totalSeasons();
        // forcando a transformacao do atributo avalicao, que string para double com o Optional
        // if/else melhorado
        this.rate = OptionalDouble.of(Double.parseDouble(seriesData.rate())).orElse(0);
        // precisamos de um metodo auxiliar para conversao dos dados
        // captura apenas o primeiro genero da lista de strings do Ombd como categoria
        this.genre = Categories.fromString(seriesData.genre().split(",")[0].trim());
        this.actors = seriesData.actors();
        this.poster = seriesData.poster();
        // traduz a sinopse
        this.plot = MyMemoryRequest.translate(seriesData.plot()).trim(); // trim para nao ter espaço em branco nem quebra de linha
    }

}
