package org.launchcode.techjobs.mvc.controllers;


import org.launchcode.techjobs.mvc.models.Job;
import org.launchcode.techjobs.mvc.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "list")
public class ListController {

//This controller provides functionality for users to see either a table showing all the options for the different
//Job fields (employer, location, coreCompetency, and positionType) or a list of details for a selected set of jobs.
    static HashMap<String, String> columnChoices = new HashMap<>();
    static HashMap<String, Object> tableChoices = new HashMap<>();


    public ListController () {
        columnChoices.put("all", "All");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("positionType", "Position Type");
        columnChoices.put("coreCompetency", "Skill");

        tableChoices.put("employer", JobData.getAllEmployers());
        tableChoices.put("location", JobData.getAllLocations());
        tableChoices.put("positionType", JobData.getAllPositionTypes());
        tableChoices.put("coreCompetency", JobData.getAllCoreCompetency());


    }

//At the top of ListController is a constructor that populates columnChoices and tableChoices with values.
//These HashMaps play the same role as in the console app, which is to provide a centralized collection of
//the different List and Search options presented throughout the user interface.

    @GetMapping(value = "")
    public String list(Model model) {
        model.addAttribute("columns", columnChoices);
        model.addAttribute("tableChoices", tableChoices);
        model.addAttribute("employers", JobData.getAllEmployers());
        model.addAttribute("locations", JobData.getAllLocations());
        model.addAttribute("positions", JobData.getAllPositionTypes());
        model.addAttribute("skills", JobData.getAllCoreCompetency());

        return "list";
    }

//This handler method renders a view that displays a table of clickable links for the different job categories.
//The handlers obtain data by implementing the JobData class methods

    @GetMapping(value = "jobs")
    public String listJobsByColumnAndValue(Model model, @RequestParam String column, @RequestParam(required = false) String value) {
        ArrayList<Job> jobs;
        if (column.equals("all")){
            jobs = JobData.findAll();
            model.addAttribute("title", "All Jobs");
        } else {
            jobs = JobData.findByColumnAndValue(column, value);
            model.addAttribute("title", "Jobs with " + columnChoices.get(column) + ": " + value);
        }
        model.addAttribute("jobs", jobs);

        return "list-jobs";
    }

//This handler method renders a different view that displays information for the jobs that relate to a selected category.
//The handlers obtain data by implementing the JobData class methods

//In the listJobsByColumnAndValue method, the controller uses two query parameters passed in as column and value
//to determine what to fetch from JobData. In the case of "all" it will fetch all job data, otherwise, it
//will retrieve a smaller set of information. The controller then renders the list-jobs.html view.

//listJobsByColumnAndValue works similarly to the search functionality, in that we are “searching” for a particular
//value within a particular field and then displaying jobs that match. However, this is slightly different from
//the other way of searching in that the user will arrive at this handler method as a result of clicking on a
//link within the list view, rather than via submitting a form. We’ll see where these links originate when we look
//at the views. Also note that the listJobsByColumnAndValue method deals with an “all” scenario differently than
//if a user clicks one of the category links.


}
