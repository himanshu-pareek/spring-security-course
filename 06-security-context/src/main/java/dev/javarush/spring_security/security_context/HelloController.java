package dev.javarush.spring_security.security_context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.concurrent.DelegatingSecurityContextRunnable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  private final UserService userService;

  public HelloController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public String sayHello() {
    String username = loggedInUsername();
    this.userService.sendWelcomeEmail();
    return "Hello " + username + "\n";
  }

  String loggedInUsername() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    Authentication authentication = securityContext.getAuthentication();
    return authentication.getName();
  }
}

@Service
class UserService {

  private final ExecutorService executorService;

  UserService() {
    executorService = Executors.newFixedThreadPool(1);
  }

//  @Async
//  void sendWelcomeEmail() {
//    try {
//      Thread.sleep(5000);
//    } catch (InterruptedException e) {
//      throw new RuntimeException(e);
//    }
//    String username = loggedInUsername();
//    System.out.println("Sending email to " + username + "...");
//  }

  void sendWelcomeEmail() {
    Runnable emailSender = () -> {
      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      String username = loggedInUsername();
      System.out.println("Sending email to " + username + "...");
    };
    DelegatingSecurityContextRunnable runnable = new DelegatingSecurityContextRunnable(emailSender);
    this.executorService.submit(runnable);
  }

  String loggedInUsername() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    Authentication authentication = securityContext.getAuthentication();
    return authentication.getName();
  }
}
