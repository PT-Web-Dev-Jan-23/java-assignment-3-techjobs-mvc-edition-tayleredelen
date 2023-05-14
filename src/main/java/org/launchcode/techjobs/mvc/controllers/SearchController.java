package org.launchcode.techjobs.mvc.controllers;

import org.launchcode.techjobs.mvc.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.launchcode.techjobs.mvc.models.Job;
import java.util.ArrayList;

import static org.launchcode.techjobs.mvc.controllers.ListController.columnChoices;


/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @GetMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);
        return "search";
    }
    //This renders the form defined in the search.html template.


    // TODO #3 - Create a handler to process a search request and render the updated search view.


@PostMapping(value = "/results")
public String displaySearchResults(Model model, @RequestParam String searchType, @RequestParam String searchTerm) { //@RequestParam value names found in search.html
//    Add a displaySearchResults handler method to SearchController:
//    Use the correct annotation for the method. To configure the correct mapping type and mapping route, refer to the form tag in the search.html template. (Use @GetMapping or @PostMapping rather than @RequestMapping.)
//    The displaySearchResults method should take in a Model parameter.
//    The method should also take in two other parameters, specifying the type of search and the search term.
//    In order for these last two parameters to be properly passed in by Spring Boot, you need to use the correct annotation. Also, you need to name them appropriately, based on the corresponding form field names defined in search.html.
    ArrayList<Job> searchJobs;
//   Create an ArrayList from Job to pull searched info
    if (searchTerm.toLowerCase().equals("all") || searchTerm.toLowerCase().isEmpty()) {
        searchJobs = JobData.findAll();
        model.addAttribute("title", "View All");
//    If the user enters “all” in the search box, or if they leave the box empty, call the findAll() method from JobData. Otherwise, send the search information to findByColumnAndValue. In either case, store the results in a jobs ArrayList.
    } else {
        searchJobs = JobData.findByColumnAndValue(searchType, searchTerm);
//    Pass jobs into the search.html view via the model parameter.
        model.addAttribute("title", "Jobs with " + ListController.columnChoices.get(searchType) + ": " + searchTerm);
    }
    model.addAttribute("jobs", searchJobs);
    model.addAttribute("columns", ListController.columnChoices);
//    Pass ListController.columnChoices into the view, as the existing search handler does.

    return "search";
    }


}


