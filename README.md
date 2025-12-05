# 🐷 Smart Piggy Bank App  

A Java-based console application designed to help users manage their weekly budget, track daily expenses, and save for future goals or events.

---

## 📋 Project Overview  
The **Smart Piggy Bank App** provides a structured way to:  
- Allocate a weekly allowance  
- Monitor spending against a daily budget  
- Plan savings for specific goals  
- Choose from different spending modes (*Conservative, Balanced, Relaxed*)  
- Set a custom weekly reset day  

All user data is saved in a text file, making it a simple yet effective tool for personal finance management.

---

## 🧠 OOP Concepts Applied  

| Concept         | Implementation                                                                 |
|-----------------|-------------------------------------------------------------------------------|
| **Abstraction**  | `FinancialItem` abstract class defines common interface for `Transaction` and `Event`. |
| **Inheritance**  | `Transaction` and `Event` extend `FinancialItem`, inheriting common attributes. |
| **Encapsulation** | All class fields are private/protected with public getters; internal state is safely managed. |
| **Polymorphism** | Overridden methods `getType()` and `getDisplayInfo()` allow type-specific behavior. |

---

## 🗂️ Program Structure  

**Main Class:** `SmartPiggyBankApp_copy`  
- Handles user input via `Scanner`  
- Contains static nested classes: `Transaction` and `Event`  
- Manages weekly allowance, spending, stored money, and lists of transactions/events  
- Uses enums for `Mode` and `DayOfWeek` for reset day  

**Key Static Methods:**  
- `main()`, `settingsMenu()`, `eventsMenu()`  
- `dailyBudget()`, `weeklyRemaining()`  
- `saveData()`, `loadData()`  
- UI helpers: `printCentered()`, `header()`, `subHeader()`  

---

## 🚀 How to Run  

### Prerequisites
- **Java Development Kit (JDK) 8 or higher**

### Steps
1. Download the source code file (`SmartPiggyBankApp.java`)
2. Open a terminal/command prompt in the directory containing the file
3. Compile the Java file:
   ```bash
   javac SmartPiggyBankApp_copy.java
