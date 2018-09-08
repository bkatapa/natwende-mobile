/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mweka;

//import com.mweka.kolopa.types.PagePath;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Bell
 */

@ManagedBean
@SessionScoped
public class MobileNavigationController implements Serializable {
    
    private boolean mobile;
    private String msg;
    private int activeTabIndex;
    
    @PostConstruct
    public void init() {
        mobile = false;
        msg = "Generic Message";
        activeTabIndex = 0;
    }    

    public boolean isMobile() {
        return mobile;
    }

    public void setMobile(boolean mobile) {
        this.mobile = mobile;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getActiveTabIndex() {
        return activeTabIndex;
    }

    public void setActiveTabIndex(int activeTabIndex) {
        this.activeTabIndex = activeTabIndex;
    }
    
    public void initMobile() {
        setMobile(true);
    }
    
    public String menuNavigation(String view) {
        switch (view) {
            case "HOME" : 
                setActiveTabIndex(0);
                return "pm:home-page?transition=slidefade&reverse=true";
            case "BOOK" : 
                setActiveTabIndex(1);
                return "pm:tripSearch-page?transition=slidefade&reverse=true";
            case "TRIP" : setActiveTabIndex(2);
                return "pm:tripList-page?transition=slidefade&reverse=true";
            case "PROFILE" : 
                setActiveTabIndex(3);
                return "pm:profileView-page?transition=slidefade&reverse=true";
            default : setActiveTabIndex(0);
        }
        return "pm:home-page?transition=slidefade&reverse=true";
    }
}