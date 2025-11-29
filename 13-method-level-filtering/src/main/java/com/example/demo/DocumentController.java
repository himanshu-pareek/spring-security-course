package com.example.demo;

import java.util.Collection;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("documents")
public class DocumentController {

  private final DocumentService service;

  public DocumentController(DocumentService service) {
    this.service = service;
  }

  @PostMapping
  Collection<Document> updateDocuments(@RequestBody List<Document> documents) {
    List<Document> docs = List.of(documents.get(0), documents.get(1), documents.get(2));
    var result = this.service.updateDocuments(documents);
    System.out.println("--------- Documents ------------------");
    documents.forEach(System.out::println);
    return result;
  }

  @GetMapping
  Collection<Document> getDocuments() {
    return this.service.getDocuments();
  }
}
