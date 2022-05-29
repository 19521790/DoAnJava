package com.server.service;


import com.server.entity.Chart;
import com.server.repository.ChartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChartService {
    @Autowired
    private ChartRepository chartRepository;

    public Chart addChart(Chart chart) {
        return chartRepository.save(chart);
    }

    public Chart findChartById(String id) {
        return chartRepository.findById(id).orElse(null);
    }

    public List<Chart> findAllCharts() {
        return chartRepository.findAll();
    }

    public String deleteChart(String id) {
        chartRepository.deleteById(id);
        return ("Chart has been deleted: " + id);
    }

    public Chart updateChart(Chart chart) {
        return chartRepository.save(chart);
    }
}
