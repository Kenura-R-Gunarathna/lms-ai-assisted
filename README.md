# Online Library Management System — AI-Assisted vs Traditional

Case study for **Advanced Programming Concepts – Assessment 02**
*(Analyzing the Role of AI-Assisted Programming in Modern Software Development).*

The same moderate-complexity problem — an **Online Library Management System** —
is implemented **twice** so the two development approaches can be compared. It is
a Gradle **multi-project** build.

| | Approach | Architecture |
|---|---|---|
| **version-a** | Traditional (written manually, no AI) | Single monolithic `Library` class + model classes |
| **version-b** | AI-assisted (ChatGPT draft, then reviewed) | Layered: **Model → Repository → Service** |

## Features (both versions)
- Manage a book catalogue and users (`Student`, `Librarian`, `Administrator` via a `User` hierarchy)
- Borrow and return books with availability tracking
- Automatic fine calculation for late returns (14-day loan, 10.0 per late day)
- Handling for missing / unavailable books

## Project structure
```
lms-ai-assisted/
├── settings.gradle            # includes version-a and version-b
├── gradlew / gradlew.bat      # Gradle wrapper (9.6.1)
├── version-a/                 # traditional approach
│   ├── build.gradle
│   └── src/main/java/com/library/
│       └── Book, User, Student, Librarian, Administrator,
│           BorrowRecord, Library, Main
└── version-b/                 # AI-assisted approach (layered)
    ├── build.gradle           # + JUnit 5
    └── src/
        ├── main/java/com/library/
        │   ├── model/         # Book, User + roles, BorrowRecord
        │   ├── repository/    # BookRepository (HashMap, O(1)), BorrowRepository
        │   ├── service/       # LibraryService (business logic)
        │   └── Main.java      # wires the layers together
        └── test/java/com/library/service/
            └── LibraryServiceTest.java   # 6 JUnit 5 tests
```

## How to build & run (Gradle)

Build everything (compiles both, runs Version B tests):
```bash
./gradlew build
```

Run Version A (traditional):
```bash
./gradlew :version-a:run
```

Run Version B (AI-assisted):
```bash
./gradlew :version-b:run
```

Run the Version B tests only:
```bash
./gradlew :version-b:test
```
Expected: **6 tests, all passing.**

## Key difference illustrated
- **Version A** searches the catalogue with a linear `ArrayList` scan and prints
  results directly (one class does everything).
- **Version B** (AI suggestions) stores books in a `HashMap` for O(1) lookup and
  returns result strings from a dedicated service layer, which makes the logic
  unit-testable and separates concerns (model / repository / service).

See the accompanying report (`../s17239_Assessment02_AI_Assisted_Programming.pdf`)
for the full comparison (development time, code quality, error handling,
maintainability), the AI prompts used, the UML diagrams, and the critical
analysis.
