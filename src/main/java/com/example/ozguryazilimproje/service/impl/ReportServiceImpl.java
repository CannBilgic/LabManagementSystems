package com.example.ozguryazilimproje.service.impl;

import com.example.ozguryazilimproje.enums.Language;
import com.example.ozguryazilimproje.exception.enums.FriendlyMessageCodes;
import com.example.ozguryazilimproje.exception.exceptions.LaborantNotFoundException;
import com.example.ozguryazilimproje.exception.exceptions.PatientNotFoundException;
import com.example.ozguryazilimproje.exception.exceptions.ReportNotFoundException;
import com.example.ozguryazilimproje.repository.entity.Image;
import com.example.ozguryazilimproje.repository.entity.Laborant;
import com.example.ozguryazilimproje.repository.entity.Patient;
import com.example.ozguryazilimproje.repository.entity.Report;
import com.example.ozguryazilimproje.repository.repository.ReportRepository;
import com.example.ozguryazilimproje.request.ReportCreateRequest;
import com.example.ozguryazilimproje.request.ReportUpdateRequest;
import com.example.ozguryazilimproje.service.ImageService;
import com.example.ozguryazilimproje.service.LaborantService;
import com.example.ozguryazilimproje.service.PatientService;
import com.example.ozguryazilimproje.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private  final ReportRepository reportRepository;
    private  final ImageService imageService;
    private  final LaborantService laborantService;
    private  final PatientService patientService;
    @Override
    public Report createReport(Language language, ReportCreateRequest reportCreateRequest) {
        try {
            log.debug("[{}][createReport] -> request{}",this.getClass().getSimpleName(),reportCreateRequest);
            Report report= Report.builder()
                    .fileNo(reportCreateRequest.getFileNo())
                    .patient(patientService.getOnePatient(language,reportCreateRequest.getPatientId().getPatientId()))
                    .laborant(laborantService.getLaborant(language,reportCreateRequest.getLaborantId().getLaborantId()))
                    .image(imageService.getFile(language,reportCreateRequest.getImage().getImageId()).get())
                    .diagnosis(reportCreateRequest.getDiagnosis())
                    .diagnosisDetail(reportCreateRequest.getDiagnosisDetail())
                    .build();
            Report reportResponse = reportRepository.save(report);
            System.out.println(imageService.getFile(language,reportCreateRequest.getImage().getImageId()).get());
            log.debug("[{}][createReport] -> request{}",this.getClass().getSimpleName(),reportResponse);
            return reportResponse;
        }catch (Exception exception){
            return null;
        }
    }

    @Override
    public Report updateReport(Language language, Long reportId, ReportUpdateRequest reportUpdateRequest) {
        log.debug("[{}][updateReport] -> request{}",this.getClass().getSimpleName(),reportId);
        Report report = reportRepository.findById(reportId).get();
        Patient patient =patientService.getOnePatient(language,reportUpdateRequest.getPatientId().getPatientId());
        Image image= imageService.getFile(language,reportUpdateRequest.getImageId().getImageId()).get();
        report.setFileNo(reportUpdateRequest.getFileNo());
        report.setPatient(patient);
        report.setImage(image);
        report.setDiagnosis(reportUpdateRequest.getDiagnosis());
        report.setDiagnosisDetail(reportUpdateRequest.getDiagnosisDetail());
        Report reportResponse = reportRepository.save(report);
        log.debug("[{}][updateReport] -> request{}",this.getClass().getSimpleName(),reportResponse);
        return reportResponse;
    }

    @Override
    public Boolean deleteReport(Language language, Long reportId) {
        log.debug("[{}][deleteReport] -> request{}",this.getClass().getSimpleName(),reportId);
        if (reportRepository.findById(reportId).isEmpty()){
            throw  new ReportNotFoundException(language,FriendlyMessageCodes.REPORT_NOT_FOUND_EXCEPTION,"Report not found");
        }else {
            reportRepository.deleteById(reportId);
            return true;
        }
    }

    @Override
    public Report getOneReport(Language language, Long reportId) {
        try {
            Report report = reportRepository.findById(reportId).get();
            return report;
        }catch (Exception exception){
            throw  new PatientNotFoundException(language, FriendlyMessageCodes.REPORT_NOT_FOUND_EXCEPTION,"Report Not Found Exception");
        }
    }

    @Override
    public List<Report> getAllReport(Language language) {
        log.debug("[{}][getAllReport] -> request{}",this.getClass().getSimpleName());
        List<Report> reports= reportRepository.findAll();
        if (reports.isEmpty()){
            throw  new ReportNotFoundException(language,FriendlyMessageCodes.REPORT_NOT_FOUND_EXCEPTION,"Report Not Found");
        }
        log.debug("[{}][getAllReport] -> request{}",this.getClass().getSimpleName(),reports);
        return reports;
    }

    @Override
    public List<Report> sortByDate() {
        List<Report> reports=reportRepository.sortByDate();
        log.debug("[{}][getAllReport] -> request{}",this.getClass().getSimpleName(),reports);
        return reports;
    }
}
