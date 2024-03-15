package testing.urls.lampit.testcase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import testing.urls.lampit.model.URLMapping;
import testing.urls.lampit.repository.URLMappingRepository;
import testing.urls.lampit.service.URLShortenerService;

public class URLShortenerServiceTest {

    private final URLMappingRepository urlMappingRepository = mock(URLMappingRepository.class);
    private final URLShortenerService urlShortenerService = new URLShortenerService(urlMappingRepository);

    @Test
    void testShortenURLWithCustomAlias() {
        String originalURL = "http://example.com";
        String customAlias = "vale";
        when(urlMappingRepository.findByAlias(customAlias)).thenReturn(null);
        String result = urlShortenerService.shortenURL(originalURL, customAlias);
        assertEquals(customAlias, result);
        verify(urlMappingRepository, times(1)).save(any(URLMapping.class));
    }

    @Test
    void testAliasExists() {
        String alias = "vale";
        when(urlMappingRepository.findByAlias(alias)).thenReturn(new URLMapping());
        boolean exists = urlShortenerService.aliasExists(alias);
        assertTrue(exists);
    }
}