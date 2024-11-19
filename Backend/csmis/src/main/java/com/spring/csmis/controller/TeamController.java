package com.spring.csmis.controller;


import com.spring.csmis.entity.TeamEntity;
import com.spring.csmis.service.TeamService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/team")
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping("/upload")
    public String uploadDepartments(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "Please upload a file!";
        }

        try {
            teamService.importTeamsFromExcel(file.getInputStream());
            return "Teams imported successfully!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error occurred while importing departments.";
        }
    }

    @PostMapping("/addTeam")
    public TeamEntity saveTeam(@RequestBody TeamEntity team){
        return teamService.insertTeam(team);
    }

    @GetMapping("/showTeams")
    public List<TeamEntity> showAllTeam(){

        return teamService.showAllTeams();
    }

    @GetMapping("/showbyTeamid/{id}")
    public Optional<TeamEntity> showDivisionbyId(@PathVariable("id") String id){
        return teamService.showbyTeamId(Integer.parseInt(id));
    }

//    @GetMapping("/showbyTeamName")
//    public ResponseEntity<Team> showTeamByName(
//            @RequestParam("department") String department,
//            @RequestParam("division") String division,
//            @RequestParam("name") String name,
//            HttpServletRequest request) {
//        System.out.println("Full URL: " + request.getRequestURL());
//        System.out.println("Query String: " + request.getQueryString());
//        System.out.println("Received parameters: department=" + department + ", division=" + division + ", name=" + name);
//
//        Optional<Team> team = teamService.getTeamByDepartmentName(department, division, name);
//        return team.map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }

    @GetMapping("/showbyTeamName")
    public ResponseEntity<TeamEntity> showTeamByName(
            @RequestParam("department") String department,
            @RequestParam("division") String division,
            @RequestParam("name") String name,
            HttpServletRequest request) {
        System.out.println("Full URL: " + request.getRequestURL());
        System.out.println("Query String: " + request.getQueryString());
        System.out.println("Received parameters: department=" + department + ", division=" + division + ", name=" + name);

        Optional<TeamEntity> team = teamService.getTeamByDepartmentName(department, division, name);
        return team.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping ("/updateTeam")
    public TeamEntity updateTeam(@RequestBody TeamEntity team){
        return teamService.updateTeam(team);
    }

    @PutMapping("/deleteTeam/{id}")
    public  void deleteDivision(@PathVariable("id") String id) {
        teamService.deleteTeam(Integer.parseInt(id));
    }
}
