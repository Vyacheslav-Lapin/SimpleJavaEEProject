<%@ page import="dao.mySql.MySqlGunDao" %>
<%@ page import="model.Gun" %>
<%@ page import="javax.annotation.Resource" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%!
    @Resource(name = "jdbc/TestDB")
    private DataSource dataSource;

    private MySqlGunDao gunDao;

    @Override
    public void jspInit() {
        gunDao = MySqlGunDao.from(dataSource);
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hello, World!</title>
    <link rel="stylesheet" href="/css/style.css"/>
</head>
<body>
<h1>Добро пожаловать!</h1>

<table class="bordered">
    <tr>
        <th>Название модели</th>
        <th>Калибр</th>
    </tr>
    <%
        //          GunDao gunDao = (GunDao) application.getAttribute(GUN_DAO);
        for (Gun gun : gunDao.getAllGuns()) {

    %>
    <tr>
        <td><a href="/buyGun?id=<%=gun.getId()%>"><%=gun.getName()%>
        </a></td>
        <td><%=gun.getCaliber()%>
        </td>
    </tr>
    <%}%>
</table>

</body>
</html>
