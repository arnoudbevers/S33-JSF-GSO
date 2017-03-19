/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klasvoorbeeld.observerexample;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 *
 * @author marcel
 */
public class ProjectPlanning extends Observable {

    private final String projectName;
    private final String projectLeaderName;
    private final List<String> tasks;

    public ProjectPlanning(String projectName, String projectLeaderName) {
        this.tasks = new ArrayList<>();
        this.projectName = projectName;
        this.projectLeaderName = projectLeaderName;
    }
    
    public void addTask(String task) {
        this.tasks.add(task);
        this.setChanged();
        this.notifyObservers(task);
    }
    
    public String getProjectName() {
        return projectName;
    }

    public String getProjectLeaderName() {
        return projectLeaderName;
    }

}
