package lk.gov.health.beans;

import lk.gov.health.schoolhealth.Area;
import lk.gov.health.beans.util.JsfUtil;
import lk.gov.health.beans.util.JsfUtil.PersistAction;
import lk.gov.health.faces.AreaFacade;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;
import lk.gov.health.schoolhealth.AreaType;

@Named
@SessionScoped
public class AreaController implements Serializable {

    @EJB
    private lk.gov.health.faces.AreaFacade ejbFacade;
    private List<Area> items = null;
    private Area selected;

    @Inject
    WebUserController webUserController;

    public String toAddProvince() {
        if (!webUserController.isCapableOfAddingProvinces()) {
            JsfUtil.addErrorMessage("You are not autherized");
            return "";
        }
        selected = new Area();
        selected.setType(AreaType.Province);
        return "/area/add_province";
    }

    public String toAddDistrict() {
        if (!webUserController.isCapableOfAddingRdhsAreas()) {
            JsfUtil.addErrorMessage("You are not autherized");
            return "";
        }
        selected = new Area();
        selected.setType(AreaType.District);
        return "/area/add_district";
    }

    public String toAddMhoArea() {
        if (!webUserController.isCapableOfAddingMohAreas()) {
            JsfUtil.addErrorMessage("You are not autherized");
            return "";
        }
        selected = new Area();
        selected.setType(AreaType.MOH);
        return "/area/add_moh";
    }

    public String toEducationalZones() {
        if (!webUserController.isCapableOfAddingMohAreas()) {
            JsfUtil.addErrorMessage("You are not autherized");
            return "";
        }
        selected = new Area();
        selected.setType(AreaType.EducationalZone);
        return "/area/add_educational_zones";
    }

    public String toAddPhiArea() {
        if (!webUserController.isCapableOfAddingPhiAreas()) {
            JsfUtil.addErrorMessage("You are not autherized");
            return "";
        }
        selected = new Area();
        selected.setType(AreaType.PHI);
        return "/area/add_phi";
    }

    public String saveNewProvince() {
        if (!webUserController.isCapableOfAddingProvinces()) {
            JsfUtil.addErrorMessage("You are not autherized");
            return "";
        }
        selected.setCreateAt(new Date());
        getFacade().create(selected);
        selected = null;
        items = null;
        webUserController.fillLogginDetails();
        JsfUtil.addSuccessMessage("New Province Saved");
        return "/area/add_area_index";
    }

    public String saveNewDistrict() {
        if (!webUserController.isCapableOfAddingRdhsAreas()) {
            JsfUtil.addErrorMessage("You are not autherized");
            return "";
        }
        selected.setCreateAt(new Date());
        getFacade().create(selected);
        selected = null;
        items = null;
        webUserController.fillLogginDetails();
        JsfUtil.addSuccessMessage("New District Saved");
        return "/area/add_area_index";
    }

    public String saveNewMoh() {
        if (!webUserController.isCapableOfAddingMohAreas()) {
            JsfUtil.addErrorMessage("You are not autherized");
            return "";
        }
        selected.setCreateAt(new Date());
        getFacade().create(selected);
        selected = null;
        items = null;
        webUserController.fillLogginDetails();
        JsfUtil.addSuccessMessage("New MOH Area Saved");
        return "/area/add_area_index";
    }

    public String saveNewEducationalZone() {
        if (!webUserController.isCapableOfAddingMohAreas()) {
            JsfUtil.addErrorMessage("You are not autherized");
            return "";
        }
        selected.setCreateAt(new Date());
        getFacade().create(selected);
        selected = null;
        items = null;
        webUserController.fillLogginDetails();
        JsfUtil.addSuccessMessage("New Educational Zone Saved");
        return "/area/add_area_index";
    }
    
    public String saveNewPhi() {
        if (!webUserController.isCapableOfAddingPhiAreas()) {
            JsfUtil.addErrorMessage("You are not autherized");
            return "";
        }
        selected.setCreateAt(new Date());
        getFacade().create(selected);
        selected = null;
        items = null;
        webUserController.fillLogginDetails();
        JsfUtil.addSuccessMessage("New PHI Area Saved");
        return "/area/add_area_index";
    }

    public List<Area> getAreas(AreaType areaType, Area superArea) {
        String j;
        Map m = new HashMap();
        j = "select a "
                + " from Area a "
                + " where a.name is not null ";
        if (areaType != null) {
            j += " and a.type=:t";
            m.put("t", areaType);
        }
        if (superArea != null) {
            j += " and (a=:pa or a.parentArea=:pa or a.parentArea.parentArea=:pa or a.parentArea.parentArea.parentArea=:pa  or a.parentArea.parentArea.parentArea.parentArea=:pa) ";
            m.put("pa", superArea);
        }
        j+=" order by a.name";
        System.out.println("m = " + m);
        System.out.println("j = " + j);
        List<Area> areas = getFacade().findBySQL(j, m);
        System.out.println("areas.size() = " + areas.size());
        return areas;
    }

    public AreaController() {
    }

    public Area getSelected() {
        return selected;
    }

    public void setSelected(Area selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private AreaFacade getFacade() {
        return ejbFacade;
    }

    public Area prepareCreate() {
        selected = new Area();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("AreaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("AreaUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("AreaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Area> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public List<Area> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Area> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Area.class)
    public static class AreaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AreaController controller = (AreaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "areaController");
            return controller.getFacade().find(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Area) {
                Area o = (Area) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Area.class.getName()});
                return null;
            }
        }

    }

}
