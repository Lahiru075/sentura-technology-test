package lk.ijse.gdse.countryservice.controller;

import lk.ijse.gdse.countryservice.dto.CountryDTO;
import lk.ijse.gdse.countryservice.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/countries")
@CrossOrigin(origins = "*")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping
    public List<CountryDTO> getCountries() {
        return countryService.getAllCountries();
    }

    @GetMapping("/search")
    public List<CountryDTO> search(@RequestParam String query) {
        return countryService.searchCountries(query);
    }
}
