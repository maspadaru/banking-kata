# 🏦 Banking Kata

[![Build Status](https://github.com/maspadaru/banking-kata/actions/workflows/ci.yml/badge.svg)](https://github.com/maspadaru/banking-kata/actions)

A clean, test-driven implementation of the [Banking Kata](https://kata-log.rocks/banking-kata), written in Java using Gradle and JUnit 5.

---

## 📋 Kata Summary

The goal of this kata is to implement a simple bank account that supports:

- Depositing money
- Withdrawing money
- Printing an account statement with transaction history

Example output:
```
Date | Amount | Balance
24.12.2015 | +500 | 500
23.12.2015 | -100 | 400
```

---

## ✅ Design Highlights

- Implemented using **TDD**
- Fully tested with clear, focused unit tests
- Clean separation of concerns:
  - `Account` interface for business operations
  - `Transaction` model with type, amount, balance, and timestamp
  - `StatementFormatter` for customizable output
- Easily extendable (e.g., support for JSON, XML, or other output formats)
- Pluggable factories for different account configurations

---

## 🚀 How to Run

Run the application from the command line:

```bash
./gradlew run
```

Or open the project in your IDE and run the Main class.

## 🧪 How to Test

Run all tests:
```bash
./gradlew test
```

## 🔧 Tech Stack

- Java 21
- Gradle
- JUnit 
- Mockito 
- GitHub Actions (CI)

## 📂 Project Structure

account/
├── api/             → Public-facing interfaces (Account, AccountFactory)
├── service/         → Business logic and models 
├── formatter/       → Statement formatting (text, JSON, etc.)
├── factory/         → Factories to create accounts with various formatters


