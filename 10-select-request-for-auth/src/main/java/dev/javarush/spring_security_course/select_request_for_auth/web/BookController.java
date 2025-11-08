package dev.javarush.spring_security_course.select_request_for_auth.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("books")
public class BookController {
  @GetMapping
  String list() {
    return "[ List of books ]\n";
  }

  @GetMapping("{id}/authors")
  String authors(@PathVariable("id") String bookId) {
    return "[ List of authors for book " + bookId + " ]\n";
  }

  @GetMapping("{id}")
  String one(@PathVariable("id") String bookId) {
    return "[ One book - " + bookId + " ]\n";
  }

  @PostMapping
  String create() {
    return "[ Created book ]";
  }
}
