package core.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import core.api.IAdmin;
import core.api.IInstructor;
import core.api.IStudent;

import core.api.impl.Admin;
import core.api.impl.Instructor;
import core.api.impl.Student;


public class TestStudent {
	private IAdmin admin;
	private IInstructor instructor;
	private IStudent student;
	
	@Before 
    public void setup() {
        this.instructor = new Instructor();
        this.admin = new Admin();
        this.student = new Student();
    }
	
	 @Test
	    public void registerBaseCase() {
	    	//to check the student is register Base Case
		 	this.admin.createClass("ecs60",2017,"sean",50);
	    	this.student.registerForClass("gurender", "ecs60", 2017);
	        assertTrue(this.student.isRegisteredFor("gurender", "ecs60", 2017));
	        //this.student.hasSubmitted("gurender", "p1", "ecs60", 2017);
	       
	    }
	
	 @Test
	    public void register1() {
	    	//to check the student is not register if he is not enrolled
		 	this.admin.createClass("ecs60",2017,"sean",50);
	    	
	        assertFalse(this.student.isRegisteredFor("gurender", "ecs60", 2017));
	       
	    }

	 @Test
	    public void register2() {
	    	//to check the student is not register if class is not exit
		 	this.student.registerForClass("gurender", "ecs60", 2017);
	        assertFalse(this.student.isRegisteredFor("gurender", "ecs60", 2017));
	       
	    }
	 @Test
	    public void register3() { 
	    	//to check the student trying to get in when capacity is limited :BUG 
		 	this.admin.createClass("ecs60",2017,"sean",2);
		 	this.student.registerForClass("gurender1", "ecs60", 2017);
		 	this.student.registerForClass("gurender2", "ecs60", 2017);
	    	this.student.registerForClass("gurender3", "ecs60", 2017);
	    	this.student.registerForClass("gurender4", "ecs60", 2017);
	        assertFalse(this.student.isRegisteredFor("gurender4", "ecs60", 2017)); 
	    }
	 @Test
	    public void register4() { 
	    	//to check student register for not existing class//!!!! corner case
		 	this.admin.createClass("ecs60",2017,"sean",2);
		 	this.student.registerForClass("gurender1", "ecs60", 2017);
	        assertFalse(this.student.isRegisteredFor("gurender1", "ecs154", 2017));
	      
	    }
	 @Test
	    public void register5() {  //!!!!!!!!!!!!!!!!!!!!!!
	    	//to check student register for class in future
		 	this.admin.createClass("ecs60",2018,"sean",4);
		 	this.student.registerForClass("gurender1", "ecs60", 2018);
	        assertTrue(this.student.isRegisteredFor("gurender1", "ecs60", 2018));
	    
	    }
	 //dropClass Test cases***************************************
	 @Test
	    public void isRegisterIfdropedBaseCase() {  
	    	//to check student drop  for class in which he register Base Case
		 	this.admin.createClass("ecs60",2017,"sean",4);
		 	this.student.registerForClass("gurender", "ecs60", 2017);
		 	this.student.dropClass("gurender", "ecs60", 2017);
	        assertFalse(this.student.isRegisteredFor("gurender", "ecs60", 2017));
	   
	    }
	 @Test
	    public void isRegisterIfdroped1() {  
		//to check student drop  for class in which he is not register
		 	this.admin.createClass("ecs60",2017,"sean",4);
		 	this.student.dropClass("gurender", "ecs60", 2017);
	        assertFalse(this.student.isRegisteredFor("gurender", "ecs60", 2017));
	 
	    }
	 
	 @Test
	 public void isRegisterIfdroped2() {  
	    	//to check student get drop from a past class 
		 	this.admin.createClass("ecs60",2016,"sean",4);
		 	this.student.registerForClass("gurender", "ecs60", 2016);
		 	this.student.dropClass("gurender", "ecs60", 2017);
	        assertFalse(this.student.isRegisteredFor("gurender", "ecs60", 2017));
	       // assertTrue(this.student.hasSubmitted("gurender", "p1", "ecs60", 2017));
	       
	    }
	 @Test
	 public void isRegisterIfdroped3() {  
	    	//to check student get drop from a class which ends a year before
		 	this.admin.createClass("ecs60",2017,"sean",4);
		 	this.student.registerForClass("gurender", "ecs60", 2017);		 
		 	this.student.dropClass("gurender", "ecs60", 2018);
	        assertFalse(this.student.isRegisteredFor("gurender", "ecs60", 2018));

	    }
	//*********************submitHomework class*******************************
	 
