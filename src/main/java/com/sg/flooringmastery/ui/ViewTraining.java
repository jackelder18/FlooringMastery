package com.sg.flooringmastery.ui;

/**
 * @date July 7, 2019
 * @author Jack Elder
 */
public class ViewTraining extends View{
    
    public ViewTraining(ConsoleIO io){
        super(io);
    }
    
    @Override
    public void displayWriteToFileError(){
        io.print("Changes not saved to file. You are currently in Training Mode.");
        io.print("Switch xml configuation file in App.java to switch to Prod mode.");
    }
    
}
