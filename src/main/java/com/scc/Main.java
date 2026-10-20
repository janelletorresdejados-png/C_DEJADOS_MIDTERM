package com.scc;

import dao.DatabaseConnection;
import ui.ScenarioMenu;

public class Main {
    public static void main(String[] args) {
        // Enforce physical file infrastructure layout integrity initialization checks
        DatabaseConnection.initializeDatabase();

        // Relinquish operational handoff to interactive interface loop wrapper
        ScenarioMenu menu = new ScenarioMenu();
        menu.runMenuLoop();
    }
}
