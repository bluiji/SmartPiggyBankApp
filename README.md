# Smart Piggy Bank App

## Table of Contents
- [Project Overview](#project-overview)
- [OOP Concepts Applied](#oop-concepts-applied)
- [Program Structure](#program-structure)
- [How to Run](#how-to-run)
- [Features](#features)
- [Sample Output](#sample-output)
- [Authors](#authors)
- [Acknowledgement](#acknowledgement)

## Project Overview
A Java-based console application designed to help users manage their weekly budget, track daily expenses, and save for future goals or events. The app provides structured budget allocation, spending monitoring, and goal planning with different spending modes and customizable weekly reset days. Data persistence is achieved through text file storage.

## OOP Concepts Applied

### Abstraction
The `FinancialItem` abstract class defines a common interface for `Transaction` and `Event` classes, exposing essential methods like `getType()` and `getDisplayInfo()` while hiding implementation details.

### Inheritance
`Transaction` and `Event` classes extend `FinancialItem`, inheriting common attributes (name, amount, date) while adding specific properties and methods.

### Encapsulation
All class fields are private/protected with public getter methods. The `Event` class encapsulates funding logic with methods like `addFunds()` and `getRemaining()`.

### Polymorphism
Both `Transaction` and `Event` override abstract methods to provide type-specific behavior, allowing polymorphic handling through `FinancialItem` references.

## Program Structure

**Main Class**: `SmartPiggyBankApp_copy`

### Components:
- **Scanner sc (static)**: Handles user input
- **Transaction class (static nested)**: 
  - Attributes: `name`, `amount`, `expense`, `date`
- **Event class (static nested)**:
  - Attributes: `name`, `needed`, `funded`, `eventDate`
- **Static Variables**:
  - `weeklyAllowance`, `weeklySpent`, `storedMoney`
  - `List<Transaction> tx`, `List<Event> events`
  - `Mode mode`, `DayOfWeek resetDay`, `LocalDate lastReset`

### Key Methods:
- Program flow: `main()`, `settingsMenu()`, `eventsMenu()`
- Budget calculations: `dailyBudget()`, `weeklyRemaining()`
- Data management: `saveData()`, `loadData()`
- UI helpers: `printCentered()`, `header()`, `subHeader()`
- Input helpers: `readInt()`, `readDouble()`

## How to Run
1. Ensure Java Development Kit (JDK) 8+ is installed
2. Copy the program content into a Java compiler/IDE
3. Compile and run the `SmartPiggyBankApp_copy` class

## Features
- **Financial Management**: Weekly allowance tracking and allocation
- **Transaction System**: Income/expense recording with categorization
- **Goals/Events System**: Targeted savings for specific dates
- **Budgeting Modes**: Conservative, Balanced, or Relaxed spending styles
- **Weekly Management**: Customizable reset day selection


- **Data Persistence**: Automatic text file storage
- **Saving Simulation**: Goal planning with daily recommendations
- **Menu Navigation**: Hierarchical interface system
