package com.example.exampro.controller;

import com.example.exampro.dao.UserRepo;
import com.example.exampro.models.NewUser;
import com.example.exampro.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    UserRepo userRepo = new UserRepo();
    User userForUpdate = new User();

    @GetMapping("/")
    public String login(Model model){
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/")
    public String login(@ModelAttribute User user, Model model){
        if (userRepo.login(user.getPassword()) != null){
            if(userRepo.login(user.getPassword()).getRole().equals("Admin")){
                return "redirect:/inStock";
            }
            else{
                return "redirect:/scanProductIn";
            }
        }
        model.addAttribute("error", true);

        return "login";
    }

    @GetMapping("/userList")
    public String userList(Model model){
        model.addAttribute("user", userRepo.readAll());

        return "userList";
    }

    @GetMapping("/updateUser")
    public String updateUser(@RequestParam("id") int id, Model model){
        model.addAttribute("user", userRepo.read(id));
        userForUpdate.setId(id);
        return "updateUser";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute User user, Model model){
        user.setId(userForUpdate.getId());
        userRepo.update(user);
        model.addAttribute("userUpdated", true);
        return "redirect:/userList";
    }

    @GetMapping("/createUser")
    public String createUser(Model model){
        model.addAttribute("newUser", new NewUser());
        return "createUser";
    }

    @PostMapping("/createUser")
    public String createUser(@ModelAttribute NewUser newUser, Model model){
        System.out.println(newUser);
        if(newUser.getUser().getPassword() != newUser.getPassword().getPassword()) {
            model.addAttribute("passwordDontMatch", true);
            return "createUser";
        }
        if (userRepo.checkPassword(newUser.getPassword().getPassword()) != null) {
            model.addAttribute("passwordExists", true);
            return "createUser";
        }

        userRepo.create(newUser.getUser());
        model.addAttribute("userCreated", true);
        return "redirect:/userList";
    }

    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") int id, Model model){
        model.addAttribute("user", userRepo.read(id));
        return "deleteUser";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@ModelAttribute User user){
        userRepo.delete(user);
        return "redirect:/userList";
    }
}
