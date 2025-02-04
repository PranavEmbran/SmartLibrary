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

public class GenresPie implements ExampleChart<PieChart> {
    
    Connect cObj = new Connect();
    Connection con = cObj.dbConnect();
    PreparedStatement pst;
    ResultSet rs1, rs2;

  public static void main(String[] args) {
   ExampleChart<PieChart> exampleChart = new GenresPie();
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
        pst = con.prepareStatement("Select genreid, count(genreid) from books group by genreid")  ;
        rs1 = pst.executeQuery();
        pst = con.prepareStatement("select genrename from genres where genreid IN "
                + "(Select genreid from books group by genreid)")  ;
        rs2 = pst.executeQuery();        
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
                    
                    rs2.next();
                    String name = rs2.getString("genrename");
                    chart.addSeries(name, num);
                } catch (SQLException ex) {
                    Logger.getLogger(GenresPie.class.getName()).log(Level.SEVERE, null, ex);
                }
            }   
        } 
        catch (SQLException ex) {
            Logger.getLogger(GenresPie.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    // Series
/*  chart.addSeries("Pennies", 1);
    chart.addSeries("Nickels", 1);    
    chart.addSeries("Dimes", 100);
    chart.addSeries("Quarters", 100);
*/
    return chart;
  }

  @Override
  public String getExampleChartName() {

    return getClass().getSimpleName() + " - Pie Chart";
  }
}


/**
 * Pie Chart with Slices
 *
 * <p>Demonstrates the following:
 *
 * <ul>
 *   <li>Pie Chart
 *   <li>ChartBuilderPie
 *   <li>Setting Non-circular to match container aspect ratio
 *   <li>Legend outside south, with Horizontal Legend Layout
 */