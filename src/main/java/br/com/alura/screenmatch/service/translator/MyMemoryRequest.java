package br.com.alura.screenmatch.service.translator;

import br.com.alura.screenmatch.service.APIConsumer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URLEncoder;

public class MyMemoryRequest {
    public static String translate(String text) {
        ObjectMapper mapper = new ObjectMapper();
        APIConsumer consumer = new APIConsumer();

        String texts = URLEncoder.encode(text);
        String langpair = URLEncoder.encode("en|pt-br");

        String URL = "https://api.mymemory.translated.net/get?q=" + texts + "&langpair=" + langpair;

        String json = consumer.dataObtainer(URL);

        TranslatedData translation;
        try {
            translation = mapper.readValue(json, TranslatedData.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return translation.dataResponse().translatedText();
    }

}

