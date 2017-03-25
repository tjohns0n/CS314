//DTR-14
//Presenter.java
//Takes ArrayList of files and boolean array for options
package edu.csu2017sp314.DTR14.tripco.Presenter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.application.Application;

import edu.csu2017sp314.DTR14.tripco.Model.Model;
import edu.csu2017sp314.DTR14.tripco.View.GenerateJavascript;
import edu.csu2017sp314.DTR14.tripco.View.View;

public class Presenter {

    //List of input .csv files
    private ArrayList<File> files;
    //Optional argument booleans

    // boolean[] opt = {_id, _mileage, _name, _2opt, _3opt, _gui};
    private boolean[] options;

    private Model model;
    private View view;
    private String _svg;
    private String _xml;
    private String[] subSet;
    private XMLReader xmlReader;

    public Presenter(ArrayList<File> files, String _xml, String _svg, boolean[] options) {
        this.files = files;
        this.options = options;
        this._svg = _svg;
        this._xml = _xml;
        this.xmlReader = new XMLReader();
    }

    public Presenter(ArrayList<File> files) {
        this.files = files;
        options = new boolean[6];
        Arrays.fill(options, false);
        this.xmlReader = new XMLReader();
    }

    public void run() throws IOException {
    	if(_xml == null ||_xml.equals("null") || _xml.equals("")) subSet = new String[0];
        else subSet = xmlReader.readSelectFile(_xml, files);
        if (options[5] == true) {
            // if GUI
            Application.launch(View.class, new String[0]);
            gui_FileHandler();
            if (subSet.length == 0) subSet = xmlReader.readSelectFile(_xml, files);
        }
        
//         System.out.println("csv File = " + files.get(0).getName());
//         System.out.println("svg File = " + _svg);
//         System.out.println("xml File = " + _xml);
//         System.out.println("_i = " + options[0]);
//         System.out.println("_m = " + options[1]);
//         System.out.println("_n = " + options[2]);
//         System.out.println("_2 = " + options[3]);
//         System.out.println("_3 = " + options[4]);
//         for(int i = 0; i < subSet.length; i++)
//             System.out.println("subSet = " + subSet[i]);
        
        model = new Model(files.get(0).getAbsolutePath());
        model.planTrip(options[3], options[4], subSet);
        String[][] route = model.reteriveTrip();
        String[] total = route[route.length - 1][0].split(",");
        int totalMileage = Integer.parseInt(total[0]);
        view = new View(files.get(0).getName(), _svg, totalMileage, options[1], options[2], options[0]);
        
        //Find if ID is an actual value or should just be value
        int idIndex = -1;
        boolean idPresent = false;
        String[] labels = route[0][2].split(",");
        //TODO
        //Add into larger loop to handle routes with multiple formats i.e. multiple extras/template formats
        for (int i = 0; i < labels.length; i++) {
            if (labels[i].equalsIgnoreCase("ID")) {
                idPresent = true;
                idIndex = i;
                break;
            }
        }
        //Feed View info to draw lines of trip
        int idCount = 1;
        //TODO
        //Make more robust loop for added options
        for (int i = 0; i < route.length - 1; i++) {
            //Essentials string splitarg0
            //[0]=Accumulated Dist, [1]=name, [2]=lat, [3]=long
            String[] essentials1 = route[i][0].split(",");
            String[] essentials2 = route[i + 1][0].split(",");
            //Leg Distance calculated by difference between accumulated miles
            //First locations should have "0" as accumulated distance
            int mile = Integer.parseInt(essentials2[0]) - Integer.parseInt(essentials1[0]);
            if (idPresent) {
                //Split to grab id
                String[] extras1 = route[i][1].split(",");
                String[] extras2 = route[i + 1][1].split(",");
                view.addLeg(Double.parseDouble(essentials1[2]), Double.parseDouble(essentials1[3]), essentials1[1], extras1[idIndex],
                        Double.parseDouble(essentials2[2]), Double.parseDouble(essentials2[3]), essentials2[1], extras2[idIndex], mile);
            } else {
                //Vanilla draw
                //ID is order they are entered
                view.addLeg(Double.parseDouble(essentials1[2]), Double.parseDouble(essentials1[3]), essentials1[1], Integer.toString(idCount),
                        Double.parseDouble(essentials2[2]), Double.parseDouble(essentials2[3]), essentials2[1], Integer.toString(idCount + 1), mile);
                idCount += 2;
            }
        }
        //Write the files
        view.writeFiles();
        //Bandaid fix for js since server still not working
        new GenerateJavascript(view.getRootName());

        URI webpage = null;
        String dir = System.getProperty("user.dir");
        if (dir.contains("src")) {
        	dir += "/main/resources/View.html";
        } else {
        	dir += "/src/main/resources/View.html";
        }
        

        System.out.println("Ready to show the webpage = " + dir);
        File webFile = new File(dir);
        webpage = webFile.toURI();
        //Launch webpage
        try {
            java.awt.Desktop.getDesktop().browse(webpage);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("We tried to get the webpage to launch without the server");
            System.out.println("A bandaid yes, but we tried, and it looks like it didn't work");
            System.out.println("But the XML and svg files should be in the directory with the proper names/data");
        }

    }
    
    private void gui_FileHandler() throws IOException{
        BufferedReader br = new BufferedReader(new FileReader("GUI_OUTPUT.txt"));
        try {
            if(files.isEmpty()) files.add(new File(br.readLine()));
            else br.readLine();
            if(_xml.equals("")) _xml = br.readLine();
            else br.readLine();
            if(_svg.equals("")) _svg = br.readLine();
            else br.readLine();
            options[0] = br.readLine().equals("true");
            options[1] = br.readLine().equals("true");
            options[2] = br.readLine().equals("true");
            options[3] = br.readLine().equals("true");
            options[4] = br.readLine().equals("true");
        } finally {
            br.close();
        }   
        File f = new File("GUI_OUTPUT.txt");
        f.delete();
    }

}
