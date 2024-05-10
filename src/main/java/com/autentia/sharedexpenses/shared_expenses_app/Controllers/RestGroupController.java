package com.autentia.sharedexpenses.shared_expenses_app.Controllers;

import com.autentia.sharedexpenses.shared_expenses_app.Controllers.DTOs.Requests.GroupRequest;
import com.autentia.sharedexpenses.shared_expenses_app.Domain.Group;
import com.autentia.sharedexpenses.shared_expenses_app.Services.GroupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//Group Rest Controller class, not finished

@RestController
@RequestMapping("/group")
public class RestGroupController {

    private final GroupService groupService;

    @Autowired
    RestGroupController(GroupService groupService){
        this.groupService = groupService;
    }

    @GetMapping
    public List<Group> getUsers(){
        return this.groupService.getGroups();
    }

    @PostMapping
    public void createGroup(@RequestBody Group group){
        this.groupService.createGroup(group);
    }

    @GetMapping(path = "/{id}")
    public Optional<Group> getGroupById(@PathVariable("id") int id){
        return this.groupService.getGroupById(id);
    }

    @PutMapping(path = "/{id}")
    public Group updateGroup(@RequestBody GroupRequest request, @PathVariable("id") int id){
        Group group = groupService.updateGroup(request, id);
        return new Group(group.getId(), group.getTitle(), group.getDescription());
    }

    @DeleteMapping(path = "/{id}")
    public String deleteGroup(@PathVariable("id") int id){

        boolean deleted = this.groupService.deleteGroup(id);

        if (deleted){
            return "Group deleted successfully";
        } else {
            return "Failed to delete group";
        }
    }

}
