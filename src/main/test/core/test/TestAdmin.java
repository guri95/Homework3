package core.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import core.api.IAdmin;
import core.api.IInstructor;
import core.api.IStudent;
import core.api.impl.Admin;
import core.api.impl.Instructor;
import core.api.impl.Student;

public class TestAdmin {

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
    public void classExistsBaseCase() {
    	//this is the base class for all upcoming tests Base case
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }

    @Test
    public void classExists1() {
    	//to checks class is not in the past year :Bug
        this.admin.createClass("Test", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    }
          
    @Test
    public void classExists2() {
    	//to check class can be in the future year
        this.admin.createClass("Test", 2018, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2018));
    }
    
    @Test
    public void classExists3() { 
    	//to checks the negative number of student does not exist :BUG
        this.admin.createClass("Test", 2017, "Instructor", -1);
        assertFalse(this.admin.classExists("Test", 2017));
    }
    
    @Test
    public void classExists4() {
    	//to checks the zero student in class does not exist
        this.admin.createClass("Test", 2017, "Instructor", 0);
        assertFalse(this.admin.classExists("Test", 2017));
    }
    
    @Test
    public void classExists5() {
    	//class5:two classes exit for one instructor  
        this.admin.createClass("ECS160", 2017, "prem", 15);
        this.admin.createClass("ECS161", 2017, "prem", 15);
        assertTrue(this.admin.classExists("ECS161", 2017));
        
        
    }
    @Test
    public void classExists6() {
        //to check more than two classes can not be assign to one instructor
        this.admin.createClass("Test", 2017, "prem", 12);
        this.admin.createClass("Test2", 2017, "prem", 12);
        this.admin.createClass("Test3", 2017, "prem", 12);
        assertFalse(this.admin.classExists("Test3", 2017));
    }
    
    //************************************************************
    @Test
    public void getClassInstructor1() {
        //class7:ClassName/year is unique
        this.admin.createClass("ECS161", 2017, "premA", 12);
        this.admin.createClass("ECS161", 2017, "prem", 12);
        assertEquals("premA", this.admin.getClassInstructor("ECS161", 2017));
        
    }   
    @Test
    public void getClassInstructor2() {
        //class7:the name of the instructor is not empty
        this.admin.createClass("ECS161", 2017, null, 12);
        String name=this.admin.getClassInstructor("ECS161", 2017);
        assertNotNull(name);
    
    } 
    //************changeCapacity******************************

    @Test
    public void changeCapacity1() {
        //check the capacity changes with out enrollment   	
    	this.admin.createClass("ECS161", 2017, "instructor", 10);
        this.admin.changeCapacity("ECS161", 2017, 5);
        assertEquals(5,this.admin.getClassCapacity("ECS161",2017));
    } 
    
    @Test
    public void changeCapacity2() {
        //check the capacity is not less than current capacity    	
    	this.admin.createClass("ECS161", 2017, "instructor", 3);
    	this.student.registerForClass("gurender1", "ECS161", 2017);
    	this.student.registerForClass("gurender2", "ECS161", 2017);
    	this.student.registerForClass("gurender3", "ECS161", 2017);
        this.admin.changeCapacity("ECS161", 2017, 5);
        assertEquals(5,this.admin.getClassCapacity("ECS161",2017));
    }
    @Test
    public void changeCapacity3() {
        //check the capacity is not less than current capacity    	
    	this.admin.createClass("ECS161", 2017, "instructor", 3);
    	this.student.registerForClass("gurender1", "ECS161", 2017);
    	this.student.registerForClass("gurender2", "ECS161", 2017);
    	this.student.registerForClass("gurender3", "ECS161", 2017);    	
        this.admin.changeCapacity("ECS161", 2017, 2);
        assertEquals(3,this.admin.getClassCapacity("ECS161",2017));
   
    }
    @Test
    public void changeCapacity4() {
        //check the capacity is not less than current capacity :corner case
    	this.admin.createClass("ECS161", 2017, "instructor", 3);
    	this.student.registerForClass("gurender1", "ECS161", 2017);
    	this.student.registerForClass("gurender2", "ECS161", 2017);
    	this.student.registerForClass("gurender3", "ECS161", 2017);
        this.admin.changeCapacity("ECS161", 2017, 3);
        assertEquals(3,this.admin.getClassCapacity("ECS161",2017));
        ///check at 3 to 3
    }
    

    @Test
    public void changeCapacity5() {
        //check not changing the capacity of class which is not created yet
     	//corner case
    	//int oldCapacity=this.admin.getClassCapacity("ECS161",2017);
        this.admin.changeCapacity("ECS160", 2017, 16);
               
        assertFalse(this.admin.classExists("ECS160", 2017));
    }
}
