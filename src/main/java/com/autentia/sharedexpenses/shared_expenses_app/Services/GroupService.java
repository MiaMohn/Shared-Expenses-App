package com.autentia.sharedexpenses.shared_expenses_app.Services;

import com.autentia.sharedexpenses.shared_expenses_app.Controllers.DTOs.Requests.GroupRequest;
import com.autentia.sharedexpenses.shared_expenses_app.Domain.Group;
import com.autentia.sharedexpenses.shared_expenses_app.Repository.IGroupRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    private final IGroupRepository groupRepository;

    @Autowired
    public GroupService(IGroupRepository groupRepository){
        this.groupRepository = groupRepository;
    }

    //Métodos
    public List<Group> getGroups(){
        return (ArrayList<Group>) groupRepository.findAll();
    }

    public void createGroup(Group group){
        groupRepository.save(group);
    }

    public Optional<Group> getGroupById(int id){
        return groupRepository.findById(id);
    }

    public Group updateGroup(GroupRequest request, int id){
        Group group = groupRepository.findById(id).orElseThrow(() -> new RuntimeException("Group not found"));;

        group.setTitle(request.getTitle());
        group.setDescription(request.getDescription());
        groupRepository.update(group, id);
        return group;
    }

    public Boolean deleteGroup(int id){

        try {
            groupRepository.deleteById(id);
            return true;

        } catch (Exception e){
            return false;
        }

    }


}
