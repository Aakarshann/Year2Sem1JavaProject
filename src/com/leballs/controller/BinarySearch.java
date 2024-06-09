/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leballs.controller;

import com.leballs.model.ModelClass;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class BinarySearch {

public void searchString(ArrayList<ModelClass> objectHolder, int low, int high, 
        String searchKey, ArrayList<Integer> positionHolder, String field) {
    String SearchField = ModelClass.getSearchField();    

        if (high >= low) {
            int mid = (low + high) / 2;
            String midValue = "";

            // Get the value of the specified field
            switch (SearchField) {
                case "Search by Name":
                    midValue = objectHolder.get(mid).getName();
                    break;
                
                case "Search by Jersey Number":
                    midValue = objectHolder.get(mid).getJerseyNumber();
                    break;
            } 

            // If the element is present at the middle itself
            if (midValue.equals(searchKey)) {
                // Add the current index to the holder
                positionHolder.add(mid);

                // Recursion: Continue searching in both subarrays
                searchString(objectHolder, low, mid - 1, searchKey, positionHolder, field);
                searchString(objectHolder, mid + 1, high, searchKey, positionHolder, field);
            } else if (midValue.compareTo(searchKey) > 0) {
                // If the element is smaller than mid, search in the left subarray
                searchString(objectHolder, low, mid - 1, searchKey, positionHolder, field);
            } else {
                // If the element is larger than mid, search in the right subarray
                searchString(objectHolder, mid + 1, high, searchKey, positionHolder, field);
            }
        }
        // Base case: Stop recursion when low exceeds high
    }
}


