package com.cash_manager.api.services;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class BankService {

    WebClient webClient = WebClient.create();

    public ClientResponse makePostRequest(String url,String json) {
        
         // Replace with the API URL
        ClientResponse clientResponse = webClient
    .post()
    .uri(url)
    .contentType(MediaType.APPLICATION_JSON)
    .body(BodyInserters.fromValue(json))
    .exchange()
    .block();
        return clientResponse;

        // Handle the response
    }
    public ClientResponse makeGetRequest(String url) {
        
         // Replace with the API URL
        ClientResponse clientResponse = webClient
        .get()
        .uri(url)
        .accept(MediaType.APPLICATION_JSON)
        .exchangeToMono(response -> {
                return Mono.just(response);
        })
        .block();
        return clientResponse;

        // Handle the response
    }
}
