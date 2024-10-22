package br.com.alura.screenmatch;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {
	@Value("${api.key}")
	private String apiKey;

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (apiKey == null || apiKey.isEmpty()) {
			throw new IllegalArgumentException("API_KEY não encontrada. Defina a variável de ambiente API_KEY.");
		}

		ConsumoAPI consumoAPI = new ConsumoAPI();
		String title = "gotham";

		var json = consumoAPI.obterDados("https://www.omdbapi.com/?t=" + title + "&apikey=" + apiKey);
		ConverteDados conversor = new ConverteDados();
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dados);

		json = consumoAPI.obterDados("https://www.omdbapi.com/?t=" + title + "&season=1&episode=1&apikey=" + apiKey);
		DadosEpisodio dadosEp = conversor.obterDados(json, DadosEpisodio.class);
		System.out.println(dadosEp);

		List<DadosTemporada> temporadas = new ArrayList<>();

		for (int i = 1; i <= dados.totalSeasons(); i++) {
			json = consumoAPI.obterDados("https://www.omdbapi.com/?t=" + title + "&season=" + i + "&apikey=" + apiKey);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}

		temporadas.forEach(System.out::println);
	}
}