package com.fire.simulation.service;

import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fire.simulation.dto.ConfigurationDto;
import java.io.InputStream;
import org.springframework.core.io.Resource;

@Service
public class ConfigurationService {

    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ConfigurationService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * Read configuration from JSON file. (used in SimulationService)
     * @return ConfigurationDto
     */
    public ConfigurationDto loadConfiguration() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:configuration.json");
        try (InputStream inputStream = resource.getInputStream()) {
            return objectMapper.readValue(inputStream, ConfigurationDto.class);
        }
    }
}
