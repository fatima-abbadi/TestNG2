package Fatima;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

public class FatimaTest {
	 @Test
	    public void BpenBrowser() {
	        System.out.println("This will execute first (Open Browser)");
	    }

	    @Test
	    public void AignIn() {
	        System.out.println("This will execute second (SignIn)");
	    }

	    @Test
	    public void LogOut() {
	        System.out.println("This will execute third ");
	    }
}