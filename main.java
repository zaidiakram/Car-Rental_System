import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car {
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String carId, String brand, String model, double basePricePerDay) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double calculatePrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnCar() {
        isAvailable = true;
    }
}

class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}

class Rental {
    private Car car;
    private Customer customer;
    private int days;
    private double totalCost;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
        this.totalCost = car.calculatePrice(days);
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }

    public double getTotalCost() {
        return totalCost;
    }
}

class CarRentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days) {
        if (car.isAvailable()) {
            car.rent();
            Rental rental = new Rental(car, customer, days);
            rentals.add(rental);
            System.out.println("Car rented successfully by " + customer.getName());
            System.out.println("Total cost: $" + rental.getTotalCost());
        } else {
            System.out.println("Car is not available for rent");
        }
    }

    public void returnCar(Car car) {
        car.returnCar();

        Rental rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToRemove = rental;
                break;
            }
        }

        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);
            System.out.println("Car returned successfully");
        } else {
            System.out.println("Car not found");
        }
    }

    public void menu() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("===== Car Rental System ======");
            System.out.println("1. Rent a car");
            System.out.println("2. Return a car");
            System.out.println("3. Exit");
            System.out.println("Enter your choice:");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                System.out.println("\n== Rent a car == \n");
                System.out.println("Enter your name:");
                String customerName = sc.nextLine();

                System.out.println("\nAvailable cars:");
                for (Car car : cars) {
                    if (car.isAvailable()) {
                        System.out.println(car.getCarId() + "  " + car.getBrand() + " " + car.getModel());
                    }
                }

                System.out.println("\nEnter the ID of the car you want to rent:");
                String carId = sc.nextLine();
                System.out.println("Enter the number of days you want to rent the car:");
                int days = sc.nextInt();
                sc.nextLine();

                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
                addCustomer(newCustomer);

                Car selectedCar = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId)) {
                        selectedCar = car;
                        break;
                    }
                }

                if (selectedCar != null) {
                    rentCar(selectedCar, newCustomer, days);
                } else {
                    System.out.println("Car not found");
                }
            } else if (choice == 2) {
                System.out.println("\n== Return a car == \n");
                System.out.println("Enter the ID of the car you want to return:");
                String carId = sc.nextLine();

                Car selectedCar = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId)) {
                        selectedCar = car;
                        break;
                    }
                }

                if (selectedCar != null) {
                    returnCar(selectedCar);
                } else {
                    System.out.println("Car not found");
                }
            } else if (choice == 3) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid choice, please try again.");
            }
        }
        sc.close();
    }
}

public class main {
    public static void main(String[] args) {
        CarRentalSystem carRentalSystem = new CarRentalSystem();
        carRentalSystem.addCar(new Car("CAR1", "Toyota", "Corolla", 50.0));
        carRentalSystem.addCar(new Car("CAR2", "Honda", "Civic", 60.0));
        carRentalSystem.addCar(new Car("CAR3", "Ford", "Focus", 55.0));

        carRentalSystem.menu();
    }
}
