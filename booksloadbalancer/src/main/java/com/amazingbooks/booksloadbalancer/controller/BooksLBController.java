package com.amazingbooks.booksloadbalancer.controller;

import com.amazingbooks.booksloadbalancer.dto.BookPutDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class BooksLBController {

    /**
     * Testing APIs
     * curl -X PUT "http://localhost:9001/book/add" -H "Content-Type: application/json"  -d '{"isbn": "isbn001", "title": "Title1", "publishedDate": "2022-03-11", "totalCopies": 100, "issuedCopies": 20, "author": "Anand"}'
     * curl -X GET "http://localhost:9001/books/fetch"
     * curl -X PUT "http://localhost:9001/book/add" -H "Content-Type: application/json"  -d '{"isbn": "isbn002", "title": "Title2", "publishedDate": "2022-06-11", "totalCopies": 150, "issuedCopies": 20, "author": "Anand2"}'
     * curl -X PUT "http://localhost:9001/book/edit" -H "Content-Type: application/json"  -d '{"totalCopies": 2500, "issuedCopies": 590, "author": "Anand2"}'
     * curl -X PUT "http://localhost:9001/book/delete" -H "Content-Type: application/json"  -d '{"totalCopies": 2500, "issuedCopies": 590, "author": "Anand2"}'
     */

    @Autowired
    LoadBalancerClientFactory booksClientFactory;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/books/fetch")
    public String fetchBooks() {
        String url = generateUrl("/books/fetch");
        return restTemplate.getForObject(url, String.class);
    }

    @GetMapping("/hello")
    public String hello() {
        restTemplate.getUriTemplateHandler();
        String url = generateUrl("/hello");
        return restTemplate.getForObject(url, String.class);
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the world of load balancer of books!";
    }


    @PutMapping(value = "/book/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addBook(@RequestBody BookPutDTO bookDto) {

        String url = generateUrl("/book/add");
        restTemplate.put(url, bookDto);

        return "BooksLBController: Book added = " + bookDto + "\r";
    }

    @PutMapping(value = "/book/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String editBook(@RequestBody BookPutDTO bookDto) {
        String url = generateUrl("/book/edit");
        restTemplate.put(url, bookDto);
        return "Book updated\n";
    }


    @PutMapping(value = "/book/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String deleteBook(@RequestBody BookPutDTO bookDto) {
        String url = generateUrl("/book/delete");
        restTemplate.put(url, bookDto);
        return "Book deleted\n";
    }
    @CircuitBreaker(name="generateUrlFailure",fallbackMethod="generateUrlFailure")
    private String generateUrl(String baseUrl) {
        RoundRobinLoadBalancer roundRobinLoadBalancer = booksClientFactory.getInstance("book_ms", RoundRobinLoadBalancer.class);

        ServiceInstance serviceInstance = roundRobinLoadBalancer.choose().block().getServer();
        if (baseUrl.startsWith("/"))
            baseUrl = baseUrl.substring(1);

        return String.format("http://%s:%d/%s", serviceInstance.getHost(), serviceInstance.getPort(), baseUrl);
    }

    public String generateUrlFailure(Exception ex) {
        return "generateUrl failed!!" + ex;
    }

}
