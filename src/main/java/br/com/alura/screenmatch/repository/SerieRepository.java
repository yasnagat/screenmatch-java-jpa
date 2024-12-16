package br.com.alura.screenmatch.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import br.com.alura.screenmatch.model.Serie;

// esse repositorio manipula uma classe para transmitir para o banco de dados
public interface SerieRepository extends JpaRepository<Serie, Long> {
}
