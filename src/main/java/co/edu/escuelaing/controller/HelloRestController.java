package co.edu.escuelaing.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para manejar solicitudes HTTP.
 */
@RestController
public class HelloRestController {

    private static final String template = "Hello, %s!";

    @GetMapping("/greeting")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format(template, name);
    }

    @GetMapping("/")
    public String home() {
        return "Welcome to Spring Docker App!";
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}