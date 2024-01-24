package com.luizvidal.angularcrudapi.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.luizvidal.angularcrudapi.domain.model.Product;
import com.luizvidal.angularcrudapi.repository.ProductRepository;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    private final ProductRepository productRepository;

    private final String URI = "/{id}";

    @GetMapping
    public List<Product> get() {
        return this.productRepository.findAll();
    }

    @GetMapping(URI)
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return this.productRepository.findById(id).map(product -> ResponseEntity.ok(product))
                .orElse(ResponseEntity.notFound().build());

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product post(
            @Valid @RequestBody Product product) {

        return this.productRepository.save(product);
    }

    @PutMapping(URI)
    public ResponseEntity<Product> put(@PathVariable Long id, @RequestBody Product product) {
        if (!this.productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        product.setId(id);

        return ResponseEntity.ok(this.productRepository.save(product));
    }

    @DeleteMapping(URI)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!this.productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        this.productRepository.deleteById(id);

        return ResponseEntity.noContent().build();

    }

}
