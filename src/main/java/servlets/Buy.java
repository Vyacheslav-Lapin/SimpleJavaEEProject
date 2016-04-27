package servlets;

import dao.interfaces.GunDao;
import dao.mySql.MySqlGunDao;

import javax.annotation.Resource;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

import static java.util.Optional.ofNullable;

@WebServlet("/buyGun")
public class Buy extends HttpServlet {

    @Resource(name = "jdbc/TestDB")
    private DataSource dataSource;

    public static final String GUN = "gun";

    private GunDao gunDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        gunDao = MySqlGunDao.from(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ofNullable(request.getParameter("id"))
                .map(Integer::parseInt)
                .flatMap(gunDao::getGunById)
                .ifPresent(gun -> request.setAttribute(GUN, gun));

        //noinspection InjectedReferences
        request.getRequestDispatcher("/buy/index.jsp").forward(request, response);
    }
}
