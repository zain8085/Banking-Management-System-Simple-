# Banking Management System

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)

A simple **Banking Management System** built using **Java**, **JDBC**, and **MySQL**.  
This desktop application allows users to **register, create bank accounts, manage money transactions, and check balances** through a database-connected interface.

---

## Table of Contents
- [Installation](#installation)
- [Features](#features)
- [Modules](#modules)
- [Technologies](#technologies)
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

---
## Installation
1. **Clone the repository:**
```bash
git clone https://github.com/your-username/banking-management-system.git.
```

---

## Features
- **User registration and login**
- **Open new bank accounts**
- **Debit and credit money**
- **Transfer money between accounts**
- **Check account balance**
- **Secure with MPIN for transactions**

---

## Modules

### User Module
- Register a new user
- Login existing users
- Verify if a user already exists

### Accounts Module
- Open new accounts linked to user email
- Generate unique account numbers
- Retrieve account numbers for transactions

### AccountManager Module
- Debit and credit money
- Transfer money to other accounts
- Check balance securely using MPIN
- Handles transaction commits and rollbacks

### Main Application
- Interactive CLI menu
- Handles user navigation: register, login, open account, transactions, and logout

---

## Technologies
- **Java** – Core programming language  
- **JDBC** – Database connectivity  
- **MySQL** – Backend database  
- **IntelliJ IDEA** – Recommended IDE  

---
## Set up a MySQL database:

- Create a database, e.g., banking_app
- Create the required tables: user, account
- Update database credentials in Banking_app.java: url, user, pass
-  Open the project in IntelliJ IDEA and run Banking_app.java
  
---

## Usage

- Launch the application from IntelliJ
- Choose options to register or login
- Open a new bank account if none exists

---

##Perform transactions:

- Debit money
- Credit money
- Transfer money
- Check account balance
- Logout when done

---

## Contributing

- Contributions are welcome! You can:
- Report issues
- Suggest new features
- Submit pull requests

Please follow standard Git workflow when contributing.

---

## License

- This project is licensed under the MIT License – see the LICENSE file for details.
