/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klasvoorbeeld.observerexample;

/**
 *
 * @author planningPTS3
 */
public class ObserverExample {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ProjectPlanning planningPTS3 = new ProjectPlanning("PTS3 - AirHockey","Marcel");
        ProjectPlanning planningChronozoomProject = new ProjectPlanning("PTS6 - ChronoZoom","Frank");
  
        ProjectLid nico = new ProjectLid("Nico");
        ProjectLid andre = new ProjectLid("Andre");
        ProjectLid patrick = new ProjectLid("Patrick");
        
        // Nico en Andre doen mee aan PTS3 en willen op de hoogte blijven
        // van nieuwe taken die door de projectleider zijn toegevoegd
        planningPTS3.addObserver(nico);
        planningPTS3.addObserver(andre);
        
        // Andre en Patrick doen mee aan PTS6 en willen op de hoogte blijven
        // van nieuwe taken die door de projectleider zijn toegevoegd
        // Let op: Andre doet dus aan beide projecten mee!
        planningChronozoomProject.addObserver(andre);
        planningChronozoomProject.addObserver(patrick);
        
        // De projectleiders voegen taken toe
        planningPTS3.addTask("Opstellen van URS");
        planningChronozoomProject.addTask("Planning poker");
        planningPTS3.addTask("Opstellen van het acceptatietestplan");
        planningChronozoomProject.addTask("Regelen lokaal voor stand-up meeting");
    }
    
}
