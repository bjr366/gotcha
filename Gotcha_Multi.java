package gotcha_multi_pkg;

import java.util.ArrayList;

import processing.core.PApplet;

import processing.core.PImage;   // For background image


public class Gotcha_Multi extends PApplet {

    // Keep track of current scoreq2	1
    int score = 0;
    
    // Keep track of saved Time for timer
    int savedTime;
    int totalTime = 20000;

    // Canvas size
    final int canvasWidth  = 500;
    final int canvasHeight = 300;
    // Declare disk
    Disk d1;
    Disk d2;
    Disk d3;
    Disk d4;
    Disk d5;

    //Disk list - myDisks
    ArrayList<Disk> myDisks = new ArrayList<Disk>();
    
    
	
    public static void main(String[] args){
        PApplet.main("gotcha_multi_pkg.Gotcha_Multi");
    }
    
    public void settings() {
        size(canvasWidth, canvasHeight);
        smooth();
    }

    // setup() runs one time at the beginning of your program
    public void setup() {
        // Create a disk
        d1 = new Disk(); myDisks.add(d1);
        d2 = new Disk(); myDisks.add(d2);
        d3 = new Disk(); myDisks.add(d3);
        d4 = new Disk(); myDisks.add(d4);
        d5 = new Disk(); myDisks.add(d5);
        
        savedTime = millis();
    }

    // draw() is called repeatedly in an infinite loop.
    // By default, it is called about 60 times per second.
    public void draw() {
        // Erase the background, if you don't, the previous shape(s) will 
        // still be displayed
    	
        eraseBackground();
        
        //iterate through myDisks
        for(int i = 0; i < myDisks.size(); i++) {
        	// Move the shape, i.e. calculate the next x and y position
            // where the shape will be drawn.
            myDisks.get(i).calcCoords();
            
            // Draw the shape
            myDisks.get(i).drawShape();
            
            // Draw the shape
            myDisks.get(i).drawShape();
            
            // Display point value on the shape
            myDisks.get(i).displayPointValue();
        }
        

        // Display player's score 
        fill(255,255,255);
        text("Score: " + this.score, 250, 250);
        
        // calculate how 20 seconds passed
        int passedTime = millis() - savedTime;
        if (passedTime > totalTime){
        	background(0); //New Background
        	
        	//Insert text
        	textSize(20);
            fill(255, 255, 255);
            textAlign(CENTER);
            text("CONGRATS! YOU SCORED:" + this.score + "!", 250, 150);
        	
        }
    }

    public void eraseBackground() {      
        // White background:
        background(255);
        //Load image to background
        PImage bg = loadImage("lighting-png-blur.png");  
        bg.resize(canvasWidth, canvasHeight);
        background(bg);
    }

    // mousePressed() is a PApplet method that you will override.
    // This method is called from PApplet one time when the mouse is pressed.
    public void mousePressed() {
        // Draw a circle wherever the mouse is
        int mouseWidth  = 20;
        int mouseHeight = 20;
        fill(0, 255, 0);
        ellipse(mouseX, mouseY, mouseWidth, mouseHeight);

        // Check whether the click occurred within range of the shape
        // for all Disks in myDisk
        int j;
        for(j = 0; j < myDisks.size(); j++) {
        if ((this.mouseX >= myDisks.get(j).x - (myDisks.get(j).shapeWidth/2)) && (this.mouseX <= myDisks.get(j).x + (myDisks.get(j).shapeWidth/2)) && 
        (this.mouseY >= myDisks.get(j).y - (myDisks.get(j).shapeHeight/2)) && (this.mouseY <= myDisks.get(j).y + (myDisks.get(j).shapeHeight/2))) {
            
            // Update score:
            score = score + myDisks.get(j).pointValue;
            System.out.println("DBG:  HIT!");
        }
        }
    }

    

    // Create a Disk class that you will use to create one or more disks with each
    // disk having a color, speed, position, etc.
    class Disk {
    	
        // Size of disk
        int shapeWidth ;
        int shapeHeight;

        // Point value of disk
        int pointValue;

        // Position of disk - keep track of x and y position of disk
        float x = random(0,500);
        float y = random(0,300);

        // Horizontal speed of disk
        float xSpeed;
        float ySpeed;

        // It's hard to click a precise pixel on a disk, to make it easier we can
        // allow the user to click somewhere on the disk.
        // You might want to make the scoring space be a rectangle fitted tightly
        // to the disk - it's easier than calculating a rounded boundary.
        int targetRange = (shapeWidth * shapeHeight);

        public float red;
        public float green;
        public float blue;

        // The constructor could be extended to accept other disk characteristics
        Disk(float red, float green, float blue) {
            this.red   = red;
            this.green = green;
            this.blue  = blue;
        }

        Disk() {
        	
        	int y = (int)random(45,120);
        	
        	this.red = random(0,255);
        	this.green = random(0,255);
        	this.blue = random(0,255);
        	
        	this.shapeWidth = y;
        	this.shapeHeight = y;
        	
        	if((y >= 45) && (y < 60)) {
        		this.xSpeed = 11;
        		this.ySpeed = 11;
        		this.pointValue = 100;
        	}
        	
        	if((y >= 60) && (y < 90)) {
        		this.xSpeed = 9;
        		this.ySpeed = 9;
        		this.pointValue = 50;
        	}
        	
        	if((y >= 90) && (y <= 120)) {
        		this.xSpeed = 6;
        		this.ySpeed = 6;
        		this.pointValue = 10;
        	}
            
        }
        
        

        public void calcCoords() { 
        	
            // Compute the x position where the shape will be drawn
            this.x += this.xSpeed;
            this.y += this.ySpeed;

            // If the x position is off right side of the canvas, reverse direction of 
            // movement:
            if (this.x > 500) {
                // Log a debug message:
                System.out.println("DBG:  <---  Change direction, go left because x = " + this.x);

                // Recalculate:
                this.xSpeed = -1 * this.xSpeed;
            }

            // If the x position is off left side of the canvas, reverse direction of 
            // movement:
            if (this.x < 0) {
                // Log a debug message:
                System.out.println("DBG:      ---> Change direction, go right because x = " + this.x + "\n");
               
                // Recalculate:
                this.xSpeed = -1 * this.xSpeed;
            } 
            
            if (this.y > 300) {
            	System.out.println("DBG:  <---  Change direction, go left because x = " + this.y);
            	this.ySpeed = -1 * this.ySpeed;
            }
            
            if (this.y < 0) {
                // Log a debug message:
                System.out.println("DBG:      ---> Change direction, go right because x = " + this.y + "\n");
               
                // Recalculate:
                this.ySpeed = -1 * this.ySpeed;
            } 

        }

        public void drawShape() {
            // Select color, then draw the shape at computed x, y location
            fill(this.red, this.green, this.blue);
            ellipse( x, y, shapeWidth, shapeHeight);
        }

        public void displayPointValue() {
            // Draw the text at computed x, y location
            
        	
            textSize(20);
            fill(0, 0, 0);
            textAlign(CENTER);
            text(this.pointValue, this.x, this.y );
            
            
        }
    }
}
