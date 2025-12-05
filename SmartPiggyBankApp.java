import java.io.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class SmartPiggyBankApp{

    static Scanner sc = new Scanner(System.in);
    static final String DATA_FILE = "data.txt";

    // Base class for all financial items
    static abstract class FinancialItem {
        protected String name;
        protected double amount;
        protected LocalDate date;

        public FinancialItem(String name, double amount, LocalDate date) {
            this.name = name;
            this.amount = amount;
            this.date = date;
        }

        public abstract String getType();
        public abstract String getDisplayInfo();
        
        public String getName() { return name; }
        public double getAmount() { return amount; }
        public LocalDate getDate() { return date; }
    }

    // Transaction inherits from FinancialItem
    static class Transaction extends FinancialItem {
        private boolean expense;

        Transaction(String name, double amount, boolean expense, LocalDate date) {
            super(name, amount, date);
            this.expense = expense;
        }

        public boolean isExpense() { return expense; }

        @Override
        public String getType() {
            return expense ? "Expense" : "Income";
        }

        @Override
        public String getDisplayInfo() {
            return String.format("%-12s | %-15s | %-8.2f | %s", date, name, amount, getType());
        }
    }

    // Event also inherits from FinancialItem
    static class Event extends FinancialItem {
        private double funded;

        Event(String name, double needed, LocalDate eventDate) {
            super(name, needed, eventDate);
            this.funded = 0;
        }

        public double getNeeded() { return amount; }
        public double getFunded() { return funded; }
        public void addFunds(double funds) { this.funded += funds; }
        public double getRemaining() { return amount - funded; }
        public LocalDate getEventDate() { return date; }

        public long getDaysLeft() {
            return Math.max(1, ChronoUnit.DAYS.between(LocalDate.now(), date));
        }

        public double getDailyRecommendation() {
            return getRemaining() / getDaysLeft();
        }

        @Override
        public String getType() {
            return "Event/Goal";
        }

        @Override
        public String getDisplayInfo() {
            return String.format("%s - Target: %.2f | Funded: %.2f | Remaining: %.2f | Days Left: %d",
                    name, amount, funded, getRemaining(), getDaysLeft());
        }
    }

    static double weeklyAllowance = 0;
    static double weeklySpent = 0;
    static double storedMoney = 0;

    static List<Transaction> tx = new ArrayList<>();
    static List<Event> events = new ArrayList<>();

    enum Mode { CONSERVATIVE, BALANCED, RELAXED }
    static Mode mode = Mode.BALANCED;

    static DayOfWeek resetDay = DayOfWeek.MONDAY;
    static LocalDate lastReset = LocalDate.now().minusWeeks(1);

    static void printCentered(String text) {
        int width = 50;
        int padding = (width - text.length()) / 2;
        if (padding < 0) padding = 0;
        System.out.println(" ".repeat(padding) + text);
    }

    static void printLeft(String text) {
        System.out.println(text);
    }

    static void header(String title) {
        System.out.println("\n" + "=".repeat(50));
        printCentered(title);
        System.out.println("=".repeat(50));
    }

    static void subHeader(String subtitle) {
        printCentered("--- " + subtitle + " ---");
    }

    static double dailyBudget() {
        return switch (mode) {
            case CONSERVATIVE -> weeklyAllowance / 10;
            case BALANCED -> weeklyAllowance / 7;
            case RELAXED -> weeklyAllowance / 5;
        };
    }

    static double getTodaySpent() {
        LocalDate today = LocalDate.now();
        return tx.stream()
                .filter(t -> t.isExpense() && t.getDate().equals(today))
                .mapToDouble(t -> t.getAmount())
                .sum();
    }

    static double reservedForEvents() {
        return events.stream().mapToDouble(e -> e.getFunded()).sum();
    }

    static double weeklyRemaining() {
        return weeklyAllowance - weeklySpent - storedMoney - reservedForEvents();
    }

    static double remainingDailyBudget() {
        return dailyBudget() - getTodaySpent();
    }

    static void checkWeeklyReset() {
        LocalDate today = LocalDate.now();
        if (today.getDayOfWeek() == resetDay && !lastReset.equals(today)) {
            weeklySpent = 0;
            storedMoney = 0;
            events.forEach(e -> e.funded = 0);
            lastReset = today;
            printCentered("✓ A new week has begun — budgets reset!");
            saveData();
        }
    }

    public static void main(String[] args) {
        boolean firstRun = !new File(DATA_FILE).exists();
        if (firstRun) {
            System.out.println("\n" + "=".repeat(50));
            printCentered("WELCOME TO SMART PIGGY BANK");
            System.out.println("=".repeat(50));
            setAllowance();
            setSpendingMode();
            setResetDay();
            saveData();
        } else {
            loadData();
        }

        checkWeeklyReset();

        while (true) {
            checkWeeklyReset();

            header("SMART PIGGY BANK");

            printCentered(String.format("Weekly Allowance : %.2f", weeklyAllowance));
            printCentered(String.format("Reserved Events  : %.2f", reservedForEvents()));
            printCentered(String.format("Stored Money     : %.2f", storedMoney));
            printCentered(String.format("Weekly Remaining : %.2f", weeklyRemaining()));

            System.out.println("-".repeat(50));

            printCentered(String.format("Daily Budget     : %.2f", dailyBudget()));
            printCentered(String.format("Today's Spent    : %.2f", getTodaySpent()));
            printCentered(String.format("Remaining Today  : %.2f", remainingDailyBudget()));

            System.out.println("-".repeat(50));

            printLeft("1. Add Transaction");
            printLeft("2. View Transactions");
            printLeft("3. Events / Goals");
            printLeft("4. Store Extra Money");
            printLeft("5. Weekly Summary");
            printLeft("6. Settings");
            printLeft("0. Exit");
            System.out.print("Choose: ");

            int c = readInt();

            switch (c) {
                case 1 -> { addTransaction(); saveData(); }
                case 2 -> listTransactions();
                case 3 -> { eventsMenu(); saveData(); }
                case 4 -> { storeMoney(); saveData(); }
                case 5 -> summary();
                case 6 -> { settingsMenu(); saveData(); }
                case 0 -> { saveData(); printCentered("Goodbye!"); return; }
                default -> printCentered("Invalid choice.");
            }
        }
    }

    static void setAllowance() {
        subHeader("Set Weekly Allowance");
        System.out.print("Enter weekly allowance: ");
        weeklyAllowance = readDouble();
        printCentered("Saved.");
    }

    static void setSpendingMode() {
        subHeader("Select Spending Mode");
        printCentered("1. Conservative");
        printCentered("2. Balanced");
        printCentered("3. Relaxed");
        System.out.print("Choose: ");
        mode = switch (readInt()) {
            case 1 -> Mode.CONSERVATIVE;
            case 2 -> Mode.BALANCED;
            case 3 -> Mode.RELAXED;
            default -> Mode.BALANCED;
        };
        printCentered("Mode set to: " + mode);
    }

    static void setResetDay() {
        subHeader("Weekly Reset Day");
        printCentered("1. Sunday");
        printCentered("2. Monday");
        printCentered("3. Tuesday");
        printCentered("4. Wednesday");
        printCentered("5. Thursday");
        printCentered("6. Friday");
        printCentered("7. Saturday");
        System.out.print("Choose: ");
        int choice = readInt();
        
        switch (choice) {
            case 1 -> resetDay = DayOfWeek.SUNDAY;
            case 2 -> resetDay = DayOfWeek.MONDAY;
            case 3 -> resetDay = DayOfWeek.TUESDAY;
            case 4 -> resetDay = DayOfWeek.WEDNESDAY;
            case 5 -> resetDay = DayOfWeek.THURSDAY;
            case 6 -> resetDay = DayOfWeek.FRIDAY;
            case 7 -> resetDay = DayOfWeek.SATURDAY;
            default -> {
                printCentered("Invalid choice. Using Monday.");
                resetDay = DayOfWeek.MONDAY;
            }
        }
        printCentered("Reset day set to: " + resetDay);
    }

    static void settingsMenu() {
        while (true) {
            header("SETTINGS");
            printLeft("1. Change Weekly Allowance");
            printLeft("2. Change Spending Mode");
            printLeft("3. Set Weekly Reset Day");
            printLeft("0. Back");
            System.out.print("Choose: ");

            int c = readInt();

            switch (c) {
                case 1 -> setAllowance();
                case 2 -> setSpendingMode();
                case 3 -> setResetDay();
                case 0 -> { return; }
                default -> printCentered("Invalid.");
            }
        }
    }

    static void addTransaction() {
        subHeader("Add Transaction");
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Amount: ");
        double amount = readDouble();
        System.out.print("1 = Expense, 2 = Income: ");
        boolean isExpense = (readInt() == 1);
        tx.add(new Transaction(name, amount, isExpense, LocalDate.now()));
        if (isExpense) weeklySpent += amount;
        printCentered(String.format("Transaction saved. Remaining today: %.2f", remainingDailyBudget()));
    }

    static void listTransactions() {
        header("TRANSACTIONS");
        if (tx.isEmpty()) {
            printCentered("No transactions recorded.");
            return;
        }
        printCentered(String.format("%-12s | %-15s | %-8s | %s", "Date", "Name", "Amount", "Type"));
        System.out.println("-".repeat(50));
        for (Transaction t : tx) {
            printCentered(t.getDisplayInfo());
        }
    }

    static void eventsMenu() {
        while (true) {
            header("EVENTS / GOALS");
            printLeft("1. Add Event");
            printLeft("2. View / Fund Event");
            printLeft("3. Plan Savings (Simulation)");
            printLeft("0. Back");
            System.out.print("Choose: ");
            int c = readInt();
            switch (c) {
                case 1 -> addEvent();
                case 2 -> fundEventMenu();
                case 3 -> simulateSavings();
                case 0 -> { return; }
                default -> printCentered("Invalid.");
            }
        }
    }

    static void addEvent() {
        subHeader("Add Event / Goal");
        System.out.print("Event name: ");
        String name = sc.nextLine();
        System.out.print("Amount needed: ");
        double need = readDouble();
        System.out.print("Event date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(sc.nextLine());
        events.add(new Event(name, need, date));
        printCentered("Event saved.");
    }

    static void fundEventMenu() {
        if (events.isEmpty()) {
            printCentered("No events available.");
            return;
        }
        while (true) {
            header("EVENTS");
            for (int i = 0; i < events.size(); i++) {
                Event e = events.get(i);
                printCentered(String.format("%d. %s", i + 1, e.getName()));
                printCentered(String.format("   Target: %.2f | Funded: %.2f | Remaining: %.2f", 
                    e.getNeeded(), e.getFunded(), e.getRemaining()));
                printCentered(String.format("   Days Left: %d | Daily Recommendation: %.2f", 
                    e.getDaysLeft(), e.getDailyRecommendation()));
            }
            printLeft("0. Back");
            System.out.print("Select event to fund: ");
            int idx = readInt() - 1;
            if (idx == -1) break;
            if (idx < 0 || idx >= events.size()) {
                printCentered("Invalid.");
                continue;
            }
            Event e = events.get(idx);
            System.out.print("Amount to add: ");
            double amount = readDouble();
            if (amount > 0) {
                e.addFunds(amount);
                printCentered("Funds updated.");
            }
        }
    }

    static void simulateSavings() {
        if (events.isEmpty()) {
            printCentered("No events available.");
            return;
        }
        subHeader("Select Simulation Mode");
        printCentered("1. Conservative");
        printCentered("2. Balanced");
        printCentered("3. Relaxed");
        Mode simMode = switch (readInt()) {
            case 1 -> Mode.CONSERVATIVE;
            case 2 -> Mode.BALANCED;
            case 3 -> Mode.RELAXED;
            default -> mode;
        };
        for (Event e : events) {
            header("Simulation: " + e.getName());
            long daysLeft = e.getDaysLeft();
            double remaining = e.getRemaining();
            double daily = remaining / daysLeft;
            daily = switch (simMode) {
                case CONSERVATIVE -> daily * 1.2;
                case RELAXED -> daily * 0.8;
                default -> daily;
            };
            printCentered(String.format("Remaining: %.2f", remaining));
            printCentered(String.format("Days Left: %d", daysLeft));
            printCentered(String.format("Recommended Daily Saving: %.2f", daily));
            double projected = daily * daysLeft;
            printCentered(String.format("Projected Savings: %.2f", projected));
            if (projected >= remaining)
                printCentered("You will reach your goal.");
            else
                printCentered(String.format("You will be short by: %.2f", remaining - projected));
        }
    }

    static void storeMoney() {
        subHeader("Store Extra Money");
        System.out.print("Amount to store: ");
        double amt = readDouble();
        if (amt > 0 && amt <= weeklyRemaining()) {
            storedMoney += amt;
            printCentered(String.format("Stored successfully. Total stored: %.2f", storedMoney));
        } else {
            printCentered("Invalid amount.");
        }
    }

    static void summary() {
        header("WEEKLY SUMMARY");
        printCentered(String.format("%-18s : %.2f", "Allowance", weeklyAllowance));
        printCentered(String.format("%-18s : %.2f", "Spent", weeklySpent));
        printCentered(String.format("%-18s : %.2f", "Reserved Events", reservedForEvents()));
        printCentered(String.format("%-18s : %.2f", "Stored Money", storedMoney));
        printCentered(String.format("%-18s : %.2f", "Weekly Remaining", weeklyRemaining()));
        System.out.println("-".repeat(50));
        printCentered(String.format("%-18s : %.2f", "Daily Budget", dailyBudget()));
        printCentered(String.format("%-18s : %.2f", "Today's Spent", getTodaySpent()));
        printCentered(String.format("%-18s : %.2f", "Remaining Today", remainingDailyBudget()));
    }

    static void saveData() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(DATA_FILE))) {
            pw.println(weeklyAllowance);
            pw.println(weeklySpent);
            pw.println(storedMoney);
            pw.println(mode);
            pw.println(resetDay);
            pw.println(lastReset);
            pw.println("===TRANSACTIONS===");
            for (Transaction t : tx)
                pw.println(t.getName() + "|" + t.getAmount() + "|" + t.isExpense() + "|" + t.getDate());
            pw.println("===EVENTS===");
            for (Event e : events)
                pw.println(e.getName() + "|" + e.getNeeded() + "|" + e.getEventDate() + "|" + e.getFunded());
        } catch (Exception e) {
            printCentered("Error saving data: " + e.getMessage());
        }
    }

    static void loadData() {
        File f = new File(DATA_FILE);
        if (!f.exists()) return;
        try (Scanner s = new Scanner(f)) {
            weeklyAllowance = Double.parseDouble(s.nextLine());
            weeklySpent = Double.parseDouble(s.nextLine());
            storedMoney = Double.parseDouble(s.nextLine());
            mode = Mode.valueOf(s.nextLine());
            resetDay = DayOfWeek.valueOf(s.nextLine());
            lastReset = LocalDate.parse(s.nextLine());
            tx.clear();
            events.clear();
            while (s.hasNextLine() && !s.nextLine().equals("===TRANSACTIONS===")) {}
            while (s.hasNextLine()) {
                String line = s.nextLine();
                if (line.equals("===EVENTS===")) break;
                String[] p = line.split("\\|");
                tx.add(new Transaction(p[0], Double.parseDouble(p[1]), Boolean.parseBoolean(p[2]), LocalDate.parse(p[3])));
            }
            while (s.hasNextLine()) {
                String[] p = s.nextLine().split("\\|");
                Event e = new Event(p[0], Double.parseDouble(p[1]), LocalDate.parse(p[2]));
                e.addFunds(Double.parseDouble(p[3]));
                events.add(e);
            }
        } catch (Exception e) {
            printCentered("Error loading data: " + e.getMessage());
        }
    }

    static int readInt() {
        while (true) {
            try { return Integer.parseInt(sc.nextLine()); }
            catch (Exception e) { System.out.print("Enter a number: "); }
        }
    }

    static double readDouble() {
        while (true) {
            try { return Double.parseDouble(sc.nextLine()); }
            catch (Exception e) { System.out.print("Enter a number: "); }
        }
    }
}