package com.ispan.mingle.projmingle.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.mingle.projmingle.domain.CountryBean;
import com.ispan.mingle.projmingle.repository.CountryRepository;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public List<CountryBean> getAllCountries() {
        List<CountryBean> countries = countryRepository.findAll();
        if (countries != null && !countries.isEmpty()) {
            return countries;
        } else {
            return null;
        }
    }
}
