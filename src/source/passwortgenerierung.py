"""
This module provides a function for generating secure, random passwords.
The passwords can be generated based on various criteria such as length, uppercase/lowercase usage, digits, special characters, and a custom pattern.
"""

import random
import string

def generate_password(criteria: dict) -> str:
    """
    Generiert ein zufälliges Passwort basierend auf den angegebenen Kriterien.

    :param length: Länge des Passworts.
    :param use_uppercase: Ob Großbuchstaben verwendet werden sollen.
    :param use_lowercase: Ob Kleinbuchstaben verwendet werden sollen.
    :param use_digits: Ob Zahlen verwendet werden sollen.
    :param use_special: Ob Sonderzeichen verwendet werden sollen.
    :param exclude_chars: Zeichen, die ausgeschlossen werden sollen.
    :param enforce_pattern: Ein Muster, das im Passwort erzwungen werden soll (z.B. "ULDS" für 1 Uppercase, 1 Lowercase, 1 Digit, 1 Special).
    :return: Das generierte Passwort.
    """
    length = criteria['length']
    use_uppercase = criteria['use_uppercase']
    use_lowercase = criteria['use_lowercase']
    use_digits = criteria['use_digits']
    use_special = criteria['use_special']
    exclude_chars = criteria.get('exclude_chars', '')
    enforce_pattern = criteria.get('enforce_pattern', '')
    # Erstellen des möglichen Zeichensatzes
    characters = ''
    if use_uppercase:
        characters += string.ascii_uppercase
    if use_lowercase:
        characters += string.ascii_lowercase
    if use_digits:
        characters += string.digits
    if use_special:
        characters += string.punctuation
    # Ausschluss bestimmter Zeichen
    if exclude_chars:
        characters = ''.join(c for c in characters if c not in exclude_chars)
    # Sicherstellen, dass der Zeichensatz nicht leer ist
    if not characters:
        raise ValueError("Zeichensatz ist leer. Bitte mindestens eine Zeichengruppe einschließen.")
    # Generierung des Passworts
    if enforce_pattern:
        password = []
        pattern_dict = {'U': string.ascii_uppercase, 'L': string.ascii_lowercase, 'D': string.digits, 'S': string.punctuation}
        # Für jedes Musterzeichen das entsprechende Zeichen hinzufügen
        for char in enforce_pattern:
            if char in pattern_dict:
                # Ausschluss bestimmter Zeichen aus dem Musterzeichensatz
                valid_chars = [c for c in pattern_dict[char] if c not in exclude_chars]
                if not valid_chars:
                    raise ValueError(f"Kein gültiges Zeichen für das Musterzeichen '{char}'.")
                password.append(random.choice(valid_chars))
            else:
                raise ValueError(f"Ungültiges Musterzeichen '{char}' in enforce_pattern.")
        # Restliche Zeichen zufällig hinzufügen
        remaining_length = length - len(password)
        password += random.choices(characters, k=remaining_length)
        # Passwort zufällig mischen
        random.shuffle(password)
        #muss man sich dann überlegen ob man das muster so haben will wie man es eingibt oder das das nur dafür da ist das auf jeden fall davon welche vorkommen.
        return ''.join(password)
    # Wenn kein Muster erzwungen wird, ein einfaches zufälliges Passwort generieren
    return ''.join(random.choice(characters) for _ in range(length))


# Beispielverwendung:
options = {
    'length': 16,
    'use_uppercase': True,
    'use_lowercase': True,
    'use_digits': True,
    'use_special': True,
    'exclude_chars': 'bums',
    'enforce_pattern': 'ULDS'
}
print(generate_password(options))
