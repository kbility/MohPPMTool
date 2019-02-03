package com.mohbility.ppmtool.Web;


import com.mohbility.ppmtool.Services.ProjectService;
import com.mohbility.ppmtool.Services.ValidationErrorService;
import com.mohbility.ppmtool.domain.Project;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    ProjectService projectService;
    private ValidationErrorService validationErrorService;

    public ProjectController(ProjectService projectService, ValidationErrorService validationErrorService) {
        this.projectService = projectService;
        this.validationErrorService = validationErrorService;
    }

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result){

       ResponseEntity<?> errorMap = validationErrorService.ValidationErrorService(result);
       if(errorMap != null) return errorMap;


        Project project1 = projectService.saveOrUpdateProject(project);
        return new ResponseEntity<Project>(project, HttpStatus.CREATED);
    }
    
    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId){
        
        Project project = projectService.findProjectByIdentifier(projectId);

        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<?> getAllProject(){
        return projectService.findAllProjects();
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectId){

        projectService.deleteProjectByIdentifier(projectId);

        return new ResponseEntity<>("Project with ID: '" + projectId + "' was deleted", HttpStatus.OK);

    }
}







