package br.com.alura.screenmatch.service.translator;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TranslatedData(@JsonAlias(value="responseData") ResponseData dataResponse) {
}
