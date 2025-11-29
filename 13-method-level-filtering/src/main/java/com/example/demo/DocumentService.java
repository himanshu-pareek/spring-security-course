package com.example.demo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {

  List<Document> documents;

  DocumentService() {
    this.documents = new ArrayList<>();
    this.documents.add(new Document(1, "java", "Document 1"));
    this.documents.add(new Document(2, "rush", "Document 2"));
    this.documents.add(new Document(3, "java", "Document 3"));
  }

  public Collection<Document> updateDocuments(Collection<Document> documents) {
    System.out.println("Updating documents - ");
    documents.forEach(System.out::println);
    return documents;
  }

  public Collection<Document> getDocuments() {
    return this.documents;
  }
}
