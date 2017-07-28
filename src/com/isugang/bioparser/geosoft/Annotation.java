/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isugang.bioparser.geosoft;

import com.google.common.collect.HashMultimap;
import java.util.HashMap;

/**
 *
 * @author sugang
 */
public class Annotation {
    
    private HashMultimap<String, String> attributes = HashMultimap.create();
    
    public void addAttribute(String key, String value){
        //System.out.println(key + "==>" + value);
        attributes.put(key, value);
    }
    
    @Override
    public String toString(){
        return "Annotation: " + attributes.size() + " entries";
    }
}
