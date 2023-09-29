package com.example.ozguryazilimproje.controller;

import com.example.ozguryazilimproje.enums.Language;
import com.example.ozguryazilimproje.exception.enums.FriendlyMessageCodes;
import com.example.ozguryazilimproje.exception.utils.FriendlyMessageUtils;
import com.example.ozguryazilimproje.repository.entity.Image;
import com.example.ozguryazilimproje.repository.entity.Laborant;
import com.example.ozguryazilimproje.repository.entity.Patient;
import com.example.ozguryazilimproje.repository.entity.Report;
import com.example.ozguryazilimproje.request.LaborantUpdateRequest;
import com.example.ozguryazilimproje.request.ReportCreateRequest;
import com.example.ozguryazilimproje.request.ReportUpdateRequest;
import com.example.ozguryazilimproje.response.*;
import com.example.ozguryazilimproje.service.ImageService;
import com.example.ozguryazilimproje.service.LaborantService;
import com.example.ozguryazilimproje.service.PatientService;
import com.example.ozguryazilimproje.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/report")
@RequiredArgsConstructor
@CrossOrigin
public class ReportController {
    private  final  ReportService reportService;
    private  final LaborantService laborantService;
    private  final PatientService patientService;
    private  final ImageService imageService;



    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{language}/create")
    public InternalApiResponse<ReportResponse> createReport(@PathVariable("language") Language language,
                                                            @RequestBody  ReportCreateRequest reportCreateRequest){

        log.debug("[{}][CreateLaborant] -> request{}",this.getClass().getSimpleName(),reportCreateRequest);
        Laborant laborant= laborantService.getLaborant(language,reportCreateRequest.getLaborantId().getLaborantId());
        Patient patient =patientService.getOnePatient(language,reportCreateRequest.getPatientId().getPatientId());
        Report report = reportService.createReport(language,reportCreateRequest);

        LaborantReportResponse laborantReportResponse=convertLaborantReportResponse(laborant);
        PatientReportResponse patientReportResponse=convertPatientReportResponse(patient);
        ImageResponse imageResponse=convertImageResponse(imageService.getFile(language,report.getImage().getImageId()).get());

        ReportResponse reportResponse= ReportResponse.builder()
                .reportId(report.getReportId())
                .fileNo(reportCreateRequest.getFileNo())
                .laborant(laborantReportResponse)
                .patient(patientReportResponse)
                .image(imageResponse)
                .diagnosis(report.getDiagnosis())
                .diagnosisDetail(report.getDiagnosisDetail())
                .CreatedDate(report.getCreateDate().getTime())
                .build();


        return  InternalApiResponse.<ReportResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.PATIENT_SUCCESSFULLY_CREATED))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(reportResponse)
                .build();

    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{language}/getOneReport/{reportId}")
    public  InternalApiResponse<ReportResponse> getOneReport(@PathVariable("language") Language language,
                                                             @PathVariable Long reportId){

        Report report=reportService.getOneReport(language,reportId);
        LaborantReportResponse laborantReportResponse=convertLaborantReportResponse(report.getLaborant());
        PatientReportResponse patientReportResponse=convertPatientReportResponse(report.getPatient());
        ImageResponse imageResponse=convertImageResponse(imageService.getFile(language,report.getImage().getImageId()).get());



        ReportResponse reportResponse= ReportResponse.builder()
                .reportId(report.getReportId())
                .fileNo(report.getFileNo())
                .laborant(laborantReportResponse)
                .patient(patientReportResponse)
                .image(imageResponse)
                .diagnosis(report.getDiagnosis())
                .diagnosisDetail(report.getDiagnosisDetail())
                .CreatedDate(report.getCreateDate().getTime())
                .build();


        return  InternalApiResponse.<ReportResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.SUCCESS))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(reportResponse)
                .build();


    }
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{language}/update/{reportId}")
    public InternalApiResponse<ReportResponse> updateReport(@PathVariable("language") Language language,
                                                                @PathVariable Long reportId,
                                                                @RequestBody ReportUpdateRequest reportUpdateRequest){
        log.debug("[{}][updateReport] -> request{}",this.getClass().getSimpleName(),reportId);
        Report report= reportService.updateReport(language,reportId,reportUpdateRequest);
        LaborantReportResponse laborantReportResponse=convertLaborantReportResponse(report.getLaborant());
        PatientReportResponse patientReportResponse=convertPatientReportResponse(report.getPatient());
        ImageResponse imageResponse=convertImageResponse(imageService.getFile(language,report.getImage().getImageId()).get());

        ReportResponse reportResponse= ReportResponse.builder()
                .reportId(report.getReportId())
                .fileNo(report.getFileNo())
                .laborant(laborantReportResponse)
                .patient(patientReportResponse)
                .image(imageResponse)
                .diagnosis(report.getDiagnosis())
                .diagnosisDetail(report.getDiagnosisDetail())
                .CreatedDate(report.getCreateDate().getTime())
                .build();
        log.debug("[{}][updateReport] -> request{}",this.getClass().getSimpleName(),reportResponse);

        return InternalApiResponse.<ReportResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.REPORT_SUCCESSFULLY_UPDATED))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(reportResponse)
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{language}/delete/{reportId}")
    public InternalApiResponse<ReportResponse> deleteReport(@PathVariable("language") Language language,
                                                            @PathVariable Long reportId
                                                            ){
        log.debug("[{}][deleteReport] -> request{}",this.getClass().getSimpleName(),reportId);
        if (reportService.deleteReport(language,reportId)){
            return InternalApiResponse.<ReportResponse>builder()
                    .friendlyMessage(FriendlyMessage.builder()
                            .title(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.SUCCESS))
                            .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.REPORT_SUCCESSFULLY_DELETED))
                            .build())
                    .httpStatus(HttpStatus.OK)
                    .hasError(false)
                    .build();
        }else {
            return InternalApiResponse.<ReportResponse>builder()
                    .friendlyMessage(FriendlyMessage.builder()
                            .title(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.ERROR))
                            .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.REPORT_NOT_FOUND_EXCEPTION))
                            .build())
                    .httpStatus(HttpStatus.OK)
                    .hasError(true)
                    .build();
        }


    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{language}/getAll")
    public InternalApiResponse<List<ReportResponse>> getAllReport(@PathVariable(name = "language")Language language){
        List<Report> reports= reportService.getAllReport(language);
        List<ReportResponse> reportResponses= reports.stream().map(args->ReportResponse.builder()
                .reportId(args.getReportId())
                .fileNo(args.getFileNo())
                .laborant(LaborantReportResponse.builder().laborantId(args.getLaborant().getLaborantId()).laborantName(args.getLaborant().getLaborantName())
                        .laborantSurName(args.getLaborant().getLaborantSurName()).hospitalIdNo(args.getLaborant().getHospitalIdNo()).build())
                .patient(PatientReportResponse.builder()
                        .patientId(args.getPatient().getPatientId()).name(args.getPatient().getName()).surName(args.getPatient().getSurName())
                        .TC(args.getPatient().getTC()).build())
                .image(ImageResponse.builder().imageId(args.getImage().getImageId()).name(args.getImage().getName())
                        .type(args.getImage().getType()).file(args.getImage().getFile()).build())
                .diagnosisDetail(args.getDiagnosisDetail())
                .diagnosis(args.getDiagnosis())
                .CreatedDate(args.getCreateDate().getTime())
                .build())
                .collect(Collectors.toList());

        return InternalApiResponse.<List<ReportResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(reportResponses)
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{language}/sortDate")
    public InternalApiResponse<List<ReportResponse>> sortByDate(@PathVariable(name = "language")Language language) {
        List<Report> reports = reportService.sortByDate();
        List<ReportResponse> reportResponses = reports.stream().map(args -> ReportResponse.builder()
                        .reportId(args.getReportId())
                        .fileNo(args.getFileNo())
                        .laborant(LaborantReportResponse.builder().laborantId(args.getLaborant().getLaborantId()).laborantName(args.getLaborant().getLaborantName())
                                .laborantSurName(args.getLaborant().getLaborantSurName()).hospitalIdNo(args.getLaborant().getHospitalIdNo()).build())
                        .patient(PatientReportResponse.builder()
                                .patientId(args.getPatient().getPatientId()).name(args.getPatient().getName()).surName(args.getPatient().getSurName())
                                .TC(args.getPatient().getTC()).build())
                        .image(ImageResponse.builder().imageId(args.getImage().getImageId()).name(args.getImage().getName())
                                .type(args.getImage().getType()).file(args.getImage().getFile()).build())
                        .diagnosisDetail(args.getDiagnosisDetail())
                        .diagnosis(args.getDiagnosis())
                        .CreatedDate(args.getCreateDate().getTime())
                        .build())
                .collect(Collectors.toList());

        return InternalApiResponse.<List<ReportResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(reportResponses)
                .build();

    }



    private static LaborantReportResponse convertLaborantReportResponse(Laborant laborant) {
        return LaborantReportResponse.builder()
                .laborantId(laborant.getLaborantId())
                .laborantName(laborant.getLaborantName())
                .laborantSurName(laborant.getLaborantSurName())
                .hospitalIdNo(laborant.getHospitalIdNo())
                .build();
    }
    private static PatientReportResponse convertPatientReportResponse(Patient patient) {
        return PatientReportResponse.builder()
                .patientId(patient.getPatientId())
                .TC(patient.getTC())
                .name(patient.getName())
                .surName(patient.getSurName())
                .build();
    }

    private static ImageResponse convertImageResponse(Image image) {
        return ImageResponse.builder()
                .imageId(image.getImageId())
                .name(image.getName())
                .type(image.getType())
                .file(image.getFile())
                .build();

    }


}
