package com.motoparts.api.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import java.util.Map;

@Service
public class MonedaExternalService {

    private final RestClient restClient;

    public MonedaExternalService(RestClient.Builder restClientBuilder) {
        // Consumimos una API pública y gratuita de tasas de cambio (Pesos Colombianos a otras monedas)
        this.restClient = restClientBuilder.baseUrl("https://open.er-api.com/v6/latest/COP").build();
    }

    @SuppressWarnings("unchecked")
    public Double obtenerTasaDolar() {
        try {
            Map<String, Object> response = restClient.get()
                    .retrieve()
                    .body(Map.class);
            
            if (response != null && response.containsKey("rates")) {
                Map<String, Object> rates = (Map<String, Object>) response.get("rates");
                Object usdRate = rates.get("USD");
                if (usdRate != null) {
                    return Double.parseDouble(usdRate.toString());
                }
            }
            return 0.0;
        } catch (RestClientResponseException e) {
            // Manejo controlado de errores de respuesta de la API externa
            System.err.println("Error HTTP al consumir la API externa: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            return 0.0;
        } catch (Exception e) {
            // Manejo de errores generales (conexión, red, formato, etc.)
            System.err.println("Error inesperado al consumir la API externa: " + e.getMessage());
            return 0.0;
        }
    }
}