package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.hw.services.CommentService;

@RequiredArgsConstructor
@Controller
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/book/{id}/comments")
    public String showCommentsForBookId(@PathVariable Long id, Model model) {
        var comments = commentService.findByBookId(id);
        model.addAttribute("comments", comments);
        return "comment";
    }

}
