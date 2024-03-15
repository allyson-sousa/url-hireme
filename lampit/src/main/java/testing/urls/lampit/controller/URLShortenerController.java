package testing.urls.lampit.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import testing.urls.lampit.controller.response.ErrorResponse;
import testing.urls.lampit.controller.response.ShortenedURLResponse;
import testing.urls.lampit.model.URLMapping;
import testing.urls.lampit.repository.URLMappingRepository;
import testing.urls.lampit.service.URLShortenerService;

import java.util.List;


@RestController
@RequestMapping("/shortener")
public class URLShortenerController {
    private URLMapping urlMapping;
    private URLMappingRepository urlMappingRepository;
    private final URLShortenerService urlShortenerService;

    public URLShortenerController(URLShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PutMapping("/create")
    public ResponseEntity<?> shortenURL(@RequestParam String url, @RequestParam(required = false) String CUSTOM_ALIAS) {
        long inicio = System.currentTimeMillis();

        // Verifica se o CUSTOM_ALIAS já existe
        if (CUSTOM_ALIAS != null && urlShortenerService.aliasExists(CUSTOM_ALIAS)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("001", "CUSTOM ALIAS ALREADY EXISTS"));
        }

        String alias = urlShortenerService.shortenURL(url, CUSTOM_ALIAS);

        long fim = System.currentTimeMillis();
        long timeTaken = fim - inicio;

        String shortenedURL = "http://url-encurtada/u/" + alias;

        ShortenedURLResponse response = new ShortenedURLResponse(alias, shortenedURL, timeTaken + "ms");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/u/{alias}")
    public ResponseEntity<?> redirectToOriginalURL(@PathVariable String alias) {
        String originalURL = urlShortenerService.getOriginalURL(alias);

        if (originalURL == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("002", "SHORTENED URL NOT FOUND"));
        } else {
            if (!originalURL.startsWith("http://") && !originalURL.startsWith("https://")) {
                originalURL = "http://" + originalURL;
            }
        }

        urlShortenerService.incrementAccessCount(alias);

        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, originalURL)
                .build();
    }

    @GetMapping("/top-10")
    public ResponseEntity<List<URLMapping>> getTop10MostAccessedURLs() {
        List<URLMapping> top10URLs = urlShortenerService.getTop10MostAccessedURLs();
        return ResponseEntity.ok(top10URLs);
    }

}

