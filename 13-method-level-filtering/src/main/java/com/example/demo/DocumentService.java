package com.example.demo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {

  List<Document> documents;

  DocumentService() {
    this.documents = List.of(
      new Document(1, "java", "Document 1"),
      new Document(2, "rush", "Document 2"),
      new Document(3, "java", "Document 3")
    );
  }

  @PreFilter("filterObject.owner == authentication.name")
  public Collection<Document> updateDocuments(Collection<Document> docs) {
    System.out.println("Updating docs - ");
    docs.forEach(System.out::println);
    return docs;
  }

  @PostFilter("filterObject.owner == authentication.name")
  public Collection<Document> getDocuments() {
    System.out.println("len - " + this.documents.size());
    return this.documents;
  }
}
