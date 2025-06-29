import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;

public class CarRentalSystem extends JFrame {
    
    // Core system classes
    static class Car {
        private String licensePlate;
        private String make;
        private String model;
        private String type;
        private double dailyRate;
        private boolean available;

        public Car(String licensePlate, String make, String model, String type, double dailyRate) {
            this.licensePlate = licensePlate;
            this.make = make;
            this.model = model;
            this.type = type;
            this.dailyRate = dailyRate;
            this.available = true;
        }

        public String getLicensePlate() { return licensePlate; }
        public String getMake() { return make; }
        public String getModel() { return model; }
        public String getType() { return type; }
        public double getDailyRate() { return dailyRate; }
        public boolean isAvailable() { return available; }
        public void setAvailable(boolean available) { this.available = available; }
        
        @Override
        public String toString() {
            return String.format("%s %s (%s) - $%.2f/day", make, model, licensePlate, dailyRate);
        }
    }

    static class Customer {
        private String id;
        private String name;
        private String licenseNumber;
        private String phone;

        public Customer(String id, String name, String licenseNumber, String phone) {
            this.id = id;
            this.name = name;
            this.licenseNumber = licenseNumber;
            this.phone = phone;
        }

        public String getId() { return id; }
        public String getName() { return name; }
        public String getLicenseNumber() { return licenseNumber; }
        public String getPhone() { return phone; }
        
        @Override
        public String toString() {
            return name + " (" + licenseNumber + ")";
        }
    }

    static class Rental {
        private String rentalId;
        private Car car;
        private Customer customer;
        private LocalDate startDate;
        private LocalDate endDate;
        private double totalCost;
        private boolean active;

        public Rental(String rentalId, Car car, Customer customer, int rentalDays) {
            this.rentalId = rentalId;
            this.car = car;
            this.customer = customer;
            this.startDate = LocalDate.now();
            this.endDate = startDate.plusDays(rentalDays);
            this.totalCost = car.getDailyRate() * rentalDays;
            this.active = true;
        }

        public void completeRental() {
            this.active = false;
            car.setAvailable(true);
        }

        public String getRentalId() { return rentalId; }
        public Car getCar() { return car; }
        public Customer getCustomer() { return customer; }
        public LocalDate getStartDate() { return startDate; }
        public LocalDate getEndDate() { return endDate; }
        public double getTotalCost() { return totalCost; }
        public boolean isActive() { return active; }
        
        @Override
        public String toString() {
            return rentalId + ": " + car.getMake() + " rented by " + customer.getName();
        }
    }

    static class CarRentalSystem {
        private List<Car> cars = new ArrayList<>();
        private List<Customer> customers = new ArrayList<>();
        private List<Rental> rentals = new ArrayList<>();
        private int rentalCounter = 1;

        public void initializeSampleData() {
            // Add sample cars
            addCar(new Car("ABC123", "Toyota", "Camry", "Sedan", 45.99));
            addCar(new Car("XYZ789", "Ford", "Explorer", "SUV", 65.50));
            addCar(new Car("LMN456", "BMW", "740i", "Luxury", 120.00));
            addCar(new Car("JKL012", "Honda", "Civic", "Compact", 35.75));
            addCar(new Car("QWE345", "Tesla", "Model 3", "Electric", 89.99));
            
            // Add sample customers
            addCustomer(new Customer("C001", "John Smith", "DL12345", "555-1234"));
            addCustomer(new Customer("C002", "Emma Johnson", "DL67890", "555-5678"));
            addCustomer(new Customer("C003", "Michael Brown", "DL24680", "555-9012"));
        }

        public void addCar(Car car) { cars.add(car); }
        
        public List<Car> getAvailableCars() { 
            List<Car> available = new ArrayList<>();
            for (Car car : cars) {
                if (car.isAvailable()) {
                    available.add(car);
                }
            }
            return available;
        }
        
        public void addCustomer(Customer customer) { customers.add(customer); }
        public List<Customer> getCustomers() { return customers; }
        
        public Rental rentCar(Customer customer, Car car, int days) {
            if (!car.isAvailable()) return null;
            
            String rentalId = "R" + rentalCounter++;
            Rental rental = new Rental(rentalId, car, customer, days);
            car.setAvailable(false);
            rentals.add(rental);
            return rental;
        }
        
        public boolean returnCar(String rentalId) {
            for (Rental rental : rentals) {
                if (rental.getRentalId().equals(rentalId) && rental.isActive()) {
                    rental.completeRental();
                    return true;
                }
            }
            return false;
        }
        
