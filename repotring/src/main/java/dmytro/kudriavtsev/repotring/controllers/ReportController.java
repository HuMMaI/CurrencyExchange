package dmytro.kudriavtsev.repotring.controllers;

import dmytro.kudriavtsev.repotring.dtos.ExchangeDTO;
import dmytro.kudriavtsev.repotring.services.ElasticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    @Autowired
    private ElasticService elasticService;

    @GetMapping("/all")
    public ResponseEntity<List<ExchangeDTO>> getAll() throws IOException {
        return new ResponseEntity<>(elasticService.getAll(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ExchangeDTO>> getExchangesReport(@RequestParam(name = "success") boolean success) throws IOException {
        return new ResponseEntity<>(elasticService.getExchangesReport(success), HttpStatus.OK);
    }
}
