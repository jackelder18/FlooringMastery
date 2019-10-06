package com.sg.flooringmastery.ui;

/**
 * @date July 2, 2019
 * @author Jack Elder
 * @source Corbin March
 */
public enum MainMenuOption {
    
    DISPLAY_ORDERS(1, "Display Orders"),
    ADD_ORDER(2, "Add an Order"),
    EDIT_ORDER(3, "Edit an Order"),
    REMOVE_ORDER(4, "Remove an Order"),
    QUIT(5, "Quit");

    private final int value;
    private final String name;

    private MainMenuOption(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static MainMenuOption fromValue(int value) {
        for (MainMenuOption mmo : MainMenuOption.values()) {
            if (mmo.getValue() == value) {
                return mmo;
            }
        }
        return QUIT;
    }
}