        public List<Rental> getActiveRentals() {
            List<Rental> active = new ArrayList<>();
            for (Rental rental : rentals) {
                if (rental.isActive()) {
                    active.add(rental);
                }
            }
            return active;
        }
    }

    // GUI Classes
    static class LoginFrame extends JFrame {
        private CarRentalSystem system;
        private JTextField usernameField;
        private JPasswordField passwordField;

        public LoginFrame(CarRentalSystem system) {
            this.system = system;
            setupUI();
        }

        private void setupUI() {
            setTitle("Car Rental System - Login");
            setSize(400, 250);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());
            
            // Header panel
            JPanel headerPanel = new JPanel();
            headerPanel.setBackground(new Color(44, 62, 80));
            JLabel titleLabel = new JLabel("CAR RENTAL SYSTEM");
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            headerPanel.add(titleLabel);
            add(headerPanel, BorderLayout.NORTH);
            
            // Form panel
            JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 15));
            formPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
            formPanel.setBackground(new Color(236, 240, 241));
            
            JLabel userLabel = new JLabel("Username:");
            userLabel.setFont(new Font("Arial", Font.BOLD, 14));
            usernameField = new JTextField();
            formPanel.add(userLabel);
            formPanel.add(usernameField);
            
            JLabel passLabel = new JLabel("Password:");
            passLabel.setFont(new Font("Arial", Font.BOLD, 14));
            passwordField = new JPasswordField();
            formPanel.add(passLabel);
            formPanel.add(passwordField);
            
            // Button panel
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
            buttonPanel.setBackground(new Color(236, 240, 241));
            
            JButton loginBtn = new JButton("Login");
            loginBtn.setBackground(new Color(41, 128, 185));
            loginBtn.setForeground(Color.WHITE);
            loginBtn.setFont(new Font("Arial", Font.BOLD, 14));
            loginBtn.setPreferredSize(new Dimension(120, 35));
            loginBtn.addActionListener(this::handleLogin);
            buttonPanel.add(loginBtn);
            
            formPanel.add(new JLabel()); // Empty cell for layout
            formPanel.add(buttonPanel);
            
            add(formPanel, BorderLayout.CENTER);
            
            // Footer panel
            JPanel footerPanel = new JPanel();
            footerPanel.setBackground(new Color(44, 62, 80));
            footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
            JLabel footerLabel = new JLabel("© 2023 Car Rental System");
            footerLabel.setForeground(Color.WHITE);
            footerPanel.add(footerLabel);
            add(footerPanel, BorderLayout.SOUTH);
        }

        private void handleLogin(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            
            if ("admin".equals(username) && "admin123".equals(password)) {
                new MainMenuFrame(system).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials!", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    static class MainMenuFrame extends JFrame {
        private CarRentalSystem system;
        
        public MainMenuFrame(CarRentalSystem system) {
            this.system = system;
            setupUI();
        }

        private void setupUI() {
            setTitle("Car Rental System");
            setSize(700, 500);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());
            
            // Header panel
            JPanel headerPanel = new JPanel();
            headerPanel.setBackground(new Color(44, 62, 80));
            JLabel titleLabel = new JLabel("CAR RENTAL MANAGEMENT SYSTEM");
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            headerPanel.add(titleLabel);
            add(headerPanel, BorderLayout.NORTH);
            
            // Main content panel
            JPanel contentPanel = new JPanel(new GridLayout(2, 2, 20, 20));
            contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
            contentPanel.setBackground(new Color(236, 240, 241));
            
            // Create menu buttons
            addMenuButton(contentPanel, "RENT A CAR", "icons/car-rental.png", this::showRentalForm);
            addMenuButton(contentPanel, "RETURN A CAR", "icons/car-return.png", this::showReturnForm);
            addMenuButton(contentPanel, "MANAGE CARS", "icons/car-management.png", this::showCarManagement);
            addMenuButton(contentPanel, "MANAGE CUSTOMERS", "icons/customer-management.png", this::showCustomerManagement);
            
            add(contentPanel, BorderLayout.CENTER);
            
            // Footer panel
            JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            footerPanel.setBackground(new Color(44, 62, 80));
            footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            
            JButton exitBtn = new JButton("Exit System");
            exitBtn.setBackground(new Color(231, 76, 60));
            exitBtn.setForeground(Color.WHITE);
            exitBtn.setFont(new Font("Arial", Font.BOLD, 12));
            exitBtn.addActionListener(e -> System.exit(0));
            footerPanel.add(exitBtn);
            
            add(footerPanel, BorderLayout.SOUTH);
        }

        private void addMenuButton(JPanel panel, String text, String iconPath, ActionListener listener) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.setBackground(new Color(52, 152, 219));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setBorder(new EmptyBorder(20, 10, 20, 10));
            button.addActionListener(listener);
            
            // Since we can't include actual icons, we'll use text labels
            JLabel iconLabel = new JLabel(text.substring(0, 1), SwingConstants.CENTER);
            iconLabel.setFont(new Font("Arial", Font.BOLD, 36));
            iconLabel.setForeground(Color.WHITE);
            iconLabel.setOpaque(true);
            iconLabel.setBackground(new Color(41, 128, 185));
            iconLabel.setPreferredSize(new Dimension(80, 80));
            
            JPanel btnPanel = new JPanel(new BorderLayout());
            btnPanel.add(iconLabel, BorderLayout.NORTH);
            btnPanel.add(button, BorderLayout.CENTER);
            btnPanel.setBackground(new Color(236, 240, 241));
            btnPanel.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
            
            panel.add(btnPanel);
        }

        private void showRentalForm(ActionEvent e) {
            new RentalForm(system).setVisible(true);
        }

        private void showReturnForm(ActionEvent e) {
            new ReturnForm(system).setVisible(true);
        }

        private void showCarManagement(ActionEvent e) {
            new CarManagementFrame(system).setVisible(true);
        }

        private void showCustomerManagement(ActionEvent e) {
            new CustomerManagementFrame(system).setVisible(true);
        }
    }

    static class RentalForm extends JFrame {
        private CarRentalSystem system;
        private JComboBox<Car> carCombo;
        private JComboBox<Customer> customerCombo;
        private JSpinner daysSpinner;

        public RentalForm(CarRentalSystem system) {
            this.system = system;
            setupUI();
        }

        private void setupUI() {
            setTitle("Rent a Car");
            setSize(500, 350);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());
            
            // Header panel
            JPanel headerPanel = new JPanel();
            headerPanel.setBackground(new Color(52, 152, 219));
            JLabel titleLabel = new JLabel("CAR RENTAL FORM");
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
            headerPanel.add(titleLabel);
            add(headerPanel, BorderLayout.NORTH);
            
            // Form panel
            JPanel formPanel = new JPanel(new GridLayout(4, 2, 15, 15));
            formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            formPanel.setBackground(new Color(236, 240, 241));
            
            formPanel.add(createFormLabel("Select Car:"));
            List<Car> availableCars = system.getAvailableCars();
            carCombo = new JComboBox<>(availableCars.toArray(new Car[0]));
            carCombo.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value instanceof Car) {
                        Car car = (Car) value;
                        setText(car.toString());
                    }
                    return this;
                }
            });
            formPanel.add(carCombo);
            
            formPanel.add(createFormLabel("Select Customer:"));
            List<Customer> customers = system.getCustomers();
            customerCombo = new JComboBox<>(customers.toArray(new Customer[0]));
            customerCombo.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value instanceof Customer) {
                        Customer customer = (Customer) value;
                        setText(customer.toString());
                    }
                    return this;
                }
            });
            formPanel.add(customerCombo);
            
            formPanel.add(createFormLabel("Rental Days:"));
            daysSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 30, 1));
            formPanel.add(daysSpinner);
            
            // Buttons panel
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
            buttonPanel.setBackground(new Color(236, 240, 241));
            
            JButton rentBtn = new JButton("Rent Car");
            rentBtn.setBackground(new Color(46, 204, 113));
            rentBtn.setForeground(Color.WHITE);
            rentBtn.setFont(new Font("Arial", Font.BOLD, 14));
            rentBtn.setPreferredSize(new Dimension(120, 35));
            rentBtn.addActionListener(this::rentCar);
            buttonPanel.add(rentBtn);
            
            JButton cancelBtn = new JButton("Cancel");
            cancelBtn.setBackground(new Color(231, 76, 60));
            cancelBtn.setForeground(Color.WHITE);
            cancelBtn.setFont(new Font("Arial", Font.BOLD, 14));
            cancelBtn.setPreferredSize(new Dimension(120, 35));
            cancelBtn.addActionListener(e -> dispose());
            buttonPanel.add(cancelBtn);
            
            formPanel.add(new JLabel()); // Empty cell for layout
            formPanel.add(buttonPanel);
            
            add(formPanel, BorderLayout.CENTER);
        }
        
        private JLabel createFormLabel(String text) {
            JLabel label = new JLabel(text);
            label.setFont(new Font("Arial", Font.BOLD, 14));
            return label;
        }

        private void rentCar(ActionEvent e) {
            Car selectedCar = (Car) carCombo.getSelectedItem();
            Customer selectedCustomer = (Customer) customerCombo.getSelectedItem();
            int days = (Integer) daysSpinner.getValue();
            
            if (selectedCar == null || selectedCustomer == null) {
                JOptionPane.showMessageDialog(this, "Please select both a car and a customer!", 
                                             "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Rental rental = system.rentCar(selectedCustomer, selectedCar, days);
            if (rental != null) {
                JOptionPane.showMessageDialog(this, 
                    "<html><div style='text-align: center;'><h2>Rental Confirmed!</h2>" +
                    "<p><b>Rental ID:</b> " + rental.getRentalId() + "</p>" +
                    "<p><b>Car:</b> " + selectedCar.toString() + "</p>" +
                    "<p><b>Customer:</b> " + selectedCustomer.toString() + "</p>" +
                    "<p><b>Duration:</b> " + days + " days</p>" +
                    "<p><b>Total Cost:</b> $" + rental.getTotalCost() + "</p></div></html>",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Car is not available!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    static class ReturnForm extends JFrame {
        private CarRentalSystem system;
        private JComboBox<Rental> rentalCombo;

        public ReturnForm(CarRentalSystem system) {
            this.system = system;
            setupUI();
        }

        private void setupUI() {
            setTitle("Return a Car");
            setSize(500, 250);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());
            
            // Header panel
            JPanel headerPanel = new JPanel();
            headerPanel.setBackground(new Color(155, 89, 182));
            JLabel titleLabel = new JLabel("CAR RETURN FORM");
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
            headerPanel.add(titleLabel);
            add(headerPanel, BorderLayout.NORTH);
            
            // Form panel
            JPanel formPanel = new JPanel(new GridLayout(2, 2, 15, 15));
            formPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
            formPanel.setBackground(new Color(236, 240, 241));
            
            formPanel.add(createFormLabel("Select Rental:"));
            List<Rental> activeRentals = system.getActiveRentals();
            rentalCombo = new JComboBox<>(activeRentals.toArray(new Rental[0]));
            rentalCombo.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value instanceof Rental) {
                        Rental rental = (Rental) value;
                        setText(rental.toString());
                    }
                    return this;
                }
            });
            formPanel.add(rentalCombo);
            
            // Buttons panel
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
            buttonPanel.setBackground(new Color(236, 240, 241));
            
            JButton returnBtn = new JButton("Return Car");
            returnBtn.setBackground(new Color(46, 204, 113));
            returnBtn.setForeground(Color.WHITE);
            returnBtn.setFont(new Font("Arial", Font.BOLD, 14));
            returnBtn.setPreferredSize(new Dimension(120, 35));
            returnBtn.addActionListener(this::returnCar);
            buttonPanel.add(returnBtn);
            
            JButton cancelBtn = new JButton("Cancel");
            cancelBtn.setBackground(new Color(231, 76, 60));
            cancelBtn.setForeground(Color.WHITE);
            cancelBtn.setFont(new Font("Arial", Font.BOLD, 14));
            cancelBtn.setPreferredSize(new Dimension(120, 35));
            cancelBtn.addActionListener(e -> dispose());
            buttonPanel.add(cancelBtn);
            
            formPanel.add(new JLabel()); // Empty cell for layout
            formPanel.add(buttonPanel);
            
            add(formPanel, BorderLayout.CENTER);
        }
        
        private JLabel createFormLabel(String text) {
            JLabel label = new JLabel(text);
            label.setFont(new Font("Arial", Font.BOLD, 14));
            return label;
        }

        private void returnCar(ActionEvent e) {
            Rental selectedRental = (Rental) rentalCombo.getSelectedItem();
            if (selectedRental == null) {
                JOptionPane.showMessageDialog(this, "Please select a rental!", 
                                             "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (system.returnCar(selectedRental.getRentalId())) {
                JOptionPane.showMessageDialog(this, 
                    "<html><div style='text-align: center;'><h2>Car Returned!</h2>" +
                    "<p><b>Rental ID:</b> " + selectedRental.getRentalId() + "</p>" +
                    "<p><b>Car:</b> " + selectedRental.getCar().toString() + "</p>" +
                    "<p><b>Customer:</b> " + selectedRental.getCustomer().toString() + "</p>" +
                    "<p><b>Total Paid:</b> $" + selectedRental.getTotalCost() + "</p></div></html>",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Failed to return car!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    static class CarManagementFrame extends JFrame {
        private CarRentalSystem system;
        private JList<Car> carList;
        private DefaultListModel<Car> listModel;

        public CarManagementFrame(CarRentalSystem system) {
            this.system = system;
            setupUI();
        }

        private void setupUI() {
            setTitle("Manage Cars");
            setSize(700, 400);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());
            
            // Header panel
            JPanel headerPanel = new JPanel();
            headerPanel.setBackground(new Color(52, 73, 94));
            JLabel titleLabel = new JLabel("CAR MANAGEMENT");
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
            headerPanel.add(titleLabel);
            add(headerPanel, BorderLayout.NORTH);
            
            // Main content
            JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
            contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            contentPanel.setBackground(new Color(236, 240, 241));
            
            // Car list
            listModel = new DefaultListModel<>();
            updateCarList();
            carList = new JList<>(listModel);
            carList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            carList.setFont(new Font("Arial", Font.PLAIN, 14));
            carList.setBackground(Color.WHITE);
            carList.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            JScrollPane scrollPane = new JScrollPane(carList);
            contentPanel.add(scrollPane, BorderLayout.CENTER);
            
            // Button panel
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
            buttonPanel.setBackground(new Color(236, 240, 241));
            
            JButton addBtn = new JButton("Add New Car");
            addBtn.setBackground(new Color(46, 204, 113));
            addBtn.setForeground(Color.WHITE);
            addBtn.setFont(new Font("Arial", Font.BOLD, 14));
            addBtn.addActionListener(this::showAddCarDialog);
            buttonPanel.add(addBtn);
            
            JButton removeBtn = new JButton("Remove Selected");
            removeBtn.setBackground(new Color(231, 76, 60));
            removeBtn.setForeground(Color.WHITE);
            removeBtn.setFont(new Font("Arial", Font.BOLD, 14));
            removeBtn.addActionListener(e -> removeSelectedCar());
            buttonPanel.add(removeBtn);
            
            JButton closeBtn = new JButton("Close");
            closeBtn.setBackground(new Color(52, 152, 219));
            closeBtn.setForeground(Color.WHITE);
            closeBtn.setFont(new Font("Arial", Font.BOLD, 14));
            closeBtn.addActionListener(e -> dispose());
            buttonPanel.add(closeBtn);
            
            contentPanel.add(buttonPanel, BorderLayout.SOUTH);
            
            add(contentPanel, BorderLayout.CENTER);
        }

        private void updateCarList() {
            listModel.clear();
            for (Car car : system.getAvailableCars()) {
                listModel.addElement(car);
            }
        }

        private void showAddCarDialog(ActionEvent e) {
            JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            JTextField licenseField = new JTextField();
            JTextField makeField = new JTextField();
            JTextField modelField = new JTextField();
            JTextField typeField = new JTextField();
            JTextField rateField = new JTextField();

            panel.add(new JLabel("License Plate:"));
            panel.add(licenseField);
            panel.add(new JLabel("Make:"));
            panel.add(makeField);
            panel.add(new JLabel("Model:"));
            panel.add(modelField);
            panel.add(new JLabel("Type:"));
            panel.add(typeField);
            panel.add(new JLabel("Daily Rate:"));
            panel.add(rateField);

            int result = JOptionPane.showConfirmDialog(this, panel, "Add New Car", 
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            
            if (result == JOptionPane.OK_OPTION) {
                try {
                    String license = licenseField.getText();
                    String make = makeField.getText();
                    String model = modelField.getText();
                    String type = typeField.getText();
                    double rate = Double.parseDouble(rateField.getText());
                    
                    if (!license.isEmpty() && !make.isEmpty() && !model.isEmpty() && !type.isEmpty()) {
                        system.addCar(new Car(license, make, model, type, rate));
                        updateCarList();
                    } else {
                        JOptionPane.showMessageDialog(this, "Please fill in all fields!", 
                                                       "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid daily rate format!", 
                                                 "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        private void removeSelectedCar() {
            Car selected = carList.getSelectedValue();
            if (selected != null) {
                system.getAvailableCars().remove(selected);
                updateCarList();
            } else {
                JOptionPane.showMessageDialog(this, "No car selected!", 
                                             "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    static class CustomerManagementFrame extends JFrame {
        private CarRentalSystem system;
        private JList<Customer> customerList;
        private DefaultListModel<Customer> listModel;

        public CustomerManagementFrame(CarRentalSystem system) {
            this.system = system;
            setupUI();
        }

        private void setupUI() {
            setTitle("Manage Customers");
            setSize(700, 400);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());
            
            // Header panel
            JPanel headerPanel = new JPanel();
            headerPanel.setBackground(new Color(52, 73, 94));
            JLabel titleLabel = new JLabel("CUSTOMER MANAGEMENT");
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
            headerPanel.add(titleLabel);
            add(headerPanel, BorderLayout.NORTH);
            
            // Main content
            JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
            contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            contentPanel.setBackground(new Color(236, 240, 241));
            
            // Customer list
            listModel = new DefaultListModel<>();
            updateCustomerList();
            customerList = new JList<>(listModel);
            customerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            customerList.setFont(new Font("Arial", Font.PLAIN, 14));
            customerList.setBackground(Color.WHITE);
            customerList.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            JScrollPane scrollPane = new JScrollPane(customerList);
            contentPanel.add(scrollPane, BorderLayout.CENTER);
            
            // Button panel
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
            buttonPanel.setBackground(new Color(236, 240, 241));
            
            JButton addBtn = new JButton("Add New Customer");
            addBtn.setBackground(new Color(46, 204, 113));
            addBtn.setForeground(Color.WHITE);
            addBtn.setFont(new Font("Arial", Font.BOLD, 14));
            addBtn.addActionListener(this::showAddCustomerDialog);
            buttonPanel.add(addBtn);
            
            JButton removeBtn = new JButton("Remove Selected");
            removeBtn.setBackground(new Color(231, 76, 60));
            removeBtn.setForeground(Color.WHITE);
            removeBtn.setFont(new Font("Arial", Font.BOLD, 14));
            removeBtn.addActionListener(e -> removeSelectedCustomer());
            buttonPanel.add(removeBtn);
            
            JButton closeBtn = new JButton("Close");
            closeBtn.setBackground(new Color(52, 152, 219));
            closeBtn.setForeground(Color.WHITE);
            closeBtn.setFont(new Font("Arial", Font.BOLD, 14));
            closeBtn.addActionListener(e -> dispose());
            buttonPanel.add(closeBtn);
            
            contentPanel.add(buttonPanel, BorderLayout.SOUTH);
            
            add(contentPanel, BorderLayout.CENTER);
        }

        private void updateCustomerList() {
            listModel.clear();
            for (Customer customer : system.getCustomers()) {
                listModel.addElement(customer);
            }
        }

        private void showAddCustomerDialog(ActionEvent e) {
            JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            JTextField idField = new JTextField();
            JTextField nameField = new JTextField();
            JTextField licenseField = new JTextField();
            JTextField phoneField = new JTextField();

            panel.add(new JLabel("Customer ID:"));
            panel.add(idField);
            panel.add(new JLabel("Full Name:"));
            panel.add(nameField);
            panel.add(new JLabel("Driver License #:"));
            panel.add(licenseField);
            panel.add(new JLabel("Phone Number:"));
            panel.add(phoneField);

            int result = JOptionPane.showConfirmDialog(this, panel, "Add New Customer", 
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            
            if (result == JOptionPane.OK_OPTION) {
                String id = idField.getText();
                String name = nameField.getText();
                String license = licenseField.getText();
                String phone = phoneField.getText();
                
                if (!id.isEmpty() && !name.isEmpty() && !license.isEmpty() && !phone.isEmpty()) {
                    system.addCustomer(new Customer(id, name, license, phone));
                    updateCustomerList();
                } else {
                    JOptionPane.showMessageDialog(this, "Please fill in all fields!", 
                                                 "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        private void removeSelectedCustomer() {
            Customer selected = customerList.getSelectedValue();
            if (selected != null) {
                system.getCustomers().remove(selected);
                updateCustomerList();
            } else {
                JOptionPane.showMessageDialog(this, "No customer selected!", 
                                             "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    // Main method
    public static void main(String[] args) {
        // Set system look and feel for better appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Create and initialize the car rental system
        CarRentalSystem system = new CarRentalSystem();
        system.initializeSampleData();
        
        // Create and show the login frame
        SwingUtilities.invokeLater(() -> {
            new LoginFrame(system).setVisible(true);
        });
    }
}