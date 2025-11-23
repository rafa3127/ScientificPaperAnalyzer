/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.graph.scientificpaperanalyzer;

import repositories.Repository;
import services.SummaryService;
import services.AnalysisService;
import services.SearchService;
import services.KeywordService;
import ui.MainFrame;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;
import java.io.IOException;


/**
 * Main class for Scientific Paper Analyzer.
 * 
 * @author rafaelc3127
 */
public class ScientificPaperAnalyzer {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Repository repo = new Repository("data");
                int loaded = repo.loadAll();
                System.out.println("Loaded " + loaded + " summaries");
                
                SummaryService sumService = new SummaryService(repo);
                AnalysisService anaService = new AnalysisService(repo);
                SearchService seaService = new SearchService(repo);
                KeywordService keyService = new KeywordService(repo);
                
                MainFrame frame = new MainFrame(sumService, anaService, seaService, keyService);
                frame.setVisible(true);
                
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al cargar datos: " + e.getMessage());
            }
        });
    }
}
