package com.example.lowpricepharmacy.controllers;

import com.example.lowpricepharmacy.entity.OriginalURL;
import com.example.lowpricepharmacy.services.URLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class URLController {

    private final URLService service;

    @Autowired
    public URLController(URLService service) {
        this.service = service;
    }

    @PostMapping("/create-short")
    public String convertToShort(@RequestBody OriginalURL originalURL) {
        return service.shortenURL(originalURL.getOriginalName());
    }

    @GetMapping(value = "/{shortenedUrl}")
    public RedirectView getURl(@PathVariable String shortenedUrl) {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(service.searchLongUrlInMap(shortenedUrl));

        return redirectView;
    }

}
