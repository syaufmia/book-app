package sum.ike.servlets.api;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

public class APIHelperServlet extends HttpServlet {


    public String getBody (HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = request.getReader();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    public String[] getSubURI (HttpServletRequest req) {
        return (req.getRequestURI().substring(1)).split("/");
    }

    public boolean compareSubURITo (HttpServletRequest req, int index, String check, String check2) {
        String[] uri = getSubURI(req);
        return uri[index].equalsIgnoreCase(check) || uri[index].equalsIgnoreCase(check2);
    }

    public boolean compareSubURITo (HttpServletRequest req, int index, String check) {
        String[] uri = getSubURI(req);
        return uri[index].equalsIgnoreCase(check);
    }

    public boolean subURIisInt (HttpServletRequest req, int index) {
        return getSubURI(req)[index].matches("\\d++");
    }
}
