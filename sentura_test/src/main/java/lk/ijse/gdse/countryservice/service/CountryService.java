package lk.ijse.gdse.countryservice.service;

import lk.ijse.gdse.countryservice.dto.CountryDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CountryService {
    private List<CountryDTO> cache = new ArrayList<>();
    private long lastFetched = 0;
    private static final long CACHE_DURATION = 10 * 60 * 1000; // 10 Minutes

    public List<CountryDTO> getAllCountries() {
        if (cache.isEmpty() || (System.currentTimeMillis() - lastFetched > CACHE_DURATION)) {
            fetchFromExternalApi();
        }
        return cache;
    }

    private void fetchFromExternalApi() {
        String url = "https://restcountries.com/v3.1/all";
        RestTemplate restTemplate = new RestTemplate();
        List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);

        this.cache = response.stream().map(this::mapToDTO).collect(Collectors.toList());
        this.lastFetched = System.currentTimeMillis();
    }

    private CountryDTO mapToDTO(Map<String, Object> map) {
        CountryDTO dto = new CountryDTO();
        dto.setName((String) ((Map<String, Object>) map.get("name")).get("common"));

        List<String> capitals = (List<String>) map.get("capital");
        dto.setCapital(capitals != null && !capitals.isEmpty() ? capitals.get(0) : "N/A");

        dto.setRegion((String) map.get("region"));
        dto.setPopulation(((Number) map.get("population")).longValue());
        dto.setFlag((String) ((Map<String, Object>) map.get("flags")).get("png"));
        return dto;
    }

    public List<CountryDTO> searchCountries(String query) {
        return getAllCountries().stream()
                .filter(c -> c.getName().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}
