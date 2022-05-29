package com.server.controller;

import com.server.entity.Chart;
import com.server.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chart")
public class ChartController {
    @Autowired
    private ChartService chartService;

    @PostMapping("/addChart")
    public Chart addChart(@RequestBody Chart playlist) {
        return chartService.addChart(playlist);
    }

    @GetMapping("/findChartById/{id}")
    public Chart findChartById(@PathVariable String id) {
        return chartService.findChartById(id);
    }

    @GetMapping("/findAllCharts")
    public List<Chart> findCharts() {
        return chartService.findAllCharts();
    }

    @DeleteMapping("/deleteChart/{id}")
    public String deleteChart(@PathVariable String id) {
        return chartService.deleteChart(id);
    }

    @PutMapping("/updateChart")
    public Chart updateChart(@RequestBody Chart playlist) {
        return chartService.updateChart(playlist);
    }
}
