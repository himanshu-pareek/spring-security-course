# Method level authorization

Applying authorization rules at the method level.

Use cases - Non web applications, Fine grained authorization rules for different methods

- Pre Authorization - Apply authorization before executing the method. Can use method parameters.
- Post Authorization - Apply authorization after the method has been executed. Can use returned value as well.

## Enable Method Security

- Disabled by default
- @EnableMethodSecurity

## @PreAuthorize

- Apply authorization rules bofore calling the method
- Accepts Spring SpEL as argument

```java
@PreAuthorize("hasAuthority('write')")
String hello() {
    return "Hello";
}

```

- hasAuthority(String)
- hasAnyAuthority(String ...)
- hasRole(String)
- hasAnyRole(String ...)

Access the method parameters:

```java
@PreAuthorize("#name == 'abcd'")
String hello(String name) {
    return "";
}

```

Access the Authentication Token from Security Context

```java
@PreAuthorize("#name == authentication.principal.username")
String hello(String name) {
    return "Hello " + name;
}

```

Apply multiple conditions

```java
@PreAuthorize("hasAuthority('write') && #name == authentication.principal.username")
String hello(String name) {
    return "Hello " + name;
}

```

## @PostAuthorize

Apply the authorization rules on the returned value of the method
The method is called
The side effects are preserved: Be cautious

```java
@PostAuthorize("returnObject.author == authentication.principal.username")
Document getDocument(int documentId) {
    // Find document by id
}

```

## Permission Evaluator

- Implement authorization rules in a separate class
- "hasPermission(object, permission)"
- Implement [PermissionEvaluator](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/access/PermissionEvaluator.html) interface
    - object, permission
    - objectId, objectType, permission
- Register the implementation with spring security

```java
@Bean
MethodSecurityExpressionHandler methodSecurityExpressionHandler() {

    var mseh = new DefaultMethodSecurityExpressionHandler();

    mseh.setPermissionEvaluator(new OurPermissionEvaluator());

    return mseh;
}

```

## References

- https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/access/PermissionEvaluator.html
- https://docs.spring.io/spring-framework/reference/core/expressions.html
- https://docs.spring.io/spring-security/reference/servlet/authorization/method-security.html