	 @Test
	    public void hasSubmittedBaseCase() {
	    	// test of the hw is submitted Base Case 
	    	this.admin.createClass("ecs60",2017,"sean",50);
	    	this.student.registerForClass("gurender", "ecs60", 2017);
	        this.instructor.addHomework("sean", "ecs60", 2017, "p1");
	        this.student.submitHomework("gurender", "p1", "abc", "ecs60", 2017);
	      assertTrue(this.student.hasSubmitted("gurender", "p1", "ecs60", 2017));
	    }
	 @Test
	    public void hasSubmitted1() {
	    	// test of the hw is submitted without even added by instructor
	    	this.admin.createClass("ecs60",2017,"sean",50);
	    	this.student.registerForClass("gurender", "ecs60", 2017);
	     // this.instructor.addHomework("sean", "ecs60", 2017, "p1");
	        this.student.submitHomework("gurender", "p1", "abc", "ecs60", 2017);
	     // assertFalse(this.student.isRegisteredFor("gurender", "ecs60", 2018));
		    assertFalse(this.student.hasSubmitted("gurender", "p1", "ecs60", 2017));
	    }
	 @Test
	    public void hasSubmitted2() {
	    	// to check the hw is submitted for a class with don't even exists: corner case
	    	//this.admin.createClass("ecs60",2017,"sean",50);
	    	this.student.registerForClass("gurender", "ecs60", 2017);
	        this.instructor.addHomework("sean", "ecs60", 2017, "p1");
	        this.student.submitHomework("gurender", "p1", "abc", "ecs60", 2017); 
	      assertFalse(this.student.hasSubmitted("gurender", "p1", "ecs60", 2017));
	    }
	 @Test
	    public void hasSubmitted3() {
	    	// test of the hw is submitted for a class before he/she dropped: corner case
	    	this.admin.createClass("ecs60",2017,"sean",50);
	    	this.student.registerForClass("gurender", "ecs60", 2017);
	       this.instructor.addHomework("sean", "ecs60", 2017, "p1");
	        this.student.submitHomework("gurender", "p1", "abc", "ecs60", 2017);
	        this.student.dropClass("gurender", "ecs60", 2017);
	     //   assertFalse(this.student.isRegisteredFor("gurender", "ecs60", 2018));
		    assertFalse(this.student.hasSubmitted("gurender", "p1", "ecs60", 2017));
	    }
	 @Test
	    public void hasSubmitted4() {
	    	// test of the hw is submitted for a class after he/she dropped: corner case
	    	this.admin.createClass("ecs60",2017,"sean",50);
	    	this.student.registerForClass("gurender", "ecs60", 2017);
	    	this.student.dropClass("gurender", "ecs60", 2017);
	        this.instructor.addHomework("sean", "ecs60", 2017, "p1");
	        this.student.submitHomework("gurender", "p1", "abc", "ecs60", 2017);
	       assertFalse(this.student.hasSubmitted("gurender", "p1", "ecs60", 2017));
	    }
	 @Test
	    public void hasSubmitted5() {
	    	// test of the hw is submitted for a another class  Corner case
	    	this.admin.createClass("ecs60",2017,"sean",50);
	    	this.admin.createClass("ecs154",2017,"sean",50);
	    	this.student.registerForClass("gurender", "ecs60", 2017);
	    	//this.student.dropClass("gurender", "ecs60", 2017);
	        this.instructor.addHomework("sean", "ecs60", 2017, "p1");
	        this.instructor.addHomework("sean", "ecs154", 2017, "h1");
	        this.student.submitHomework("gurender", "p1", "abc", "ecs154", 2017);
	       assertFalse(this.student.hasSubmitted("gurender", "p1", "ecs154", 2017));
	    }

	 @Test
	    public void hasSubmitted6() {
	    	// test of the hw is submitted to a class in future
	    	this.admin.createClass("ecs60",2019,"sean",50);
	    	this.student.registerForClass("gurender", "ecs60", 2019);
	    	//this.student.dropClass("gurender", "ecs60", 2017);
	        this.instructor.addHomework("sean", "ecs60", 2019, "p1");
	        this.student.submitHomework("gurender", "p1", "abc", "ecs60", 2019);
	       
	     //   assertFalse(this.student.isRegisteredFor("gurender", "ecs60", 2018));
		    assertFalse(this.student.hasSubmitted("gurender", "p1", "ecs60", 2019));
	    }
	 @Test
	    public void hasSubmitted7() {
	    	// test of the hw is submitted to a class in past
	    	this.admin.createClass("ecs60",2015,"sean",50);
	    	this.student.registerForClass("gurender", "ecs60", 2015);
	    	//this.student.dropClass("gurender", "ecs60", 2017);
	        this.instructor.addHomework("sean", "ecs60", 2015, "p1");
	        this.student.submitHomework("gurender", "p1", "abc", "ecs60", 2015);
	        assertFalse(this.student.hasSubmitted("gurender", "p1", "ecs60", 2015));
	    }
	 @Test
	    public void hasSubmitted8() {
	    	// to check the hw is submitted by a student who is not even in class BUG
	    	this.admin.createClass("ecs60",2017,"sean",50);
	    //	this.student.registerForClass("gurender", "ecs60", 2017);
	        this.instructor.addHomework("sean", "ecs60", 2017, "p1");
	        this.student.submitHomework("gurender", "p1", "abc", "ecs60", 2017);
		    assertFalse(this.student.hasSubmitted("gurender", "p1", "ecs60", 2017));
	    }
}
