package org.project.easyf1.models.dto;

import java.util.List;
import java.util.Map;


public class RankingDTO {

    private String get;

    private Map<String, String> parameters;

    private List<String> errors;

    private int results;

    private List<DriverDetailDTO> response;

    public String getGet() {
        return get;
    }

    public void setGet(String get) {
        this.get = get;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public int getResults() {
        return results;
    }

    public void setResults(int results) {
        this.results = results;
    }

    public List<DriverDetailDTO> getResponse() {
        return response;
    }

    public void setResponse(List<DriverDetailDTO> response) {
        this.response = response;
    }
}

