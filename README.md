# Smart Piggy Bank App 🐷💰

A robust, console-based personal finance tracker written in Java. This
application helps users manage a weekly allowance, track daily spending,
store extra cash, and save towards specific financial goals using smart
budgeting algorithms.

# IT 2112

Ebora, Justin 

Marasigan, Arvin

Pilapil, Florence

Rocio, Neil

------------------------------------------------------------------------

# Project Overview

The Smart Piggy Bank App is a Java-based console application designed to help users manage their weekly budget, track daily expenses, and save for future goals or events. Also, it provides a structured way to allocate a weekly allowance, monitor spending against a daily budget, and plan savings for specific goals. The app supports different spending modes (Conservative, Balanced, Relaxed) and allows users to set a custom weekly reset day. The data inserted by the user is saved with the use of a text file, making it an appropriate tool for personal finance management. Moreover, this program solves the problem of personal budget mismanagement by providing a simple, structured way for users to track their weekly spending.

------------------------------------------------------------------------

## OOP Concepts Applied

### Abstraction
The `FinancialItem` abstract class defines a common interface for `Transaction` and `Event` classes, exposing essential methods like `getType()` and `getDisplayInfo()` while hiding implementation details.

### Inheritance
`Transaction` and `Event` classes extend `FinancialItem`, inheriting common attributes (name, amount, date) while adding specific properties and methods.

### Encapsulation
All class fields are private/protected with public getter methods. The `Event` class encapsulates funding logic with methods like `addFunds()` and `getRemaining()`.

### Polymorphism
Both `Transaction` and `Event` override abstract methods to provide type-specific behavior, allowing polymorphic handling through `FinancialItem` references.

------------------------------------------------------------------------

## Program Structure

## SmartPiggyBankApp (Main Class)

```text
SmartPiggyBankApp (Main Class)
├── Scanner sc (static) - Handles user input
├── Transaction (static nested class)
│   ├── String name
│   ├── double amount
│   ├── boolean expense
│   └── LocalDate date
│
├── Event (static nested class)
│   ├── String name
│   ├── double needed
│   ├── double funded
│   └── LocalDate eventDate
│
├── Static Variables
│   ├── double weeklyAllowance
│   ├── double weeklySpent
│   ├── double storedMoney
│   ├── List<Transaction> tx
│   ├── List<Event> events
│   ├── Mode mode (enum)
│   ├── DayOfWeek resetDay
│   └── LocalDate lastReset
│
└── Static Methods
    ├── Main program flow methods (main(), settingsMenu(), eventsMenu())
    ├── Budget calculation methods (dailyBudget(), weeklyRemaining())
    ├── Data management methods (saveData(), loadData())
    ├── UI methods (printCentered(), header(), subHeader())
    └── Helper methods (readInt(), readDouble())

```

------------------------------------------------------------------------

## 🚀 Features

### **Smart Budgeting Modes**

Choose between **Conservative**, **Balanced**, or **Relaxed** modes to
automatically calculate your recommended daily spending limit.

### **Transaction Tracking**

Log expenses and income with timestamping.

### **Goal/Event Planning**

Set financial goals (e.g., *Concert Ticket*), track progress, and get
daily saving recommendations.

### **Data Persistence**

Automatically saves progress to `data.txt`.

### **Automatic Weekly Reset**

Resets weekly allowance while keeping savings intact.

### **Simulations**

Simulate how spending habits affect your ability to reach future goals.

------------------------------------------------------------------------

## 📋 Prerequisites

-   **Java Development Kit (JDK):** Version 8 or higher

------------------------------------------------------------------------

## 🛠️ Compilation & Execution

Save the Java code as:\
`SmartPiggyBankApp.java`

### Compile:

    javac SmartPiggyBankApp.java

### Run:

    java SmartPiggyBankApp

------------------------------------------------------------------------

## 🖥️ Usage & Sample Outputs

### **1. First Run (Setup)**

    ==================================================
               WELCOME TO SMART PIGGY BANK
    ==================================================
                  --- Set Weekly Allowance ---
    Enter weekly allowance: 2000
    ...

### **2. Main Dashboard**

    ==================================================
                     SMART PIGGY BANK
    ==================================================
    Weekly Allowance : 2000.00
    Reserved Events  : 0.00
    Stored Money     : 0.00
    Weekly Remaining : 2000.00
    --------------------------------------------------
    Daily Budget     : 285.71
    Today's Spent    : 0.00
    Remaining Today  : 285.71

### **3. Adding a Transaction**

    --- Add Transaction ---
    Name: Lunch
    Amount: 150
    1 = Expense, 2 = Income: 1
    Transaction saved. Remaining today: 135.71

### **4. Event/Goal Tracking**

    --- Add Event / Goal ---
    Event name: Concert Tickets
    Amount needed: 5000
    Event date (YYYY-MM-DD): 2024-12-25
    Event saved.

Example event view:

    1. Concert Tickets
    Target: 5000.00 | Funded: 500.00 | Remaining: 4500.00
    Days Left: 30 | Daily Recommendation: 150.00

------------------------------------------------------------------------

## 💾 Data Persistence (data.txt)

A `data.txt` file is automatically created.

### **Sample Snippet**

    2000.0
    150.0
    0.0
    BALANCED
    MONDAY
    2023-10-27
    ===TRANSACTIONS===
    Lunch|150.0|true|2023-10-27
    Freelance Work|500.0|false|2023-10-26
    ===EVENTS===
    Concert Tickets|5000.0|2024-12-25|500.0
    New Laptop|45000.0|2025-01-15|1200.0

------------------------------------------------------------------------

## ⚙️ Budgeting Logic

### **Daily Budget Formula**

| Mode          | Formula          | Description                 |
|--------------|------------------|-----------------------------|
| Conservative | Allowance / 10   | Very safe, saves more money |
| Balanced     | Allowance / 7    | Even distribution           |
| Relaxed      | Allowance / 5    | Higher daily cap            |

------------------------------------------------------------------------

## Authors

<table>
<tr>
    <th> &nbsp; </th>
    <th> Name </th>
    <th> Role </th>
</tr>
<tr>
    <td><img src="static/eboraj.png" width="100" height="100"> </td>
    <td><strong>Ebora, Justin Miguel</strong> <br/>
        </a>
    </td>
    <td>Developer</td>
</tr>
<tr>
    <td><img src="static/avnmsgn.png" width="100" height="100"> </td>
    <td><strong>Marasigan, Arvin Christlan</strong> <br/>
        </a>
    </td>
    <td>Developer</td>
</tr>
<tr>
    <td><img src="static/test.jpg" width="100" height="100"> </td>
    <td><strong>Pilapil, Florence</strong> <br/>
        </a>
    </td>
    <td>Developer</td>
  <tr>
    <td><img src="static/test.jpg" width="100" height="100"> </td>
    <td><strong>Rocio, Neil Dominic</strong> <br/>
        </a>
    </td>
    <td>Developer</td>
</tr>
</table>

------------------------------------------------------------------------

# Acknowledgement

We sincerely express our gratitude to our instructor for the guidance and support provided throughout the completion of this project. We also extend our appreciation to our classmates and peers for their cooperation and encouragement during the development process.

------------------------------------------------------------------------
