# Car Rental Management System

A comprehensive desktop application built with Java Swing for managing car rental operations. This system provides a user-friendly graphical interface for rental agencies to manage their fleet, customers, and rental transactions.

## 🚗 Features

### Core Functionality
- **Car Fleet Management**: Add, view, and remove vehicles from inventory
- **Customer Management**: Maintain customer database with personal details
- **Rental Operations**: Process car rentals and returns with automatic cost calculation
- **Real-time Availability**: Track which cars are available or currently rented
- **Rental History**: View active rentals and transaction records

### User Interface
- **Modern GUI Design**: Clean, professional interface with color-coded sections
- **Secure Login System**: Password-protected access (Username: `admin`, Password: `admin123`)
- **Intuitive Navigation**: Easy-to-use menu system with dedicated forms for each operation
- **Data Validation**: Input validation to ensure data integrity
- **Responsive Dialogs**: Informative success/error messages

## 🛠️ Technical Stack

- **Language**: Java
- **GUI Framework**: Swing
- **Date Handling**: Java Time API (LocalDate)
- **Architecture**: Object-Oriented Programming with MVC-like structure

## 📋 Prerequisites

- Java Development Kit (JDK) 8 or higher
- Any Java IDE (IntelliJ IDEA, Eclipse, NetBeans) or text editor
- Basic understanding of Java and Swing

## 🚀 Installation & Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/car-rental-system.git
   cd car-rental-system
   ```

2. **Compile the application**
   ```bash
   javac CarRentalSystem.java
   ```

3. **Run the application**
   ```bash
   java CarRentalSystem
   ```

## 💻 Usage

### Login
- Launch the application
- Enter credentials:
  - **Username**: `admin`
  - **Password**: `admin123`

### Main Operations

#### 1. Rent a Car
- Select an available car from the dropdown
- Choose a customer from the list
- Specify rental duration (1-30 days)
- Confirm rental to generate rental ID and calculate total cost

#### 2. Return a Car
- Select an active rental from the dropdown
- Confirm return to make the car available again

#### 3. Manage Cars
- View all available cars in the fleet
- Add new cars with details (license plate, make, model, type, daily rate)
- Remove cars from inventory

#### 4. Manage Customers
- View all registered customers
- Add new customers with personal information
- Remove customers from the database

## 📊 Sample Data

The system comes pre-loaded with sample data:

### Cars
- Toyota Camry (Sedan) - $45.99/day
- Ford Explorer (SUV) - $65.50/day
- BMW 740i (Luxury) - $120.00/day
- Honda Civic (Compact) - $35.75/day
- Tesla Model 3 (Electric) - $89.99/day

### Customers
- John Smith (DL12345)
- Emma Johnson (DL67890)
- Michael Brown (DL24680)

## 🏗️ Project Structure

```
src/
└── CarRentalSystem.java
    ├── Car (Model class)
    ├── Customer (Model class)
    ├── Rental (Model class)
    ├── CarRentalSystem (Business logic)
    ├── LoginFrame (Authentication UI)
    ├── MainMenuFrame (Main dashboard)
    ├── RentalForm (Car rental UI)
    ├── ReturnForm (Car return UI)
    ├── CarManagementFrame (Fleet management UI)
    └── CustomerManagementFrame (Customer management UI)
```

## 🎨 Design Features

- **Color Scheme**: Professional blue and gray theme
- **Typography**: Clean Arial fonts with appropriate sizing
- **Layout**: Grid and Border layouts for organized content
- **User Experience**: Intuitive button placement and clear visual hierarchy

## 🔧 Customization

### Adding New Car Types
Modify the car creation in the `initializeSampleData()` method or add through the GUI.

### Changing Login Credentials
Update the credentials in the `handleLogin()` method within `LoginFrame` class.

### Modifying Daily Rate Limits
Adjust the spinner limits in `RentalForm` for rental duration.

## 📝 Future Enhancements

- [ ] Database integration (MySQL/PostgreSQL)
- [ ] Advanced reporting and analytics
- [ ] Email notifications for rentals
- [ ] Multi-user support with role-based access
- [ ] Payment processing integration
- [ ] Mobile application companion
- [ ] Inventory management alerts
- [ ] Customer loyalty program

## 🐛 Known Issues

- Car removal only works for available cars (not currently rented)
- Customer removal doesn't check for active rentals
- No data persistence between application sessions

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 🔗 Connect with Me

- 🔗 GitHub: [@ADiTyaRaj8969](https://github.com/ADiTyaRaj8969)  
- ✉️ Email: adivid198986@gmail.com  
- 💼 LinkedIn: [Aditya Raj](https://www.linkedin.com/in/aditya-raj-710a5a291/)

## 🙏 Acknowledgments

- Java Swing documentation
- Oracle Java tutorials
- Open source community for inspiration

---

⭐ If you found this project helpful, please give it a star!

---

**Note**: This is a desktop application built for educational and demonstration purposes. For production use, consider implementing proper database integration and enhanced security measures.

