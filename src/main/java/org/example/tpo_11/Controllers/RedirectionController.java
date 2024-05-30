package org.example.tpo_11.Controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.tpo_11.Services.RedirectService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;


@RestController
@RequestMapping(path="/red",
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class RedirectionController
{
    private final RedirectService redirectService;

    public RedirectionController(RedirectService redirectService)
    {
        this.redirectService = redirectService;
    }

    @Tag(name = "GET", description = "Get original link")
    @GetMapping("/{id}")
    public ResponseEntity<?> redirect(@PathVariable String id) {
        if (redirectService.incrementVisits(id)) {
            URI savedBookLocation;
            try
            {
                savedBookLocation = new URI(redirectService.getLinkById(id).getTargetUrl());
                HttpHeaders headers = new HttpHeaders();
                headers.add("Location", String.valueOf(savedBookLocation));
                return new ResponseEntity<>(savedBookLocation.toString(),headers, HttpStatus.FOUND);
            } catch (URISyntaxException e)
            {
                throw new RuntimeException(e);
            }

        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
