package com.iqbal.karim.ahmed.salik.project;

import com.iqbal.karim.ahmed.salik.project.databaseClasses.Item;
import com.iqbal.karim.ahmed.salik.project.databaseClasses.User;

import java.util.ArrayList;

public class Commons {

    public static ArrayList<Item> items = new ArrayList<>();
//    public static User currentUser = new User(1, "Iqbal Karim", "123","iqbalkarim@gmail.com", R.drawable.p5);
    public static User currentUser = null;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        Commons.currentUser = currentUser;
    }

    public static ArrayList<Item> getItems() {
        return items;
    }

    public static void setItems(ArrayList<Item> items) {
        Commons.items = items;
    }
}
