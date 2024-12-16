package br.com.alura.screenmatch.service.translator;

import br.com.alura.screenmatch.service.APIConsumer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URLEncoder;

// cria uma chamada para API MyMemory para traduzir o texto da sinopse
public class MyMemoryRequest {
    public static String translate(String text) {
        // criando os objetos das classes de serviço
        ObjectMapper mapper = new ObjectMapper();
        APIConsumer consumer = new APIConsumer();

        String texts = URLEncoder.encode(text);
        String langpair = URLEncoder.encode("en|pt-br");

        String URL = "https://api.mymemory.translated.net/get?q=" + texts + "&langpair=" + langpair;

        // request para API
        String json = consumer.dataObtainer(URL);

        // executa a traducao
        TranslatedData translation;
        try {
            translation = mapper.readValue(json, TranslatedData.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return translation.dataResponse().translatedText();
    }

}

