package com.udacity.jwdnd.course1.cloudstorage.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;


@ControllerAdvice
public class FileExceptionHandler
{
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleFileSizeLimitExceeded(Model model, Exception ex) {
        model.addAttribute("result", "error");
        model.addAttribute("message", "File size should be less than 10 MB.");

        return "result";
    }
}
