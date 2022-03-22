import java.io.*;
import java.util.Properties;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/questionario/submit")
public class QuestionarioSubmit extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String food = request.getParameter("food");
        File results = new File(getServletContext().getRealPath("/WEB-INF/results.txt"));
        Properties props = new Properties();
        if (results.exists() && !results.isDirectory()) {
            FileInputStream fis = new FileInputStream(results);
            props.load(fis);
            fis.close();
            String property = props.getProperty(food);
            if (property != null) {
                int count = Integer.parseInt(property) + 1;
                props.setProperty(food, String.valueOf(count));
            } else {
                props.setProperty(food, "1");
            }
        } else {
            results.createNewFile();
            props.setProperty(food, "1");
        }
        FileOutputStream fos = new FileOutputStream(results);
        props.store(fos, null);
        fos.close();
        response.sendRedirect("results");
    }
}