package me.desu.chromecookieextractor.controller;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.desu.chromecookieextractor.dto.CookiesDTO;
import me.desu.chromecookieextractor.service.ExtractorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController("/api/extractor")
public class ExtractorController {
    private final ExtractorService extractorService;

    @SneakyThrows
    @GetMapping("/read-cookie")
    public List<CookiesDTO> readCookie(@RequestParam String hostKey) {
        return extractorService.readCookie(hostKey);
    }
}
