# Method Level Filtering

Allows certain authorized values in method parameters and as return values

- PreFiltering - Filter the values of the parameters before invoking the method
- PostFiltering - Filter the returned values from the method

Can only be applied to parameters and return values of type Collection.

## @Prefilter

- Changes the original collection, does not create a new one
- Immutable collections won't work - won't filter anything
- Supports arrays, collections, maps, streams (should be open)

## @Postfilter

Filter the elements in returned collection.

- Changes the original collection (for mutable collections) / create a new one (for immutable collections)
- Supports arrays, collections, maps, streams (should be open)

## References

- https://docs.spring.io/spring-security/reference/servlet/authorization/method-security.html#use-prefilter
- https://docs.spring.io/spring-security/reference/api/java/org/springframework/security/access/prepost/PreFilter.html
- https://docs.spring.io/spring-security/reference/api/java/org/springframework/security/access/prepost/PostFilter.html