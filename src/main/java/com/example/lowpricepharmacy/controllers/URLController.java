package com.example.lowpricepharmacy.controllers;

import com.example.lowpricepharmacy.entity.OriginalURL;
import com.example.lowpricepharmacy.entity.ShortenedURL;
import com.example.lowpricepharmacy.services.URLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class URLController {

    private final URLService service;
    private String shortURL;

    @Autowired
    public URLController(URLService service) {
        this.service = service;
    }

    @PostMapping("/create-short")
    public String convertToShort(@RequestBody OriginalURL originalURL) {
        ShortenedURL url = new ShortenedURL();
        url.setShortenedURL(service.shortenURL(originalURL.getOriginalName()));
        shortURL = url.getShortenedURL();

        return url.getShortenedURL();
    }

    @GetMapping("/original-url-redirect")
    public RedirectView getURl() {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(service.getProtocolName() + service.expandURL(shortURL));

        return redirectView;
    }

}
