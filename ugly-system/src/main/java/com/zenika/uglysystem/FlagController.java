package com.zenika.uglysystem;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("/flags")
public class FlagController {

    @GetMapping
    public List<String> getFlags() {
        return asList(
        );
    }

}