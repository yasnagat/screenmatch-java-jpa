package br.com.alura.screenmatch.model;

import java.time.DateTimeException;
import java.time.LocalDate;

public class Episode {
    private String title;
    private Integer seasonNumber;
    private Integer episodeNumber;
    private Double rating;
    private LocalDate launchDate;

    public Episode(Integer seasonNumber, EpisodesData episodesData) {
        this.seasonNumber = seasonNumber;
        this.title = episodesData.title();
        this.episodeNumber = episodesData.number();

        try {
            this.rating = Double.valueOf(episodesData.rating());

        } catch (NumberFormatException e) {
            this.rating = 0.0;
        }
        try {

            this.launchDate = LocalDate.parse(episodesData.launchDate());
        } catch (DateTimeException e) {
            this.launchDate = null;
        }

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public LocalDate getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(LocalDate launchDate) {
        this.launchDate = launchDate;
    }
    @Override
    public String toString() {
        return                 "Título = '" + title + '\'' +
                ", Número Episódio = " + episodeNumber +
                ", Avaliação = " + rating +
                ", Data Lançamento = " + launchDate;
    }


}
