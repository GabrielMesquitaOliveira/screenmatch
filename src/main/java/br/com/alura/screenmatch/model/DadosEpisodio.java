package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosEpisodio(@JsonAlias("Title") String titulo,
        @JsonAlias("Episode") Integer episodio,
        @JsonAlias("Season") Integer temporada,
        @JsonAlias("imdbRating") String rating,
        @JsonAlias("Released") String lancamento) {

}
