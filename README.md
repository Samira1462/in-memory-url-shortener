### Objective

implement a URL shortening service using Java and Spring.

### Brief

ShortLink is a URL shortening service where you enter a URL such as https://codesubmit.io/library/react and it returns a short URL such as http://short.est/GeAi9K.

### Tasks

-   Implement assignment using:
    -   Language: **Java**
    -   Framework: **Spring**
    -   Two endpoints are required
        -   /encode - Encodes a URL to a shortened URL
        -   /decode - Decodes a shortened URL to its original URL.
    -   Both endpoints should return JSON
    -   Choose the HTTP verbs that make the most sense.
-   There is no restriction on how your encode/decode algorithm should work. You just need to make sure that a URL can be encoded to a short URL and the short URL can be decoded to the original URL. **You do not need to persist short URLs to a database. Keep them in memory.**
-   Provide detailed instructions on how to run your assignment in a separate markdown file
-   Provide API tests for both endpoints

### Evaluation Criteria

-   Java & Spring best practices
-   API implemented featuring a /encode and /decode endpoint
-   Tests
-   Implementation of the shortening algorithm (Algorithm Correctness, Requirement Fulfillment)

### ThoughtProcess
A [thought-process.md](thought-process.md) file