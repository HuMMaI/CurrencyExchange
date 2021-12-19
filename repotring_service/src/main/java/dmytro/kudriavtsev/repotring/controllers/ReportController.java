package dmytro.kudriavtsev.repotring.controllers;

import dmytro.kudriavtsev.repotring.dtos.CountReportDTO;
import dmytro.kudriavtsev.repotring.dtos.ExchangeDTO;
import dmytro.kudriavtsev.repotring.dtos.ExchangeEventReportDTO;
import dmytro.kudriavtsev.repotring.services.ElasticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/reports")
public class ReportController {
    @Autowired
    private ElasticService elasticService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ExchangeDTO> getAll() throws IOException {
        return elasticService.getAll();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ExchangeDTO> getExchangesReport(@RequestParam(name = "success") boolean success) throws IOException {
        return elasticService.getExchangesReport(success);
    }

    @GetMapping("/count")
    @ResponseStatus(HttpStatus.OK)
    public CountReportDTO getCountReport() throws IOException {
        return elasticService.getCountReport();
    }

    @GetMapping("/exchange-event")
    @ResponseStatus(HttpStatus.OK)
    public ExchangeEventReportDTO getExchangeEventReport() throws IOException {
        return elasticService.getReportByExchangeEvent();
    }
}
