/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klasvoorbeeld.observerexample;

import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author marcel
 */
public class ProjectLid implements Observer {
    
    private final String name;

    public ProjectLid(String name) {
        this.name = name;
    }

    @Override
    public void update(Observable o, Object arg) {
        ProjectPlanning planning = (ProjectPlanning)o;
        String nieuweTaak = (String)arg;
        String melding = "Alert voor %s!\t : PL %s van %s heeft de volgende taak toegevoegd: %s%n";
        System.out.format(melding,this.name,planning.getProjectLeaderName(),planning.getProjectName(),nieuweTaak);
    }
    
}
