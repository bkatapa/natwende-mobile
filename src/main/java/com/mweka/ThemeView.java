/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mweka;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Bell
 */
@ManagedBean
@SessionScoped
public class ThemeView implements Serializable {
    
    /**
     *
     * @return
     */
    public List<BusVO> getBusList() {
        return Arrays.asList(new BusVO[]{
            //new BusVO("Juldan Motors", "juldan-bus.png"),
            //new BusVO("Mazhandu Family Bus Services", "mazhandu-bus.png"),
            new BusVO("Post Bus", "post-bus.png"),
            new BusVO("Shalom Bus Services", "shalom-bus.png"),
            new BusVO("Wada Chovu", "wadachovu-bus.png")                
        });
    }   
    
}
