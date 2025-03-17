package com.example;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import cleverquiz.controller.Controller;
import cleverquiz.controller.IController;

import java.io.Serializable;

@ManagedBean
@ViewScoped
public class CategoryBean implements Serializable {
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void saveCategory() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (categoryName == null || categoryName.trim().isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Category name cannot be empty"));
        } else {

            IController controller = new Controller();
            boolean success = controller.addCategory(categoryName);

            if (success) {
                // Add a success message to the FacesContext
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Category saved: " + categoryName));
            }
            else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Category could not be saved!"));
            }

            // ToDo: Remove print
            // Print out the content of the input field
            System.out.println("Category: " + categoryName);

            // Reset the input field
            categoryName = null;
        }
    }
}