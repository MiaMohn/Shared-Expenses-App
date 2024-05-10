package com.autentia.sharedexpenses.shared_expenses_app.Services;

import com.autentia.sharedexpenses.shared_expenses_app.Controllers.DTOs.Requests.GroupRequest;
import com.autentia.sharedexpenses.shared_expenses_app.Domain.Group;
import com.autentia.sharedexpenses.shared_expenses_app.Repository.IGroupRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//Group service class, not in use, not finished
@Service
public class GroupService {

    private final IGroupRepository groupRepository;

    @Autowired
    public GroupService(IGroupRepository groupRepository){
        this.groupRepository = groupRepository;
    }

    //List all groups:

    public List<Group> getGroups(){
        return (ArrayList<Group>) groupRepository.findAll();
    }

    //Create a new group:

    public void createGroup(Group group){
        groupRepository.save(group);
    }

    //Find a group by its id:

    public Optional<Group> getGroupById(int id){
        return groupRepository.findById(id);
    }

    //Update group:

    public Group updateGroup(GroupRequest request, int id){
        Group group = groupRepository.findById(id).orElseThrow(() -> new RuntimeException("Group not found"));;

        group.setTitle(request.getTitle());
        group.setDescription(request.getDescription());
        groupRepository.update(group, id);
        return group;
    }

    //Delete group:

    public Boolean deleteGroup(int id){

        try {
            groupRepository.deleteById(id);
            return true;

        } catch (Exception e){
            return false;
        }

    }


}
