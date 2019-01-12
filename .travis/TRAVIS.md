# TRAVIS CI

## Code Signing

    gpg --export --armor ct@polygenesis.io > .travis/codesigning.asc
    gpg --export-secret-keys --armor ct@polygenesis.io >> .travis/codesigning.asc
    travis encrypt-file .travis/codesigning.asc .travis/codesigning.asc.enc
    rm .travis/codesigning.asc

