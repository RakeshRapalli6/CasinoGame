package com.casino.CasinoGame;

import java.util.Random;
import java.util.Scanner;

import org.apache.tomcat.util.buf.StringCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableEurekaServer
public class CasinoGameApplication {
	
	int Grand = 1000;
	int major = 50;
	int minor = 20;
	int mini = 10;
	int creditsWon = 0;
	int moneyWon = 0;
	int yourWallet = 0;
	int totalBet = 0;
	int amountBeforeBet = 0;
	int moneyBet = 5;
	int amountLost = 0;
	int money = 0;
	final int rows = 3;
	final int cols = 5;
	Random randNum = new Random();	

	@GetMapping("/home")
	public String home() {
		return "Welcome to the game! ";
	}

	@PostMapping("/post")
	public String samplePost() {
		return "Success";
	}
 
	public void spinTheWheel() {	
		String spinWheel = "s";
		System.out.println("Enter s to spin the wheel");
		Scanner sc = new Scanner(System.in);
		int chances = 1;
		while(chances > 0) {
			String userInput = sc.nextLine().toLowerCase();
			if(userInput.equals(spinWheel)) {
				if(this.major > 50000) {
					System.out.println("You won a major and a spin");
					spinGridMajor();
				} else if(this.minor > 5000 && this.minor < 50000) {
					System.out.println("You won a minor and a spin");
					spinGridMinor();
				} else if(this.mini > 100 && this.mini < 5000){
					System.out.println("You won a mini and a spin");
					spinGridMini();
				} else {	
					System.out.println("You haven't won any spin credits");
				}
			} 
			chances--;
		}
		System.out.println("The total amount you won" + " " + this.moneyWon);
		// this.amountLost += this.moneyWon;
		System.out.println("Enter co to earn the money");
		String input = sc.nextLine().toLowerCase();
		if(input.equals("co")) {
			this.yourWallet += this.moneyWon;
		}
		System.out.println("Congrats!" + " " + this.yourWallet + " " + "is yours");
		this.startGame();
	}

	//returns current instance of the class
	public CasinoGameApplication getInstance() {
		return this;
	}
	

	public boolean lostMoney() {
		if(this.moneyWon < this.totalBet) {
			return true;
		} else {
			this.money = this.moneyWon - this.totalBet;
			return false;
		}
	}

	public boolean isStartGame() {
		System.out.println("Enter p to start the game or s to stop! ");
		Scanner sc = new Scanner(System.in);
		String userInput = sc.nextLine();
		String toPlay = "p";
		String toStop = "s";
		String inputLower = userInput.toLowerCase();

		if(this.yourWallet <= 0 && inputLower.equals(toPlay)) {
			System.out.println("Please enter the amount you want to bet: ");
			this.yourWallet = Integer.parseInt(sc.nextLine());
		} 

		if(inputLower.equals(toPlay) && this.yourWallet >= 5) {
			System.out.println("your wallet:" + " " + this.yourWallet + "$"); 
			this.yourWallet -= this.moneyBet; 
			this.totalBet += this.moneyBet;
			System.out.println("The total amount you bet" + " " + this.moneyBet + " " + "your wallet: " + " " + this.yourWallet + "$");
			return true;
		} else if(inputLower.equals(toStop)) {
			if(!lostMoney()) {
				System.out.println("Amount you bet: " + this.totalBet);
				System.out.println("Amount you won: " + Math.abs(this.money));
				System.out.println("wallet: " + this.yourWallet);
				return false;
			} else {
				System.out.println("Amount you bet: " + this.totalBet);
				System.out.println("amount lost: " + (this.totalBet - this.moneyWon));
				System.out.println("wallet: " + this.yourWallet);
				return false;
				}
			}
		return false;
	}

	public void spinGridMini() {
		int[][] spinGrid = new int[rows][cols];
		for(int row = 0; row < rows; row++) {
			for(int col = 0; col < cols; col++) {
				int credit = randNum.nextInt(100);
				if(credit % 2 != 0) {
					spinGrid[row][col] = credit;
				} else {
					spinGrid[row][col] = 0;
				}
				this.moneyWon += 5.0 / 100 * spinGrid[row][col];
				System.out.print(spinGrid[row][col] + " ");
			}
			System.out.println();  
		}
	}
	
	public void spinGridMinor() {
		int[][] spinGrid = new int[rows][cols];
		for(int row = 0; row < rows; row++) {
			for(int col = 0; col < cols; col++) {
				int credit = randNum.nextInt(300);
				if(credit % 2 != 0) {
					spinGrid[row][col] = credit;
				} else {
					spinGrid[row][col] = 0;
				}
				this.moneyWon += 5.0 / 100 * spinGrid[row][col];
				System.out.print(spinGrid[row][col] + " ");
			}
			System.out.println(); 
		}
	}

	public void spinGridMajor() {
		int[][] spinGrid = new int[rows][cols];
		for(int row = 0; row < rows; row++) {
			for(int col = 0; col < cols; col++) {
				int credit = randNum.nextInt(500);
				if(credit % 2 != 0) {
					spinGrid[row][col] = credit;
				} else {
					spinGrid[row][col] = 0;
				}
				this.moneyWon += 5.0 / 100 * spinGrid[row][col];
				System.out.print(spinGrid[row][col] + " ");
			}
			System.out.println(); 
		}
	}

	@GetMapping("/grid")
	public void grid() {
		String[][] gameGrid = new String[rows][cols];
		for(int row = 0; row < rows; row++) {
			for(int col = 0; col < cols; col++) {
				String credit = String.valueOf(randNum.nextInt(500));
				int creditInt = Integer.parseInt(credit);
				if(creditInt % 2 == 0) {
					gameGrid[row][col] = credit;
					// System.out.println("credits: " + creditInt);
					if(creditInt < 100 && creditInt > 10) {
						this.mini += creditInt;
						// System.out.println("mini"+this.mini);
						this.creditsWon += this.mini;
						// System.out.println("credits won"+this.creditsWon);
					} else if(creditInt < 300 && creditInt > 100) {
						this.minor += creditInt;
						// System.out.println("minor"+this.minor);
						this.creditsWon += this.minor;
						// System.out.println("credits won"+this.creditsWon);
					} else {
						this.major += creditInt;
						// System.out.println("major"+this.major);
						this.creditsWon += this.major;
						// System.out.println("credits won"+this.creditsWon);
					}
					System.out.print(gameGrid[row][col] + " ");
				} else {
					String alphabet = RandomString();
					gameGrid[row][col] = alphabet;
					System.out.print(gameGrid[row][col] + " ");
				}
			}
			System.out.println(); 
		} 

		if(this.creditsWon > 50000) {
			this.creditsWon = 0;
			spinTheWheel();
		} else {
			startGame();
		}
	}
	
	public void startGame() {
		if(isStartGame()) {
			grid();
		} else {
			GameOver();
		}
	}

	public void GameOver() {
		System.out.println("You have exited the game");
	}

	@GetMapping("/alphabet")
	public String RandomString() {
	
		int max = 26; 
		String res = ""; 
		char[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 
							'h', 'i', 'j', 'k', 'l', 'm', 'n',  
							'o', 'p', 'q', 'r', 's', 't', 'u', 
							'v', 'w', 'x', 'y', 'z' }; 
	
		res = res + alphabet[(int)(Math.random() * 100 % max)]; 
	
		return res; 
	} 

	public static void main(String[] args) {
		SpringApplication.run(CasinoGameApplication.class, args);
		CasinoGameApplication cg = new CasinoGameApplication();
		if(cg.isStartGame()) {
			cg.grid();
		} 
			
	}
}

