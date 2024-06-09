/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leballs.controller;

/**
 *
 * @author user
 */


import com.leballs.model.ModelClass;

import java.util.ArrayList;

public class MergeSort {

    public ArrayList<ModelClass> mergeSort(ArrayList<ModelClass> arr, String sortBy) {
        if (arr.size() <= 1) {
            return arr;
        }

        int mid = arr.size() / 2;
        ArrayList<ModelClass> left = new ArrayList<>(arr.subList(0, mid));
        ArrayList<ModelClass> right = new ArrayList<>(arr.subList(mid, arr.size()));

        return merge(mergeSort(left, sortBy), mergeSort(right, sortBy), sortBy);
    }

    private ArrayList<ModelClass> merge(ArrayList<ModelClass> left, ArrayList<ModelClass> right, String sortBy) {
        ArrayList<ModelClass> result = new ArrayList<>();
        int leftIndex = 0, rightIndex = 0;

        while (leftIndex < left.size() && rightIndex < right.size()) {
            ModelClass leftModel = left.get(leftIndex);
            ModelClass rightModel = right.get(rightIndex);

            int comparison = getComparisonResult(leftModel, rightModel, sortBy);

            if (comparison <= 0) {
                result.add(leftModel);
                leftIndex++;
            } else {
                result.add(rightModel);
                rightIndex++;
            }
        }

        result.addAll(left.subList(leftIndex, left.size()));
        result.addAll(right.subList(rightIndex, right.size()));

        return result;
    }
private int getComparisonResult(ModelClass model1, ModelClass model2, String sortBy) {
    switch (sortBy) {
        case "Points Per Game":
            // Compare based on points per game 
            float pointsPerGame1 = Float.parseFloat(model1.getPointsPerGame());
            float pointsPerGame2 = Float.parseFloat(model2.getPointsPerGame());
            return Float.compare(pointsPerGame1, pointsPerGame2);

        case "Assists Per Game":
            // Compare based on assists per game)
            float assistsPerGame1 = Float.parseFloat(model1.getAssistsPerGame());
            float assistsPerGame2 = Float.parseFloat(model2.getAssistsPerGame());
            return Float.compare(assistsPerGame1, assistsPerGame2);
        
        case "Name":
            //compare name
            String name1 = model1.getName();
            String name2 = model2.getName();
            return name1.compareTo(name2);
        case "Jersey Number":
            //compare based on Jersey Number
            int jerseyNumber1 = Integer.parseInt(model1.getJerseyNumber());
            int jerseyNumber2 = Integer.parseInt(model2.getJerseyNumber());
            return Integer.compare(jerseyNumber1, jerseyNumber2);

        default:
            throw new IllegalArgumentException("Invalid sorting criteria: " + sortBy);
    }
}}