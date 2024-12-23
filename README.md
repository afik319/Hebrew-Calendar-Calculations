# Hebrew Calendar Calculations

## Overview
This project is a comprehensive system for managing and analyzing the Hebrew calendar. It combines an intuitive user interface with advanced algorithms to perform astronomical calculations and generate custom Hebrew year representations. The system is designed with modular components to support scalability and integration into various business or research applications.

---

## Key Features
- **User Interface**: Provides an interactive and dynamic interface for exploring the Hebrew calendar.
- **Advanced Algorithms**: Implements astronomical calculations for leap year determination, month structure analysis, and custom calendar rules.
- **Customizable Design**: Modular architecture allows for seamless addition of new features and adaptations to specific use cases.
- **Gematria Conversion**: Converts Hebrew years into their corresponding Gematria representation.
- **Year and Month Navigation**: Intuitive navigation with keyboard and mouse for exploring years and months dynamically.

---

## Prerequisites
- **Java 11** or later
- **JavaFX** (ensure your development environment supports JavaFX)

---

## Installation
1. **Clone the repository**:
   ```bash
   git clone https://github.com/afik319/Hebrew-Calendar-Calculations.git
   cd Hebrew-Calendar-Calculations
   ```

2. **Import into your IDE**:
   - Open your favorite IDE (e.g., Eclipse or IntelliJ).
   - Import the project as a Maven or Gradle project (depending on your setup).

3. **Run the application**:
   - Ensure the JavaFX libraries are correctly configured in your IDE.
   - Execute the `main()` function in the `CalendarController` class.

---

## Usage
- **Navigate the Calendar**:
  - Use the dropdown menus to select a year and month.
  - View calculated lines and astronomical details for the selected period.
- **Keyboard Shortcuts**:
  - Use `Alt+Y` or `Alt+M` to navigate between years or months.
- **Dynamic Updates**:
  - The interface dynamically updates when new data is entered or calculated.

---

## Project Structure
```plaintext
Calendar2.0/
├── src/
│   ├── main/
│   │   ├── java/                # Java source files
│   │   │   ├── CalendarController.java    # Handles user interface and input
│   │   │   ├── Calculations.java          # Core algorithms for calendar logic
│   │   │   ├── CalendarCreator.java       # Manages calendar data and calculations
│   │   │   ├── LinesTitles.java           # Handles line titles and descriptions
│   │   │   └── ...                        # Additional helper classes
│   ├── resources/
│   │   ├── fxml/              # FXML files for the UI layout
│   │   │   └── Calendar.fxml  # Main UI layout file
│   ├── test/                  # Unit tests
├── .gitignore                 # Git ignore rules
├── README.md                  # Project documentation (this file)
├── pom.xml                    # Maven configuration file
```

---

## Potential Applications
- **Scheduling Systems**: Can be integrated into platforms requiring Hebrew date calculations or advanced calendrical features.
- **Educational Tools**: A great resource for students or researchers studying the Hebrew calendar and its astronomical basis.
- **Business Use Cases**: Useful for businesses requiring custom calendar solutions for planning or analytics.

---

## Contact
For questions or support, feel free to reach out:
- **Author**: Afik Ratzon
- **Email**: afik.ratzon@gmail.com
