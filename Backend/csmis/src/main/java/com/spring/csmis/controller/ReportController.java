package com.spring.csmis.controller;

import com.spring.csmis.service.report.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/menuweek/pdf")
    public ResponseEntity<byte[]> generateMenuWeekReport(@RequestParam Long menuweek_id) {
        try {
            byte[] pdfReport = reportService.generateMenuWeekReport(menuweek_id);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "MenuWeeklyReport.pdf");
            headers.setContentLength(pdfReport.length);  // Set the content length if required

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfReport);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/feedbackforamenu/pdf")
    public ResponseEntity<byte[]> generateFeedBackForAMenuReport(@RequestParam Long menu_id) {
        try {
            byte[] pdfReport = reportService.generateFeedBackForAMenuReport(menu_id);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "MenuWeeklyReport.pdf");
            headers.setContentLength(pdfReport.length);  // Set the content length if required

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfReport);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/feedbackformenuweekly/pdf")
    public ResponseEntity<byte[]> generateFeedBackAndSuggestionForMenuWeeklyReport(@RequestParam Long menuweek_id) {
        try {
            byte[] pdfReport = reportService.generateFeedBackAndSuggestionForMenuWeeklyReport(menuweek_id);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "FeedBackForMenuWeeklyReport.pdf");
            headers.setContentLength(pdfReport.length);  // Set the content length if required

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfReport);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/goodfeedback&suggestionformenuweekly/pdf")
    public ResponseEntity<byte[]> generateGoodFeedBackAndSuggestionForMenuWeeklyByMenuweekIdReport(@RequestParam Long menuweek_id) {
        try {
            byte[] pdfReport = reportService.generateGoodFeedBackAndSuggestionForMenuWeeklyByMenuweekIdReport(menuweek_id);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "GoodFeedBack&SuggestionForMenuWeeklyReport.pdf");
            headers.setContentLength(pdfReport.length);  // Set the content length if required

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfReport);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @GetMapping("/badfeedback&suggestionformenuweekly/pdf")
    public ResponseEntity<byte[]> generateBadFeedBackAndSuggestionForMenuWeeklyByMenuweekIdReport(@RequestParam Long menuweek_id) {
        try {
            byte[] pdfReport = reportService.generateBadFeedBackAndSuggestionForMenuWeeklyByMenuweekIdReport(menuweek_id);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "BadFeedBack&SuggestionForMenuWeeklyReport.pdf");
            headers.setContentLength(pdfReport.length);  // Set the content length if required

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfReport);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
