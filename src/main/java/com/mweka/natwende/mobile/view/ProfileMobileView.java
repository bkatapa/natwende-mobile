/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mweka.natwende.mobile.view;

import com.mweka.natwende.mobile.util.MessageHelper;
import com.mweka.natwende.user.vo.UserVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Bell
 */

@Named
@SessionScoped
public class ProfileMobileView extends MessageHelper {
    
    private UserVO selectedEntity;
    private List<UserVO> entityList;
    private int activeIndex;
    private boolean guest, keepSignedIn;
    
    @PostConstruct
    public void init() {
        selectedEntity = new UserVO();
        entityList = new ArrayList<>();
        activeIndex = 0;
        guest = false;
        keepSignedIn = false;
    }

    public UserVO getSelectedEntity() {
        return selectedEntity;
    }

    public void setSelectedEntity(UserVO selectedEntity) {
        this.selectedEntity = selectedEntity;
    }

    public List<UserVO> getEntityList() {
        return entityList;
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }

    public boolean isGuest() {
        return guest;
    }

    public void setGuest(boolean guest) {
        this.guest = guest;
    }

    public boolean isKeepSignedIn() {
        return keepSignedIn;
    }

    public void setKeepSignedIn(boolean keepSignedIn) {
        this.keepSignedIn = keepSignedIn;
    }
    
    public String viewEntity() {
        return "pm:seatSelection-page?transition=slide";
    }
    
    public String createEntity() {
        return "pm:seatSelection-page?transition=slide";
    }
    
    public String deleteEntity() {
        return "pm:seatSelection-page?transition=slide";
    }
    
    public void onGuestSelect() {
        //System.out.println("Roud trip is: " + roundTrip);
    }
    
    public void onKeepSignedIn() {
        //System.out.println("Roud trip is: " + roundTrip);
    }
}
