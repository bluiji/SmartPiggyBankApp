Smart Piggy Bank App 🐷💰

A robust, console-based personal finance tracker written in Java. This application helps users manage a weekly allowance, track daily spending habits, store extra cash, and save towards specific financial goals (Events) using smart budgeting algorithms.

🚀 Features

Smart Budgeting Modes: Choose between Conservative, Balanced, or Relaxed modes to automatically calculate your recommended daily spending limit based on your weekly allowance.

Transaction Tracking: Log expenses and income with date stamping.

Goal/Event Planning: Set up financial goals (e.g., "Concert Ticket"), track funding progress, and get daily saving recommendations.

Data Persistence: Automatically saves your progress to a local file (data.txt) so you never lose your financial data.

Automatic Weekly Reset: Detects when a new week begins (based on your preferred reset day) and resets budgets while keeping stored savings intact.

Simulations: Simulate how different spending habits affect your ability to reach future goals.

📋 Prerequisites

Java Development Kit (JDK): Version 8 or higher (required for java.time and Stream API).

🛠️ Compilation & Execution

Save the code: Save the provided Java code into a file named SmartPiggyBankApp.java.

Compile:

javac SmartPiggyBankApp.java


Run:

java SmartPiggyBankApp


🖥️ Usage & Sample Outputs

Below are examples of what the application looks like during execution.

1. First Run (Setup)

When running the app for the first time, it asks for your baseline configuration.

==================================================
           WELCOME TO SMART PIGGY BANK
==================================================
              --- Set Weekly Allowance ---
Enter weekly allowance: 2000
                     Saved.
              --- Select Spending Mode ---
               1. Conservative
               2. Balanced
               3. Relaxed
Choose: 2
             Mode set to: BALANCED
              --- Weekly Reset Day ---
               1. Sunday
               2. Monday
...
Choose: 2
           Reset day set to: MONDAY


2. Main Dashboard

The main screen updates dynamically based on your spending.

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
--------------------------------------------------
1. Add Transaction
2. View Transactions
3. Events / Goals
4. Store Extra Money
5. Weekly Summary
6. Settings
0. Exit
Choose: 


3. Adding a Transaction

Adding an expense updates your daily and weekly remaining totals immediately.

              --- Add Transaction ---
Name: Lunch
Amount: 150
1 = Expense, 2 = Income: 1
      Transaction saved. Remaining today: 135.71


4. Event/Goal Tracking

Setting a goal calculates how much you need to save per day to reach it by the deadline.

              --- Add Event / Goal ---
Event name: Concert Tickets
Amount needed: 5000
Event date (YYYY-MM-DD): 2024-12-25
                  Event saved.


If you view the event later:

==================================================
                      EVENTS
==================================================
              1. Concert Tickets
   Target: 5000.00 | Funded: 500.00 | Remaining: 4500.00
   Days Left: 30 | Daily Recommendation: 150.00


💾 Data Persistence (data.txt)

The application creates a file named data.txt in the same directory. This file stores your settings, transactions, and event progress.

Structure Explanation

Header: Settings (Allowance, Spent, Stored, Mode, Reset Day, Last Reset Date).

Transactions: Delimited by ===TRANSACTIONS===.

Events: Delimited by ===EVENTS===.

Sample data.txt Snippet

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


⚙️ Budgeting Logic

The Daily Budget is calculated based on your Weekly Allowance and your selected Mode:

Mode

Formula

Description

Conservative

Allowance / 10

Extremely safe. Saves a large portion of allowance automatically.

Balanced

Allowance / 7

Standard logic. Distributes money evenly across the week.

Relaxed

Allowance / 5

High daily cap. Assumes you won't spend money on weekends.
