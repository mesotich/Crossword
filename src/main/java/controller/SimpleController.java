package controller;

import model.Model;

public class SimpleController implements Controller{
    private Model model;

    public SimpleController(Model model) {
        this.model = model;
    }

    @Override
    public void start() {
       model.startBusinessLogic();
    }
}
