"""
This module provides functionality for encrypting and decrypting data, specifically dictionaries, using AES encryption with a password-derived key.

The module includes functions to:
- Encrypt data using AES encryption in CFB mode.
- Decrypt data that was encrypted with AES in CFB mode.
- Save a Python dictionary to a file as encrypted data.
- Load and decrypt a Python dictionary from an encrypted file.
"""
import json
import os
from typing import Any
from cryptography.hazmat.primitives.ciphers import Cipher, algorithms, modes
from cryptography.hazmat.backends import default_backend
from cryptography.hazmat.primitives import padding
from cryptography.hazmat.primitives import hashes
from cryptography.hazmat.primitives.kdf.pbkdf2 import PBKDF2HMAC


def encrypt_data(data: bytes, key: bytes, iv: bytes) -> Any:
    """
    Encrypts the given data using AES encryption in CFB mode with the specified key and initialization vector (IV).

    :param data: The plaintext data to be encrypted (in bytes).
    :param key: The encryption key (in bytes).
    :param iv: The initialization vector (IV) for the cipher (in bytes).
    :return: The encrypted data (in bytes).
    """
    padder = padding.PKCS7(128).padder() # type: ignore
    padded_data = padder.update(data) + padder.finalize()
    cipher = Cipher(algorithms.AES(key), modes.CFB(iv), backend=default_backend()) # type: ignore
    encryptor = cipher.encryptor() # type: ignore
    encrypted_data = encryptor.update(padded_data) + encryptor.finalize()
    return encrypted_data

def decrypt_data(encrypted_data: bytes, key: bytes, iv: bytes) -> Any:
    """
    Decrypts the given encrypted data using AES decryption in CFB mode with the specified key and initialization vector (IV).

    :param encrypted_data: The data to be decrypted (in bytes).
    :param key: The decryption key (in bytes).
    :param iv: The initialization vector (IV) used during encryption (in bytes).
    :return: The decrypted plaintext data (in bytes).
    """
    cipher = Cipher(algorithms.AES(key), modes.CFB(iv), backend=default_backend()) # type: ignore
    decryptor = cipher.decryptor() # type: ignore
    padded_data = decryptor.update(encrypted_data) + decryptor.finalize()
    unpadder = padding.PKCS7(128).unpadder() # type: ignore
    data = unpadder.update(padded_data) + unpadder.finalize()
    return data

def save_encrypted_dict_to_file(data_dict: dict, output_filename: str, password: str) -> None:
    """
    Encrypts a dictionary and saves it to a file using a password-derived key.

    The dictionary is first serialized into a JSON string and then encrypted using AES encryption.
    The encryption key is derived from the provided password using PBKDF2 with a random salt.

    :param data_dict: The dictionary to be encrypted and saved.
    :param output_filename: The name of the file to save the encrypted data to.
    :param password: The password used to derive the encryption key.
    """
    json_string = json.dumps(data_dict)
    data = json_string.encode('utf-8')
    salt = os.urandom(16)
    kdf = PBKDF2HMAC(
        algorithm=hashes.SHA256(),
        length=32,
        salt=salt,
        iterations=100000,
        backend=default_backend() # type: ignore
    )
    key = kdf.derive(password.encode())
    iv = os.urandom(16)
    encrypted_data = encrypt_data(data, key, iv)
    with open(output_filename, 'wb') as file:
        file.write(salt + iv + encrypted_data)

def load_encrypted_dict_from_file(input_filename: str, password: str) -> Any:
    """
    Loads and decrypts an encrypted dictionary from a file using a password-derived key.

    The function reads the salt, IV, and encrypted data from the file, derives the decryption key using the provided password, and decrypts the data back into its original dictionary form.

    :param input_filename: The name of the file containing the encrypted data.
    :param password: The password used to derive the decryption key.
    :return: The decrypted dictionary.
    """
    with open(input_filename, 'rb') as file:
        salt = file.read(16)
        iv = file.read(16)
        encrypted_data = file.read()
    kdf = PBKDF2HMAC(
        algorithm=hashes.SHA256(),
        length=32,
        salt=salt,
        iterations=100000,
        backend=default_backend() # type: ignore
    )
    key = kdf.derive(password.encode())
    decrypted_data = decrypt_data(encrypted_data, key, iv)
    json_string = decrypted_data.decode('utf-8')
    return json.loads(json_string)

# Beispielhafte Verwendung:

# Speichern eines Dictionaries als verschlüsselte Datei
info_dict = {'name': 'John Doe', 'age': 30, 'city': 'New York'}
OUTPUT_FILENAME = 'encrypted_data.json'
PASSWORD = 'strong_password123'
save_encrypted_dict_to_file(info_dict, OUTPUT_FILENAME, PASSWORD)

# Laden eines Dictionaries aus einer verschlüsselten Datei
loaded_dict = load_encrypted_dict_from_file(OUTPUT_FILENAME, PASSWORD)
print(loaded_dict)
