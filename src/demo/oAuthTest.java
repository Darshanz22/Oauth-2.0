package demo;
import static io.restassured.RestAssured.given;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.testng.Assert;


import Pojo.Api;
import Pojo.GetCourse;
import dev.failsafe.internal.util.Assert;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
public class oAuthTest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		String[] courseTitles= { "Selenium Webdriver Java","Cypress","Protractor"};
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Darshan\\Documents\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://sso.teachable.com/secure/9521/identity/login/password");
	     driver.findElement(By.cssSelector("input[type='email']")).sendKeys("darshanzore3@gmail.com");
	     driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
	     Thread.sleep(3000);
	     driver.findElement(By.cssSelector("input[type='password']")).sendKeys("Darshan123");
	     driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
	     Thread.sleep(3000);
	     String url=driver.getCurrentUrl();
	     //String url="https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2FvAHBQUZU6o4WJ719NrGBzSELBFVBI9XbxvOtYpmYpeV47bFVExkaxWaF_XR14PHtTZf7ILSEeamywJKwo_BYs9M&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&session_state=0c32992f0d47e93d273922018ade42d1072b9d1f..a35c&prompt=none#";
	     String partialcode=url.split("code=")[1];
	     String code=partialcode.split("&passive")[0];
	     System.out.println(code);
	     
	     
			String accessTokenResponse= given().urlEncodingEnabled(false)	
					.queryParams("code",code)
				    .queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
			         .queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
			         .queryParams("grant_type", "authorization_code")
	.when().log().all()
	.post("https://www.googleapis.com/oauth2/v4/token").asString();
			JsonPath js=new JsonPath(accessTokenResponse);
			String accessToken=js.getString("access_token");
				
				
				
				
				
				GetCourse gc=given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON)
				.when()
					
					.get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);
					
					System.out.println(gc.getLinkedIn());
					System.out.println(gc.getInstructor());
					System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
					
					
					List<Api> apiCourses=gc.getCourses().getApi();
					for(int i=0;i<apiCourses.size();i++)
					{
						if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
								{
							System.out.println(apiCourses.get(i).getPrice());
								}
					}
					
					//Get the course names of WebAutomation
					ArrayList<String> a= new ArrayList<String>();
					
					
					List<Pojo.WebAutomation> w=gc.getCourses().getWebAutomation();
					
				for(int j=0;j<w.size();j++)
					{
						//System.out.println(w.get(j).getCourseTitle());
					a.add(w.get(j).getCourseTitle());
					}
					
				List<String> expectedList=	Arrays.asList(courseTitles);
				
				//Assert.assertTrue(a.equals(expectedList));
				
			
					
					
					
					
					
					//System.out.println(response);
					
					
				}

			
			
			
	}


