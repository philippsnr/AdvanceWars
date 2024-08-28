# Passwordmanager

Secure and easy-to-use password manager for storing and managing your passwords locally, with encryption to protect your sensitive data

## Inhaltsverzeichnis
- [Installation](#installation)
- [How-to-use](#how-to-use)
- [Features](#features)
- [Creators-and-Contacts](#creators-and-contacts)

## Installation
    1. Add the project to VS Code Workspace
    2. Go in console and type in the following command to install all neccessary dependencies: 
        pip install -r requirements.txt
    3. Start main.py
    4. You can now use the password manager 

## How to use
    For detailed instructions on how to use the password manager, please refer to the [documentation.pdf](./documentation/documentation.pdf) included in this project.

## Features
Secure Password Storage:
    Encrypted storage of usernames, passwords, and associated website URLs.
    Ability to store additional information such as notes and categories.
    Storage of password metadata like creation date and time.

Strong Encryption:
    Data encryption using AES-256.
    Secure storage of the master password via hashing.

Password Management:
    Generation of secure, random passwords with configurable length and character set.
    Password history tracking to prevent reuse of old passwords.
    Customizable password strength by excluding certain characters or enforcing specific patterns.

User-Friendly Console:
    Intuitive menu navigation via the command line.
    Clear instructions and error messages.
    Control using arrow keys or alternative input methods.

User Management:
    Support for multiple user accounts, each with their own master password.

Import/Export Functionality:
    Export and import passwords in JSON format.

Password Validation:
    Password strength validation and warnings for weak or reused passwords.
    Integration with the "Have I Been Pwned" API to check for known data breaches.
    Robust handling when the API is offline or no internet connection is available.

Testing and Code Quality:
    Automated unit tests with at least 75% code coverage per file.
    Static code analysis using Pylint and Mypy to ensure code quality.

## Creators and Contacts
    Janne Nußbaum           TIT23       janu10.jn@gmail.com
    Linus Gerlach           TIT23       li.gerlach@freenet.de
    Philipp Staudinger      TIK23       philipp.eckhard.staudinger@gmail.com
    Nils Fleschhut          TIT23       fleschhut.nils@gmail.com
