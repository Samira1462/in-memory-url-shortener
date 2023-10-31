package de.challenge.urlshortener.service;

import de.challenge.urlshortener.repository.UrlRepository;
import de.challenge.urlshortener.dto.ResponseDto;
import de.challenge.urlshortener.entity.Url;
import de.challenge.urlshortener.exception.NotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UrlShortenerServiceImplTest {

    @InjectMocks
    private UrlShortenerServiceImpl urlShortenerServiceUnderTest;

    static Map<String, Url> repo;

    @BeforeAll
    public static void initializeRepo() throws Exception {
        Field field = UrlRepository.class.getDeclaredField("repo");
        field.setAccessible(true);
        repo = (Map<String, Url>) field.get(UrlRepository.class);
        var id = UUID.randomUUID();
        repo.put(id.toString(), new Url(id.toString(), "http://home.com", new StringBuilder("http://bl.co/abdcfg")));
    }

    @Test
    public void encode_whenUrlExists_thenReturnResponseDto() {
        String longUrl = "http://home.com";
        StringBuilder shortUrl = new StringBuilder("http://bl.co/abdcfg");
        Optional<Map.Entry<String, Url>> result = UrlRepository.getOriginalUrl("http://home.com");
        try (MockedStatic<UrlRepository> urlRepositoryMockedStatic = Mockito.mockStatic(UrlRepository.class)) {
            urlRepositoryMockedStatic.when(() -> UrlRepository.getOriginalUrl(longUrl)).thenReturn(result);
        }

        Optional<ResponseDto> actual = urlShortenerServiceUnderTest.encode(longUrl);

        ResponseDto responseDto = actual.get();
        assertEquals(longUrl, responseDto.getOriginalUrl());
        assertEquals(shortUrl.toString(), responseDto.getShortUrl().toString());
        assertNotNull(responseDto);
    }

    @Test
    public void encode_whenUrlDoesNotExistInRepo_thenAddAndReturnResponseDto() {

        String longUrl = "http://home.com";
        String shortUrl = "http://bl.co/abdcfg";

        try (MockedStatic<UrlRepository> urlRepositoryMockedStatic = Mockito.mockStatic(UrlRepository.class)) {
            urlRepositoryMockedStatic.when(() -> UrlRepository.getOriginalUrl(longUrl)).thenReturn(Optional.empty());
        }

        Optional<ResponseDto> result = urlShortenerServiceUnderTest.encode(longUrl);

        assertTrue(result.isPresent());
        ResponseDto responseDto = result.get();
        assertEquals(shortUrl, responseDto.getShortUrl().toString());
    }

    @Test
    public void decode_whenShortenerUrlExists_thenReturnResponseDto() {

        StringBuilder shortenerUrl = new StringBuilder("http://bl.co/abdcfg");
        String longUrl = "http://home.com";
        Optional<Map.Entry<String, Url>> result = UrlRepository.getShortenerUrl(shortenerUrl);
        try (MockedStatic<UrlRepository> urlRepositoryMockedStatic = Mockito.mockStatic(UrlRepository.class)) {
            urlRepositoryMockedStatic.when(() -> UrlRepository.getShortenerUrl(shortenerUrl)).thenReturn(result);
        }

        Optional<ResponseDto> actual = urlShortenerServiceUnderTest.decode(shortenerUrl.toString());

        assertTrue(result.isPresent());
        ResponseDto responseDto = actual.get();
        assertEquals(longUrl, responseDto.getOriginalUrl());
    }

    @Test
    public void decode_whenShortenerUrlDoesNotExist_thenThrowNotFoundException() {

        StringBuilder shortenerUrl = new StringBuilder("");

        try (MockedStatic<UrlRepository> urlRepositoryMockedStatic = Mockito.mockStatic(UrlRepository.class)) {
            urlRepositoryMockedStatic.when(() -> UrlRepository.getShortenerUrl(shortenerUrl)).thenReturn(Optional.empty());
        }

        assertThrows(NotFoundException.class, () -> urlShortenerServiceUnderTest.decode(shortenerUrl.toString()));
    }
}
