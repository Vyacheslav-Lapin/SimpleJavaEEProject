<%@ page import="java.sql.Date" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.Period" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%!
    private int getAge(ResultSet rs, String field) {
        // TODO: 4/25/2016 подумать как обозначить тип поля
        try {
            return getAge(rs.getDate(field));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int getAge(Date date) {
        return Period.between(date.toLocalDate(), LocalDate.now())
                .getYears();
    }
%>
<html>
<head>
    <title>GetGuns</title>
</head>
<body>

<sql:setDataSource var="snapshot" dataSource="jdbc/TestDB"/>

<sql:query dataSource="${snapshot}" var="result">
    SELECT id, first_name, last_name, dob from Person;
</sql:query>

<table border="1" width="100%">
    <tr>
        <th>Person ID</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Age</th>
    </tr>

    <c:forEach var="row" items="${result.rows}">
        <tr>
            <td>${row.id}</td>
            <td>${row.first_name}</td>
            <td>${row.last_name}</td>
                <%--<td>${row.dob}</td>--%>
            <td><%=getAge((ResultSet) pageContext.getAttribute("row"), "dob")%></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
