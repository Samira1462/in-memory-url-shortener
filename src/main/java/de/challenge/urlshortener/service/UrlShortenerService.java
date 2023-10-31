package de.challenge.urlshortener.service;

import de.challenge.urlshortener.dto.ResponseDto;

import java.util.Optional;

public interface UrlShortenerService {

    Optional<ResponseDto> encode(String longUrl);

    Optional<ResponseDto> decode(String shortenerUrl);
}
