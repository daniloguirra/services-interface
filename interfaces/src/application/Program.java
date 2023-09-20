package application;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

import model.entities.CarRental;
import model.entities.Vehicle;
import model.services.BrazilTaxService;
import model.services.RentalService;

public class Program {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		
		try {
			
		
			System.out.println("Enter rental data: ");
			System.out.print("Car model: ");
			String carModel = sc.nextLine();
			
			System.out.print("Pickup (dd/MM/yyyy hh:mm): ");
			LocalDateTime start = LocalDateTime.parse(sc.nextLine(), fmt);
			System.out.print("Return (dd/MM/yyyy hh:mm): ");
			LocalDateTime finish  = LocalDateTime.parse(sc.nextLine(), fmt);
			
			CarRental cr = new CarRental(start, finish, new Vehicle(carModel));
			
			System.out.print("Enter price per hour: ");
			double pricePerHour = sc.nextDouble();
			System.out.print("Enter price per day: ");
			double pricePerDay = sc.nextDouble();
			
			RentalService rentalSerivce = new RentalService(pricePerHour, pricePerDay, new BrazilTaxService());
			
			rentalSerivce.processInvoice(cr);
			
			System.out.println("INVOICE:");
			System.out.println("Basic Payment: " + cr.getInvoice().getBasicPayment());
			System.out.println("Tax: " + cr.getInvoice().getTax());
			System.out.println("Total payment: " + cr.getInvoice().getTotalPayment());
		}
		catch(DateTimeParseException e) {
			System.out.println("Something declared is invalid");
		}
		catch(InputMismatchException e) {
			System.out.println("This field only accepts numbers");
		}
		
		sc.close();

	}

}
