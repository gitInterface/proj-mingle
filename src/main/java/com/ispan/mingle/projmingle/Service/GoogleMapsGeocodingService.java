package com.ispan.mingle.projmingle.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ispan.mingle.projmingle.domain.WorkBean;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class GoogleMapsGeocodingService {
    @Value("${google.maps.apiKey}")
    private String apiKey;

    public String getFormattedAddress(WorkBean work) {
        String fullAddress = work.getCity()+work.getAddress();
        String apiUrl = "https://maps.googleapis.com/maps/api/geocode/json?address="+fullAddress+"&key={AIzaSyChYl423JJyZHyoVgPhUWBgi7bLCH3pGNA}";

        // Create a RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Define a ParameterizedTypeReference to specify the generic type
        ParameterizedTypeReference<Map<String, Object>> responseType =
                new ParameterizedTypeReference<Map<String, Object>>() {};

        // Make the request to Google Maps Geocoding API
        ResponseEntity<Map<String, Object>> responseEntity = restTemplate
                .exchange(apiUrl, HttpMethod.GET, null, responseType, fullAddress, apiKey);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> response = responseEntity.getBody();

            if (response != null && "OK".equals(response.get("status"))) {
                List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");
                if (results != null && results.size() > 0) {
                    return (String) results.get(0).get("formatted_address");
                }
            }
        }

        // Return the original address if the request fails or no data is found
        return fullAddress;
    }
    
    public List<String> getFormattedAddresses(List<WorkBean> workBeans) {
        return workBeans.stream()
                .map(this::getFormattedAddress)
                .collect(Collectors.toList());
    }
}
