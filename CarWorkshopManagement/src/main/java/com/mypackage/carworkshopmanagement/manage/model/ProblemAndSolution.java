package com.mypackage.carworkshopmanagement.manage.model;

public class ProblemAndSolution {
    private int id;
    private String problem;
    private String possibleSolution;

    public ProblemAndSolution(int id, String problem) {
        this.id = id;
        this.problem = problem;
        this.possibleSolution = "";
    }

    public ProblemAndSolution(int id, String problem, String possibleSolution) {
        this.id = id;
        this.problem = problem;
        this.possibleSolution = possibleSolution;
    }

    public ProblemAndSolution(String problem, String possibleSolution) {
        this.problem = problem;
        this.possibleSolution = possibleSolution;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getPossibleSolution() {
        return possibleSolution;
    }

    public void setPossibleSolution(String possibleSolution) {
        this.possibleSolution = possibleSolution;
    }
}

