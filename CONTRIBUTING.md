# Contributing to PolyGenesis Platform

We would love for you to contribute to the **PolyGenesis Platform** and help make it even better than it is today! 
As a contributor, here are the guidelines we would like you to follow:
* Coding Rules
* Commit Message Guidelines 
* Working on a task

## Coding Rules
To ensure consistency throughout the source code, keep these rules in mind as you are working:
* All features or bug fixes **must be tested** by one or more specs (unit-tests).
* All public objects and methods must be documented.
* Execute `prepare-to-commit.sh` before your commit and fix any possible errors before committing.

`prepare-to-commit.sh` will execute:

```bash
./mvnw license:update-file-header
./mvnw fmt:format
./mvnw clean install -P validate-license,validate-code-format,validate-code-style,validate-code-bugs,validate-code \
 -DskipTests=true -Dmaven.javadoc.skip=true -B -V
./mvnw test -B
```

## Commit Message Guidelines
We have very precise rules over how our git commit messages can be formatted.  This leads to **more
readable messages** that are easy to follow when looking through the **project history**.  But also,
we use the git commit messages to **generate our change log** (coming soon).

### Commit Message Format
Each commit message consists of a **header**, a **body** and a **footer**.  The header has a special
format that includes a **type**, a **scope** and a **subject**:
```
<type>(<scope>): <subject>
<BLANK LINE>
<body>
<BLANK LINE>
<footer>
```

The **header** is mandatory and the **scope** of the header is optional.

Any line of the commit message cannot be longer 100 characters! This allows the message to be easier
to read on GitHub as well as in various git tools.

The footer should contain a [closing reference to an issue](https://help.github.com/articles/closing-issues-via-commit-messages/) if any.

Samples:
```
feat(app): provide health endpoints
* get server status
* get db status
```

```
refactor(api): standardize api calls to fetch and dispatch events
```

### Type
Must be one of the following:

* **chore**: Changes that affect the build system or external dependencies (example scopes: maven, flyway)
* **ci**: Changes to our CI configuration files and scripts (example scopes: Travis, Circle, BrowserStack, SauceLabs)
* **docs**: Documentation only changes
* **feat**: A new feature
* **fix**: A bug fix
* **perf**: A code change that improves performance
* **refactor**: A code change that neither fixes a bug nor adds a feature
* **test**: Adding missing tests or correcting existing tests

