package com.mainul35.google.calendar.controller;

import com.mainul35.google.calendar.dto.DriveFiles;
import com.mainul35.google.calendar.enums.SessionKey;
import com.mainul35.google.calendar.exception.CalendarAccessDeniedException;
import com.mainul35.google.calendar.service.GoogleDriveService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
public class DriveFilesController {

    private final GoogleDriveService googleDriveService;

    public DriveFilesController(GoogleDriveService googleCalendarService, ClientRegistrationRepository clientRegistrationRepository, RestTemplate restTemplate) {
        this.googleDriveService = googleCalendarService;
    }

    @RequestMapping("/files")
    public String getCalendarEvents(HttpSession session, Model model) {
        String accessToken = session.getAttribute(SessionKey.GOOGLE_OAUTH_TOKEN.toString()) == null
                ? "" : session.getAttribute(SessionKey.GOOGLE_OAUTH_TOKEN.toString()).toString();

        if (accessToken == null || accessToken.isBlank()) {
            throw new CalendarAccessDeniedException("Invalid token");
        }
        DriveFiles driveFiles = googleDriveService.getDriveFiles(accessToken);
        System.out.println(driveFiles.toString());
        model.addAttribute("files", driveFiles);
        return "files";
    }
}
