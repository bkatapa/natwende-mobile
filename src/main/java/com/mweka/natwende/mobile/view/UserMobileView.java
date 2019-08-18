/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mweka.natwende.mobile.view;

import com.mweka.natwende.mobile.service.UserMobileService;
import com.mweka.natwende.mobile.util.MessageHelper;
import com.mweka.natwende.user.vo.UserVO;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Bell
 */
@Named
@SessionScoped
public class UserMobileView extends MessageHelper {
    
    private List<UserVO> entityList;
    private UserVO selectedEntity;    
    private FacesContext facesContext;
    private int activeIndex;
    
    @Inject
    private UserMobileService userMobileService;
    
    @PostConstruct
    public void init() {
        selectedEntity = new UserVO();
        entityList = new ArrayList<>();
        facesContext = FacesContext.getCurrentInstance();
        activeIndex = 0;
    }

    public List<UserVO> getEntityList() {
        return entityList;
    }

    public UserVO getSelectedEntity() {
        return selectedEntity;
    }

    public void setSelectedEntity(UserVO selectedEntity) {
        this.selectedEntity = selectedEntity;
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }
    
    public void fetchUserAccount() {
        try {
            selectedEntity = userMobileService.getEntity("749865");
        }
        catch (Exception ex) {
            onMessage("error", ex.getMessage());
        }
    }
    
    public StreamedContent getPreviewImage() {
        if (facesContext.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        }
        try {
            byte[] imageBytes = userMobileService.getImageBytes(selectedEntity);
            InputStream in = new ByteArrayInputStream(imageBytes);
            return imageBytes == null ? getDefaultPreviewImage() : new DefaultStreamedContent(in);
        }
        catch (Exception ex) {
            onMessage("error", ex.getMessage());
        }
        return null;
    }
    
    public StreamedContent getDefaultPreviewImage() {
        return new DefaultStreamedContent(facesContext.getExternalContext().getResourceAsStream("/resources/images/default-user.png"));
    }
}
