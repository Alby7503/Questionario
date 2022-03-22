import java.io.*;
import java.util.Properties;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/questionario/results")
public class QuestionarioResults extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Properties props = new Properties();
        File results = new File(getServletContext().getRealPath("/WEB-INF/results.txt"));
        if (results.exists() && !results.isDirectory()) {
            FileInputStream fis = new FileInputStream(results);
            props.load(fis);
            out.println("<h1>Risultati</h1>");
            String[] choices = { "chocolate", "apple", "simple", "vanilla" };
            int[] counts = new int[choices.length];
            int tot = 0;
            for (int i = 0; i < choices.length; i++) {
                String choice = choices[i];
                String value = props.getProperty(choice);
                if (value != null) {
                    counts[i] = Integer.parseInt(value);
                    tot += counts[i];
                } else {
                    counts[i] = 0;
                }
            }
            // Calculate percentages
            for (int i = 0; i < counts.length; i++) {
                counts[i] = (int) Math.round(((double) counts[i] / (double) tot) * 100);
            }
            out.println("<ul>");
            for (int i = 0; i < counts.length; i++) {
                String food = choices[i];
                out.println("<li>" + food + ": " + props.getProperty(food) + " (" + counts[i] + "%)</li>");
            }
            out.println("</ul>");
            fis.close();
        } else {
            out.println("<h1>Ancora nessun risultato... <a href='/Scuola/questionario'>Vota!</a></h1>");
        }
        out.close();
    }
}