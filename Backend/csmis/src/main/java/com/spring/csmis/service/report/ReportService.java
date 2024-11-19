package com.spring.csmis.service.report;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private DataSource dataSource;


    @Autowired
    private ResourceLoader resourceLoader;
    public byte[] generateMenuWeekReport(Long menuweekId) throws Exception {


        try (InputStream reportStream = getClass().getResourceAsStream("/reports/menuweek.jrxml");
             Connection connection = dataSource.getConnection()) {

            if (reportStream == null) {
                throw new RuntimeException("Report template not found: /reports/menuweek.jrxml");
            }

            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("menuweek_id", menuweekId);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (JRException e) {
            e.printStackTrace();
            throw new RuntimeException("Error generating report: " + e.getMessage(), e);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unexpected error: " + e.getMessage(), e);
        }
    }


    public byte[] generateFeedBackForAMenuReport(Long menuId) throws Exception {


        try (InputStream reportStream = getClass().getResourceAsStream("/reports/feedbackforamenu.jrxml");
             InputStream subReport = getClass().getResourceAsStream("/reports/menubyday.jrxml");
//
             Connection connection = dataSource.getConnection()) {

            if (reportStream == null) {
                throw new RuntimeException("Report template not found: /reports/feedbackforamenu.jrxml");
            }

            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            JasperReport subreport = JasperCompileManager.compileReport(subReport);
//


            Map<String, Object> parameters = new HashMap<>();
            parameters.put("menu_id", menuId);
            parameters.put("SUBREPORT", subreport);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (JRException e) {
            e.printStackTrace();
            throw new RuntimeException("Error generating report: " + e.getMessage(), e);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unexpected error: " + e.getMessage(), e);
        }
    }



//    public byte[] generateFeedBackForMenuWeeklyReport(Long menuweekId) throws Exception {
//        try (InputStream mainReportStream = getClass().getResourceAsStream("/reports/goodfeedbackreportforweekly.jrxml");
//             InputStream subReportStream1 = getClass().getResourceAsStream("/reports/badfeedbackreportforweekly.jrxml");
//             InputStream subReportStream2 = getClass().getResourceAsStream("/reports/weeklychartforgoodfeedback.jrxml");
//             InputStream subReportStream3 = getClass().getResourceAsStream("/reports/weeklychartforbadfeedback.jrxml");
//             Connection connection = dataSource.getConnection()) {
//
//            if (mainReportStream == null || subReportStream1 == null || subReportStream2 == null || subReportStream3 == null) {
//                throw new RuntimeException("One or more report templates not found.");
//            }
//
//            // Compile main report and subreports from .jrxml files
//            JasperReport mainReport = JasperCompileManager.compileReport(mainReportStream);
//            JasperReport subReport1 = JasperCompileManager.compileReport(subReportStream1);
//            JasperReport subReport2 = JasperCompileManager.compileReport(subReportStream2);
//            JasperReport subReport3 = JasperCompileManager.compileReport(subReportStream3);
//
//            // Prepare parameters for main report
//            Map<String, Object> parameters = new HashMap<>();
//            parameters.put("menuweek_id", menuweekId);
//            parameters.put("SUBREPORT1", subReport1);
//            parameters.put("SUBREPORT2", subReport2);
//            parameters.put("SUBREPORT3", subReport3); // If you have a third subreport
//
//            // Fill the main report
//            JasperPrint jasperPrint = JasperFillManager.fillReport(mainReport, parameters, connection);
//
//            // Export to PDF
//            return JasperExportManager.exportReportToPdf(jasperPrint);
//        } catch (JRException | SQLException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Error generating report: " + e.getMessage(), e);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("Unexpected error: " + e.getMessage(), e);
//        }
//    }


    public byte[] generateFeedBackAndSuggestionForMenuWeeklyReport(Long menuweekId) throws Exception {
        try (InputStream mainReportStream = getClass().getResourceAsStream("/reports/goodfeedbackreportforweekly.jrxml");
             InputStream subReportStream1 = getClass().getResourceAsStream("/reports/badfeedbackreportforweekly.jrxml");
             InputStream subReportStream2 = getClass().getResourceAsStream("/reports/weeklychartforgoodfeedback.jrxml");
             InputStream subReportStream3 = getClass().getResourceAsStream("/reports/weeklychartforbadfeedback.jrxml");
             InputStream subReportStream4 = getClass().getResourceAsStream("/reports/suggestionforgoodfeedbackbymenu.jrxml");
             InputStream subReportStream5 = getClass().getResourceAsStream("/reports/suggestionforbadfeedbackbymenu.jrxml");
             InputStream subReportStream6 = getClass().getResourceAsStream("/reports/menubyday.jrxml");

             Connection connection = dataSource.getConnection()) {

            if (mainReportStream == null || subReportStream1 == null || subReportStream2 == null || subReportStream3 == null) {
                throw new RuntimeException("One or more report templates not found.");
            }

            // Compile main report and subreports from .jrxml files
            JasperReport mainReport = JasperCompileManager.compileReport(mainReportStream);
            JasperReport subReport1 = JasperCompileManager.compileReport(subReportStream1);
            JasperReport subReport2 = JasperCompileManager.compileReport(subReportStream2);
            JasperReport subReport3 = JasperCompileManager.compileReport(subReportStream3);
            JasperReport subReport4 = JasperCompileManager.compileReport(subReportStream4);
            JasperReport subReport5 = JasperCompileManager.compileReport(subReportStream5);
            JasperReport subReport6 = JasperCompileManager.compileReport(subReportStream6);


            // Prepare parameters for main report
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("menuweek_id", menuweekId);
            parameters.put("SUBREPORT1", subReport1);
            parameters.put("SUBREPORT2", subReport2);
            parameters.put("SUBREPORT3", subReport3);
            parameters.put("SUBREPORT4", subReport4);
            parameters.put("SUBREPORT5", subReport5);
            parameters.put("SUBREPORT6", subReport6);

            // Fill the main report
            JasperPrint jasperPrint = JasperFillManager.fillReport(mainReport, parameters, connection);

            // Export to PDF
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error generating report: " + e.getMessage(), e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unexpected error: " + e.getMessage(), e);
        }
    }

    public byte[] generateGoodFeedBackAndSuggestionForMenuWeeklyByMenuweekIdReport(Long menuweekId) throws Exception {
        try (InputStream mainReportStream = getClass().getResourceAsStream("/reports/goodfeedbackreportforweekly.jrxml");
             InputStream subReportStream1 = getClass().getResourceAsStream("/reports/weeklychartforgoodfeedback.jrxml");
             InputStream subReportStream2 = getClass().getResourceAsStream("/reports/suggestionforgoodfeedbackbymenu.jrxml");
             InputStream subReportStream3 = getClass().getResourceAsStream("/reports/menubyday.jrxml");

             Connection connection = dataSource.getConnection()) {

            if (mainReportStream == null || subReportStream1 == null || subReportStream2 == null || subReportStream3 == null) {
                throw new RuntimeException("One or more report templates not found.");
            }

            // Compile main report and subreports from .jrxml files
            JasperReport mainReport = JasperCompileManager.compileReport(mainReportStream);
            JasperReport subReport1 = JasperCompileManager.compileReport(subReportStream1);
            JasperReport subReport2 = JasperCompileManager.compileReport(subReportStream2);
            JasperReport subReport3 = JasperCompileManager.compileReport(subReportStream3);


            // Prepare parameters for main report
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("menuweek_id", menuweekId);
            parameters.put("SUBREPORT1", subReport1);
            parameters.put("SUBREPORT2", subReport2);
            parameters.put("SUBREPORT3", subReport3);


            // Fill the main report
            JasperPrint jasperPrint = JasperFillManager.fillReport(mainReport, parameters, connection);

            // Export to PDF
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error generating report: " + e.getMessage(), e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unexpected error: " + e.getMessage(), e);
        }
    }


    public byte[] generateBadFeedBackAndSuggestionForMenuWeeklyByMenuweekIdReport(Long menuweekId) throws Exception {
        try (InputStream mainReportStream = getClass().getResourceAsStream("/reports/badfeedbackreportforweekly.jrxml");
             InputStream subReportStream1 = getClass().getResourceAsStream("/reports/weeklychartforbadfeedback.jrxml");
             InputStream subReportStream2 = getClass().getResourceAsStream("/reports/suggestionforbadfeedbackbymenu.jrxml");
             InputStream subReportStream3 = getClass().getResourceAsStream("/reports/menubyday.jrxml");

             Connection connection = dataSource.getConnection()) {

            if (mainReportStream == null || subReportStream1 == null || subReportStream2 == null || subReportStream3 == null) {
                throw new RuntimeException("One or more report templates not found.");
            }

            // Compile main report and subreports from .jrxml files
            JasperReport mainReport = JasperCompileManager.compileReport(mainReportStream);
            JasperReport subReport1 = JasperCompileManager.compileReport(subReportStream1);
            JasperReport subReport2 = JasperCompileManager.compileReport(subReportStream2);
            JasperReport subReport3 = JasperCompileManager.compileReport(subReportStream3);


            // Prepare parameters for main report
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("menuweek_id", menuweekId);
            parameters.put("SUBREPORT1", subReport1);
            parameters.put("SUBREPORT2", subReport2);
            parameters.put("SUBREPORT3", subReport3);


            // Fill the main report
            JasperPrint jasperPrint = JasperFillManager.fillReport(mainReport, parameters, connection);

            // Export to PDF
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error generating report: " + e.getMessage(), e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unexpected error: " + e.getMessage(), e);
        }
    }

}