package core.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import core.api.IAdmin;
import core.api.impl.Admin;

import core.api.IInstructor;
import core.api.IStudent;
import core.api.impl.Instructor;
import core.api.impl.Student;

public class TestInstructor {

	
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
    public void addHomeworkBaseCase0() {
    	// to check homework is added
    	this.admin.createClass("ecs60",2017,"sean",50);
        this.instructor.addHomework("sean", "ecs60", 2017, "p1");
        assertTrue(this.instructor.homeworkExists("ecs60", 2017, "p1"));
    }
    
    @Test
    public void homeworkExists1() { //corner case
    	// to check homework is not added
    	this.admin.createClass("ecs60",2017,"sean",50);
        //this.instructor.addHomework("sean", "ecs60", 2017, "p2");
        assertFalse(this.instructor.homeworkExists("ecs60", 2017, "p1"));
    }
    
    @Test
    public void homeworkExists2() {
      //test of instructor adding homework to a class he does not teach corner case
        this.admin.createClass("ecs60",2017,"sean",50);
        this.instructor.addHomework("sean", "ecs61", 2017, "p1");
        assertFalse(this.instructor.homeworkExists("ecs61", 2017, "p1"));
    }
    
    @Test
    public void homeworkExists3() {
    	// test of instructor adding homework to a class he teach in past
    	this.admin.createClass("ecs60",2016,"sean",50);
        this.instructor.addHomework("sean", "ecs60", 2016, "p1");
        assertFalse(this.instructor.homeworkExists("ecs61", 2016, "p1"));
    }
    @Test
    public void homeworkExists4() {
    	// test of instructor adding homework to a class he'll teach in future
    	this.admin.createClass("ecs60",2018,"sean",50);
        this.instructor.addHomework("sean", "ecs60", 2018, "p1");
        assertFalse(this.instructor.homeworkExists("ecs61", 2018, "p1"));
    }
    
  
    
//************assignGrade**************************
    @Test
    public void assignGradeBaseCase0() {
    	// test of the grades are write Base case
    	this.admin.createClass("ecs60",2017,"sean",50);
    	this.student.registerForClass("gurender", "ecs60", 2017);
        this.instructor.addHomework("sean", "ecs60", 2017, "p1");
        this.student.submitHomework("gurender", "p1", "abc", "ecs60", 2017);
        this.instructor.assignGrade("sean", "ecs60", 2017, "p1", "gurender", 100);
        assertEquals(100,(int) this.instructor.getGrade("ecs60", 2017, "p1", "gurender"));
    }
    @Test
    public void assignGrade1() {
    	//Bug !!!!Instructor is not assign  so grades does not exists and everything else is same 
    	this.admin.createClass("ecs60",2017,"sean",50);
    	this.student.registerForClass("gurender", "ecs60", 2017);
        this.instructor.addHomework("sean", "ecs60", 2017, "p1");
        this.student.submitHomework("gurender", "p1", "abc", "ecs60", 2017);        
        this.instructor.assignGrade("sean2", "ecs60", 2017, "p1", "gurender", 80);
        assertNull(this.instructor.getGrade("ecs60", 2017, "p1", "gurender"));
    }

    @Test
    public void assignGrade2() {
    	//hw is not assign so grade does not exists
    	this.admin.createClass("ecs60",2017,"sean",50);
    	this.student.registerForClass("gurender", "ecs60", 2017);
       // this.instructor.addHomework("sean", "ecs60", 2017, "p1");
        this.student.submitHomework("gurender", "p1", "abc", "ecs60", 2017);        
        this.instructor.assignGrade("sean", "ecs60", 2017, "p1", "gurender", 80);
       assertNull(this.instructor.getGrade("ecs60", 2017, "p1", "gurender"));
    }
    @Test
    public void assignGrade3() {
    	// hw is not submitted so grades does not exists BUG
    	this.admin.createClass("ecs60",2017,"sean",50);
    	this.student.registerForClass("gurender", "ecs60", 2017);
        this.instructor.addHomework("sean", "ecs60", 2017, "p1");
      //  this.student.submitHomework("gurender", "p1", "abc", "ecs60", 2017);
        this.instructor.assignGrade("sean", "ecs60", 2017, "p1", "gurender", 80);
        assertNull(this.instructor.getGrade("ecs60", 2017, "p1", "gurender"));
    }
    
