import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@WebServlet(name = "MainServlet", value = "/MainServlet")
@MultipartConfig
public class MainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        Iterator<String> iterator = request.getParameterNames().asIterator();
        while (iterator.hasNext()) {
            String name = iterator.next();
            log(name + ": " + request.getParameter(name));
        }
        double a = Double.parseDouble(request.getParameter("a"));
        double b = Double.parseDouble(request.getParameter("b"));
        double c = Double.parseDouble(request.getParameter("c"));
        List<String> result = solveEquation(a, b, c);
        request.setAttribute("a", a);
        request.setAttribute("b", b);
        request.setAttribute("c", c);
        if (request.getParameter("d")!=null)
            request.setAttribute("d", "Дискриминант: " + String.valueOf(b * b - 4 * a * c));
        if (result.get(0).equals("Нет корней"))
            request.setAttribute("result3", result.get(0));
        else
            if (result.get(0).equals(result.get(1)))
            request.setAttribute("result1", "x=" + result.get(0));
            else {
                request.setAttribute("result1", "x<sub>1</sub>=" + result.get(0));
                request.setAttribute("result2", "x<sub>2</sub>=" + result.get(1));
            }
        request.getRequestDispatcher("newindex.jsp").forward(request, response);
    }

    public List<String> solveEquation(double a, double b, double c) {
        List<String> result = new ArrayList<>();
        double x1, x2;
        double d = b * b - 4 * a * c;
        if (d>=0) {
            x1 = (-b + Math.sqrt(d)) / (2 * a);
            x2 = (-b - Math.sqrt(d)) / (2 * a);
            result.add(String.valueOf(x1));
            result.add(String.valueOf(x2));
        }
        else result.add("Нет корней");
        return result;
    }
}