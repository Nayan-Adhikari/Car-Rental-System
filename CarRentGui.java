// LoginFrame.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {
    private CarRentalSystem system;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame(CarRentalSystem system) {
        this.system = system;
        setupUI();
    }

    private void setupUI() {
        setTitle("Car Rental System - Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        panel.add(new JLabel("Username:"));
        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);
        
        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(this::handleLogin);
        panel.add(loginBtn);
        
        add(panel);
    }

    private void handleLogin(ActionEvent e) {
        // Simple authentication (in real app, use secure authentication)
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

// MainMenuFrame.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainMenuFrame extends JFrame {
    private CarRentalSystem system;
    
    public MainMenuFrame(CarRentalSystem system) {
        this.system = system;
        setupUI();
    }

    private void setupUI() {
        setTitle("Car Rental System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        addButton(panel, "Rent a Car", this::showRentalForm);
        addButton(panel, "Return a Car", this::showReturnForm);
        addButton(panel, "Manage Cars", this::showCarManagement);
        addButton(panel, "Manage Customers", this::showCustomerManagement);
        addButton(panel, "Exit", e -> System.exit(0));
        
        add(panel);
    }

    private void addButton(JPanel panel, String text, java.awt.event.ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(button);
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

// RentalForm.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class RentalForm extends JFrame {
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
        setSize(500, 300);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Car selection
        panel.add(new JLabel("Select Car:"));
        List<Car> availableCars = system.getAvailableCars();
        carCombo = new JComboBox<>(availableCars.toArray(new Car[0]));
        panel.add(carCombo);
        
        // Customer selection
        panel.add(new JLabel("Select Customer:"));
        List<Customer> customers = system.getCustomers();
        customerCombo = new JComboBox<>(customers.toArray(new Customer[0]));
        panel.add(customerCombo);
        
        // Rental days
        panel.add(new JLabel("Rental Days:"));
        daysSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 30, 1));
        panel.add(daysSpinner);
        
        // Buttons
        JButton rentBtn = new JButton("Rent Car");
        rentBtn.addActionListener(this::rentCar);
        panel.add(rentBtn);
        
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(e -> dispose());
        panel.add(cancelBtn);
        
        add(panel);
    }

    private void rentCar(ActionEvent e) {
        Car selectedCar = (Car) carCombo.getSelectedItem();
        Customer selectedCustomer = (Customer) customerCombo.getSelectedItem();
        int days = (Integer) daysSpinner.getValue();
        
        Rental rental = system.rentCar(selectedCustomer, selectedCar, days);
        if (rental != null) {
            JOptionPane.showMessageDialog(this, 
                "Rental Successful!\n" +
                "Rental ID: " + rental.getRentalId() + "\n" +
                "Total Cost: $" + rental.getTotalCost(),
                "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Car is not available!", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

// ReturnForm.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ReturnForm extends JFrame {
    private CarRentalSystem system;
    private JComboBox<Rental> rentalCombo;

    public ReturnForm(CarRentalSystem system) {
        this.system = system;
        setupUI();
    }

    private void setupUI() {
        setTitle("Return a Car");
        setSize(500, 200);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Rental selection
        panel.add(new JLabel("Select Rental:"));
        List<Rental> activeRentals = system.getActiveRentals();
        rentalCombo = new JComboBox<>(activeRentals.toArray(new Rental[0]));
        panel.add(rentalCombo);
        
        // Buttons
        JButton returnBtn = new JButton("Return Car");
        returnBtn.addActionListener(this::returnCar);
        panel.add(returnBtn);
        
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(e -> dispose());
        panel.add(cancelBtn);
        
        add(panel);
    }

    private void returnCar(ActionEvent e) {
        Rental selectedRental = (Rental) rentalCombo.getSelectedItem();
        if (selectedRental != null && system.returnCar(selectedRental.getRentalId())) {
            JOptionPane.showMessageDialog(this, 
                "Car returned successfully!\n" +
                "Rental ID: " + selectedRental.getRentalId(),
                "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Failed to return car!", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

// CarManagementFrame.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class CarManagementFrame extends JFrame {
    private CarRentalSystem system;
    private JList<Car> carList;
    private DefaultListModel<Car> listModel;

    public CarManagementFrame(CarRentalSystem system) {
        this.system = system;
        setupUI();
    }

    private void setupUI() {
        setTitle("Manage Cars");
        setSize(600, 400);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Car list
        listModel = new DefaultListModel<>();
        updateCarList();
        carList = new JList<>(listModel);
        panel.add(new JScrollPane(carList), BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        
        JButton addBtn = new JButton("Add Car");
        addBtn.addActionListener(this::showAddCarDialog);
        buttonPanel.add(addBtn);
        
        JButton removeBtn = new JButton("Remove Car");
        removeBtn.addActionListener(e -> removeSelectedCar());
        buttonPanel.add(removeBtn);
        
        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> dispose());
        buttonPanel.add(closeBtn);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(panel);
    }

    private void updateCarList() {
        listModel.clear();
        system.getAvailableCars().forEach(listModel::addElement);
    }

    private void showAddCarDialog(ActionEvent e) {
        JTextField licenseField = new JTextField();
        JTextField makeField = new JTextField();
        JTextField modelField = new JTextField();
        JTextField typeField = new JTextField();
        JTextField rateField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(5, 2));
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
                
                system.addCar(new Car(license, make, model, type, rate));
                updateCarList();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid daily rate!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void removeSelectedCar() {
        Car selected = carList.getSelectedValue();
        if (selected != null) {
            system.getAvailableCars().remove(selected);
            updateCarList();
        } else {
            JOptionPane.showMessageDialog(this, "No car selected!", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }
}

// CustomerManagementFrame.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class CustomerManagementFrame extends JFrame {
    private CarRentalSystem system;
    private JList<Customer> customerList;
    private DefaultListModel<Customer> listModel;

    public CustomerManagementFrame(CarRentalSystem system) {
        this.system = system;
        setupUI();
    }

    private void setupUI() {
        setTitle("Manage Customers");
        setSize(600, 400);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Customer list
        listModel = new DefaultListModel<>();
        updateCustomerList();
        customerList = new JList<>(listModel);
        panel.add(new JScrollPane(customerList), BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        
        JButton addBtn = new JButton("Add Customer");
        addBtn.addActionListener(this::showAddCustomerDialog);
        buttonPanel.add(addBtn);
        
        JButton removeBtn = new JButton("Remove Customer");
        removeBtn.addActionListener(e -> removeSelectedCustomer());
        buttonPanel.add(removeBtn);
        
        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> dispose());
        buttonPanel.add(closeBtn);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(panel);
    }

    private void updateCustomerList() {
        listModel.clear();
        system.getCustomers().forEach(listModel::addElement);
    }

    private void showAddCustomerDialog(ActionEvent e) {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField licenseField = new JTextField();
        JTextField phoneField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("License #:"));
        panel.add(licenseField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add New Customer", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            String id = idField.getText();
            String name = nameField.getText();
            String license = licenseField.getText();
            String phone = phoneField.getText();
            
            system.addCustomer(new Customer(id, name, license, phone));
            updateCustomerList();
        }
    }

    private void removeSelectedCustomer() {
        Customer selected = customerList.getSelectedValue();
        if (selected != null) {
            system.getCustomers().remove(selected);
            updateCustomerList();
        } else {
            JOptionPane.showMessageDialog(this, "No customer selected!", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }
}