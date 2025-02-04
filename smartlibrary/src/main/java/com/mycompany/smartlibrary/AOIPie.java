/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartlibrary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.demo.charts.ExampleChart;
import org.knowm.xchart.style.Styler;


public class AOIPie implements ExampleChart<PieChart> {
    
    Connect cObj = new Connect();
    Connection con = cObj.dbConnect();
    PreparedStatement pst;
    ResultSet rs1, rs2;

  public static void main(String[] args) {
   ExampleChart<PieChart> exampleChart = new AOIPie();
   PieChart chart = exampleChart.getChart();
   JFrame chartjf = new SwingWrapper<>(chart).displayChart();

  }

  @Override
  public PieChart getChart() {

    // Create Chart
    PieChart chart = new PieChartBuilder().width(600).height(600).title(getClass().getSimpleName()).build();

    // Customize Chart
    chart.getStyler().setCircular(false);
    chart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideS);
    chart.getStyler().setLegendLayout(Styler.LegendLayout.Horizontal);

   try {

        pst = con.prepareStatement("select genreid, count(genreid) from books where bookid IN (Select bookid from bookissue) group by genreid");
        rs1 = pst.executeQuery();
   }
    catch (SQLException ex)
    {
        Logger.getLogger(Genres.class.getName()).log(Level.SEVERE, null, ex);
    }
       
        int num, i=0 ;
        try {
            while(rs1.next()){
                try {
                    num = rs1.getInt("count(genreid)");   
                    
                 //   rs2.next();
                    String name = rs1.getString("genreid");
                    chart.addSeries(name, num);
                } catch (SQLException ex) {
                    Logger.getLogger(AOIPie.class.getName()).log(Level.SEVERE, null, ex);
                }
            }   
        } 
        catch (SQLException ex) {
            Logger.getLogger(AOIPie.class.getName()).log(Level.SEVERE, null, ex);
        }

    return chart;
  }

  @Override
  public String getExampleChartName() {

    return getClass().getSimpleName() + " - Pie Chart";
  }
}
