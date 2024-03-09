package net.codejava.UserRegistration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AppController {

    @Autowired
    private UserRepository repo;

    @Autowired
    private IntroRepository introRepository;

    @GetMapping("/")
    public String viewHomePage() {
        return "index";
    } 

    @GetMapping("/register")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());
        return "signup_form";
    }

    @PostMapping("/register/save")
    public String processRegistration(User user) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setLoginCount(0);

        var existingUser = repo.findByEmail(user.getEmail());

        if (existingUser != null) {
            return "redirect:/register?failed";
        } else {
            repo.save(user);
            return "redirect:/register?success";
        }
    }
    
    @GetMapping("/details")
    public String viewUserDetails(Model model) {
        final CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();

        Intro intro = introRepository.findByUserId(currentUser.getId());
        model.addAttribute("intro", intro);

        User user = repo.findByEmail(currentUser.getUsername());

        if(user.getLoginCount() == 0) {
            return "redirect:/details/intro";
        } else {
            return "details";
        }        
    }

        
    @GetMapping("/details/intro")
    public String updateIntro(Model model) {
        model.addAttribute("intro", new Intro());
        
        return "update_details";
    }

    @PostMapping("/details/save")
    public String introSubmit(@ModelAttribute Intro intro, Model model) {
        final CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();

        User user = repo.findByEmail(currentUser.getUsername());
        //model.addAttribute("intro", intro);
        //model.addAttribute("user", user);
        
        intro.setUserId(currentUser.getId());
        user.setLoginCount(currentUser.getLoginCount()+1);

        introRepository.save(intro);
        repo.save(user);

        return "redirect:/details?intro_saved";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
    // @GetMapping("/list_users")
    // public String viewUsersList(Model model) {
    //     List<User> listUsers = repo.findAll();
    //     model.addAttribute("listUsers", listUsers);
    //     return "/users";
    // }
}
