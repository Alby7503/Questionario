import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/questionario")
public class Questionario extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String html = getServletContext().getRealPath("/") + "WEB-INF/Questionario.html";
        BufferedReader in = new BufferedReader(new FileReader(html));
        String line;
        while ((line = in.readLine()) != null) {
            out.println(line);
        }
        in.close();
        out.close();
    }
}