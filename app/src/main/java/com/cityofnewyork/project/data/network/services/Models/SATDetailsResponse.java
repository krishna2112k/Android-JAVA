package com.cityofnewyork.project.data.network.services.Models;

public class SATDetailsResponse {

    private String dbn;

    private String sat_writing_avg_score;

    private String sat_critical_reading_avg_score;

    private String sat_math_avg_score;

    private String school_name;

    private String num_of_sat_test_takers;

    public String getDbn ()
    {
        return dbn;
    }

    public void setDbn (String dbn)
    {
        this.dbn = dbn;
    }

    public String getSat_writing_avg_score ()
    {
        return sat_writing_avg_score;
    }

    public void setSat_writing_avg_score (String sat_writing_avg_score)
    {
        this.sat_writing_avg_score = sat_writing_avg_score;
    }

    public String getSat_critical_reading_avg_score ()
    {
        return sat_critical_reading_avg_score;
    }

    public void setSat_critical_reading_avg_score (String sat_critical_reading_avg_score)
    {
        this.sat_critical_reading_avg_score = sat_critical_reading_avg_score;
    }

    public String getSat_math_avg_score ()
    {
        return sat_math_avg_score;
    }

    public void setSat_math_avg_score (String sat_math_avg_score)
    {
        this.sat_math_avg_score = sat_math_avg_score;
    }

    public String getSchool_name ()
    {
        return school_name;
    }

    public void setSchool_name (String school_name)
    {
        this.school_name = school_name;
    }

    public String getNum_of_sat_test_takers ()
    {
        return num_of_sat_test_takers;
    }

    public void setNum_of_sat_test_takers (String num_of_sat_test_takers)
    {
        this.num_of_sat_test_takers = num_of_sat_test_takers;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [dbn = "+dbn+", sat_writing_avg_score = "+sat_writing_avg_score+", sat_critical_reading_avg_score = "+sat_critical_reading_avg_score+", sat_math_avg_score = "+sat_math_avg_score+", school_name = "+school_name+", num_of_sat_test_takers = "+num_of_sat_test_takers+"]";
    }

}
