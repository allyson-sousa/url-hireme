package testing.urls.lampit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testing.urls.lampit.exceptions.ShortenedURLNotFoundException;
import testing.urls.lampit.model.URLMapping;
import testing.urls.lampit.repository.URLMappingRepository;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class URLShortenerService {

    private final URLMappingRepository urlMappingRepository;

    @Autowired
    public URLShortenerService(URLMappingRepository urlMappingRepository) {
        this.urlMappingRepository = urlMappingRepository;
    }

    public boolean aliasExists(String alias) {
        return urlMappingRepository.findByAlias(alias) != null;
    }

    public String shortenURL(String url, String customAlias) {
        String alias;
        if (customAlias != null && !aliasExists(customAlias)) {
            alias = customAlias;
        } else {
            alias = generateAlias();
        }

        // Definir a data de criação
        LocalDateTime createdAt = LocalDateTime.now();

        // Criar a entidade URLMapping
        URLMapping urlMapping = new URLMapping();
        urlMapping.setOriginalURL(url);
        urlMapping.setAlias(alias);
        urlMapping.setCreatedAt(createdAt); // Data de criação da URL


        urlMappingRepository.save(urlMapping);

        return alias;
    }

    private String generateAlias() {
        String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int aliasLength = 6;

        StringBuilder aliasBuilder = new StringBuilder();
        Random random = new Random();

        // Gera o alias com caracteres aleatórios
        for (int i = 0; i < aliasLength; i++) {
            int randomIndex = random.nextInt(allowedChars.length());
            aliasBuilder.append(allowedChars.charAt(randomIndex));
        }

        return aliasBuilder.toString();
    }

    public String getOriginalURL(String alias) {
        URLMapping urlMapping = urlMappingRepository.findByAlias(alias);
        if (urlMapping != null) {
            return urlMapping.getOriginalURL();
        } else {
            throw new ShortenedURLNotFoundException();
        }
    }
    public void incrementAccessCount(String alias) {
        URLMapping urlMapping = urlMappingRepository.findByAlias(alias);
        if (urlMapping != null) {
            int accessCount = urlMapping.getHits() + 1;
            urlMapping.setHits(accessCount);
            urlMappingRepository.save(urlMapping);
        }
    }
    public List<URLMapping> getTop10MostAccessedURLs() {
        return urlMappingRepository.findTop10ByOrderByHitsDesc();
    }
}
