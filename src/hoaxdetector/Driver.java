/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hoaxdetector;

import hoaxdetector.Operations.KNNOperations;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;

/**
 *
 * @author ghost
 */
public class Driver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException  {
        HoaxDetector hd = new HoaxDetector();
        hd.operations();
//        System.out.println(Math.abs(-1-5));
//        System.out.println(Math.sqrt(4467));
        
    }
    
}
