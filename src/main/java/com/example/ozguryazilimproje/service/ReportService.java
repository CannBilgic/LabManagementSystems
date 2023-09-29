package com.example.ozguryazilimproje.service;

import com.example.ozguryazilimproje.enums.Language;
import com.example.ozguryazilimproje.repository.entity.Report;
import com.example.ozguryazilimproje.request.ReportCreateRequest;
import com.example.ozguryazilimproje.request.ReportUpdateRequest;

import java.util.Date;
import java.util.List;

public interface ReportService {
    Report createReport(Language language, ReportCreateRequest reportCreateRequest);
    Report updateReport(Language language, Long reportId, ReportUpdateRequest reportUpdateRequest);
    Boolean deleteReport(Language language,Long reportId);

    Report getOneReport(Language language,Long reportId);

    List<Report> getAllReport(Language language);
    List<Report>  sortByDate();

}
