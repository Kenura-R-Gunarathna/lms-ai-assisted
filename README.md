# Online Library Management System — AI-Assisted vs Traditional

| Key | Details |
|---|---|
| **Course** | IT 3003 – Advanced Programming Techniques |
| **Assessment** | Assessment 02 – Report Submission |
| **Assignment Title** | Analyzing the Role of AI-Assisted Programming in Modern Software Development |
| **Case Study** | Online Library Management System |
| **Name** | Kenura R. Gunarathna |
| **Index Number** | S17239 |
| **Registration No.** | 2023S20003 |

The same moderate-complexity problem — an **Online Library Management System
REST API** — is implemented **twice** so the two development approaches can be
compared. It is a Spring Boot + Gradle **multi-project** build.

| | Approach | Architecture |
|---|---|---|
| **version-a** | Traditional (written manually, no AI) | Monolithic `@RestController` holding data + logic |
| **version-b** | AI-assisted (ChatGPT draft, then reviewed) | Layered: **Controller → Service → Repository** (+ DTOs, exception→status mapping) |

## REST API (both versions)

| Method | Endpoint | Body | Success | Errors |
|---|---|---|---|---|
| `GET`    | `/api/library/books`  | — | `200` list of books | — |
| `POST`   | `/api/library/borrow` | `{"memberId","bookId"}` | `200` issued | `404` book not found · `409` unavailable |
| `DELETE` | `/api/library/return` | `{"memberId","bookId","returnDate"}` | `200` returned (+ fine if late) | `404` no active record |

Fine rule: 14-day loan, `10.0` per late day. Users are modelled with a `User`
hierarchy (`Student`, `Librarian`, `Administrator`).

## Project structure
```
lms-ai-assisted/
├── settings.gradle            # includes version-a and version-b
├── gradlew / gradlew.bat      # Gradle wrapper (9.6.1)
├── version-a/                 # traditional approach  (port 8080)
│   ├── build.gradle           # Spring Boot 4.1.0
│   └── src/main/java/com/library/
│       ├── LibraryApplication.java
│       ├── LibraryController.java     # everything in one class
│       └── Book, User, Student, Librarian, Administrator, BorrowRecord
└── version-b/                 # AI-assisted approach (layered, port 8081)
    ├── build.gradle
    └── src/
        ├── main/java/com/library/
        │   ├── LibraryApplication.java
        │   ├── controller/    # LibraryController (thin REST layer)
        │   ├── service/       # LibraryService (business logic)
        │   ├── repository/    # BookRepository (HashMap, O(1)), BorrowRepository
        │   ├── model/         # Book, User + roles, BorrowRecord
        │   ├── dto/           # BorrowRequest, ReturnRequest (records)
        │   └── exception/     # NotFoundException (404), ConflictException (409)
        └── test/java/com/library/service/
            └── LibraryServiceTest.java   # 6 JUnit 5 tests
```

## How to build, test & run (Gradle)

Build everything and run the Version B tests:
```bash
./gradlew build
```

Run the Version B tests only (expected: **6 tests, all passing**):
```bash
./gradlew :version-b:test
```

Run a server (Ctrl+C to stop):
```bash
./gradlew :version-a:bootRun     # traditional  -> http://localhost:8080
./gradlew :version-b:bootRun     # AI-assisted  -> http://localhost:8081
```

Try it once the server is up:
```bash
curl http://localhost:8081/api/library/books
curl -X POST http://localhost:8081/api/library/borrow \
     -H "Content-Type: application/json" \
     -d '{"memberId":"S001","bookId":"B001"}'
```

## Key difference illustrated
- **Version A** keeps the data (`ArrayList`) and all logic inside a single
  controller, uses a linear search, and sets HTTP status codes by hand.
- **Version B** (AI suggestions) separates concerns into
  **Controller → Service → Repository**, stores books in a `HashMap` for O(1)
  lookup, and throws domain exceptions that Spring maps to `404` / `409`
  automatically — keeping the controller thin and the service unit-testable.

See the accompanying report
(`../s17239_Assessment02_AI_Assisted_Programming.pdf`) for the full comparison
(development time, code quality, error handling, maintainability), the AI prompts
used, the UML diagrams, and the critical analysis.
