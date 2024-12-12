package br.com.alura.screenmatch.service;

public interface IDataConverter {

    <T> T dataObtainer(String json, Class<T> classe);

}
