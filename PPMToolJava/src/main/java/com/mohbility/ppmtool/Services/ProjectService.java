package com.mohbility.ppmtool.Services;


import com.mohbility.ppmtool.domain.Project;
import com.mohbility.ppmtool.exceptions.ProjectIdException;
import com.mohbility.ppmtool.repository.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project saveOrUpdateProject(Project project){

        try{

            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);

        }catch (Exception e){
            throw new ProjectIdException("Project ID '" +project.getProjectIdentifier().toUpperCase() +"' already exists");
        }
    }


    public Project findProjectByIdentifier(String projectId){

        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project == null){
            throw new ProjectIdException("Project ID '" + projectId +"' does not exists");
        }

        return project;
    }

    public Iterable<Project> findAllProjects(){

        return projectRepository.findAll();

    }

    public void deleteProjectByIdentifier(String projectId){

        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project == null) {
            throw new ProjectIdException(("Cannot delete Project with ID '" + projectId + "'. This project does not exist! "));
        }

        projectRepository.delete(project);
    }
}









