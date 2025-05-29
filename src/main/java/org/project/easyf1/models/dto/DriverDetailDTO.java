package org.project.easyf1.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.project.easyf1.models.entity.DriverDetail;

public class DriverDetailDTO {

    // Dados básicos do piloto
    @JsonProperty("first_name") // Assume que o JSON tem "first_name"
    private String first_name;

    @JsonProperty("last_name") // Assume que o JSON tem "last_name"
    private String last_name;

    @JsonProperty("driver_number") // Assume que o JSON tem "driver_number"
    private int driver_number;

    @JsonProperty("country_code") // Assume que o JSON tem "country_code"
    private String country_code;

    // Dados da equipe
    @JsonProperty("team_name") // Assume que o JSON tem "team_name"
    private String team_name;

    @JsonProperty("team_colour") // Assume que o JSON tem "team_colour"
    private String team_colour;

    @JsonProperty("team_full_name") // Assume que o JSON tem "team_full_name"
    private String team_full_name;

    @JsonProperty("headshot_url") // Adicione este campo para mapear o "headshot_url" do JSON
    private String headshot_url;

    @JsonProperty("team_base") // Assume que o JSON tem "team_base"
    private String team_base;

    @JsonProperty("team_principal") // Assume que o JSON tem "team_principal"
    private String team_principal;

    @JsonProperty("team_championship_position") // Assume que o JSON tem "team_championship_position"
    private int team_championship_position;

    // Desempenho
    @JsonProperty("championship_position") // Assume que o JSON tem "championship_position"
    private int championship_position;

    @JsonProperty("points") // Assume que o JSON tem "points"
    private int points;

    @JsonProperty("best_position") // Assume que o JSON tem "best_position"
    private int best_position;

    @JsonProperty("worst_position") // Assume que o JSON tem "worst_position"
    private int worst_position;

    @JsonProperty("victories") // Assume que o JSON tem "victories"
    private int victories;

    // Carro
    @JsonProperty("car_model") // Assume que o JSON tem "car_model"
    private String car_model;

    @JsonProperty("engine") // Assume que o JSON tem "engine"
    private String engine;

    @JsonProperty("power_hp") // Assume que o JSON tem "power_hp"
    private int power_hp;

    @JsonProperty("weight_kg") // Assume que o JSON tem "weight_kg"
    private int weight_kg;

    public DriverDetailDTO() {
    }

    public DriverDetailDTO(DriverDetail driver) {
        this.first_name = driver.getFirstName();
        this.last_name = driver.getLastName();
        this.driver_number = driver.getDriverNumber();
        this.country_code = driver.getCountryCode();
        this.team_name = driver.getTeamName();
        this.team_colour = driver.getTeamColour();
        this.team_full_name = driver.getTeamFullName();
        this.team_base = driver.getTeamBase();
        this.team_principal = driver.getTeamPrincipal();
        this.team_championship_position = driver.getTeamChampionshipPosition();
        this.championship_position = driver.getChampionshipPosition();
        this.points = driver.getPoints();
        this.best_position = driver.getBestPosition();
        this.worst_position = driver.getWorstPosition();
        this.victories = driver.getVictories();
        this.car_model = driver.getCarModel();
        this.engine = driver.getEngine();
        this.power_hp = driver.getPowerHp();
        this.weight_kg = driver.getWeightKg();
    }

    // Getters e Setters
    public String getFirst_name() { return first_name; }
    public void setFirst_name(String first_name) { this.first_name = first_name; }

    public String getLast_name() { return last_name; }
    public void setLast_name(String last_name) { this.last_name = last_name; }

    public int getDriver_number() { return driver_number; }
    public void setDriver_number(int driver_number) { this.driver_number = driver_number; }

    public String getCountry_code() { return country_code; }
    public void setCountry_code(String country_code) { this.country_code = country_code; }

    public String getTeam_name() { return team_name; }
    public void setTeam_name(String team_name) { this.team_name = team_name; }

    public String getTeam_colour() { return team_colour; }
    public void setTeam_colour(String team_colour) { this.team_colour = team_colour; }

    public String getTeam_full_name() { return team_full_name; }
    public void setTeam_full_name(String team_full_name) { this.team_full_name = team_full_name; }

    public String getTeam_base() { return team_base; }
    public void setTeam_base(String team_base) { this.team_base = team_base; }

    public String getTeam_principal() { return team_principal; }
    public void setTeam_principal(String team_principal) { this.team_principal = team_principal; }

    public int getTeam_championship_position() { return team_championship_position; }
    public void setTeam_championship_position(int team_championship_position) { this.team_championship_position = team_championship_position; }

    public int getChampionship_position() { return championship_position; }
    public void setChampionship_position(int championship_position) { this.championship_position = championship_position; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    public int getBest_position() { return best_position; }
    public void setBest_position(int best_position) { this.best_position = best_position; }

    public int getWorst_position() { return worst_position; }
    public void setWorst_position(int worst_position) { this.worst_position = worst_position; }

    public int getVictories() { return victories; }
    public void setVictories(int victories) { this.victories = victories; }

    public String getCar_model() { return car_model; }
    public void setCar_model(String car_model) { this.car_model = car_model; }

    public String getEngine() { return engine; }
    public void setEngine(String engine) { this.engine = engine; }

    public int getPower_hp() { return power_hp; }
    public void setPower_hp(int power_hp) { this.power_hp = power_hp; }

    public int getWeight_kg() { return weight_kg; }
    public void setWeight_kg(int weight_kg) { this.weight_kg = weight_kg; }

    public DriverDetail getDriverDetail() {
        DriverDetail driverDetail = new DriverDetail();
        driverDetail.setDriverNumber(this.driver_number);
        driverDetail.setBestPosition(this.best_position);
        driverDetail.setWorstPosition(this.worst_position);
        driverDetail.setVictories(this.victories);
        driverDetail.setCarModel(this.car_model);
        driverDetail.setEngine(this.engine);
        driverDetail.setPowerHp(this.power_hp);
        driverDetail.setWeightKg(this.weight_kg);
        driverDetail.setCountryCode(this.country_code);
        driverDetail.setTeamName(this.team_name);
        driverDetail.setTeamColour(this.team_colour);
        driverDetail.setTeamFullName(this.team_full_name);
        driverDetail.setTeamBase(this.team_base);
        driverDetail.setTeamPrincipal(this.team_principal);
        driverDetail.setTeamChampionshipPosition(this.team_championship_position);
        driverDetail.setChampionshipPosition(this.championship_position);
        driverDetail.setPoints(this.points);
        driverDetail.setFirstName(this.first_name);
        driverDetail.setLastName(this.last_name);

        return driverDetail;
    }

    public String getHeadshot_url() {
        return headshot_url;
    }

    public void setHeadshot_url(String headshot_url) {
        this.headshot_url = headshot_url;
    }
}