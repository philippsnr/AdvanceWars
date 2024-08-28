"""
This module provides a function which validates a password
"""
from typing import Any
import re
import hashlib
import requests
import unittest

def is_password_correct(password: str) -> bool:
    """
    Validates the given password against specific security criteria.

    The password must:
    - Be at least 8 characters long.
    - Contain at least one digit.
    - Contain at least one uppercase letter.
    - Contain at least one lowercase letter.
    - Contain at least one special character.
    - Not be found in known data breaches.

    :param password: The password string to validate.
    :return: True if the password is secure, False otherwise.
    """
    is_password_secure = True
    if len(password) < 8:
        print("Password too short")
        is_password_secure = False
    if not re.search(r'\d', password):
        print("Password needs at least one digit")
        is_password_secure = False
    if not re.search(r'[A-Z]', password):
        print("Password needs at least one uppercase letter")
        is_password_secure = False
    if not re.search(r'[a-z]', password):
        print("Password needs at least one lowercase letter")
        is_password_secure = False
    if not re.search(r'[_!@#$%^&*(),.?":{}|<>-]', password):
        print("Password needs at least one special character")
        is_password_secure = False
    if is_password_pwned(password):
        print("Password is pwned")
        is_password_secure = False
    if not is_password_secure:
        return False
    return True
def is_password_pwned(password: str) -> bool:
    """
    Checks if the given password has been compromised in a known data breach.

    :param password: The password string to check.
    :return: True if the password has been compromised, False otherwise.
    """
    sha1_hashed_password = hashlib.sha1(password.encode()).hexdigest().upper()
    prefix_hashed_password = sha1_hashed_password[:5]
    suffix_hashed_password = sha1_hashed_password[5:]
    result = request_api(prefix_hashed_password)
    if result is None:
        print("Could not check the password due to API issues.")
        return False
    hash_suffixes = (line.split(':') for line in result.splitlines())
    for returned_suffix, _ in hash_suffixes:
        if returned_suffix == suffix_hashed_password:
            return True
    return False
def request_api(prefix_hashed_password: str) -> Any:
    """
    Requests data from the 'Have I Been Pwned' API for passwords 
    that match the given SHA-1 hash prefix.

    :param prefix_hashed_password: The first 5 characters of the hashed password.
    :return: The API response text if successful, None otherwise.
    """
    url = f'https://api.pwnedpasswords.com/range/{prefix_hashed_password}'
    try:
        response = requests.get(url, timeout=10)
        response.raise_for_status()
        return response.text
    except requests.RequestException as e:
        print(f"Error contacting API: {e}")
        return None

print(is_password_correct("Q2z!R7y%W4t"))
print(is_password_correct("Password!23"))