    @Test
    public void assignGrade4() {
    	// test of grades are not equal
    	this.admin.createClass("ecs60",2017,"sean",50);
    	this.student.registerForClass("gurender", "ecs60", 2017);
        this.instructor.addHomework("sean", "ecs60", 2017, "p1");
        this.student.submitHomework("gurender", "p1", "abc", "ecs60", 2017);
        this.instructor.assignGrade("sean", "ecs60", 2017, "p1", "gurender", 100);
        assertEquals(100,(int)this.instructor.getGrade("ecs60", 2017, "p1", "gurender"));
    }
        
  
   
  
    @Test
    public void assignGrade5() {
    	// to check the grade can not be negative  :BUG
    	this.admin.createClass("ecs60",2017,"sean",50);
    	this.student.registerForClass("gurender", "ecs60", 2017);
        this.instructor.addHomework("sean", "ecs60", 2017, "p1");
        this.student.submitHomework("gurender", "p1", "abc", "ecs60", 2017);
        
        this.instructor.assignGrade("sean", "ecs60", 2017, "p1", "gurender", -1);
        assertNull(this.instructor.getGrade("ecs60", 2017, "p1", "gurender"));
    }
    @Test
    public void assignGrade6() {
    	// to check the grade can be zero 
    	this.admin.createClass("ecs60",2017,"sean",50);
    	this.student.registerForClass("gurender", "ecs60", 2017);
        this.instructor.addHomework("sean", "ecs60", 2017, "p1");
       // this.student.hasSubmitted("gurender", "p1", "ecs60", 2017);
        this.student.submitHomework("gurender", "p1", "abc", "ecs60", 2017);
        this.instructor.assignGrade("sean", "ecs60", 2017, "p1", "gurender", 0);
      
        assertEquals(0,(int)this.instructor.getGrade("ecs60", 2017, "p1", "gurender"));
    }
    @Test
    public void assignGrade7() {
    	// to check the grade percentage can not be more than 100  :corner case :BUG
    	this.admin.createClass("ecs60",2017,"sean",50);
    	this.student.registerForClass("gurender", "ecs60", 2017);
        this.instructor.addHomework("sean", "ecs60", 2017, "p1");
        this.student.submitHomework("gurender", "p1", "abc", "ecs60", 2017);
        this.instructor.assignGrade("sean", "ecs60", 2017, "p1", "gurender", 150);
        assertNull(this.instructor.getGrade("ecs60", 2017, "p1", "gurender"));
    }
    @Test
    public void assignGrade8() {
    	// to check assignment graded for unregister student  corner case :BUG
    	this.admin.createClass("ecs60",2017,"sean",50);
    //	this.student.registerForClass("gurender", "ecs60", 2017);
        this.instructor.addHomework("ecs60", "ecs60", 2017, "p1");
        this.student.submitHomework("gurender", "p1", "abc", "ecs60", 2017);
        this.instructor.assignGrade("ecs60", "ecs60", 2017, "p1", "gurender", 60);
      
        assertNull(this.instructor.getGrade("ecs60", 2017, "p1", "gurender"));
       }
    @Test
    public void assignGrade9() {
    	// test of grades are for the same homework corner case
    	this.admin.createClass("ecs60",2017,"sean",50);
    	this.student.registerForClass("gurender", "ecs60", 2017);
        this.instructor.addHomework("sean", "ecs60", 2017, "p1");
        this.instructor.addHomework("sean", "ecs60", 2017, "p2");
        this.student.submitHomework("gurender", "p1", "abc", "ecs60", 2017);
        this.student.submitHomework("gurender", "p2", "abc", "ecs60", 2017);
        this.instructor.assignGrade("sean", "ecs60", 2017, "p1", "gurender", 100);
        this.instructor.assignGrade("sean", "ecs60", 2017, "p2", "gurender", 80);
        assertEquals(80,(int)this.instructor.getGrade("ecs60", 2017, "p2", "gurender"));
    }
    
    
    @Test
    public void assignGrade10() {
    	// test of grades are for the same person or not corner case
    	this.admin.createClass("ecs60",2017,"sean",50);
    	this.student.registerForClass("gurender", "ecs60", 2017);
    	this.student.registerForClass("gurender2", "ecs60", 2017);
        this.instructor.addHomework("sean", "ecs60", 2017, "p1");
        this.student.submitHomework("gurender", "p1", "abc", "ecs60", 2017);
        this.student.submitHomework("gurender2", "p1", "abc", "ecs60", 2017);
        this.instructor.assignGrade("sean", "ecs60", 2017, "p1", "gurender", 100);
        this.instructor.assignGrade("sean", "ecs60", 2017, "p1", "gurender2", 80);
        assertNotEquals(100,(int)this.instructor.getGrade("ecs60", 2017, "p1", "gurender2"));
    }
 
    
}
